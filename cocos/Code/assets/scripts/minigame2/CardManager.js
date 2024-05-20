
const SoundManager = require("../GameLogic/SoundManager");


cc.Class({
    extends: cc.Component,

    properties: {

        errorPage: cc.Node,
        camera: cc.Camera,

        cards: {
            
            default: [],
            type : cc.Node,
        },


        answerArray: [],
    },
    

    start () {

    },

    HideCards: function(){
        for(var i = 0; i < this.cards.length; i++){
            this.cards[i].getComponent(cc.Animation).play("card_off");
            this.cards[i].getChildByName('label').getComponent(cc.Label).string = '';

            this.cards[i].active = false;
        }
    },


    InitCards: function(answer){

        this.answerArray = answer;

        for(var i = 0; i < answer.length; i++){
            this.cards[i].getComponent(cc.Animation).play("card_off");
            this.cards[i].getChildByName('label').getComponent(cc.Label).string = '';
            this.cards[i].active = true;
        }
    },


    ShowCards: function(idx){

        
        console.log("call load --> "+idx);

        var label = this.cards[idx].getChildByName('label');
        label.getComponent(cc.Label).string = this.answerArray[idx];
        this.cards[idx].getComponent(cc.Animation).play('card_on');
    },



        /**
     * 유저가 잘못된 클릭을 했을때 효과
     * @constructor
     */

    ShakeIt : function() {
        var magnitude = 15.2;
        let shakes = [];

        var originalPosition = this.camera.node.position;


        SoundManager.getInstance().PlaySfx(Env.SFX_ERROR);
        this.errorPage.active = true;

        for (let i = 0; i < 4; i++) {
            // 무작위 위치 변경
            let shakeX = (Math.random() - 0.5) * 2 * magnitude;
            let shakeY = (Math.random() - 0.5) * 2 * magnitude;
            let delay = cc.delayTime(0.02);  // 쉐이크 간격
            let move = cc.moveBy(0.02, cc.v2(shakeX, shakeY));
            let restore = cc.moveBy(0.02, cc.v2(-shakeX, -shakeY));
            shakes.push(delay, move, restore);
        }

        // 모든 쉐이크 후에 카메라 원래 위치로 복귀
        let restoreOriginal = cc.callFunc(() => {
            this.errorPage.active = false;
            this.camera.node.position = originalPosition;
        });

        // 시퀀스 생성 및 실행
        let sequence = cc.sequence(shakes[0],shakes[1],shakes[2],shakes[3], restoreOriginal);
        this.camera.node.runAction(sequence);

    },

});
