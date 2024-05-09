

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




const STATE_IDLE = -1;
const STATE_PRINT_ERROR = -2;
const STATE_PRINTING =  -3;
const STATE_MOVE = -5;
const STATE_ATTACK = -6;
const STATE_RANGE_ATTACK = 1;
const STATE_TURN = -7; // passive monster
const STATE_DEAD = -8;
const STATE_WAIT = -9;
const NONE = -10 // 사용안함.
const STATE_ATTACK_MELLE = -11; // aggressive_monster_1 근접공격


const DIR_LEFT = 0;
const DIR_RIGHT = 1;
const DIR_NONE = 2;

const PASSIVE = 0;
const AGGREE_1 = 1;
const AGGREE_2 = 2;


cc.Class({
    extends: cc.Component,

    properties: {
        aniArray : {
            default: []
        },

        monster_type : PASSIVE,
        id: 0,
        direction : '',
        status: '',
        init_position : null,

        init_json_data: null,
        monster_number : 0,
        isDead : false,

        deadIntevalID: null,
        timout : null,

        explosionPrefabs: cc.Node,
    },

    start(){
        this.aniArray = [
            ['idle_left_red', 'idle_right_red', 'hit_left_red', 'hit_right_red', 'atk_left_red', 'atk_right_red','r_atk_left_red', 'r_atk_right_red'],
            ['idle_left_yel', 'idle_right_yel', 'hit_left_yel', 'hit_right_yel', 'atk_left_yel', 'atk_right_yel','r_atk_left_yel', 'r_atk_right_yel']
        ];

    },

    /**
     * 현재 몬스터에 해당되는 ID값
     * @returns ID
     */
    GetID: function(){
        return this.id;
    },

    InitMonster: function(json, init_pos){

        this.init_position = init_pos;
        this.id = json.id;


        console.log("만들어진 id > "+ this.id);


        this.init_json_data = json;
        if(json.type === 'aggressive_monster_2'){
            this._SetMonsterAnimation(0,IDLE_LEFT);
            this.monster_number = MONSTER_RED;
            this.direction = DIR_NONE;
            this.monster_type = AGGREE_2;
        }
        else if(json.type === 'passive_monster'){
            if(json.dir === 'left'){
                this._SetMonsterAnimation(1,IDLE_LEFT);
                this.direction  = DIR_LEFT;
            }
            else if(json.dir === 'right' ){
                this._SetMonsterAnimation(1,IDLE_RIGHT);
                this.direction = DIR_RIGHT;
            }
            this.monster_number = MONSTER_YELLOW;
            this.monster_type = PASSIVE;
        }
        else if(json.type == 'aggressive_monster_1'){
            this._SetMonsterAnimation(1,IDLE_LEFT);
            this.direction = DIR_NONE;
            this.monster_number = MONSTER_YELLOW;
            this.monster_type = AGGREE_1;
        }

        this.InitDeadStatus();
    },

    ShowExplosion: function(){
        this.explosionPrefabs.getComponent(cc.Animation).play("explosion");
    },

    /**
     * 몬스터의 status값을 받아 해당되는 애니메이션 처리를 진행하면됨.
     * @param {status} status
     */
    UpdateStatus: function(status){

        if(typeof status != "number" ){
          
            var sub_status = status.status;
            switch(sub_status){
                case STATE_IDLE:
                    this.node.opacity = 255;
                    var ani = IDLE_LEFT;
                    if(this.direction === DIR_RIGHT){
                        ani = IDLE_RIGHT;
                    }
                    this._SetMonsterAnimation(this.monster_number, ani ) ;
                    this.InitDeadStatus();
                    break;
                

                case STATE_ATTACK:
                    if(this.monster_type == AGGREE_1){

                        var ani = RANGE_ATK_LEFT;

                        if(status.dir === 'right'){
                            ani = RANGE_ATK_RIGHT;
                        }
                        this._SetMonsterAnimation(this.monster_number, ani);
                    }
                break;

                case STATE_WAIT:
                    this.Movement(this.init_position);
                    var ani = IDLE_LEFT;
                    if(this.direction === DIR_RIGHT){
                        ani = IDLE_RIGHT;
                    }
                    this._SetMonsterAnimation(this.monster_number, ani ) ;
                    this.InitDeadStatus();
                    break;
            }
            

        }
        else{
            switch(status){
                case STATE_IDLE :
                    this.node.opacity = 255;
                    var ani = IDLE_LEFT;
                    if(this.direction === DIR_RIGHT){
                        ani = IDLE_RIGHT;
                    }
                    this._SetMonsterAnimation(this.monster_number, ani ) ;
                    this.InitDeadStatus();

                     break;
                case STATE_PRINTING :
                    var ani = HIT_LEFT;
                    if(this.direction === DIR_RIGHT){
                        ani = HIT_RIGHT;
                    }
                    this._SetMonsterAnimation(this.monster_number, ani ) ;
                    break;
                case STATE_PRINT_ERROR :
                    console.log("PRINT_ERROR");
                    break;
                case  STATE_MOVE : break;
                case  STATE_ATTACK: case STATE_ATTACK_MELLE:

                    if(this.monster_type == PASSIVE ){
                        var ani = ATK_RIGHT;
                        if(this.direction === DIR_RIGHT){
                            ani = ATK_LEFT;
                        }
                        this._SetMonsterAnimation(this.monster_number, ani) ;
                    }
                    else{
                        var ani = ATK_LEFT;
                        if(this.direction === DIR_RIGHT){
                            ani = ATK_RIGHT;
                        }
                        this._SetMonsterAnimation(this.monster_number, ani) ;
                    }
                     break;
                case STATE_WAIT:
                    this.Movement(this.init_position);
                    var ani = IDLE_LEFT;
                    if(this.direction === DIR_RIGHT){
                        ani = IDLE_RIGHT;
                    }
                    this._SetMonsterAnimation(this.monster_number, ani ) ;
                    this.InitDeadStatus();
                    break;
                case  STATE_RANGE_ATTACK: 
                    break;

                // case  STATE_TURN :
                //     var ani = IDLE_RIGHT;
                //     if(this.direction === DIR_RIGHT){
                //         ani = IDLE_LEFT;
                //     }
                //     this._SetMonsterAnimation(this.monster_number, ani ) ;
                //      break;    
                case  STATE_DEAD: 
                    if(this.IsDeadMonster()) break;
                    this.MonsterDeadAnimation();
                   
                    break;
                case  NONE:
                    break;
            }
        }
    },

    InitDeadStatus: function(){
        this.isDead = false;
        this.node.opacity = 255;

        clearInterval( this.deadIntevalID);
        clearInterval( this.timout);
    },

    IsDeadMonster : function(){
        return this.isDead;
    },


    /**
     * 플레이어가 이동합니다.
     * @param pos 이동될 포지션 값
     * @constructor
     */
    Movement: function(pos, dir){
        
        console.log("연결 -> "+this.id);

        var ani = IDLE_LEFT;
        if(dir == 'right'){
            ani = IDLE_RIGHT;
        }

        this._SetMonsterAnimation(this.monster_number,ani);
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

    _SetMonsterAnimation: function(number,animation_number){
        
        var clip = this.getComponent(cc.Animation);
        var animationName = this.aniArray[number][animation_number];

        var upState = clip.getAnimationState(animationName);
        var isPlaying = upState.isPlaying;

        if(!isPlaying){
            clip.play(animationName);
        }

    },

   

    MonsterDeadAnimation: function(){
        if(this.isDead) return;

        this.isDead = true;

        if (this.direction === DIR_RIGHT) {
            this._SetMonsterAnimation(this.monster_number, HIT_RIGHT);
        }
        else {
            this._SetMonsterAnimation(this.monster_number, HIT_LEFT);
        }

        var self = this;


        this.timout = setTimeout(function() {
            self.deadIntervalID = setInterval(function() {
                self.node.opacity -= 9; // 먼저 투명도를 감소시킵니다.
                if (self.node.opacity < 1) {
                    clearInterval(self.deadIntervalID); // 투명도가 5 미만이면 인터벌 중지
                }
            }, 30);
        }, 300);
        
    },



});
