

/**
 * 애니메이션에서 사용되는 전역 변수
 */
const IDLE_LEFT = 0;
const IDLE_RIGHT = 1;
const HIT_LEFT = 2;
const HIT_RIGHT = 3;
const ATK_LEFT = 4;
const ATK_RIGHT = 5;
const RANGE_ATK_LEFT = 6;
const RANGE_ATK_RIGHT = 7;

const MONSTER_RED = 0;
const MONSTER_YELLOW = 1;


const Controller = require("Controller");

cc.Class({
    extends: cc.Component,

    properties: {
        aniArray : {
            default: []
        },
    },

    ctor() {
        // 생성자에서 배열 초기화
        this.aniArray = [
            ['idle_left_red', 'idle_right_red', 'hit_left_red', 'hit_right_red', 'atk_left_red', 'atk_right_red','r_atk_left_red', 'r_atk_right_red'],
            ['idle_left_yel', 'idle_right_yel', 'hit_left_yel', 'hit_right_yel', 'atk_left_yel', 'atk_right_yel','r_atk_left_yel', 'r_atk_right_yel']
        ];

    },

    /**
     * 플레이어가 이동합니다.
     * @param pos 이동될 포지션 값
     * @constructor
     */
    Movement: function(pos, status){
        this.changeSpriteDirection();
        this.node.setPosition(pos);
    },


    changeStatus: function(status){
        

    },

    convertToAnimationNumber: function(status){
        switch(status){
            case -1 : return 0;
        }

        return 0;
    },


    /**
     * MONSTER 애니메이션
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

   



});
