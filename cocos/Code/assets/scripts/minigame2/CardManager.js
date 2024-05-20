cc.Class({
    extends: cc.Component,

    properties: {

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

});
