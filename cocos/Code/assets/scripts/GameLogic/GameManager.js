


const Controller = require("../Controller");
const SoundManger = require("./SoundManager");
const Loader = require("./Loader");
const SoundManager = require("./SoundManager");

cc.Class({
    extends: cc.Component,

    properties: {

        // DEBUG
        btn_x_plus : cc.Button,
        btn_x_minus : cc.Button,
        btn_y_plus : cc.Button,
        btn_y_minus : cc.Button,

        btn_zoom_in : cc.Button,
        btn_zoom_out : cc.Button,

        btnMan: cc.Button,
        btnManSuit : cc.Button,
        btnWoman : cc.Button,
        btnWomanSuit : cc.Button,

        // DEBUG_END

        // 로딩 화면
        loadingBG: cc.Node,

        isLoaded : false,
        isCameraShaked : false,

        // 코드가 현재 플레이중인 상태를 저장하는 변수
        isPlay: false,

        // 메인 카메라
        camera : cc.Camera,

        // 게임 시작전 강제로 포지션 오프셋값 주는 배열 변수
        mapOffset : {
            default:[],
        },

        // 현재 읽고 있는 id 값 뜻함.
        idx: {
            default: 0,
            visiable : false
        },

        /**
         * 실제 사용하는 객체 모음
         */
        // 맵 객체
        gameMap: cc.TiledMap,
        // 플레이어 객체
        player : cc.Node,
        // 우주선 파츠 부모 객체
        //파츠는 해당 객체 아래에 추가됨.
        spaceShip : cc.Node,
        // 배경화면 뿌려지는곳.
        bgNode: cc.Node,


        //플레이어, 맵을 제외한 월드에 위치 하는 객체를 담는 배열
        item: [],

        // 드롭 아이템 리스트
        dropItemList: [],
        // 드롭스위치 리스트
        dropSwitchList:[],

        // 게임 최종도착지
        goalObject: cc.Node,

        // 객체노드.
        objectParent: cc.Node,

        effectParent: cc.Node,

        audioManager: cc.Node,



        // 픽업 이펙트 담는 리스트
        pList : [],
        pIdx : 0,

        // 회복 이펙트 담는 리스트
        hList : [],
        hIdx : 0,

        // 이펙트 담는 리스트
        expList: [],
        expIdx : 0,

        // 아이템 떨어질때 이펙트 리스트
        dropEffectList: [],
        dropEffectIdx : 0,


        // 폭발 이펙트 중인지 체크
        isPlayExplosion : false,


        timeCheck : 0,
        startTime :0,

        isSceneLoaded : false,



        print_ui_object: cc.Node,
        print_ui_label: cc.Label,


        /**스테이지 2 에서 사용되는 정보 */
        print_array : [],


        /** 2스테이지에서 보여질 객체 처리 */
        bomb_sprites : {
            default: [],
        },

        /** 2스테이지에서 2키 프레임 보다 작을경우 loop됨.  */
        isShowVariation : false,
        
        bomb_sprite_max_range : 0,

        bomb_sprite_id : null,


        /**
         * 3스테이지에서 사용하는 몬스터 객체 리스트
         */

        monster_list: [],
      
        /**
         * 몬스터 폭발 효과를 주기위한 행동.
         */
        monster_isDead : [],

        /**
         * 보스용
         */

        bossObject: cc.Node,
    },

    /**
     * MAP DEBUG Mode 
     */
    addBtn: function(){
        this.btnMan.node.on('click',this.btnManShow, this);
        this.btnManSuit.node.on('click',this.btnManSuitShow, this);
        this.btnWoman.node.on('click',this.btnWomanShow, this);
        this.btnWomanSuit.node.on('click',this.btnWomanSuitShow, this);


        this.btn_x_plus.node.on('click',this.PlusX, this);
        this.btn_x_minus.node.on('click',this.MinusX, this);
        this.btn_y_plus.node.on('click',this.PlusY, this);
        this.btn_y_minus.node.on('click',this.MinusY, this);

        this.btn_zoom_in.node.on("click",this.ZoomInDeubug, this);
        this.btn_zoom_out.node.on("click",this.ZoomOutDebug, this);
    },

    PlusX: function(){
         var x = this.camera.node.position.x;
         var y = this.camera.node.position.y;
         x += 50;

         this.camera.node.setPosition(cc.v3(x, y, 0)); // 여기서 x, y, z는 포지션의 좌표값입니다.
         console.log(this.camera.node.position.x + " " +this.camera.node.position.y );
    },
    PlusY: function(){
        var x = this.camera.node.position.x;
        var y = this.camera.node.position.y;
        y += 50;

        this.camera.node.setPosition(cc.v3(x, y, 0)); // 여기서 x, y, z는 포지션의 좌표값입니다.
        console.log(this.camera.node.position.x + " " +this.camera.node.position.y );
    },
    MinusX: function(){
        var x = this.camera.node.position.x;
        var y = this.camera.node.position.y;
        x -= 50;

        this.camera.node.setPosition(cc.v3(x, y, 0)); // 여기서 x, y, z는 포지션의 좌표값입니다.
        console.log(this.camera.node.position.x + " " +this.camera.node.position.y );
    },
    MinusY: function(){
        var x = this.camera.node.position.x;
        var y = this.camera.node.position.y;
        y -= 50;
        this.camera.node.setPosition(cc.v3(x, y, 0)); // 여기서 x, y, z는 포지션의 좌표값입니다.
        console.log(this.camera.node.position.x + " " +this.camera.node.position.y );
    },

    ZoomInDeubug: function(){
        this.camera.zoomRatio += 0.1;

        console.log("ratio => " + this.camera.zoomRatio);
    },

    ZoomOutDebug: function(){
        this.camera.zoomRatio -= 0.1;
        console.log("ratio => " + this.camera.zoomRatio);
    },


    btnManShow: function(){
        Controller.getInstance().setcharNumber(0);
    },
    btnManSuitShow: function(){
        Controller.getInstance().setcharNumber(1);
    },
    btnWomanShow: function(){
        Controller.getInstance().setcharNumber(2);
    },
    btnWomanSuitShow: function(){
        Controller.getInstance().setcharNumber(3);
    },


    /**
     * 게임에서 사용될 이미지로드 SingleTon 로드가 된걸 확인한 뒤
     * 로딩 화면을 감춰주는 Interval 생성
     */
    onLoad(){
        this.addBtn();
        
        // print_UI 초기화.
        this._InitPrintUI();

        this.loadingBG.active = true;
        // this.startTime = performance.now(); // 시작 시간 기록
        this.EffectInit();
        // this.InitGame();

        var self = this;
        var inter = setInterval(function(){
            if(Loader.getInstance().GetImage(0) != null){
                clearInterval(inter);
                self.InitGame();
            }
        },5);
    },

    start(){
        /**
         * 오디오 로드 테스함.
         * @type {number}
         */
        var self = this;
        var audioInter = setInterval(()=>{
            if(SoundManger.getInstance().IsLoadCheck() != null){
                clearInterval(audioInter);
            }
        },5);
    },

    /**
     * 큰 타일맵 로드시 맵이 짤려보여는 현상을 해결하기 위해 맵을 2초에 걸쳐 맵을 좌우로 이동시켜주는 효과
     * 해당 효과로 맵 랜더링 오류가 해결
     *
     * 2024-04-23 조금 딜레이 주며 수정
     * 카메라 offset 1 -> 5 수정.
     * 100-> 300
     * 200 -> 600
     */
    _TileMapShake: function(){
        var s = this;
        setTimeout(function(){
            s.CameraMoveX(2);
        },300);

        setTimeout(function(){

            s.CameraMoveX(-2);
            s.LoadingFadeOut();
        },600);
    },

    /**
     * mapShake
     * @param idx
     * @constructor
     */
    CameraMoveX : function(idx){
        const currentPosition = this.camera.node.position;
        const newXPosition = currentPosition.x - idx;

        var v3 = cc.v3(newXPosition, currentPosition.y, currentPosition.z);
        this.camera.node.setPosition(v3);
    },


    /**
     * 로딩 검은 화면 FadeOut 해주는 함수.
     * Init관련된건 이미 처리하고 Fade out 처리해줌.
     * @constructor
     */
    LoadingFadeOut: function(){
        var self = this;
        var offset = 128;

        this.loadingBG.active = false;
        this.isLoaded = true;
        Controller.getInstance().finalIndex = true;

       

        // 카메라 초기화
        // 최종 다되면 카메라 셋업.
        this.InitialCamera();
    },

   
    


    /**
     * 게임 초기화 해주는 함수입니다.
     * @constructor
     */
    InitGame: function(){
        var self = this;

        this.bomb_sprites = [
            [],
            [],
            [],
        ];

        this.mapOffset = [
            // T-1
            { x: 0, y: 2 },
            // T-2
            { x: 0, y: 1 },
            // 1-1-E
            { x: 0, y: 1 },
            { x: 0, y: 1 },
            { x: 0, y: 1 },

            // 1-1-N
            { x: 0, y: 1 },
            { x: 0, y: 1 },
            { x: 0, y: 1 },

            // 1-1-H
            { x: 0, y: 1 },
            { x: 0, y: 1 },
            { x: 0, y: 1 },


            // 1-2-E
            { x: 0, y: 1 },
            { x: 0, y: 1 },
            { x: 0, y: 1 },

            // 1-2-N
            { x: 0, y: 1 },
            { x: 0, y: 1 },
            { x: 0, y: 1 },

            // 1-2-H
            { x: 0, y: 1},
            { x: 0, y: 1 },
            { x: 0, y: -1 },

            // 1-3-E
            { x: 1, y: -2 },
            { x: 0, y: -1 },
            { x: -4, y: 1 },

            // 1-3-N
            { x: -1, y: -2 },
            { x: 0, y: -1 },
            { x: -4, y: 1 },

            //1-3-H
            { x: -1, y: -2 },
            { x: 0, y: -1 },
            { x: -4, y: 1 },



            // 2-1-E
            { x: 0, y: -1 },
            { x: 0, y: -1 },
            { x: 0, y: -1 },

            // 2-1-N
            { x: 0, y: -1 },
            { x: 0, y: -1 },
            { x: 0, y: -1 },

            //2-1-H
            { x: 0, y: -1 },
            { x: 0, y: -1 },
            { x: 0, y: -1 },


            // 2-2-E
            { x: -1, y: 1 },
            { x: 0, y: 1 },
            { x: -1, y: 1 },

            // 2-2-N
            { x: -1, y: 1 },
            { x: 0, y: 1 },
            { x: -1, y: 1 },

            //2-2-H
            { x: -1, y: 1 },
            { x: 0, y: 1 },
            { x: -1, y: 1 },
  
              
            // 2-3-E
            { x: 0, y: 1 },
            { x: -1, y: 1 },
            { x: 0, y: -1 },

            // 2-3-N
            { x: 0, y: 1 },
            { x: -1, y: 1 },
            { x: 0, y: -1 },

            //2-3-H
            { x: 0, y: 1 },
            { x: -1, y: 1 },
            { x: 0, y: -1 },



             // 3-1-E
             { x: 1, y: 2 },
             { x: -1, y: 1 },
             { x: 0, y: 1 },
 
             // 3-1-N
             { x: 1, y: 2 },
             { x: -1, y: 1 },
             { x: 0, y: 1 },
 
             // 3-1-H
             { x: 1, y: 2},
             { x: -1, y: 1 },
             { x: 0, y: 1 },
 
             // 3-2-E
             { x: 0, y: 2 },
             { x: 0, y: 0 },
             { x: 0, y: 1 },
 
             // 3-2-N
             { x: 0, y: 2 },
             { x: 0, y: 0 },
             { x: 0, y: 1 },
 
             // 3-2-H
             { x: 0, y: 2 },
             { x: 0, y: 0 },
             { x: 0, y: 1 },
   
               
             // 3-3-E
             { x: 0, y: 1 },
             { x: 0, y: 1 },
             { x: 0, y: 1 },
 
             // 3-3-N
             { x: 0, y: 1 },
             { x: 0, y: 1 },
             { x: 0, y: 1 },
 
             // 3-3-H
             { x: 0, y: 1 },
             { x: 0, y: 1 },
             { x: 0, y: 1 },


                     
             // 3-4-E
             { x: 0, y: 1 },
             { x: 0, y: 1 },
             { x: 0, y: 1 },
 
             // 3-4-N
             { x: 0, y: 1 },
             { x: 0, y: 1 },
             { x: 0, y: 1 },
 
             // 3-4-H
             { x: 0, y: 1 },
             { x: 0, y: 1 },
             { x: 0, y: 1 },

        ];
        this.loadInit();

    },
    

    /**
     * Point Array 정보 받는 곳.
     */
    GetPointArray: function(){
        var initData =  Controller.getInstance().getInitOjbectDatas();

        /** 
         * 예외 처리
         */
        if(initData == null || initData == '') return;
        if(initData.length < 1) return;


        for( var i = 0 ; i < initData.length; i++ ){
            if(initData[i].type === 'print_point') {
                this.print_array = initData[i].require_print;
                break;
            }

        }


    },



    /**
     *  Json 데이터가 로드가 정상적으로 되었는지 Interval을 이용하여 확인한다.
     */
    loadInit: function(){

        var self = this;
        // Interver 이용하여 로드 체크한다.
        var inter = setInterval(function(){

            if(Controller.getInstance().initJson != null){
                //self.EffectInit();

                if(self.IsBonusStage()){
                    var stageObject = Controller.getInstance().getInitStageData();
                    var step = stageObject.step;

                    self.bonusGameSceneLoaded(step);
                }
                else{
                    self.InitMap();
                    self.InitPlayer();
                    self.InitMonster();
                    self.InitBoss();


                    self.InitObject();

                    // 2스테이지이상 부터 사용하는 Print
                    self.GetPointArray();
                }
                clearInterval(inter);
            }

        }, 5);
    },


    /**
     * 현재 접근하는 스테이지가 보너스 스테이지에 해당하는지 체크.
     * 1-4 , 2-4 화면 전환.
     * @returns status_change true / none false
     */
    IsBonusStage: function(){
        var stageObject = Controller.getInstance().getInitStageData();
        var step = stageObject.step;

        if(step == "1-4"){
            return true;
        }
        else if(step == "2-4"){
            return true;
        }
        
        return false;
    },



    /**
     * 보너스게임 Scene Change 해주는것.
     * @param {step} SendInitData_Stage_step_값 
     */
    bonusGameSceneLoaded: function(step){
        if(step === "1-4" ) {
            cc.director.loadScene("minigame1");
        }
        else if(step === "2-4"){

        }
    },
  

    /**
     * 카메라 흔들림효과, 폭탄, 레이저 공격받았을시 사용.
     * @returns 
     */
    ShakeEffect: function() {
        if(this.isCameraShaked) return;
        this.isCameraShaked = true;

        var intensity = cc.v2(5, 0);

        // 초기 위치 저장
        const initialPos = this.camera.node.position.clone();
        // 일정 시간 동안 흔들림 애니메이션
        this.camera.node.runAction(
            cc.sequence(
                cc.moveBy(0.02, intensity),
                cc.moveBy(0.04, intensity.neg()),
                cc.moveBy(0.02, intensity.neg()),
                cc.moveBy(0.04, intensity.neg()),
                cc.moveTo(0, initialPos),
            )
        );
    },

    /**
     *  게임 Update 코드 상태값을 확인하여 코드를 동작시키는 함수
     *  0 값이면 동작하지않고,
     *  1 이면 동작하도록 Controller Status 값으로 확인.
     * @param dt
     */
    update(dt){
        if(!this.isLoaded) return;

        var status = Controller.getInstance().GetStatus();
        if(status){
            this.OnCodePlay();
        }
    },


    /**
     * 플레이어 초기화 해주는 함수 입니다.
     * @constructor
     */
    InitPlayer: function(){
        var initPlayerObject = Controller.getInstance().getInitPlayerData();

        var initPos = initPlayerObject.pos;
        var playerDir = initPlayerObject.dir;
        var initHp = initPlayerObject.hp;
        var playerStatus = initPlayerObject.status;
        var foodCount = initPlayerObject.food_count;
        var rocket_parts_count = initPlayerObject.rocket_parts_count;

        var self = this;

        var initvector = this.GVector(initPos[0],initPos[1]);

        this.player.getComponent("Player").Init(initvector, playerDir);
    },

    /**
     *
     * Json coordination -> Cocos game Coordination
     *
     * 실제 게임 벡터로 변환 해주는 함수
      * @param x 입력되는 X좌표 값입니다.
     *  @param y 입력되는 Y좌표 값입니다.
     * @constructor
     */
    GVector : function (x, y) {

        var lX = x * 1;
        var lY = y * -1;

        var stageObject = Controller.getInstance().getInitStageData();

        var step = stageObject.step;
        var diff = stageObject.diff;
        var level = stageObject.level;

        var gameLevel = this.ConvertGameLevel(step,diff,level);

        var v2 = cc.v2(
            ((lX + this.mapOffset[gameLevel].x) * Env.OFFSET_X) + Env.PLAYER_RADIO,
            ((lY - this.mapOffset[gameLevel].y) * Env.OFFSET_Y) - Env.PLAYER_RADIO
        );
        return v2;
    },


    /**
     * 보스 만드는곳.
     */
    InitBoss: function(){
        var stageObject = Controller.getInstance().getInitStageData();

        var step = stageObject.step;
        var level = stageObject.level

        if(step !== '3-4') return;
        if(level !== 3 ) return;

        var initJson = Controller.getInstance().getInitOjbectDatas();

            // 타입이 몬스터가 아닌경우 예외처리가 필요.
        this.MakeUpBoss(initJson[0]);
    },



    /**
     * 몬스터 최초 생성해주고, Init 처리하는 함수. 
     * 
     * @returns 
     */
    InitMonster: function(){
        var stageObject = Controller.getInstance().getInitStageData();

        var step = stageObject.step;
        var diff = stageObject.diff;
        var level = stageObject.level;

        var gameLevel = this.ConvertGameLevel(step,diff,level);


        // 스테이지 3 미만 접근 금지
        if(gameLevel < 56) return;
        if(gameLevel == 85 || gameLevel === 88 || gameLevel === 91 ) return;


        var initJson = Controller.getInstance().getInitOjbectDatas();

        for( var i  = 0; i < initJson.length; i++){
            // 타입이 몬스터가 아닌경우 예외처리가 필요.
            if(this.IsMonsterType(initJson[i].type) ){
                this.MakeUpMonster(initJson[i]);
                this.monster_isDead.push(false);
            }

        }
    },

    /**
     * for loop에서 버그인지 체크가 안되는 문제가 있음
     * 함수로 분리 
     * @param {*} json 
     */
    MakeUpMonster: function(json){
        var self = this;
        var monsterURL = "./prefabs/monster" ;

        cc.loader.loadRes(monsterURL, cc.Prefab, function (err, prefabs) {
            // 리소스 로드가 완료된 후 실행할 코드
            if (err) {
                cc.error("Error loading image: " + err);
                return;
            }

            var x = json.pos[0];
            var y = json.pos[1];

            var origin_pos = cc.v2(x, y);

            var n1 = cc.instantiate(prefabs);
            var v1 = self.GVector(origin_pos.x,origin_pos.y);

            n1.getComponent('Monster').InitMonster(json, v1);
            n1.setPosition(v1);

            self.monster_list.push(n1);
            self.node.addChild(n1);
    
        });
    },

    /**
     * 보스 만드는곳.
     * 
     * @param {json} json 
     */
    MakeUpBoss: function(json){
        var self = this;
        var bossUrl = "./prefabs/boss" ;

        cc.loader.loadRes(bossUrl, cc.Prefab, function (err, prefabs) {
            // 리소스 로드가 완료된 후 실행할 코드
            if (err) {
                cc.error("Error loading image: " + err);
                return;
            }
            var x = json.pos[0] +2;
            var y = json.pos[1];

            var origin_pos = cc.v2(x, y);

            var n1 = cc.instantiate(prefabs);
            var v1 = self.GVector(origin_pos.x,origin_pos.y);

            n1.getComponent('Boss').InitBoss(json);
            n1.setPosition(v1);

            self.bossObject = n1;

            self.node.addChild(n1);
        });
    },


    IsBoss: function(type){
        if(type === 'boss')return true;

        return false;
    },


    IsMonsterType: function(type){


        if(type === 'aggressive_monster_1' ) return true;
        if(type === 'passive_monster') return true;
        if(type === 'boss') return true;
        if(type === 'aggressive_monster_2' ) return true;

        return false;
    },


    /**
     * 
     * 몬스터 String json 값입력받아
     * 몬스터들의 상태값을 조정함.
     * 
     * @param {streamjson} streamjson 
     */
    UpdateMonster: function(streamjson){

        // streamJson.length가 없으면 동작안함 
        // Maybe 이곳은 거의 들어올듯
        if(streamjson.length < 1 ) return;
        
        // 이곳에서 몬스터가 없으면 안돌으니 이곳에서 걸릴듯.        
        // 몬스터 리스트가 없으면 동작안함.
        if(this.monster_list.length < 1) return;

        for(var i = 0; i < streamjson.length; i++){
            var monster = this.FindMonsterByID(i);
            if(monster == null) continue;

            if(typeof streamjson[i] != 'number' ){

                var monster_id = monster.getComponent("Monster").GetID();

                if(streamjson[i].status === -5){
                    this.monster_isDead[monster_id] = false;

                    var convertPos = this.GVector(streamjson[i].pos[0], streamjson[i].pos[1]);
                    monster.getComponent("Monster").Movement(convertPos, streamjson[i].dir);
                }
                else if(streamjson[i].status === -4 || streamjson[i].status === -8){
                    if(this.monster_isDead[monster_id] == true) continue;
                    this.monster_isDead[monster_id] = true;
                    
                    monster.getComponent("Monster").ShowExplosion();
                }
            }
            monster.getComponent("Monster").UpdateStatus(streamjson[i]);
        }
    },

    /**
     * 보스 상태 업데이트 해주는곳.
     * @param {StreamJson} streamJson 
     */
    UpdateBoss: function(streamJson){

        if(streamJson.length < 1) return;
        if(this.bossObject == null) return;

        this.bossObject.getComponent("Boss").UpdateStatus(streamJson[0]);
    },


    FindMonsterByID: function(id){

        for(var i = 0; i < this.monster_list.length; i++){
            var monster_id = this.monster_list[i].getComponent("Monster").GetID();

            if(id == monster_id) return this.monster_list[i];
        }

        return null;
    },


    /**
     * Json 정보를 토대로 맵 오프셋값을 가져옵니다.
     * @param step json 단계
     * @param diff json 난이도  Easy , Normal , Hard
     * @param level Json 레벨
     * @returns {number}  컨버팅된 게임 레벨값
     * @constructor
     */
    ConvertGameLevel: function(step,diff,level){

        var gameLevel = 0;

        if(step === "tutorial"){
            gameLevel =  level - 1;
        }
        else{
            // 1-1-E
            if(step === "1-1" && diff === "Easy" && level === 1){ gameLevel = 2; }
            if(step === "1-1" && diff === "Easy" && level === 2){ gameLevel = 3; }
            if(step === "1-1" && diff === "Easy" && level === 3){ gameLevel = 4; }

            // 1-1-N
            if(step === "1-1" && diff === "Normal" && level === 1){ gameLevel = 5; }
            if(step === "1-1" && diff === "Normal" && level === 2){ gameLevel = 6; }
            if(step === "1-1" && diff === "Normal" && level === 3){ gameLevel = 7; }

            // 1-1-H
            if(step === "1-1" && diff === "Hard" && level === 1){ gameLevel = 8; }
            if(step === "1-1" && diff === "Hard" && level === 2){ gameLevel = 9; }
            if(step === "1-1" && diff === "Hard" && level === 3){ gameLevel = 10; }

            // 1-2-E
            if(step === "1-2" && diff === "Easy" && level === 1){ gameLevel = 11; }
            if(step === "1-2" && diff === "Easy" && level === 2){ gameLevel = 12; }
            if(step === "1-2" && diff === "Easy" && level === 3){ gameLevel = 13; }

            // 1-2-N
            if(step === "1-2" && diff === "Normal" && level === 1){ gameLevel = 14; }
            if(step === "1-2" && diff === "Normal" && level === 2){ gameLevel = 15; }
            if(step === "1-2" && diff === "Normal" && level === 3){ gameLevel = 16; }

            // 1-2-H
            if(step === "1-2" && diff === "Hard" && level === 1){ gameLevel = 17; }
            if(step === "1-2" && diff === "Hard" && level === 2){ gameLevel = 18; }
            if(step === "1-2" && diff === "Hard" && level === 3){ gameLevel = 19; }

            // 1-3-E
            if(step === "1-3" && diff === "Easy" && level === 1){ gameLevel = 20; }
            if(step === "1-3" && diff === "Easy" && level === 2){ gameLevel = 21; }
            if(step === "1-3" && diff === "Easy" && level === 3){ gameLevel = 22; }

            // 1-3-N
            if(step === "1-3" && diff === "Normal" && level === 1){ gameLevel = 23; }
            if(step === "1-3" && diff === "Normal" && level === 2){ gameLevel = 24; }
            if(step === "1-3" && diff === "Normal" && level === 3){ gameLevel = 25; }

            // 1-3-H
            if(step === "1-3" && diff === "Hard" && level === 1){ gameLevel = 26; }
            if(step === "1-3" && diff === "Hard" && level === 2){ gameLevel = 27; }
            if(step === "1-3" && diff === "Hard" && level === 3){ gameLevel = 28; }

            // 2-1-E
            if(step === "2-1" && diff === "Easy" && level === 1){ gameLevel = 29; }
            if(step === "2-1" && diff === "Easy" && level === 2){ gameLevel = 30; }
            if(step === "2-1" && diff === "Easy" && level === 3){ gameLevel = 31; }

            // 2-1-N
            if(step === "2-1" && diff === "Normal" && level === 1){ gameLevel = 32; }
            if(step === "2-1" && diff === "Normal" && level === 2){ gameLevel = 33; }
            if(step === "2-1" && diff === "Normal" && level === 3){ gameLevel = 34; }

            // 2-1-H
            if(step === "2-1" && diff === "Hard" && level === 1){ gameLevel = 35; }
            if(step === "2-1" && diff === "Hard" && level === 2){ gameLevel = 36; }
            if(step === "2-1" && diff === "Hard" && level === 3){ gameLevel = 37; }


            // 2-2-E
            if(step === "2-2" && diff === "Easy" && level === 1){ gameLevel = 38; }
            if(step === "2-2" && diff === "Easy" && level === 2){ gameLevel = 39; }
            if(step === "2-2" && diff === "Easy" && level === 3){ gameLevel = 40; }

            // 2-2-N
            if(step === "2-2" && diff === "Normal" && level === 1){ gameLevel = 41; }
            if(step === "2-2" && diff === "Normal" && level === 2){ gameLevel = 42; }
            if(step === "2-2" && diff === "Normal" && level === 3){ gameLevel = 43; }

            // 2-2-H
            if(step === "2-2" && diff === "Hard" && level === 1){ gameLevel = 44; }
            if(step === "2-2" && diff === "Hard" && level === 2){ gameLevel = 45; }
            if(step === "2-2" && diff === "Hard" && level === 3){ gameLevel = 46; }

            // 2-3-E
            if(step === "2-3" && diff === "Easy" && level === 1){ gameLevel = 47; }
            if(step === "2-3" && diff === "Easy" && level === 2){ gameLevel = 48; }
            if(step === "2-3" && diff === "Easy" && level === 3){ gameLevel = 49; }

            // 2-3-N
            if(step === "2-3" && diff === "Normal" && level === 1){ gameLevel = 50; }
            if(step === "2-3" && diff === "Normal" && level === 2){ gameLevel = 51; }
            if(step === "2-3" && diff === "Normal" && level === 3){ gameLevel = 52; }

            // 2-3-H
            if(step === "2-3" && diff === "Hard" && level === 1){ gameLevel = 53; }
            if(step === "2-3" && diff === "Hard" && level === 2){ gameLevel = 54; }
            if(step === "2-3" && diff === "Hard" && level === 3){ gameLevel = 55; }


         
            
            // 3-1-E
            if(step === "3-1" && diff === "Easy" && level === 1){ gameLevel = 56; }
            if(step === "3-1" && diff === "Easy" && level === 2){ gameLevel = 57; }
            if(step === "3-1" && diff === "Easy" && level === 3){ gameLevel = 58; }

            // 3-1-N
            if(step === "3-1" && diff === "Normal" && level === 1){ gameLevel = 59; }
            if(step === "3-1" && diff === "Normal" && level === 2){ gameLevel = 60; }
            if(step === "3-1" && diff === "Normal" && level === 3){ gameLevel = 61; }

            // 3-1-H
            if(step === "3-1" && diff === "Hard" && level === 1){ gameLevel = 62; }
            if(step === "3-1" && diff === "Hard" && level === 2){ gameLevel = 63; }
            if(step === "3-1" && diff === "Hard" && level === 3){ gameLevel = 64; }


            // 3-2-E
            if(step === "3-2" && diff === "Easy" && level === 1){ gameLevel = 65; }
            if(step === "3-2" && diff === "Easy" && level === 2){ gameLevel = 66; }
            if(step === "3-2" && diff === "Easy" && level === 3){ gameLevel = 67; }

            // 3-2-N
            if(step === "3-2" && diff === "Normal" && level === 1){ gameLevel = 68; }
            if(step === "3-2" && diff === "Normal" && level === 2){ gameLevel = 69; }
            if(step === "3-2" && diff === "Normal" && level === 3){ gameLevel = 70; }

            // 3-2-H
            if(step === "3-2" && diff === "Hard" && level === 1){ gameLevel = 71; }
            if(step === "3-2" && diff === "Hard" && level === 2){ gameLevel = 72; }
            if(step === "3-2" && diff === "Hard" && level === 3){ gameLevel = 73; }

            // 3-3-E
            if(step === "3-3" && diff === "Easy" && level === 1){ gameLevel = 74; }
            if(step === "3-3" && diff === "Easy" && level === 2){ gameLevel = 75; }
            if(step === "3-3" && diff === "Easy" && level === 3){ gameLevel = 76; }

            // 3-3-N
            if(step === "3-3" && diff === "Normal" && level === 1){ gameLevel = 77; }
            if(step === "3-3" && diff === "Normal" && level === 2){ gameLevel = 78; }
            if(step === "3-3" && diff === "Normal" && level === 3){ gameLevel = 79; }

            // 3-3-H
            if(step === "3-3" && diff === "Hard" && level === 1){ gameLevel = 80; }
            if(step === "3-3" && diff === "Hard" && level === 2){ gameLevel = 81; }
            if(step === "3-3" && diff === "Hard" && level === 3){ gameLevel = 82; }


             // 3-4-E
             if(step === "3-4" && diff === "Easy" && level === 1){ gameLevel = 83; }
             if(step === "3-4" && diff === "Easy" && level === 2){ gameLevel = 84; }
             if(step === "3-4" && diff === "Easy" && level === 3){ gameLevel = 85; }
 
             // 3-4-N
             if(step === "3-4" && diff === "Normal" && level === 1){ gameLevel = 86; }
             if(step === "3-4" && diff === "Normal" && level === 2){ gameLevel = 87; }
             if(step === "3-4" && diff === "Normal" && level === 3){ gameLevel = 88; }
 
             // 3-4-H
             if(step === "3-4" && diff === "Hard" && level === 1){ gameLevel = 89; }
             if(step === "3-4" && diff === "Hard" && level === 2){ gameLevel = 90; }
             if(step === "3-4" && diff === "Hard" && level === 3){ gameLevel = 91; }
 


        }
        return gameLevel;
    },

    /**
     * 맵정보를 로드하고, 초기화 합니다.
     * @constructor
     */
    InitMap: function() {
        var self = this;
        var mapUrl = "./map/" + this.GetMapURL();  // 맵 URL 동적 생성

        // Tiled Map 리소스 로드
        cc.loader.loadRes(mapUrl, cc.TiledMapAsset, function (err, tmxFile) {
            if (err) {
                cc.error("맵 로딩 에러: " + err);
                return;
            }
            self.gameMap.tmxAsset = tmxFile;  // Tiled Map 설정

            self.MapSetupCamera();  // 카메라 설정 함수 분리
        });
    },

    /**
     * 카메라를 맵 크기에 맞게 설정합니다.
     */
    MapSetupCamera: function() {
        var mapSize = this.gameMap.getMapSize();
        var tileSize = this.gameMap.getTileSize();
        var mapHeight = mapSize.height * tileSize.height;

        this.gameMap.node.setPosition(0, -mapHeight * 3);  // 맵 노드 위치 설정
        this._TileMapShake();  // 맵 흔들림 효과 호출
    },

    /**
     * Json Stage 정보를 토대로 저장된 맵 프리팹 url 리턴합니다.
     * @returns {string}
     * @constructor
     */
    GetMapURL : function(){
        var url = "";
        var stageObject = Controller.getInstance().getInitStageData();

        var map  = stageObject.map;
        var step = stageObject.step;
        var diff = stageObject.diff;
        var level = stageObject.level;


        if(step === "tutorial"){
            url = "map_T-"+level.toString();
        }
        else{
            var cLevel = "Easy";
            switch (diff){
                case "Easy": cLevel = "E"; break;
                case "Normal" : cLevel = "N"; break;
                case "Hard" : cLevel = "H"; break;
            }
            url = "map_"+step.toString() + "-" + cLevel + "-" + level.toString();
        }
        return url;
    },

    /**
     * 카메라 포지션을 조정합니다.
     * @constructor
     * @param x X좌표
     * @param y Y좌표
     */
    SetCamera : function(x = 0, y = 0, zoomLevel = 1.5){
        // 메인 카메라의 포지션 설정
        this.camera.node.setPosition(cc.v3(x, y, 0)); // 여기서 x, y, z는 포지션의 좌표값입니다.
        this.camera.zoomRatio = zoomLevel;
    },

    /**
     * 맵의 갯수가 많지 않으니 하드 코딩처리되어있습니다.
     * 카메라 포지셔닝, 맵의 오프셋값 이 정의 되어있습니다.
     * @constructor
     */
    InitialCamera: function() {

        // Default option
        // -600 -860  1
        var stageObject = Controller.getInstance().getInitStageData();

        var step = stageObject.step;
        var diff = stageObject.diff;
        var level = stageObject.level;

        var gameLevel = this.ConvertGameLevel(step,diff,level);

        this.spaceShip.active = false;


        console.log(gameLevel);


        switch (gameLevel){
            case 0 : case 1:
                this.SetCamera(-700,-870,1.4);
                break;
            case 2: case 3:
            case 5: case 6:
                this.SetCamera(-550,-1000,1.0);
                break;
            case 8: case 9:
                this.SetCamera(-600,-970,1.2);
                break;
            case 4: case 7: case 10:
                this.SetCamera(-400,-1060,1);
                break;
            case 11: case 14: case 17:
                this.SetCamera(-300,-1200,0.75);
                break;
            case 12: case 15: case 18:
                this.SetCamera(350,-1050,0.7);
                break;
            case 13: case 16: case 19:
                this.SetCamera(650,-1000,0.58);
                break;

            case 20: case 23: case 26:
                this.SetCamera(140,-900,0.8);
                this.spaceShip.setPosition(cc.v2(-4830,600));
                this.spaceShip.active = true;
                break;

            case 21: case 24: case 27:
                this.SetCamera(350,-900,0.7);
                this.spaceShip.setPosition(cc.v2(-550, 600));
                this.spaceShip.active = true;
                break;

            case 22: case 25: case 28:
                this.SetCamera(250,-1300,0.7);
                this.spaceShip.setPosition(cc.v2(5260,-780));
                this.spaceShip.active = true;
                break;

            // 2-1 E ~ 2-1-H 모두 동일
            case 29: case 30: case 31:
            case 32: case 33: case 34: 
            case 35: case 36: case 37:
                this.SetCamera(-300,-850,0.8);
                break;
            
            // 2-2-E-1 / 2-2-N-1 / 2-2-H-1
            case 38: case 41: case 44:
                this.SetCamera(450,-800,0.66);
                break;
            // 2-2-E-2 / 2-2-N-2 / 2-2-H-2
            case 39: case 42: case 45:
                this.SetCamera(-200,-1050,0.8);
                break;
            // 2-2-E-3 / 2-2-N-3 / 2-2-H-3
            case 40: case 43: case 46:
                this.SetCamera(450,-1000,0.6);
                break;
            
            // 2-3-E-1 ~
            case 47: case 50: case 53:
            this.SetCamera(550,-950,0.63);
                break;

            // 2-3-E-2 ~
            case 48: case 51: case 54:
                this.SetCamera(580,-650,0.6);
                break;

            // 2-3-E-3 ~
            case 49: case 52: case 55:
                this.SetCamera(-400,-1050,0.9);
                    break;
            // 3-1-E-1
            case 56: case 59: case 62:
                this.SetCamera(-400,-1050,1);
                    break;

            // 3-1-E-2
            case 57: case 60: case 63:
                this.SetCamera(250,-950,0.8);
                break;
            // 3-1-E-3
            case 58: case 61: case 64:
                this.SetCamera(-50,-1000,1.1);
                break;

            // 3-2-E-1
            case 65: case 68: case 71:
                this.SetCamera(-400,-1100,1);
                break;
            // 3-2-E-2
            case 66: case 69: case 72:
                this.SetCamera(450,-700,0.7);
                break;

            // 3-2-E-3
            case 67: case 70: case 73:
                this.SetCamera(-200,-1350,0.7);
                break;

            // 3-3-E-1            
            
            case 74: case 75: case 76:
            case 77: case 78: case 79:
            case 80: case 81: case 82:
                this.SetCamera(250,-1050,0.8);
                break;
            
            case 83: case 86: case 89:
                this.SetCamera(550,-850,0.6);
                break;
            
            case 84: case 87: case 90:
                this.SetCamera(550,-900,0.6);
                break;

            case 85: case 88: case 91:
                this.SetCamera(50,-1550,0.6);
                break;

        }


        
        // TODO 
    },


    /**
     * 이펙트 초기화 해주는 함수
     * 게임의 진행에 따라 Call 될수 있습니다.
     *
     * 코드 개선 2024-04-15
     * @constructor
     */
    EffectInit: function(){
        var self = this;
        var urls = ['/prefabs/explosion', '/prefabs/heal', '/prefabs/pickup', '/prefabs/dropEffect'];
        var lists = [this.expList, this.hList, this.pList, this.dropEffectList]; // 각 URL에 해당하는 리스트

        urls.forEach((url, index) => {
            for (var i = 0; i < 15; i++) {
                cc.loader.loadRes(url, cc.Prefab, function (err, effect) {
                    if (err) {
                        cc.error("이미지 로딩 에러: " + err);
                        return;
                    }

                    var newInstance = cc.instantiate(effect);
                    newInstance.active = false;
                    self.effectParent.addChild(newInstance);
                    lists[index].push(newInstance); // URL 인덱스에 따라 적절한 리스트에 추가
                });
            }
        });

    },

    /**
     * 공통 이펙트 표시 함수
     * @param {Array} list - 이펙트 객체를 포함하고 있는 리스트
     * @param {Number} idx - 현재 인덱스
     * @param {cc.Vec2} pos - 이펙트를 표시할 위치
     * @param {String} animationName - 재생할 애니메이션 이름
     * @param {Function} extraAction - 추가적으로 실행할 함수
     */
    showEffect: function(list, idx, pos, animationName, extraAction = null) {
        if (idx >= list.length - 1) idx = 0;  // 인덱스 초기화
        var node = list[idx];
        node.active = true;
        node.setPosition(pos);
        var animation = node.getComponent(cc.Animation);
        animation.once('finished', () => {
            node.active = false;
            list[idx] = node;  // 다음 사용을 위해 업데이트
            if (extraAction) extraAction();
            idx++;
        }, this);
        animation.play(animationName);

        return idx;  // 업데이트된 인덱스 반환
    },

    /**
     * 폭발 이펙트를 표시합니다.
     * @param {cc.Vec2} pos - 이펙트를 표시할 위치
     */
    ShowExplosion: function(pos) {
        this.expIdx = this.showEffect(this.expList, this.expIdx, pos, "explosion", () => {
            this.isCameraShaked = false;
            SoundManager.getInstance().PlaySfx(Env.SFX_BOMB);
        });
    },

    /**
     * 회복 이펙트를 표시합니다.
     * @param {cc.Vec2} pos - 이펙트를 표시할 위치
     */
    ShowHeal: function(pos) {
        this.hIdx = this.showEffect(this.hList, this.hIdx, pos, "heal");
    },

    /**
     * 픽업 이펙트를 표시합니다.
     * @param {cc.Vec2} pos - 이펙트를 표시할 위치
     */
    ShowPickup: function(pos) {
        this.pIdx = this.showEffect(this.pList, this.pIdx, pos, "pickup", () => {
            SoundManager.getInstance().PlaySfx(Env.SFX_EARN_ITEM);
        });
    },

    /**
     * 드롭 이펙트를 표시합니다.
     * @param {cc.Vec2} pos - 이펙트를 표시할 위치
     */
    ShowDropEffect: function(pos) {
        this.dropEffectIdx = this.showEffect(this.dropEffectList, this.dropEffectIdx, pos, "dropeffect");
    },

    /**
     * 맵위에 출력될 객체 초기화합니다.
     * @constructor 
     */
    InitObject: function() {

        var goalList = Controller.getInstance().getInitStageData().goal_list;
        this.MakeGoal(goalList);

        var objects = Controller.getInstance().getInitOjbectDatas();

        if (objects.length < 1) {
            return;
        } else {
            for (var i = 0; i < objects.length; i++) {
                this.MakeUpObject(objects[i]);

                this.AddBombSprites(objects[i]);
            }
        }
        // var ts = performance.now();
        // var li =  ts - self.startTime;

        // 처리..
        this.ShowVariableBombSprites();
    },

    /**
     * 골 지점 객체를 생성합니다.
     * @param object 골객체 관련.
     * @constructor
     */
    MakeGoal: function(object){
        for(var j = 0; j < object.length; j++){
            if(object[j].goal === "target"){
                var targets = object[j];
                var goalPos = cc.v2(targets.pos[0], targets.pos[1]);
                this.AddPrefabs(Env.GOAL, -1, goalPos);
                break;
            }
        }
    },

    /**
     * 게임의 전반적인 모든 객체를 만드는 함수
     * 해당 함수는 총괄 함수는 컨트롤하는 함수입니다.
     *
      * @param type 객체의 타입을 받습니다.
     * @constructor
     */
    MakeUpObject: function(object){
        var type = object.type;

        if(type === "laser"){

            var id = object.id;
            var dir = object.dir;
            var startX = object.pos_start[0];
            var startY = object.pos_start[1];
            var status = object.status;

            var startPos = cc.v2(startX, startY);
            this.AddLaserPrefab(Env.LASER_START_ON, id, startPos , status , dir );

            var endX = object.pos_end[0];
            var endY = object.pos_end[1];

            var endPos = cc.v2(endX, endY);
            this.AddLaserPrefab(Env.LASER_END_ON, id, endPos, status, dir );

            /**
             * 중간 레이저 포인터 가져오기.
             */
            if(dir === 'h'){
                // 가로
                for(var i = startX+1 ; i < endX ; i++){
                    var p = cc.v2(i, startY);
                    this.AddLaserPrefab(Env.LASER_MIDDLE_ON, id, p , status, dir);
                }
            }
            else if(dir === "v"){
                // 세로
                for(var i = startY+1 ; i < endY ; i++){
                    var p = cc.v2(startX, i);
                    this.AddLaserPrefab(Env.LASER_MIDDLE_ON, id, p , status, dir);
                }
            }
        }
        else if(type === 'drop_switch'){
            // drop Switch
            var switchId = object.id;
            var switchTag = this.NameToTag(object.type);
            var switchX = object.pos[0];
            var switchY = object.pos[1];

            var switchPos = cc.v2(switchX, switchY);

            var dropX = object.pos_drop[0];
            var dropY = object.pos_drop[1];
            var dropType = object.drop_type;
            var dropItemTag = this.NameToTag(dropType);

            var dropItemPos = cc.v2(dropX, dropY);
            this.AddDropSwitchPrefabs(switchTag, switchId, switchPos, dropItemTag,  dropItemPos )

        }
        else{
            var tag = this.NameToTag(object.type);

            // 예외 처리
            if(tag == -99){
                console.log(object.type);

                return;
            } 
            else{
                var posX = object.pos[0];
                var posY = object.pos[1];
                var itemId = object.id;
                var pos = cc.v2(posX, posY);
                var status = object.status;

                if(tag === Env.ROCKET_EMPTY){
                    this.AddRocketParts(tag,itemId,pos, object);
                }
                else{
                    this.AddPrefabs(tag, itemId,  pos , status , object);
                }
            }
        }

    },

    /**
     * 입력된 String 값을 게임에서 사용되는 태그로바꿔주는 함수
     * @param str 입력된 Json type값
     * @constructor
     */
    NameToTag: function(str){

        switch(str){
            case "food" : return Env.FOOD;
            case "bomb": return Env.BOMB;
            case "rocket_parts" : return Env.BATTERY;
            case "drop_switch" : return Env.NORMAL_SWITCH_ON;
            case "laser_switch": return Env.LASER_SWITCH_ON;
            case "engines": case "solid_propellant": case "liquid_fuel":  return Env.ROCKET_EMPTY;
            case "variation_switch" : return Env.VARIATION_SWITCH_ON;
            case "door":  return Env.DOOR_ON;
            case "medicine": return Env.MEDICINE;
            case "box": return Env.BOMB_BOX;

            default : return -99;
        }
    },

    /**
     * 로켓파츠 프리팹 만들어주는 함수
     * @param tag  로켓파츠의 태그
     * @param id  객체 아이디
     * @param pos  GVector 의 포지션
     * @param object  json Stream  (방향정보를 가져오기위함)
     * @constructor
     */
    AddRocketParts: function(tag, id, pos, object){
        var prefabName = "rocketParts";
        var self = this;

        var itemurl = "./prefabs/" + prefabName;

        cc.loader.loadRes(itemurl, cc.Prefab, function (err, prefabs) {
            // 리소스 로드가 완료된 후 실행할 코드
            if (err) {
                cc.error("Error loading image: " + err);
                return;
            }

            var n1 = cc.instantiate(prefabs);
            n1.addComponent("Gobject");
            n1.getComponent('Gobject').Init(tag,id);

            var v1 = self.GVector(pos.x,pos.y);
            n1.setPosition(v1);

            // n1.opacity = 100;

            switch (object.require_dir){
                case "up": n1.rotation = 0; break;
                case "down": n1.rotation = -180; break;
                case "left": n1.rotation = -90; break;
                case "right": n1.rotation = 90; break;
            }

            self.item.push(n1);
            // self.node.addChild(n1);
            self.objectParent.addChild(n1);
            self.objectParent.setLocalZOrder = 10;


            var ts = performance.now();
            var li =  ts - self.startTime;
        });
    },

    /**
     * 프리팹을 생성해줍니다.
     * @param tag  객체의 태그값 Env 전역넘버
     * @param id  Json 에서 불러온 id 값
     * @param pos  cc.v2 포지션값
     * @constructor
     */
    AddPrefabs: function(tag , id , pos, status = 1 , obj ){
        var prefabName = "";
        var self = this;

        // 태그값에 해당하는 프리팹 이름 가져옴.
        switch (tag){
            case Env.FOOD : prefabName = "food";    break;
            case Env.NORMAL_SWITCH_ON : case Env.NORMAL_SWITCH_OFF : prefabName = "nSwitch";  break;
            case Env.LASER_SWITCH_ON : case Env.LASER_SWITCH_OFF : prefabName = "lSwitch"; break;
            case Env.BATTERY : prefabName = "battery"; break;
            case Env.BOMB : prefabName = "bomb"; break;
            case Env.ROCKET_EMPTY : case Env.ROCKET_FILLED : prefabName = "rocketParts";  break;
            case Env.GOAL : prefabName = "goal"; break;
            case Env.FLOOR: prefabName = "floor"; break;
            case Env.DOOR_ON: 
            /**
             * Door 객체 전용으로 따로 구분.
             */
            if(obj.dir === 'v'){
                prefabName = "door_v";
            }
            else if(obj.dir === 'h'){
                prefabName = "door";
            }
            break;
            
            case Env.VARIATION_SWITCH_OFF: case Env.VARIATION_SWITCH_ON : prefabName = "variation_switch"; break;
            case Env.MEDICINE: prefabName = "medicine"; break;
            case Env.BOMB_BOX : prefabName = "bomb_box"; break;
        }

        

        if(prefabName == "" ){
            console.log("Not found Prefabs => " + tag + " id => " + id);
            return;
        }

        var itemurl = "./prefabs/" + prefabName;

        cc.loader.loadRes(itemurl, cc.Prefab, function (err, prefabs) {
            // 리소스 로드가 완료된 후 실행할 코드
            if (err) {
                cc.error("Error loading image: " + err);
                return;
            }

            var n1 = cc.instantiate(prefabs);
            var v1 = self.GVector(pos.x,pos.y);
            n1.setPosition(v1);
            n1.addComponent("Gobject");
            n1.getComponent('Gobject').Init(tag,id);

            if(status == 0){
                n1.getComponent('Gobject').Show();
                n1.getComponent('Gobject').Hide();
            }

            if(tag === Env.GOAL){
                self.goalObject = n1;
                self.node.addChild(n1);
            }
            else if(tag === Env.DOOR_ON){
                if(obj.dir === 'v'){
                    n1.getComponent('Gobject').SetDir("v");
                }
                else if(obj.dir === 'h'){
                    n1.getComponent('Gobject').SetDir('h');
                }
                self.item.push(n1);
                self.node.addChild(n1);
            }

            else{
                self.item.push(n1);
                self.node.addChild(n1);
            }
        });
    },

    /**
     * 프리팹을 생성하고 초기화하는 공통 함수
     * @param {String} prefabUrl - 프리팹의 리소스 URL
     * @param {String} tag - 생성할 객체의 태그
     * @param {Number} id - 객체의 ID
     * @param {Object} pos - 객체의 위치 (x, y 포함하는 객체)
     * @param {Array} list - 객체를 추가할 리스트
     * @param {Boolean} hide - 드롭 아이템의 초기 숨김 상태 (선택적)
     */
    createAndInitPrefab: function(prefabUrl, tag, id, pos, list, hide=false) {
        var self = this;
        cc.loader.loadRes(prefabUrl, cc.Prefab, function(err, prefab) {
            if (err) {
                cc.error("프리팹 로딩 에러: " + err);
                return;
            }
            var instance = cc.instantiate(prefab);
            instance.addComponent("Gobject");
            instance.getComponent("Gobject").Init(tag, id);
            if (hide) {
                instance.getComponent("Gobject").DropItemHide(true);
            }
            instance.setPosition(self.GVector(pos.x, pos.y));
            self.node.addChild(instance);
            list.push(instance);
        });
    },

    /**
     * 드롭 스위치 프리팹과 관련 드롭 아이템 프리팹을 생성하는 함수
     * @param {Number} tag - 드롭 스위치의 태그
     * @param {Number} id - 객체의 아이디
     * @param {Object} pos - 드롭 스위치의 위치
     * @param {Number} drop_item_tag - 드롭 아이템의 태그
     * @param {Object} drop_item_pos - 드롭 아이템의 위치
     */
    AddDropSwitchPrefabs: function(tag, id, pos, drop_item_tag, drop_item_pos) {
        var switchUrl = "./prefabs/nSwitch"; // 드롭 스위치 프리팹 경로
        var dropUrl = './prefabs/food';

        if(drop_item_tag === Env.BATTERY){
            dropUrl = './prefabs/battery'; // 드롭 아이템 프리팹 경로
        }
        else if(drop_item_tag === Env.FOOD){
            dropUrl = './prefabs/food';
        }
        else if(drop_item_tag === Env.MEDICINE){
            dropUrl = './prefabs/medicine';
        }
        // 드롭 스위치 프리팹 생성 및 초기화
        this.createAndInitPrefab(switchUrl, tag, id, pos, this.dropSwitchList);

        // 드롭 아이템 프리팹 생성 및 초기화
        this.createAndInitPrefab(dropUrl, drop_item_tag, id, drop_item_pos, this.dropItemList, true);
    },



    /**
     * 레이저 객체 프리팹을 만들어주는 함수
     * @param tag  레이저 태그
     * @param id  id
     * @param pos  방향
     * @constructor
     */
    AddLaserPrefab: function(tag, id, pos , status, dir){
        var prefabName = "laser";
        var self = this;


        // 레이저 중간 객체라면 아래처럼 이미지 교체.
        if(tag === Env.LASER_MIDDLE_ON)  prefabName = "laserMiddle";

        var itemurl = "./prefabs/" + prefabName;

        cc.loader.loadRes(itemurl, cc.Prefab, function (err, prefabs) {
            // 리소스 로드가 완료된 후 실행할 코드
            if (err) { cc.error("Error loading image: " + err); return; }
            var n1 = cc.instantiate(prefabs);

            n1.addComponent("Gobject");
            n1.getComponent('Gobject').Init(tag, id);

            var isStatus = false;

            if(status === 0)  isStatus = false;
            else  isStatus = true;

            n1.getComponent("Gobject").LaserInitial(tag,isStatus , dir);

            var v1 = self.GVector(pos.x,pos.y);
            n1.setPosition(v1);
            self.node.addChild(n1);
            self.item.push(n1);

            var ts = performance.now();
            var li =  ts - self.startTime;

        });
    },

    /**
     * ExcuteCommand 에서 코드 압축을 위해 함수 화.
     * 3-1 스테이지 예외 처리
     * 
     * @param {command} command 
     */
    PrintUIDialog: function(command){
        var stageObject = Controller.getInstance().getInitStageData();
        var stage_step = stageObject.step;

        if(stage_step === '3-1'){
            return;
        }
        else{
            var playerStatus = command.player.status;

            if(playerStatus === 19){
                var item_list = command.item_list;
                var print_array =  item_list[0].print_array;
                var print_data = '';
                for(var i = 0; i < print_array.length; i++){
                    print_data += ( print_array[i] + " ");
                }
                var convert = print_data.toString();
    
                this.ShowPrintUI(convert);
            }
            else if(playerStatus != 0){
                this._InitPrintUI();
            }
        }
    },

    /**
     * PYthon 코드 대로 동작을 실행합니다.
     * 실제 플레이어 및  모든 객체 상태를 변화를 줍니다.
     * 단 return 값을 false 하면 이동종료를 뜻합니다.
     * @returns {boolean}
     */
    executeCommand: function(id = 0){
        var command = Controller.getInstance().getCommandLine(id);
        /**
         * 명령어 종료를 뜻함
         */
        if(command === -1) return false;
        if(command.status === 1) return false;

        // 객체 상태값 업데이트
        this.ObjectUpdate(command.item_list);

        this.UpdateMonster(command.item_list);
        


        // 플레이어 상태값
        var playerStatus = command.player.status;

        this.PrintUIDialog(command);

        var convertPos = this.GVector(command.player.pos[0], command.player.pos[1]);
        this.EffectControl(playerStatus, convertPos);


        if(id == 0){
            // HIDE 처리!
            console.log("init");
            this.bossObject.getComponent("Boss").HideSpecialAttack();
        }

      
        var hit_stauts = command.player.hit_status;

        if(hit_stauts === 2){
            this.bossObject.getComponent("Boss").SpecialAttack();
        }

        if(hit_stauts == 1 && command.player.hp > 1){
            this.player.getComponent("Player").SetHitStatus();
        }
        else{
            this.player.getComponent("Player").setDirection(command.player.dir);
            this.player.getComponent("Player").setPlayerStatus(playerStatus);
           
            this.player.getComponent("Player").Movement(convertPos);
        }


        this.UpdateBoss(command.item_list);

        return true;
    },

    /**
     * 폭탄, 픽업 아이템 보여주는 함수
     * @param status 현재 상태값
     * @param pos 포지션 GVector로 적용해야함
     * @constructor
     */
    EffectControl: function(status , pos){
        var self = this;

        if(status === 11){
            this.ShakeEffect();
            this.ShowExplosion(pos);
        }
        else if( status === 3 || status === 37){
            this.ShowPickup(pos);
        }
    },

    /**
     * 스트림 데이터를 입력받아 맵위에 있는 객체의 상태를 변화 하거나
     * Sprite를 변경해주는 업데이트 함수.
     * @param id_list
     * @constructor
     */
    ObjectUpdate: function(id_list) {
        // 비어 있다면 실행하지 않습니다.
        if(id_list.length < 1) return;

        for(var i = 0; i < id_list.length; i++){
            // 아이템 스테이터스 업데이트
            this.ItemStatusUpdate(i, id_list[i]);
            // Drop Switch UPdate
            this.DropSwitchUpdate(i, id_list[i]);
        }
    },

    /**
     * 드롭 스위치 의 상태를 표현해주는 함수입니다.
     * @param index  현재 상태 리스트
     * @param status 상태값을 찾습니다.
     * @constructor
     */
    DropSwitchUpdate: function(index, status){
        // 드랍 스위치가 아닌경우
        for(var i = 0; i < this.dropSwitchList.length; i++){
            var dropSwitch = this.dropSwitchList[i].getComponent("Gobject");

            var dropSwitchId = dropSwitch.GetItemID();


            if(dropSwitchId === index && status === 0){
                var dropItem =  this.FindDropItem(dropSwitchId);
                var itemComp = dropItem.getComponent("Gobject");

                if(dropItem.active === false){
                    this.ShowDropEffect(dropItem.position);
                }
                itemComp.DropItemShow();
                dropSwitch.Hide();

            }
            else if(dropSwitchId === index && status === 1){
                var dropItem =  this.FindDropItem(dropSwitchId);
                var itemComp = dropItem.getComponent("Gobject");
                // itemComp.Hide();

                itemComp.DropItemHide();
                dropSwitch.Hide();
            }
            else if(dropSwitchId === index && status === 2){
                var dropItem =  this.FindDropItem(dropSwitchId);
                var itemComp = dropItem.getComponent("Gobject");
                // itemComp.Show();
                itemComp.DropItemShow();

                dropSwitch.Show();
            }
            else if(dropSwitchId === index && status === 3){
                var dropItem =  this.FindDropItem(dropSwitchId);
                var itemComp = dropItem.getComponent("Gobject");
                // itemComp.Hide();

                itemComp.DropItemHide();

                dropSwitch.Show();

            }
        }
    },

    /**
     * 드랍아이템 찾는 함수
     * @param id  드롭아이템이랑 매칭되는 스위치 아이디
     * @return {*}  찾은 객체 리턴
     * @constructor
     */
    FindDropItem: function(id){
      for( var i = 0; i <  this.dropItemList.length; i++){
          if(this.dropItemList[i].getComponent("Gobject").GetItemID() === id) return this.dropItemList[i];
      }
    },


     /**
     * 2스테이지 프린트 UI를 초기화 처리 해줌
     */
     _InitPrintUI: function(){
        this.print_ui_object.active = false;
        this.print_ui_object.scale = cc.v2(1, 0);
        this.print_ui_label.string = '';
    },


    /**
     * open 효과 이펙트 줌
     * 
     */
    _OpenPrintUI: function(){
        if(this.print_ui_object.active) return;

        this.print_ui_object.active = true;
        this.print_ui_object.getComponent(cc.Animation).play('open');
    },

    /**
     * 화면에 출력될 내용 입력받아 print() 처리
     * @param {string} text 
     */
    ShowPrintUI: function(text){
        this._OpenPrintUI();
        this.print_ui_label.string = text;
    },


    /**
     * 폭탄 보였다 안보였다 처리 효과.
     */
    ShowVariableBombSprites: function(){

        var self = this;
        setInterval(function(){
            if(Controller.getInstance().getCurrentCommandID() > 2) self._HideVariableSprites();
            else self._ShowVariableSprites();
        },30);

        
    },


    /**
     * Variation Bomb Sprite 표현.
     * @param {*} object 
     * @returns 
     */
    AddBombSprites: function(object){
        var tag = this.NameToTag(object.type);
        

        if(tag !== Env.BOMB) return;
        var variation_no = object.variation_no;
        if(variation_no == null) return;

        var posX = object.pos[0];
        var posY = object.pos[1];
        var pos = cc.v2(posX, posY);

        var itemurl = "./prefabs/var_bomb";

        var self = this;

        cc.loader.loadRes(itemurl, cc.Prefab, function (err, prefabs) {
            // 리소스 로드가 완료된 후 실행할 코드
            if (err) {
                cc.error("Error loading image: " + err);
                return;
            }


            var n1 = cc.instantiate(prefabs);
            n1.opacity = 0;
            var v1 = self.GVector(pos.x,pos.y);
            n1.setPosition(v1);

            // 최대치 계산
            self.bomb_sprite_max_range = self.bomb_sprite_max_range > variation_no[0] ? self.bomb_sprite_max_range : variation_no[0];


            if(variation_no.length == 1){
                switch(variation_no[0]){
                    case 1:
                        self.bomb_sprites[0].push(n1);
                        break;
                    case 2:
                        self.bomb_sprites[1].push(n1);
                        break;
                    case 3:
                        self.bomb_sprites[2].push(n1);
                        break;
                    case 4:
                        self.bomb_sprites[3].push(n1);
                        break;
                    case 5:
                        self.bomb_sprites[4].push(n1);
                        break;
                }
            }
            else{
                for(var i = 0; i < variation_no.length; i++){
                    
                    switch(variation_no[i]){
                        case 1:
                            self.bomb_sprites[0].push(n1);
                            break;
                        case 2:
                            self.bomb_sprites[1].push(n1);
                            break;
                        case 3:
                            self.bomb_sprites[2].push(n1);
                            break;
                        case 4:
                            self.bomb_sprites[3].push(n1);
                            break;
                        case 5:
                            self.bomb_sprites[4].push(n1);
                            break;
                    }
                }
            }
            //Grouping
            self.node.addChild(n1);
        });

    },



    /**
     * 안보이게 하는 Sprites 리스트들
     */
    _HideVariableSprites: function(){
        if(this.isShowVariation === false)  return;
        this.isShowVariation = false;



        clearInterval(this.bomb_sprite_id);
        for(var i = 0; i < this.bomb_sprites.length; i++){
            for( var j = 0; j < this.bomb_sprites[i].length; j++){
                this.bomb_sprites[i][j].opacity = 0;
                this.bomb_sprites[i][j].active = false;
            }
        }
    },

    /**
     * 보이게 하는 Sprites;
     */
    _ShowVariableSprites: function(){
        if(this.isShowVariation) return;
        this.isShowVariation = true;


        var show_idx = 0;
        var opt = [0, 0, 0 ,0, 0, 0, 0];

        var self = this;

        this.bomb_sprite_id = setInterval(function(){

            if(opt[show_idx] > 250){
                opt[show_idx] = 0;

                for( var j = 0; j < self.bomb_sprites[show_idx].length; j++){
                    self.bomb_sprites[show_idx][j].opacity = 0;
                }
                
                if(show_idx >=  self.bomb_sprite_max_range-1 ){
                    show_idx = 0;
                }
                else{
                    show_idx++;
                }
            }
            else{
                for( var j = 0; j < self.bomb_sprites[show_idx].length; j++){
                    self.bomb_sprites[show_idx][j].opacity = opt[show_idx];
                    opt[show_idx] += 3;
                }
            }
            
        },90);

    },

    /**
     * 아이템 상태를 관리해주는 함수
     *
     * @param index 아이템 인덱스값을 받음
     * @param status 상태는 스트림데이터로 넘어오는 아이템들 리스트 임.
     * @constructor
     */
    ItemStatusUpdate: function(index, status){
        for(var i = 0; i < this.item.length; i++){
            var itemObject = this.item[i].getComponent("Gobject");

            var itemID = itemObject.GetItemID();
            var itemTag = itemObject.GetItemTag();
            if(itemID === index && status === 0){
                itemObject.Hide();
            }
            else if(itemObject.GetItemID() === index && status === 1){
                itemObject.Show();
            }
        }
    },


    /**
     * Json에서 읽어온 코드 뭉치를 게임에 적용하여 플레이 합니다.
     * 실제 사용되는 함수입니다.
     * @constructor
     */
    OnCodePlay: function(){
        if(this.isPlay) return;
        var self = this;
        this.isPlay = true;
        this.idx = Controller.getInstance().GetProgressId();

        var inter = setInterval(function(){

            if(Controller.getInstance().isGamePause){
                var a =  Controller.getInstance().GetProgressId();
                self.idx = a;
                return;
            }

            self.player.getComponent("Player").ResetInint();

            if(self.executeCommand(self.idx) === false){
                /**
                 * Reset 버튼 체크하는 Interval
                 */
                self.ResetDetector();
                clearInterval(inter);
                self.isPlay = false;
                Controller.getInstance().SetStatus(false);

            }
            self.idx++;
        }, 1000/60);
    },



    /**
     * TMD 리셋 요청 코드로 인해 만든 함수 
     * 코드 종료후 Reset 버튼 클릭시 최초 상태로 되돌리기위한 함수. 
     */
    ResetDetector: function(){
        var self = this;
        var inter = setInterval(function(){

            var command = Controller.getInstance().getCommandLine(self.idx);

            if( command === -1 && Controller.getInstance().isGameReset){

                self.executeCommand(0)
                self.player.getComponent("Player").ResetPlayer();
                self.OnCodePlay();
                clearInterval(inter);
            }
            
        }, 1000/60);
    },


});
