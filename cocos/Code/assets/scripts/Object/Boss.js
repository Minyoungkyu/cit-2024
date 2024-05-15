

const IDLE = -1;
const ATK = -6;
const HIT = -4;
const DEAD = -8;



const ANIMATION_IDLE = 0;
const ANIMATION_HIT = 1;
const ANIMATION_ATK = 2;




const SoundManager = require("../GameLogic/SoundManager");

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


    InitBoss: function(json){
        this.id = json.id;
        this.init_json_data = json;
        this.InitDeadStatus();
        this.HideSpecialAttack();
    },


    /**즉사 공격 이펙트 표현 */
    SpecialAttack: function(){
        if(this.special_atk_particle.active) return;
            
        this.special_atk_particle.active = true;


        SoundManager.getInstance().PlaySfx(Env.SFX_BOSS_SPECIAL);
    },

    HideSpecialAttack: function(){
        this.special_atk_particle.active = false;
    },

    /**
     * 
     */
    ShowExplosion: function(){
        this.explosionPrefabs.getComponent(cc.Animation).play("explosion");
    },

    /**
     * 업데이트 상태 표시 전환
     * @param {*} status 
     */
    UpdateStatus: function(status){

        // console.log("boss -> "+status);


        switch(status){
            case IDLE:
                this._SetMonsterAnimation(ANIMATION_IDLE);
                this.InitDeadStatus();
                break;
            
            case ATK:
                this._SetMonsterAnimation(ANIMATION_ATK);
                break;
            
            case HIT:
                this._SetMonsterAnimation(ANIMATION_HIT);
                break;

            case DEAD:
                this.DeadAnimation();
                break;
        }
    },


    /**
     *  Dead Status 초기화 해주는곳.
     * 
     * @returns 
     */
    InitDeadStatus: function(){
       

        if(this.isDead == false) return;
        this.isDead = false;
        this.node.opacity = 255;
        this.HideSpecialAttack();

        clearInterval( this.deadIntevalID);
        clearInterval( this.timout);
    },

    /**
     * BOSS 애니메이션
     * @param {*} ANIMATION_NUMBER 
     */
    _SetMonsterAnimation: function(animation_number){
        var animationName = this.aniArray[animation_number];

        var clip = this.getComponent(cc.Animation);

        var upState = clip.getAnimationState(animationName);
        var isPlaying = upState.isPlaying;

        if(!isPlaying){
            if(animationName === 'boss_left_hit'){
                this.ShowExplosion();
            }
            clip.play(animationName);
        }

    },

    /**
     * 미사용
     * HP 처리 사용
     * @param {*} hp 
     * @returns 
     */
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

    /**
     * 미사용, HP 처리 필요
     */
    _InitHP: function(){
        this.hpBar.progress = this.maxHP;
        this.barBackground.color = new cc.Color(0, 255, 0);
    },


    /**
     * 보스 죽었을때 애니메이션 처리
     */
    DeadAnimation: function(){
        if(this.isDead) return;

        this.isDead = true;
        this._SetMonsterAnimation(ANIMATION_HIT);
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
