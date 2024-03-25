declare global {
    interface Window {
      pyodide: any;
      loadPyodide: any;
    }
  }
  
  export async function loadPyodide() {
    if (!window.pyodide) {
      await new Promise<void>((resolve, reject) => {
        const script = document.createElement('script');
        script.src = '/pyodide.js'; 
        script.onload = () => resolve();
        script.onerror = reject;
        document.body.appendChild(script);
      });
  
      window.pyodide = await window.loadPyodide({
        indexURL: "/pyodide/" 
      });
    }
    return window.pyodide;
  }
  
  export async function runPythonCode2(pyodide: any) {
    try {
      let result = await pyodide.runPythonAsync(
        `
        import copy
        import inspect
        import json
        import sys
        import datetime
        
        class Character:
            def __init__(self, data):
                # 세팅값
                self.fps = 30
                self.go_time = 2.0
                self.go_cannot_time = 0.5
                self.turn_time = 0.3
                self.hit_bomb_time = 0.5
                self.set_time= 0.5
                self.set_cannot_time= 0.5
                self.wait_time = 0.5
        
                # 상태값
                self.hero_die_status = 9
                self.hero_cannot_move_status = 10
                self.hero_hit_bomb_status = 11
                self.hero_do_set_status = 12
                self.hero_miss_dir_status = 13
                self.hero_miss_item_status = 14
        
                self.item_off_status = 0
                self.item_on_status = 1
        
                # 초기값s
                self.data = data
                
                # 결과값
                self.frames = []
        
            def frame_append(self, line_num):
                self.frames.append({
                    "id": len(self.frames), 
                    "status": 0, 
                    "line_num": line_num,
                    "player": self.make_player(), 
                    "item_list": self.make_item_list()
                    })
        
            def make_player(self):
                return {
                    "pos": [self.data["player"]["pos"][0], self.data["player"]["pos"][1]],
                    "dir": self.data["player"]["dir"], 
                    "hp": self.data["player"]["hp"],
                    "status": self.data["player"]["status"],
                    "food_count": self.data["player"]["food_count"],
                    "rocket_parts_count": self.data["player"]["rocket_parts_count"]
                }
        
            def make_item_list(self):
                item_list = [] 
                for item in self.data["item_list"]:
                    if "status" in item:
                        item_list.append(
                            item["status"]
                          )
                return item_list
                    
            def handle_item_status(self, target_id, status): # 아이템 리스트 두 곳 에서 아이템 status 변경
                for item in self.data['stage']['init_item_list']:
                    if item['id'] == target_id:
                        item['status'] = status 
        
                for item in self.data['item_list']:
                    if item['id'] == target_id:
                        item['status'] = status
        
            def handle_laser_status(self, target_id): # 레이저 상태 변경
                for item in self.data['stage']['init_item_list']:
                    if item['id'] == target_id:
                        item["status"] = 1 if item["status"] == 0 else 0
                
                for item in self.data['item_list']:
                    if item['id'] == target_id:
                        item["status"] = 1 if item["status"] == 0 else 0
        
            def handle_switch_status(self): # 스위치 상태 변경 (밟혀있던거 다 올라오도록)
                status_mapping = {0: 2, 1: 3}
                target_drop_switch_id = []
                target_laser_switch_id = []
        
                for item in self.data['stage']['init_item_list']:
                    if item['type'] == 'drop_switch':
                        item['status'] = status_mapping.get(item['status'], item['status'])
                        target_drop_switch_id.append(item['id'])
                    elif item['type'] == 'laser_switch':
                        item['status'] = 1
                        target_laser_switch_id.append(item['id'])
                
                for item in self.data['item_list']:
                    if item['id'] in target_drop_switch_id:
                        item['status'] = status_mapping.get(item['status'], item['status'])
                    elif item['id'] in target_laser_switch_id:
                        item['status'] = 1
        
            def set_fail(self, status, line_num):
                total_frames = int(self.set_cannot_time * self.fps)
                self.data["player"]["status"] = status
                for _ in range(total_frames):
                    self.frames.append({
                        "id": len(self.frames), 
                        "status": 0, 
                        "line_num": line_num - start_line, 
                        "player": self.make_player, 
                        "item_list": self.make_item_list()
                        })
                self.data["player"]["status"] = 0
                    
            def set_success(self, line_num):
                total_frames = int(self.set_time * self.fps)
                self.data["player"]["status"] = self.hero_do_set_status
                for _ in range(total_frames):
                    self.frames.append({
                        "id": len(self.frames), 
                        "status": 0, 
                        "line_num": line_num - start_line, 
                        "player": self.make_player(), 
                        "item_list": self.make_item_list()
                        })
                self.data["player"]["status"] = 0
        
            def can_move(self):
                now_x = int(round(self.data["player"]["pos"][0]))
                now_y = int(round(self.data["player"]["pos"][1]))
                new_x = now_x
                new_y = now_y
                new_x_middle = now_x
                new_y_middle = now_y
                
                # 이동할 좌표 계산
                # 기본 이동 2칸씩 [new_x, new_y]
                # 중간지점 [new_x_middle, new_y_middle]
                if self.data["player"]["dir"] == "right":
                    new_x = round(now_x + 2)
                    new_x_middle = round(now_x + 1)
                elif self.data["player"]["dir"] == "left":
                    new_x = round(now_x - 2)
                    new_x_middle = round(now_x - 1)
                elif self.data["player"]["dir"] == "up":
                    new_y = round(now_y - 2)
                    new_y_middle = round(now_y - 1)
                elif self.data["player"]["dir"] == "down":
                    new_y = round(now_y + 2)
                    new_y_middle = round(now_y + 1)
                
                # 맵 밖으로 나가는지 체크
                if new_x < 0 or new_y < 0 or new_y >= len(self.data["stage"]["tile"]) or new_x >= len(self.data["stage"]["tile"][0]):
                    return 1
        
                # item_list 로 중간위치 체크
                for item in self.data['stage']['init_item_list']:
                    if item['type'] == 'bomb' and item['status'] == self.item_on_status and [new_x_middle, new_y_middle] == item['pos']: # 폭탄 밟음
                        return 2
                    
                    elif item['type'] == 'laser' and item['status'] == self.item_on_status: # 레이저 닿음
                        player_middle_pos_rounded = [new_x_middle, new_y_middle]
                        
                        if (item['dir'] == 'h' and player_middle_pos_rounded[1] == item["pos_start"][1] and # 가로레이저
                            item["pos_start"][0] <= player_middle_pos_rounded[0] <= item["pos_end"][0]):
                                return 3
        
                        elif (item['dir'] == 'v' and player_middle_pos_rounded[0] == item["pos_start"][0] and # 세로 레이저
                            item["pos_start"][1] <= player_middle_pos_rounded[1] <= item["pos_end"][1]):
                                return 3
                # 중간위치 벽 체크 
                if self.data["stage"]["tile"][new_y_middle][new_x_middle] == 2:
                    return 1
                
                return 0
            
            def decision_dir(self, directions): # 회전 후 방향 결정
                dir_index = directions.index(self.data["player"]["dir"]) 
                new_dir_index = (dir_index + 1) % len(directions)
        
                return directions[new_dir_index]
            
            def do_move(self, total_frames, line_num):
                if self.data["player"]["dir"] == "right":
                    for i in range(int(total_frames)):
                        if i == int(total_frames)//2:
                            self.handle_switch_status()
                        self.move(2/int(self.go_time * self.fps), 0, line_num)
                elif self.data["player"]["dir"] == "left":
                    for i in range(int(total_frames)):
                        if i == int(total_frames)//2:
                            self.handle_switch_status()
                        self.move(-2/int(self.go_time * self.fps), 0, line_num)
                elif self.data["player"]["dir"] == "up":
                    for i in range(int(total_frames)):
                        if i == int(total_frames)//2:
                            self.handle_switch_status()
                        self.move(0, -2/int(self.go_time * self.fps), line_num)
                elif self.data["player"]["dir"] == "down":
                    for i in range(int(total_frames)):
                        if i == int(total_frames)//2:
                            self.handle_switch_status()
                        self.move(0, 2/int(self.go_time * self.fps), line_num)        
        
            def move(self, dx, dy, line_num):
                new_x = self.data["player"]["pos"][0] + dx
                new_y = self.data["player"]["pos"][1] + dy
                
                # 플레이어 이동
                self.data["player"]["pos"] = [new_x, new_y]
                # 결과값 저장
                self.frame_append(line_num - start_line)
        
            def turn_half(self, line_num): 
                total_frames = int(self.turn_time * self.fps)
        
                for _ in range(int(total_frames/2)):
                    self.frame_append(line_num - start_line)
                    
            def go(self, line_num):
                move_ability = self.can_move()
        
                if move_ability == 0:
                    # 총프레임수 = 이동시간(초) * 초당프레임수
                    total_frames = int(self.go_time * self.fps)
                    self.do_move(total_frames, line_num)
        
                elif move_ability == 1:
                    total_frames = int(self.go_cannot_time * self.fps)
                    # 결과값 저장  10:이동불가 말풍선
                    self.data["player"]["status"] = self.hero_cannot_move_status
                    for _ in range(total_frames):
                        self.frame_append(line_num - start_line)
                    self.data["player"]["status"] = 0
        
                elif move_ability == 2: # 중간에 폭탄밟음
                    move_frames = int(self.go_time * self.fps)
                    action_frames = int(self.hit_bomb_time * self.fps)
                    self.do_move(move_frames/2, line_num)
        
                    self.handle_item_status('bomb', self.item_off_status)
        
                    # for item in self.data['item_list']:
                    #     if item['type'] == 'bomb' and [round(pos) for pos in self.data["player"]["pos"]] == item['pos']:
                    #         item["status"] = 0
                            
                    self.data["player"]["hp"] -= 50
                    if self.data["player"]["hp"] <= 0: # 플레이어 사망
                        return
        
                    self.data["player"]["status"] = self.hero_hit_bomb_status
                    for _ in range(action_frames):
                        self.frame_append(line_num - start_line)
                    self.data["player"]["status"] = 0
        
                    self.do_move(move_frames/2, line_num)
        
                    ############################################### 여기까지 수정함
        
                elif move_ability == 3: # 중간에 레이져 밟음 ToDo: 나중에 디버깅 및 수정 필요
                    move_frames = int(self.go_time * self.fps)
                    self.do_move(move_frames/2, line_num)
                    self.data["player"]["hp"] = 0
                    return
        
        
        
            def turnRight(self, line_num): 
                self.turn_half(line_num)
        
                directions = ["right", "down", "left", "up"] 
                self.data["player"]["dir"] = self.decision_dir(directions)
        
                self.turn_half(line_num)
        
            def turnLeft(self, line_num):
                self.turn_half(line_num)
        
                directions = ["right", "up", "left", "down"] 
                self.data["player"]["dir"] = self.decision_dir(directions)
        
                self.turn_half(line_num)
        
            def set(self, item, line_num):
                dir_offsets = {
                    'up': (0, -2),
                    'down': (0, 2),
                    'left': (-2, 0),
                    'right': (2, 0)
                }
        
                item_types = ['solid_propellant', 'liquid_fuel', 'engines']
                player_dir = self.data["player"]["dir"]
                player_pos = [round(pos) for pos in self.data["player"]["pos"]]
        
                offset = dir_offsets.get(player_dir, (0, 0))
                target_pos = (player_pos[0] + offset[0], player_pos[1] + offset[1])
        
                for item_data in self.data["stage"]["init_item_list"]:
                    if (item_data["type"] in item_types and
                        tuple(item_data["pos"]) == target_pos and
                        item_data["status"] == self.item_off_status):
        
                        if item_data["require_dir"] == player_dir:
        
                            if item_data["name"] == item:
                                self.handle_item_status(item_data["type"], self.item_on_status)
                                self.set_success(line_num)
                                return
                            else: 
                                self.set_fail(self.hero_miss_item_status, line_num)
                                return
                        else:
                            self.set_fail(self.hero_miss_dir_status, line_num)
                            return
        
                self.set_fail(self.hero_miss_dir_status, line_num)
                
        
            def check_goal(self, line_num): # 목적지 상태 (상호작용) 체크
                if self.data["player"]["hp"] <= 0: # 플레이어 사망
                    self.data["player"]["status"] = self.hero_die_status
                    self.frame_append(0)
                
                player_pos = [round(pos) for pos in self.data["player"]["pos"]]
                    
                for item in self.data['stage']['init_item_list']: # 아이템리스트 상호작용 시작
                    if item['type'] == 'food' and player_pos == item['pos'] and item['status'] == self.item_on_status: # food 획득
                        self.data["player"]["food_count"] += 1
                        self.handle_item_status(item['id'], self.item_off_status)
                        self.frame_append(line_num - start_line)
        
                    elif item['type'] == 'rocket_parts' and player_pos == item['pos'] and item['status'] == self.item_on_status: # 로켓부품 획득
                        self.data["player"]["rocket_parts_count"] += 1
                        self.handle_item_status(item['id'], self.item_off_status)
                        self.frame_append(line_num - start_line)
        
                    elif item['type'] == 'laser_switch' and player_pos == item['pos'] and self.frames[-2]["player"]["pos"] != self.data["player"]["pos"]:
                        self.handle_item_status(item['id'], 0)
                        for laser_id in item["laser_id"]:  # 스위치 작동
                            for laser in self.data['stage']['init_item_list']:
                                if laser['id'] == laser_id:
                                    self.handle_laser_status(laser_id)
                                    self.frame_append(line_num - start_line)
                                    
                    elif item['type'] == 'laser' and item['status'] == 1: # 레이저 닿음
                        player_pos_rounded = round(self.data["player"]["pos"][0]), round(self.data["player"]["pos"][1])
                        
                        if (item['dir'] == 'h' and player_pos_rounded[1] == item["pos_start"][1] and # 가로레이저
                            item["pos_start"][0] <= player_pos_rounded[0] <= item["pos_end"][0]):
                                self.data["player"]["hp"] = 0
                                self.data["player"]["status"] = self.hero_die_status
                                self.frame_append(0)
        
                        elif (item['dir'] == 'v' and player_pos_rounded[0] == item["pos_start"][0] and # 세로 레이저
                            item["pos_start"][1] <= player_pos_rounded[1] <= item["pos_end"][1]):
                                self.data["player"]["hp"] = 0
                                self.data["player"]["status"] = self.hero_die_status
                                self.frame_append(0)    
                                  
                    elif item['type'] == 'drop_switch' and player_pos == item['pos'] and self.frames[-2]["player"]["pos"] != self.data["player"]["pos"] and all(not (i['type'] == item['drop_type'] and i['pos'] == item['pos_drop']) for i in self.data["stage"]["init_item_list"]): # 드롭 스위치 작동
                            if item['count'] > 0:
                                item['count'] -= 1
                                item['status'] = 0 # 스위치 밟힘, 카운트가 남아서 아이템이 등장함
                            
                            else:
                                item['status'] = 1 # 스위치는 밟힘, 카운트가 없어서 아이템이 등장하지 않음
                            self.frame_append(line_num - start_line)
        
                    elif item['type'] == 'drop_switch' and player_pos == item['pos_drop'] and item['status'] == 2:
                        self.data["player"]["rocket_parts_count"] += 1
                        item['status'] = 3
                        self.frame_append(line_num - start_line)
        
                wating_frame = int(self.wait_time * self.fps)
                for _ in range(wating_frame):
                    self.frame_append(line_num - start_line)
                # 아이템리스트 상호작용 끝
                                       
                # 게임 클리어 체크
                all_achieved = True
                for goal in self.data["stage"]["goal_list"]:
                    if not self.check_goal_achieved(goal, line_num):
                        all_achieved = False
                        break  
        
                if all_achieved:
                    self.frames.append({
                        "id": len(self.frames),
                        "status": 1,
                        "line_num": 0,
                        "player": self.make_player(),
                        "item_list": self.make_item_list()
                    })
                    
            def check_goal_achieved(self, goal, line_num): # 게임 목표 체크 함수
                if goal['goal'] == 'target':
                    return goal["pos"] == [round(pos) for pos in self.data["player"]["pos"]]
                elif goal['goal'] == 'item' and goal['type'] == 'food':
                    return goal["count"] <= self.data["player"]["food_count"]
                elif goal['goal'] == 'item' and goal['type'] == 'rocket_parts':
                    return goal["count"] <= self.data["player"]["rocket_parts_count"]
                elif goal['goal'] == 'line':
                    return goal["count"] >= line_num - start_line
                elif goal['goal'] == 'set':
                    count = sum(1 for item in self.data["stage"]['init_item_list'] if item["type"] == goal['type'] and item["status"] == 1)
                    return count >= goal['count']
        
                return False 
            
            def check_game_status(self): # 게임 종료 체크
                if self.frames:
                    if self.frames[-1]["player"]["status"] == self.hero_die_status or self.frames[-1]["status"] == 1:
                        return True
        
            def get_frames(self):
                return self.frames
        
        def go(num = 1):
            for _ in range(num):
                # print("go")
                if(hero.check_game_status()):
                    return
                hero.go(inspect.currentframe().f_back.f_lineno)
                hero.check_goal(inspect.currentframe().f_back.f_lineno)
        
        def turnRight(num = 1):
            for _ in range(num):
                # print("turnRight")
                if(hero.check_game_status()):
                    return
                hero.turnRight(inspect.currentframe().f_back.f_lineno)
                hero.check_goal(inspect.currentframe().f_back.f_lineno)
            
        def turnLeft(num = 1):
            for _ in range(num):
                # print("turnLeft")
                if(hero.check_game_status()):
                    return
                hero.turnLeft(inspect.currentframe().f_back.f_lineno)
                hero.check_goal(inspect.currentframe().f_back.f_lineno)
        
        def set(item):
            # print("set("+item+")")
            if(hero.check_game_status()):
                return
            hero.set(item, inspect.currentframe().f_back.f_lineno)
            hero.check_goal(inspect.currentframe().f_back.f_lineno)
        
        stage = {
            "stage" : {
                "map" : 1,
                "step" : "1-2",
                "diff" : "Hard",
                "level" : 3,
                "tile" : [
                    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                    [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],
                    [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0],
                    [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0],
                    [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0],
                    [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0],
                    [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0],
                    [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],
                    [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0],
                    [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0],
                    [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0],
                    [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0],
                    [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0],
                    [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],
                    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
                ],
                "init_item_list" : [
                    {"id":0, "type": "laser_switch", "pos": [3,5], "laser_id": [4,5,8,9], "status": 1},
                    {"id":1, "type": "laser_switch", "pos": [3,9], "laser_id": [4,5,8,9], "status": 1},
                    {"id":2, "type": "laser_switch", "pos": [29,5], "laser_id": [6,7,10,11], "status": 1},
                    {"id":3, "type": "laser_switch", "pos": [29,9], "laser_id": [6,7,10,11], "status": 1},
                    {"id":4, "type": "laser", "dir": "h", "pos_start": [6,6], "pos_end": [8,6], "status": 1},
                    {"id":5, "type": "laser", "dir": "h", "pos_start": [12,6], "pos_end": [14,6], "status": 1},
                    {"id":6, "type": "laser", "dir": "h", "pos_start": [18,6], "pos_end": [20,6], "status": 1},
                    {"id":7, "type": "laser", "dir": "h", "pos_start": [24,6], "pos_end": [26,6], "status": 1},
                    {"id":8, "type": "laser", "dir": "h", "pos_start": [6,8], "pos_end": [8,8], "status": 1},
                    {"id":9, "type": "laser", "dir": "h", "pos_start": [12,8], "pos_end": [14,8], "status": 1},
                    {"id":10, "type": "laser", "dir": "h", "pos_start": [18,8], "pos_end": [20,8], "status": 1},
                    {"id":11, "type": "laser", "dir": "h", "pos_start": [24,8], "pos_end": [26,8], "status": 1},
                    {"id":12, "type": "drop_switch", "pos": [7,5], "pos_drop": [9,3], "count": 3, "drop_type": "rocket_parts", "status": 3},
                    {"id":13, "type": "drop_switch", "pos": [15,5], "pos_drop": [13,3], "count": 3, "drop_type": "rocket_parts", "status": 3},
                    {"id":14, "type": "drop_switch", "pos": [21,5], "pos_drop": [21,3], "count": 3, "drop_type": "rocket_parts", "status": 3},
                    {"id":15, "type": "drop_switch", "pos": [23,5], "pos_drop": [23,3], "count": 3, "drop_type": "rocket_parts", "status": 3},
                    {"id":16, "type": "drop_switch", "pos": [7,11], "pos_drop": [7,9], "count": 3, "drop_type": "rocket_parts", "status": 3},
                    {"id":17, "type": "drop_switch", "pos": [11,11], "pos_drop": [13,11], "count": 3, "drop_type": "rocket_parts", "status": 3},
                    {"id":18, "type": "drop_switch", "pos": [17,9], "pos_drop": [19,11], "count": 3, "drop_type": "rocket_parts", "status": 3},
                    {"id":19, "type": "drop_switch", "pos": [23,9], "pos_drop": [23,11], "count": 3, "drop_type": "rocket_parts", "status": 3},
                    {"id":20, "type": "bomb", "pos": [27,4], "status": 1},
                    {"id":21, "type": "bomb", "pos": [17,10], "status": 1}
                ],
                "goal_list" : [
                    {"goal": "item", "type": "rocket_parts", "count": 24},
                    {"goal": "line", "count": 200}
                ]
            },
            "player" : {
                "pos" : [1,7],
                "dir" : "right", 
                "hp" : 100,
                "status" : 0,
                "food_count" : 0,
                "rocket_parts_count" : 0
            },
            "item_list" : [
                {"id":0, "status": 1},
                {"id":1, "status": 1},
                {"id":2, "status": 1},
                {"id":3, "status": 1},
                {"id":4, "status": 1},
                {"id":5, "status": 1},
                {"id":6, "status": 1},
                {"id":7, "status": 1},
                {"id":8, "status": 1},
                {"id":9, "status": 1},
                {"id":10, "status": 1},
                {"id":11, "status": 1},
                {"id":12, "status": 3},
                {"id":13, "status": 3},
                {"id":14, "status": 3},
                {"id":15, "status": 3},
                {"id":16, "status": 3},
                {"id":17, "status": 3},
                {"id":18, "status": 3},
                {"id":19, "status": 3},
                {"id":20, "status": 1},
                {"id":21, "status": 1}
            ]
        }
        
        
        
        # Create a character and move it around the map
        hero = Character(stage)
        
        # player input start  
        start_line = inspect.currentframe().f_lineno
        for i in range(4):
            go()
            turnLeft()
        for i in range(2):
            go(3)
            turnLeft()
            go()
            turnRight()
            for i in range(6):
                go()
                turnLeft()
                go()
                turnLeft()
                go()
            turnRight()
            go(2)
            turnRight()
            for i in range(6):
                go()
                turnLeft()
                go()
                turnLeft()
                go()
            turnRight()
            go()
            turnRight()
        go(8)
        turnRight()
        go()
        turnRight(2)
        go()
        turnLeft()
        for i in range(2):
            go(2)
            turnRight()
            go()
            turnRight()
            go()
            turnLeft(2)
            for i in range(3):
                go(2)
                turnRight()
                go()
                turnRight()
                go(2)
                turnLeft(2)
                go(2)
                turnLeft()
                go()
                turnLeft()
                go(2)
                turnRight(2)
            go()
            turnLeft()
            go(2)
            turnRight()
            go()
            turnRight(2)
            for i in range(3):
                go()
                turnRight()
                go()
                turnRight()
                go()
                turnLeft(2)
                go()
                turnLeft()
                go()
                turnLeft()
                go()
                turnRight(2)
            go()
            turnLeft()
            go()
            turnLeft()
            go()
                
        # Print the list of frames with character's positions
        frames = hero.get_frames() 

        json.dumps(frames)
        `
      );
      console.log(result);
    } catch (error) {
      console.error('Python 코드 실행 중 오류 발생:', error);
    }
  }
