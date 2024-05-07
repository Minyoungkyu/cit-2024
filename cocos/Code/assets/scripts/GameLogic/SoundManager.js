var SoundManager = cc.Class({
    extends: cc.Component,

    statics: {
        _instance: null,
        getInstance: function () {
            if (!SoundManager._instance) {
                SoundManager._instance = new SoundManager();
                SoundManager._instance.init();
            }
            return SoundManager._instance;
        },
    },

    properties: {
        sound: {
            default: [],
            type: cc.AudioClip,
        },
        isLoadedSFX: false,
    },

    init: function(){
        this._LoadSFX();
    },

    /**
     * 정상적으로 사운드가 로드되었는지 체크하는 함수입니다.
     * @return {*}
     * @constructor
     */
    IsLoadCheck: function(){
        return this.sound[7] !== null;
    },

    /**
     * SFX를 출력해주는 함수입니다.
     * @param TAG Env 내부 SFX_로 시작하는 태그들중 하나
     * @constructor
     */
    PlaySfx: function(TAG){
        if (TAG >= this.sound.length || TAG < 0) {
            console.error("SFX Load ERROR: Index out of range");
            return;
        }

        var clip = this.sound[TAG];
        if (!clip) {
            console.error("SFX NULL ERROR: No sound loaded for TAG " + TAG);
            return;
        }
        cc.audioEngine.playEffect(clip, false);
    },

    /**
     * 내부 함수로 SFX를 모두 loadRes로 로드 합니다.
     * @private
     */
    _LoadSFX: function(){
        if (this.isLoadedSFX) return;

        this.sound = new Array(8).fill(null);

        var loadClip = function (index, filename) {
            cc.loader.loadRes(Env.SFX_DIRECTORY_PATH + filename, cc.AudioClip, (err, clip) => {
                if (err) {
                    console.error("Error loading sound for " + filename + ": ", err);
                } else {
                    this.sound[index] = clip;
                }
            });
        };

        loadClip.call(this, 0, Env.SFX_FILENAME_DROP_SWITCH);
        loadClip.call(this, 1, Env.SFX_FILENAME_LASER_SWITCH);
        loadClip.call(this, 2, Env.SFX_FILENAME_EXPLOSION);
        loadClip.call(this, 3, Env.SFX_FILENAME_EARN);
        loadClip.call(this, 4, Env.SFX_FILENAME_DROP_ITEM);
        loadClip.call(this, 5, Env.SFX_FILENAME_LASER_ON);
        loadClip.call(this, 6, Env.SFX_FILENAME_LASER_OFF);
        loadClip.call(this, 7, Env.SFX_FILENAME_PARTS_DOCKING);

        this.isLoadedSFX = true;
    },
});
