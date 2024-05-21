



const SoundManager = require("../GameLogic/SoundManager");
const Manager2 = require("Manager2");

cc.Class({
    extends: cc.Component,

    properties: {
        id: -1,
        isAnswer: false,

        // 정답이라면 정답 인덱스값도 가지고 있어야함.
        answerIndex: -1,
        isInit: false,
        label: cc.Label,

        isClicked : false,

        intervalID: null,

        cardManager: null,
    },
   
    /**
     * 몹최초에 초기화 처리해줄때 
     * @param {*} id json_id 
     * @param {*} str  문자열처리
     * @param {*} isAnswer  만약 정답이라면 true, 기본값 false.
     */
    InitMob: function(id, str, answertNumber = -1 ,  isAnswer = false ){
        this.isAnswer = isAnswer;
        this.id = id;
        this.label.string = str;
        this.answerIndex = answertNumber;
        this.node.active = false;
        this.isClicked = false;

        clearInterval(this.intervalID);
    },

    ShowMonster: function(){
        // if(this.node.active) return;

        this.node.active = true;
        this.node.opacity = 255;
        
        this.node.getComponent(cc.Animation).play('mob_show');
    },


    onLoad() {
        // 터치 이벤트 리스너 추가
        this.node.on(cc.Node.EventType.TOUCH_START, this.onTouchStart, this);
        this.node.on(cc.Node.EventType.MOUSE_DOWN, this.onTouchStart, this);


        this.cardManager = cc.find("Game1/quest_title/card_parent");
    },

    onTouchStart(event) {
        if(this.isClicked) return;

        this.isClicked = true;
        this.label.string = '';
        this.node.getComponent(cc.Animation).play('mob_hit');

        if(this.isAnswer){
            Manager2.getInstance().GetIt(this.answerIndex);
            this.cardManager.getComponent("CardManager").ShowCards(this.answerIndex);
            SoundManager.getInstance().PlaySfx(Env.SFX_MINIGAME_2_CORRECT);
        }
        else{
            this.cardManager.getComponent("CardManager").ShakeIt();
            SoundManager.getInstance().PlaySfx(Env.SFX_MINI_2_ERROR);
        }
        


        SoundManager.getInstance().PlaySfx(Env.SFX_MINI_2_HIT);
        var self = this;

        this.intervalID = setTimeout(function(){
            self.isClicked = false;
            self.node.active = false;
        },1500);
    },

});
