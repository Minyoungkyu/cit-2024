var Loader = cc.Class({
    extends: cc.Component,

    statics: {
        _instance: null,
        getInstance: function () {
            if (!Loader._instance) {
                Loader._instance = new Loader();
                Loader._instance.init();
            }
            return Loader._instance;
        },
    },

    properties: {
        initStatus: false, // 초기화 상태를 표시, false면 초기화 되지 않음, true면 초기화 됨
        spr: [], // Sprite 정보를 저장하는 배열
    },

    init: function () {
        this._LoadImage();
    },

    /**
     * 최초 객체가 생성될 때 지정된 이미지를 읽어와 저장합니다.
     * 단, 최초 1회만 진행합니다.
     * 일반적으로 Loader 객체가 생성될 때 1회 만 동작하도록 하고 있습니다.
     * @returns {boolean}
     * @private
     */
    _LoadImage: function () {
        if (this.initStatus) return false;
        this.initStatus = true;

        this.spr = new Array(Env.MAX_LOAD_IMAGE_LENGTH).fill(null);

        cc.loader.loadRes(Env.DIRECTORY_PATH + 'atlas', cc.SpriteAtlas, (err, atlas) => {
            if (err) {
                console.error("Failed to load atlas:", err);
                return;
            }

            var imgArray = [
                Env.FOOD_BOX_FILE_NAME,
                Env.N_SWITCH_ON_FILE_NAME,
                Env.N_SWITCH_OFF_FILE_NAME,
                Env.L_SWITCH_ON_FILE_NAME,
                Env.L_SWITCH_OFF_FILE_NAME,
                Env.BATTERY_FILE_NAME,
                Env.BOMB_FILE_NAME,
                Env.ROCKET_EMPTY_FILE_NAME,
                Env.ROCKET_FILLED_FILE_NAME,
                Env.L_START_ON_FILE_NAME,
                Env.L_MIDDLE_FILE_NAME,
                Env.L_END_ON_FILE_NAME,
                Env.L_START_OFF_FILE_NAME,
                Env.L_END_OFF_FILE_NAME,
                Env.GOAL_FILE_NAME,
                Env.VL_START_ON_FILE_NAME,
                Env.VL_MIDDLE_ON_FILE_NAME,
                Env.VL_END_ON_FILE_NAME,
                Env.VL_START_OFF_FILE_NAME,
            ];

            for (let i = 0; i < imgArray.length; i++) {
                let spriteFrame = atlas.getSpriteFrame(imgArray[i]);
                if (spriteFrame) {
                    this.spr[i] = spriteFrame;
                } else {
                    console.error("Missing sprite frame for:", imgArray[i]);
                }
            }
        });

        return true;
    },

    /**
     * 메인으로 사용되는 함수입니다.
     * 해당 함수는 매개변수로 tag값을 받아 Sprite를 리턴해주는 함수입니다.
     * @param tag Env에 저장되어 있는 Tag값
     * @returns {cc.SpriteFrame|null} 저장되어 있는 Sprite 리턴합니다.
     * @constructor
     */
    GetImage: function (tag) {
        if (tag >= 0 && tag < this.spr.length) {
            return this.spr[tag];
        } else {
            console.warn("Invalid tag: " + tag + "; Index should be between 0 and " + (this.spr.length - 1));
            return null;
        }
    },
});
