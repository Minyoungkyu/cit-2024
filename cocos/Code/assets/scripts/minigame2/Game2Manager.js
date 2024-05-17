



const SoundManager = require("../GameLogic/SoundManager");
const Controller = require("../Controller");


/**
 * 게임 라이프 사이클
 * 
 * STATUS_INIT-> STATUS_GAME_READY -> STATUS_PLAY -> STATUS_RESULT -> STATUS_CHECKUP-> STATUS_DONE
 * 
 * STATUS_INIT
 *  실행시 1회로 제한 
 *  객체 생성
 * 
 * STATUS_GAME_READY 
 *  게임 준비 를 위한 초기화 작업이 진행된다.
 *  
 * 
 * STATUS_PLAY 
 *  게임이 종료 되기 까지 기다림
 * 
 * STATUS_RESULT
 *  게임 결과를 표현해줌, 즉 성공시  Animation 보여줌
 *      -> OX game에서 X 시  디스크립션 정보 보여줌
 * 
 * STATUS_CHECKUP 
 *  게임이 6문제를 풀었을지 체크해주며 6문 이라면 끝
 *  그렇지 않으면 GameReady상태로 전환
 * 
 
 */

STATUS_INIT = 0;
STATUS_GAME_READY = 1;
STATUS_PLAY = 2;
STATUS_RESULT = 3;
STATUS_CHECKUP = 4;
STATUS_DONE = 5;

TYPE_CARD_GAME  = 0;
TYPE_OX_GAME = 1;

cc.Class({
    extends: cc.Component,

    properties: {

        gamestatus: STATUS_INIT,

        jsonData: null,
        isAudioLoaded: false,

        gamePattern: [],


        OX_GAME_NODE: cc.Node,

        btnO: cc.Button,
        btnX: cc.Button,

        oxGameLabel: cc.Label,

        currentIndex: 0,


        resultArray: [],

    },


    onLoad(){

        var self = this;

        cc.loader.loadRes("minigame_2/minigame", (err, jsonAsset) => {
            if (err) {
                cc.error(err);
                return;
            }

            self.jsonData = jsonAsset.json['minigame'];
            // JSON 데이터를 활용한 추가 로직을 여기에 작성하세요.

            self.MainGameLogic();
        });
    },


    start () {
        this.gamePattern = [
            [0,1,2,3,7,8],
            [7,8,3,2,1,0],
        ]
        this.AddEvnet();
        // this.MainGameLogic();
    },

    AddEvnet: function(){
        this.btnO.node.on('click', this.btnOLogic,this );
        this.btnX.node.on('click', this.btnXLogic,this );
    },

    btnOLogic: function(){
        var pat = this.gamePattern[0];


        // console.log(pat[this.currentIndex]);


        var str = this.GetQuestionTitle(pat[this.currentIndex]);

        this.ShowOXGameTitle(str);
        this.currentIndex++;
        
    },

    btnXLogic: function(){
        console.log("BTN X LOGIC CLICK");
    },

    MainGameLogic: function(){

        // console.log(this.jsonData['minigame']);

        // for(var i = 0; i < this.jsonData.length; i++){
        //     console.log(this.jsonData[i]);
        // }
    },


    GetQuestionTitle: function(id){

        console.log(id);

        if(id > this.jsonData[id].length) return null;
        return this.jsonData[id].title;
    },

    GetQuestionAnswer: function(id){
        if(this.jsonData[id].type === TYPE_CARD_GAME){
            return this.jsonData[id].split('');
        }
        else if(this.jsonData[id].type === TYPE_OX_GAME ){
            return this.jsonData[id].answer;
        }
        return -1;
    },

    GetDescription: function(id){
        var type = this.jsonData[id].type;
        var answer = this.jsonData[i].answer;

        if(type === TYPE_OX_GAME && answer === 'X'){
            return this.jsonData[i].description;
        }

        return -1;
    },
    

    /**
     * Get NUmber 처리..
     * @param {*} quest_type 
     */
    GetShuffle: function(quest_type){
        var isShuffleBack = false;
        var min = 1;
        var max = 9;

        var resultNumber = 0;
        resultNumber = Math.floor(Math.random() * (max - min + 1)) + min;

    },




    ShowOXGameTitle: function(str){
        this.typeText(this.oxGameLabel,str, this.CallTypingEnd.bind(this));
    },



    /**
     * 타이핑 끝났을때 처리해주는 함수 
     */
    CallTypingEnd: function() {
        // 타이핑 끝났을때 처리해주는 함수.
        console.log("Typing Done");
    },

    /**
     * 
     * 타이핑 효과줄 텍스트
     * @param {*} label 
     * @param {*} text 
     * @param {*} onComplete 
     */
    //  this.typeText(this.popupLabel, str, this.CallTypingEnd.bind(this));
    // 타이핑 효과를 위한 공통 함수
    typeText: function(label, text, onComplete) {
        label.string = ""; // 레이블 초기화
        let index = 0; // 현재 문자열의 위치 인덱스

        const typeChar = () => {
            if (index < text.length) {
                label.string += text.charAt(index); // 현재 인덱스의 문자 추가
                index++; // 인덱스 증가
                SoundManager.getInstance().PlaySfx(Env.SFX_TYPING)
                setTimeout(typeChar, 30); // 다음 문자를 위한 시간 지연 설정 (100ms)
            } else if (onComplete) {
                onComplete(); // 모든 문자 출력 완료 시 호출할 함수
            }
        };

        typeChar(); // 타이핑 시작
    },
});
