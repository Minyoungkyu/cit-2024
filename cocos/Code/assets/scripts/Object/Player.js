

/**
 * 애니메이션에서 사용되는 전역 변수
 */
const IDLE_LEFT = 0;
const IDLE_RIGHT = 1;
const IDLE_UP = 2;
const IDLE_DOWN = 3;
const RUN_LEFT = 4;
const RUN_RIGHT = 5;
const RUN_UP = 6;
const RUN_DOWN = 7;
const HIT_LEFT = 8;
const HIT_RIGHT = 9;
const ATK_LEFT = 10;
const ATK_RIGHT = 11;
const JUMP_LEFT = 12;
const JUMP_RIGHT = 13;
const JUMP_UP = 14;
const JUMP_DOWN = 15;



/**
 * TODO
 * 플레이어 상태값에 대한 CONST 처리 필요.
 */

const STATUS_JUMP = 0;


const Controller = require("Controller");

cc.Class({
    extends: cc.Component,

    properties: {
        // 최초 포지션, 포지션 정보가 저장됩니다.
        // replay 해당 포지션으로 초기화 됩니다.
        initPosition: null,

        // 플레이어가 바라보고 있는 방향입니다.
        direction : Env.DIRECTION_UP,

        // 말풍선 객체입니다.
        bubble: cc.Node,
        // 말풍선 내부에 있는 텍스트 객체입니다.
        bubbleLabel: cc.Label,

        // 메시지가 보여지고있는 상태입니다.
        isShowMessage : false,

        // 죽었음을 표현
        playerIsDead: false,
        playerStatusInfo: 0,


        audioFootStep:{
            default : [],
            type: cc.AudioClip,
        },

        
        audioStep: 0,
        isPlaySound : false,

        isBombAnimation : false,

        /**
         * Reset용 스테이터스
         */
        isResetStatus: false,

        /**
         * 큰 번호
         * 0번 남성 노말
         * 1번 남성 슈트
         * 2번 여성 노말
         * 3번 여성 슈트
         */
            

        /**
         * 세부
         * 0 idle_left
         * 1 idle_right
         * 2 idle_up
         * 3 idle_down
         * 4 run_left
         * 5 run_right
         * 6 run_up
         * 7 run_down
         * 8 hit_left
         * 9 hit_right
         * 10 atk_left
         * 11 atk_right
         * 12 jump_left
         * 13 jump_right
         * 14 jump_up
         * 15 jump_down
         */
        aniArray : {
            default: []
        },


        // Message Detect
        isRunningDetector: false,
        // Messagedetetor ID
        mdID: null,
    

    },

    ctor() {
        // 생성자에서 배열 초기화
        this.aniArray = [
            ["idle_left_m", "idle_right_m", "idle_up_m", "idle_down_m", "run_left", "run_right", "run_up", "run_down", "hit_l_m", "hit_r_m", "", "", "", "", "", ""],
            ["idle_left_mh", "idle_right_mh", "idle_up_mh", "idle_down_mh", "run_left_mh", "rum_right_mh", "rum_up_mh", "run_down_mh", "hit_left_mh", "hit_right_mh", "atk_left_mh", "atk_right_mh", "jump_left_mh", "jump_right_mh", "jump_up_mh", "jump_down_mh"],
            ["idle_left_w", "idle_right_w", "idle_up_w", "idle_down_w", "run_left_w", "run_right_w", "run_up_w", "run_down_w", "hit_left_w", "hit_right_w", "", "", "", "", "", ""],
            ["left_idle", "right_idle", "idle_back", "idle_front", "leftRun", "rightRun", "upRun", "downRun", "hit_left", "hit_right", "attack_left", "attack_right", "jump_left", "jump_right", "jump_up", "jump_down"]
        ];




    },


    ResetInint : function(){
        this.isResetStatus = false;
    },

    ResetPlayer: function(){

        if(this.isResetStatus) return;
        
        this.isResetStatus = true;


        if(this.direction === Env.DIRECTION_LEFT){
            // idle_left 애니메이션 적용 예정
            this.ForcePlayerAnimation(IDLE_LEFT);

            // this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_LEFT);
        }
        else if(this.direction === Env.DIRECTION_RIGHT){
            // idle_right 애니메이션 적용 예정
            this.ForcePlayerAnimation(IDLE_RIGHT);
            // this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_RIGHT);
        }
        else if(this.direction === Env.DIRECTION_UP){
            // this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_UP);
            this.ForcePlayerAnimation(IDLE_UP);

        }
        else if(this.direction === Env.DIRECTION_DOWN){

            this.ForcePlayerAnimation(IDLE_DOWN);
            // this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_DOWN);
        }
    },

    /**
     * 플레이어가 이동합니다.
     * @param pos 이동될 포지션 값
     * @constructor
     */
    Movement: function(pos){
        this.changeSpriteDirection();
        this.node.setPosition(pos);

    },

    PlayFootStep: function(){
        if(this.isPlaySound) return;

        this.isPlaySound = true;
        var self = this;
        setTimeout(()=>{
            self.isPlaySound = false;
        },300);


        var audioClip = this.audioFootStep[this.audioStep]; // 오디오 클립 가져오기
        cc.audioEngine.play(audioClip, false, 1, function () {
            // 오디오가 종료될 때 호출되는 콜백 함수
            // 다음에 재생할 오디오 클립 인덱스 업데이트
            if (this.audioStep === 1)
                this.audioStep = 0;
            else
                this.audioStep = 1;
        }.bind(this));
    },

    /**
     * Player 애니메이션 로드
     * @param {*} ANIMATION_NUMBER 
     */

    setPlayerAnimation: function(animation_number){
        var number = Controller.getInstance().getCharNumber();
        var clip = this.getComponent(cc.Animation);
        var animationName = this.aniArray[number][animation_number];

        var upState = clip.getAnimationState(animationName);
        var isPlaying = upState.isPlaying;

        if(!isPlaying){
            clip.play(animationName);
        }

    },

    ForcePlayerAnimation: function(animation_number){
        var number = Controller.getInstance().getCharNumber();
        var clip = this.getComponent(cc.Animation);
        var animationName = this.aniArray[number][animation_number];

        var upState = clip.getAnimationState(animationName);
        clip.play(animationName);

    },



    /**
     * 현재 방향에 따른 Scale 변경
     * 로테이션 처리
     */
    changeSpriteDirection: function(){

        var animationClip = this.getComponent(cc.Animation)
        // RUN 상태

        var isPlaying = false;
        if(this.playerStatusInfo  < 2){
            this.PlayerInitAnimation();
        }

        if(this.playerStatusInfo === 1){

            switch (this.direction){

                case Env.DIRECTION_UP:

                        this.setPlayerAnimation(RUN_UP);
                        // animationClip.play(Env.ANIMATION_UP);
                    break;
                case Env.DIRECTION_DOWN:
                        this.setPlayerAnimation(RUN_DOWN);
                    break;
                case Env.DIRECTION_RIGHT:
                        this.setPlayerAnimation(RUN_RIGHT);
                    break;
                case Env.DIRECTION_LEFT:
                        this.setPlayerAnimation(RUN_LEFT);
                    break;
            }
            // 효과음 추가.
            this.PlayFootStep();
        }
        else if(this.playerStatusInfo === 11){
            if(this.isBombAnimation) return;


            this.isBombAnimation = true;
            // 폭탄에 맞음
            if (this.direction === Env.DIRECTION_LEFT) {
                this.setPlayerAnimation(HIT_LEFT);
            } else {
                this.setPlayerAnimation(HIT_RIGHT);
            }

            var self = this;
            setTimeout(function(){
                self.isBombAnimation = false;
            }, 1000);

        }
        else if(this.playerStatusInfo === 21){
            // 점프상태

            if(this.direction === Env.DIRECTION_LEFT){
                // idle_left 애니메이션 적용 예정
                this.setPlayerAnimation(JUMP_LEFT);

                // this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_LEFT);
            }
            else if(this.direction === Env.DIRECTION_RIGHT){
                // idle_right 애니메이션 적용 예정
                this.setPlayerAnimation(JUMP_RIGHT);
                // this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_RIGHT);
            }
            else if(this.direction === Env.DIRECTION_UP){
                // this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_UP);
                this.setPlayerAnimation(JUMP_UP);

            }
            else if(this.direction === Env.DIRECTION_DOWN){

                this.setPlayerAnimation(JUMP_DOWN);
                // this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_DOWN);
            }
        }
        else{
            if(this.playerStatusInfo !== 9){
                // 방향이 왼쪽을 제외하곤 다 오른쪽 보도록
                if(this.direction === Env.DIRECTION_LEFT){
                    // idle_left 애니메이션 적용 예정
                    this.setPlayerAnimation(IDLE_LEFT);

                    // this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_LEFT);
                }
                else if(this.direction === Env.DIRECTION_RIGHT){
                    // idle_right 애니메이션 적용 예정
                    this.setPlayerAnimation(IDLE_RIGHT);
                    // this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_RIGHT);
                }
                else if(this.direction === Env.DIRECTION_UP){
                    // this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_UP);
                    this.setPlayerAnimation(IDLE_UP);

                }
                else if(this.direction === Env.DIRECTION_DOWN){

                    this.setPlayerAnimation(IDLE_DOWN);
                    // this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_DOWN);
                }
            }
        }
    },


    /**
     * 플레이어 객체가 생성되면 최초로 해당 함수를 불러 초기화 해야합니다.
     * @param v2  최초 포지션 인덱스를 가지고 옵니다.
     * @param DIR  최초 플레이어가 바라보고 있는 방향
     */
    Init: function(v2 , DIR){
        this.initPosition = v2;
        this.node.setPosition(v2);
        this._HideBubble();
        this.PlayerInitAnimation();

        this.node.zIndex = cc.macro.MAX_ZINDEX - 1;

        var defaultDIR = 0;
        switch (DIR){
            case Env.DIRECTION_LEFT: defaultDIR = Env.PLAYER_DEFAULT_LEFT; break;
            case Env.DIRECTION_RIGHT: defaultDIR = Env.PLAYER_DEFAULT_RIGHT; break;
            case Env.DIRECTION_UP: defaultDIR = Env.PLAYER_DEFAULT_UP; break;
            case Env.DIRECTION_DOWN: defaultDIR = Env.PLAYER_DEFAULT_DOWN; break;
        }


        if(defaultDIR === Env.PLAYER_DEFAULT_LEFT){

            this.setPlayerAnimation(IDLE_LEFT);
            // this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_LEFT);
        }
        else if(defaultDIR === Env.PLAYER_DEFAULT_UP){
            // 위로 보고있을때
            // this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_UP);

            this.setPlayerAnimation(IDLE_UP);
        }
        else if(defaultDIR === Env.PLAYER_DEFAULT_DOWN){
            // 아래보고있을때.
            // this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_DOWN);
            this.setPlayerAnimation(IDLE_DOWN);
        }
        else if(defaultDIR === Env.PLAYER_DEFAULT_RIGHT){
            // this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_RIGHT);
            this.setPlayerAnimation(IDLE_RIGHT);
        }
    },

    /**
     * 플레이어 정보를 초기화 하는 함수입니다.
     */
    playerInit : function(){
        this.node.setPosition(this.initPosition);
        this.direction = Env.DIRECTION_RIGHT;
        this.changeSpriteDirection();
    },

    /**
     * 플레이어 바라보고 있는 방향을 설정합니다.
     * @param dir 플레이어가 바라보는 방향정보
     */
    setDirection : function(dir){
        this.direction = dir;
    },

    /**
     * 플레이어 상태값을 입력받아
     * 말풍선, 죽음, 플레이어상태 표현 출력
     * @param status
     */
    setPlayerStatus: function(status){

        this.playerStatusInfo = status;
        if(this.playerStatusInfo < 2 ) return;


        


        switch (status) {
            case 9:
                // 플레이어 죽음.
                this.PlayerDealAnimation();
                break;
            case 10:
                // 해당방향으로 이동하지못함.
                this.ShowMessage("이 방향으로 이동할수 없어!");
                break;
            case 13:
                // Set 명령어 시도중 방향이 다를경우
                this.ShowMessage("이곳에서 할수 없는 명령어야!");
                break;
            case 14:
                this.ShowMessage("문자열이 잘못 입력되었어.");
                break;

            case 15:
                this.ShowMessage("고체추진제 장착!");
                // 고체추진제
                break;
            case 16:
                // 액체연료
                this.ShowMessage("액체연료 장착!");
                break;
            case 17:
                // 추가엔진
                this.ShowMessage("추가 엔진 장착!");
                break;
            case 19:
                // 출력처리
                this.ShowPrintingBubble();
                break;
            
            case 22:
                this.ShowMessage("점프하기엔 위험해");
                break;

            case 25:
                this.ShowMessage("회복 아이템이 없어!");
                break;

            case 18: 
                this.ShowMessage("잘못된 명령어야!");
                break;
            case 23:
                this.ShowMessage("윽.. ");
                    break;
            case 24:
                this.ShowMessage("살것같아!");
                    break;
            case 27:
                this.ShowMessage("list가 비어있는데?");
                        break;
            case 29:
                this.ShowMessage("타입이 틀린거같아");
                        break;

            case 31:
                this.ShowMessage("폭탄이 없어!");
                        break;

            case 37:
                this.ShowMessage("폭탄설치중");
                        break;

            case 39:
                this.ShowMessage("중력이 너무강해서 점프가 어려워");
                    break;
        }


        this.ProcessEncryptWord(this.playerStatusInfo);



    },

    /**
     * GetInfo했을때 처리되는 곳.
     * 이곳은 playerStatus 100 보다 작으면 처리되지않음
     * @param {*} playerStatus
     * 
     */
    ProcessEncryptWord: function(playerStatus){
        if(playerStatus < 100) return;

        // Init 데이터 배열 에 해당하는 값 표현!
        // "··" 암호획득

        var idx = playerStatus -100;


        var initPoint = Controller.getInstance().getInitPrintPointArray(idx);

        var str = initPoint + " 암호획득";

        this.ShowMessage(str);

    },

    
    /**
     * 유저가 프린트 하고자할때 처리됨.
     */
    ShowPrintingBubble: function(){

        /// TODO 3-1-{}-1 스테이지 몬스터 print는 따로 예외 처리가 필요함.

        var currentID = Controller.getInstance().getCurrentCommandID();
        var item_list = Controller.getInstance().getStreamItemList(currentID).item_list;
        var print_array =  item_list[0].print_array;

        var print_data = '';
        for(var i = 0; i < print_array.length; i++){
            print_data += ( print_array[i] + " ");
        }
        var convert = print_data.toString();
        this.PrintMessage(convert);
    },

    /**
     * 
     * 일반 ShowMessage와 동일하나,
     * 일부 구조 다름.
     * @param {Print_array} str 
     */
    PrintMessage: function(str){
        this.bubbleLabel.string = str;
        this._ShowBubble();

        this.MessageDetector();
    },


    
    /**
     * 메시지 표현 탐지 Interval 함수
     * 기존 메시지를 띄우기 보단 
     * print 중인 현재 팝업을 보여줘야하는데.
     * 이걸 Interval로 체크하는 함수
     */
    MessageDetector: function(){
        if(this.isRunningDetector) return;
        this.isRunningDetector = true;

        var self = this;
        this.mdID = setInterval(function(){
            if(self.playerStatusInfo != 19){

                setTimeout(function(){
                    self._HideBubble();
                },500);
                self.InitMessageDetector();
            }

        },30);
    },

    /**
     * 초기화 처리.
     * 추후 다른곳에 필요할지도..?
     */
    InitMessageDetector: function(){
        this.isRunningDetector = false;
        clearInterval(this.mdID);

    },


    /**
     * 메시지를 띄워줍니다.
     * ex) 플레이어가 이동불가, 특별한 상태 메시지를 띄워줍니다.
     * @param label 표기될 텍스트
     */
    ShowMessage: function(label){
        if(this.isShowMessage) return;

        this.bubbleLabel.string =  label;
        this._ShowBubble();

        var self = this;
        setTimeout(function(){
            self._HideBubble();
        }, 600); // 2000 밀리초 = 2초
    },


    /**
     * 메시지를 감춥니다.
     * @private
     */
    _HideBubble: function(){
        this.isShowMessage = false;
        this.bubble.active = false;
    },

    /**
     * 메시지를 보여줍니다.
     * @private
     */
    _ShowBubble: function(){
        this.isShowMessage = true;
        this.bubble.active = true;
    },

    /**
     * 플레이어 애니메이션 초기화.
     * @constructor
     */
    PlayerInitAnimation: function(){
        if(this.playerIsDead === false) return;
        this.playerIsDead = false;
        this.node.opacity = 255;

    },

    /**
     * 플레이어 죽었을때 애니메이션 처리.
     * @constructor
     */
    PlayerDealAnimation: function(){
        if(this.playerIsDead) return;
        this.playerIsDead = true;

        var self = this;
        var animationClip = this.getComponent(cc.Animation);
        // if (this.direction === Env.ANIMATION_LEFT) {
        //     animationClip.play(Env.ANIMATION_LEFT_HIT);
        // } else {
        //     animationClip.play(Env.ANIMATION_RIGHT_HIT);
        // }
        var pInterval = setInterval(function(e){
            if(self.node.opacity < 1){
                clearInterval(pInterval);
            }
            self.node.opacity -= 10;
        }, 20);
    },



});
