

const IDLE = -1;
const ATK = -6;
const HIT = 2;
const DEAD = -8;



const ANIMATION_IDLE = 0;
const ANIMATION_HIT = 1;
const ANIMATION_ATK = 2;




cc.Class({
    extends: cc.Component,

    properties: {

        special_atk_particle: cc.Node,
        aniArray: [],

        explosionPrefabs : cc.Node,

        hpBar: cc.ProgressBar,
        barBackground: cc.Node,

        maxHP: 0,

        init_json : null,

        isDead: false,

        deadIntervalID: null,
        timout: null,
    },


    start(){
        this.aniArray = [ 'boss_left_idle' ,'boss_left_hit','boss_left_atk'];
    },


    InitBoss: function(json, init_pos){
        this.id = json.id;
        this.init_json_data = json;

        

        this.InitDeadStatus();
    },

    ShowExplosion: function(){
        this.explosionPrefabs.getComponent(cc.Animation).play("explosion");
    },

    UpdateStatus: function(status){

        switch(status){
            case IDLE:
                this._SetMonsterAnimation(ANIMATION_IDLE);
                break;
            
            case ATK:
                this._SetMonsterAnimation(ANIMATION_ATK);
                break;
            
            case HIT:
                
                this._SetMonsterAnimation(ANIMATION_HIT);
                break;
        }


    },

    InitDeadStatus: function(){
        this.isDead = false;
        this.node.opacity = 255;

        clearInterval( this.deadIntevalID);
        clearInterval( this.timout);
    },

    /**
     * BOSS 애니메이션
     * @param {*} ANIMATION_NUMBER 
     */
    _SetMonsterAnimation: function(animation_number){

        var hit_ani = this.aniArray[ANIMATION_HIT];
        var atk_ani = this.aniArray[ANIMATION_ATK];

        var hit_c = this.getComponent(cc.Animation);
        var atk_c = this.getComponent(cc.Animation);

        var hit_animation = hit_ani;
        var atk_animation = atk_ani;

        var hit_state = hit_c.getAnimationState(hit_animation);

        var atk_state = atk_c.getAnimationState(atk_animation);


        // 예외 처리 동작중이면..
        if(hit_state.isPlaying || atk_state ) return;


        
        var clip = this.getComponent(cc.Animation);
        var animationName = this.aniArray[animation_number];

        var upState = clip.getAnimationState(animationName);
        var isPlaying = upState.isPlaying;

        if(!isPlaying){

            if(animationName === HIT){
                this.ShowExplosion();
            }

            clip.play(animationName);
        }

    },

    _UpdateHP: function(hp){
        if(this.monster_type !== AGGREE_2) return;
        
        this.hpBar.progress = hp / this.maxHP;

        if(this.hpBar.progress > 0.6){
            this.barBackground.color = new cc.Color(0, 255, 0);
        }
        else{
            this.barBackground.color = new cc.Color(255, 0, 0);
        }
    },

    _InitHP: function(){
        this.hpBar.progress = this.maxHP;
        this.barBackground.color = new cc.Color(0, 255, 0);
    },


    DeadAnimation: function(){
        if(this.isDead) return;

        this.isDead = true;

        if (this.direction === DIR_RIGHT) {
            this._SetMonsterAnimation(this.monster_number, HIT_RIGHT);
        }
        else {
            this._SetMonsterAnimation(this.monster_number, HIT_LEFT);
        }

        var self = this;

        this.ShowExplosion();

        this.timout = setTimeout(function() {
            self.deadIntervalID = setInterval(function() {
                self.node.opacity -= 9; // 먼저 투명도를 감소시킵니다.
                if (self.node.opacity < 1) {
                    clearInterval(self.deadIntervalID); // 투명도가 5 미만이면 인터벌 중지
                }
            }, 10);
        }, 300);
        
    },

});
