export async function runPythonCode(editor: any, stage: string, capturedPrints: string[]) {
    // @ts-ignore
    const runner = new BrythonRunner({
        stderr: {
            write(content: string) {
                console.error('StdErr: ' + content);
            },
            flush() {},
        },
        stdout: {
            write(content: string) {
                capturedPrints.push(content);  
            },
            flush() {},
        },
    });
    const stageCode = stage.split('\n').map((line: String) => `    ${line}`).join('\n');
    const userCodeIndented = editor.getValue().split('\n').map((line: String) => `    ${line}`).join('\n');
    const finalCode = `${gamePre}\n${stageCode}\n${initialCode}\n${userCodeIndented}\n${gamePost}`; // TODO REMOVE THIS
    await runner.runCode(finalCode);
    }

    var gamePre = 
    `
    import copy
    import inspect
    import json

    class Character:
        def __init__(self, data):
            # 세팅값
            self.fps = 30
            self.go_time = 2.0
            self.go_cannot_time = 0.5
            self.turn_time = 0.3
            self.player_input_line = 199
            
            
            # 초기값
            self.data = data
            
            # 결과값
            self.frames = []

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
                new_x = now_x + 2
                new_x_middle = now_x + 1
            elif self.data["player"]["dir"] == "left":
                new_x = now_x - 2
                new_x_middle = now_x - 1
            elif self.data["player"]["dir"] == "up":
                new_y = now_y + 2
                new_y_middle = now_y + 1
            elif self.data["player"]["dir"] == "down":
                new_y = now_y - 2
                new_y_middle = now_y - 1
            
            # 맵 밖으로 나가는지 체크
            if new_x < 0 or new_y < 0 or new_y >= len(self.data["stage"]["tile"]) or new_x >= len(self.data["stage"]["tile"][0]):
                return False
            
            # 이동가능지역 체크  0:이동불가 1:이동가능 2:벽
            if self.data["stage"]["tile"][new_y_middle][new_x_middle] == 2:
                return False
            
            return self.data["stage"]["tile"][new_y][new_x] == 1
        
        def decision_dir(self, directions): # 회전 후 방향 결정
            dir_index = directions.index(self.data["player"]["dir"]) 
            new_dir_index = (dir_index + 1) % len(directions)

            return directions[new_dir_index]

        def move(self, dx, dy, line_num):
            new_x = self.data["player"]["pos"][0] + dx
            new_y = self.data["player"]["pos"][1] + dy
            
            # 플레이어 이동
            self.data["player"]["pos"] = [new_x, new_y]
            # 결과값 저장
            self.frames.append({"id": len(self.frames), "status":0, "line_num":line_num - self.player_input_line, "player": copy.deepcopy(self.data["player"]), "item_list": []})

        def turn_half(self, line_num): 
            total_frames = int(self.turn_time * self.fps)

            for _ in range(int(total_frames/2)):
                self.frames.append({
                    "id": len(self.frames), 
                    "status":0, 
                    "line_num":line_num - self.player_input_line,
                    "player": copy.deepcopy(self.data["player"]), 
                    "item_list": []
                    })
                
        def go(self, line_num):
            if self.can_move():
                # 총프레임수 = 이동시간(초) * 초당프레임수
                total_frames = int(self.go_time * self.fps)
                if self.data["player"]["dir"] == "right":
                    for _ in range(total_frames):
                        self.move(2/total_frames, 0, line_num)
                elif self.data["player"]["dir"] == "left":
                    for _ in range(total_frames):
                        self.move(-2/total_frames, 0, line_num)
                elif self.data["player"]["dir"] == "up":
                    for _ in range(total_frames):
                        self.move(0, 2/total_frames, line_num)
                elif self.data["player"]["dir"] == "down":
                    for _ in range(total_frames):
                        self.move(0, -2/total_frames, line_num)
            else:
                total_frames = int(self.go_cannot_time * self.fps)
                # 결과값 저장  10:이동불가 말풍선
                for _ in range(total_frames):
                    self.frames.append({
                        "id": len(self.frames), 
                        "status":0, "line_num":line_num - self.player_input_line, 
                        "player": {**copy.deepcopy(self.data["player"]), 
                        "status": 10}, "item_list": []
                        })
        

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

        def check_goal(self): 
            if self.frames:
                last_player_pos = [round(pos) for pos in self.frames[-1]["player"]["pos"]]
            else:
                last_player_pos = [round(pos) for pos in self.data["player"]["pos"]]
            
            for goal in self.data["stage"]["goal_list"]:
                if goal["type"] == "target" and goal["pos"] == last_player_pos:
                    self.frames.append({
                        "id": len(self.frames), 
                        "status": 1, 
                        "line_num": 0,
                        "player": copy.deepcopy(self.data["player"]), 
                        "item_list": []
                        })
                    return True
                    
            return False

        def get_frames(self):
            return self.frames

    def go():
        print("go")
        if(hero.check_goal()):
            return
        hero.go(inspect.currentframe().f_back.f_lineno)

    def turnRight():
        print("turnRight")
        if(hero.check_goal()):
            return
        hero.turnRight(inspect.currentframe().f_back.f_lineno)
        
    def turnLeft():
        print("turnLeft")
        if(hero.check_goal()):
            return
        hero.turnLeft(inspect.currentframe().f_back.f_lineno)

    def check_goal():
        hero.check_goal()

    `;

    var initialCode =
    `
    hero = Character(stage)
    `;
        
    var gamePost = 
    `
    check_goal()

    frames_json = json.dumps(hero.get_frames())
    print(frames_json)
    `;
