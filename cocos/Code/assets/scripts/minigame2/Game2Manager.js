



const SoundManager = require("../GameLogic/SoundManager");
const Controller = require("../Controller");

const Manager2 = require("Manager2");

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


TYPE_NONE = -1;
TYPE_CARD_GAME  = 0;
TYPE_OX_GAME = 1;



CARD_INIT = 0;
CARD_PICK_NUMBER = 1;
CARD_SHOW = 2;
CARD_WAIT_TIME = 3;
CARD_GAME_END = 4;


cc.Class({
    extends: cc.Component,

    properties: {

        gamestatus: STATUS_INIT,

        jsonData: null,
        isAudioLoaded: false,
        gamePattern: [],

        // 현재 게임 순서 즉 문제 순서를 표현함.
        currentIndex: 0,

        /**
         * OX GAME UI OBJECT
         */
        OX_GAME_NODE: cc.Node,
        btnO: cc.Button,
        btnX: cc.Button,
        oxGameLabel: cc.Label,

        /**
         * FAKE STRING PATTERN
         */
        stringPattern : [],


        /**
         * CARD Game UI OBJECT
         * 
         */

        cardGameLabel: cc.Label,
        


        /*
            Card게임에서 몬스터 객체들 
        */
        minimobArr: {
            default: [],
            type: cc.Node,
        },


        cardManager: cc.Node,

        // 선택된 리스트들 이곳에 넣는다.
        cardPickMonsterArray: [],


        cardGameStatus: CARD_INIT,



        // 정답 처리용 배열
        resultCardGameArr: [],
        resultIdx: 0,

        isLoopInterval: false,


        CURRENT_GAME_TYPE : TYPE_NONE,
        
        currentGameID: 0,

        startTime: null,
        waiting: false,

        // Random돌릴때 정답 보유중인지?
        isHaveShuffleAnswer : false,
        cardAnswerIdx : -1,


        mobPickArr: [],
    },


    onLoad(){

        var self = this;

        cc.loader.loadRes("minigame_2/minigame", (err, jsonAsset) => {
            if (err) {
                cc.error(err);
                return;
            }

            console.log("LOADED");
            self.jsonData = jsonAsset.json['minigame'];
            // JSON 데이터를 활용한 추가 로직을 여기에 작성하세요.

            self.GameUpdator();
        });
    },


    start () {
        this.gamePattern = [
            [1,3,4,5,6,7],

            [0,1,2,3,7,8],
            [7,8,3,2,1,0],
        ]


        this.stringPattern = ['얄','구','펭','쥭','무','요','한','갬','뷱','졍','통','앨','멜',
                                '슌','셈','눙','룽','둥','퓽','젭','셉','케','퐁','렙','룹','엔','뽁',
                            '핫','묠','랫','풰','툽','였','줍','몹','쿄','몽','푭','훗','띠','쁘']
        
        this.AddEvnet();
    },



    GameUpdator: function(){

        var self = this;

        setInterval(function(){
            switch(self.gamestatus){
    
                case STATUS_INIT:
                    self._GameInit();
                    break;
    
                case STATUS_GAME_READY: 
                    self.PickGame();
                    break;
    
                case STATUS_PLAY:
                    break;
    
                case STATUS_RESULT:
                    // Result  성공 처리 보여줌

                    break;
    
                case STATUS_CHECKUP:

                    if(self.currentIndex == 6){
                        self.gamestatus = STATUS_DONE;
                    }
                    else{
                        self.currentIndex++;
                        self.gamestatus = STATUS_GAME_READY;
                    }

                    break;
                
                case STATUS_DONE:
                    break;
    
            }
        },30);

    },


    _GameInit: function(){
        this.currentIndex = 0;
        this.gamestatus = STATUS_GAME_READY;
        this.cardManager.getComponent("CardManager").HideCards();
    },
    


    PickGame: function(){
        this.currentGameID = this.gamePattern[0][this.currentIndex];
        // console.log("PICK ME");

        this.CURRENT_GAME_TYPE = this.GetGameType(this.currentGameID);
        // console.log(this.currentGameID);

        if(this.CURRENT_GAME_TYPE === TYPE_CARD_GAME){
            this.InitCardGame();
        }
        else if(this.CURRENT_GAME_TYPE === TYPE_OX_GAME){
            this.InitOXGame();
        }
        else{
            console.log("Not Found Game");
        }

        this.gamestatus = STATUS_PLAY;

    },


    InitCardGame: function(){
        var title  = this.GetQuestionTitle(this.currentGameID);

        var ans = this.GetQuestionAnswer(this.currentGameID);
        this.resultCardGameArr = ans;
        console.log(this.resultCardGameArr);

        Manager2.getInstance().InitResultArray(this.resultCardGameArr.length);

        this.ShowCardGameTitle(title);

        var self = this;

        setTimeout(function(){
            self.StartCardGame();
        },3000);
    },

    


    InitOXGame: function(){



    },

    

    StartOXGame: function(){
        
    },


 


    GetRandomNumber: function(max_number) {
        return Math.floor(Math.random() * max_number) + 1;
    },



    StartCardGame: function(){
        if(this.isLoopInterval) return;
        this.isLoopInterval = false;

        var self = this;

        var interval = setInterval(function(){
            // if(self.currentIndex > 11) self.currentIndex = 0;
            // var obj = self.minimobArr[self.currentIndex].getComponent("MiniMob");

            switch(self.cardGameStatus){
                case CARD_INIT:
                    // 초기화 해주고
                    // 정답 이 있을지 랜덤으로 수치 가져오기
                    self.InitMob();

                    break;
                case CARD_PICK_NUMBER:
                    // 만약 정답이 있다면 몇마리 보여줄것인지? 
                    self.CardGamePickNumber();
                    break;
                
                case CARD_SHOW:
                    // 보여준다.
                    //
                    self.ShowCardMiniMob();
                    break;

                case CARD_WAIT_TIME:
                    // 2초를 세고 숨겨주고
                    // 게임이 종료됬는지 체크 하고 끝나지않았다면
                    // Init 끝나면 GameEnd로 표현

                    self.WaitForTime();

                    break;

                case CARD_GAME_END:
                    // 종료됬으니 모두 숨기고 초기화.

                    clearInterval(interval);
                   

                    self.CardGameClear();
                    self.gamestatus = STATUS_CHECKUP;
                    break;
            }
            // self.currentIndex++;
        },30);
    },

    
    CardGameClear: function(){
        this.ShowCardGameTitle('');
        this.cardManager.getComponent("CardManager").HideCards();
        this.isLoopInterval = false;
        this.cardGameStatus = CARD_INIT;

        for(var i = 0; i < this.minimobArr.length;i++){
            this.minimobArr[i].getComponent("MiniMob").InitMob(i, this.getRandomString(), -1, false);
        }
    },



    ShowCardMiniMob: function(){
        for(var i = 0; i < this.mobPickArr.length; i++){
            var mobIdx = this.mobPickArr[i];

            // console.log(mobIdx);

            var obj =  this.minimobArr[mobIdx].getComponent("MiniMob");
            obj.ShowMonster();
        }

        if(this.cardAnswerIdx !== -1){
            if(this.minimobArr[this.cardAnswerIdx].active === false){
                this.minimobArr[this.cardAnswerIdx].getComponent("MiniMob").ShowMonster();
            }
        }

        this.cardGameStatus = CARD_WAIT_TIME;
    },


    InitMob: function(){
        // 초기화 처리해주는것.

        if(Manager2.getInstance().IsCardGameClear()){
            this.cardGameStatus = CARD_GAME_END;
            return;
        }

     

        var isHaveAnswer = this.GetRandomNumber(2);
        // var isHaveAnswer = 2;

        if(isHaveAnswer !== -1){

            for(var i = 0; i < this.minimobArr.length;i++){
                this.minimobArr[i].getComponent("MiniMob").InitMob(i, this.getRandomString(), -1, false);
            }

            this.isHaveShuffleAnswer = true;

            this.cardAnswerIdx = this.GetRandomNumber(11);
            var answer = this.GetQuestionAnswer(this.currentGameID);


            var selectIdx = 0;

            for(var i = 0; i < answer.length; i++){
                if(Manager2.getInstance().IsClearArray(i)) continue;

                selectIdx = i;
                break;
            }

            var str =  answer[selectIdx];
            this.minimobArr[this.cardAnswerIdx].getComponent("MiniMob").InitMob(this.cardAnswerIdx, str, selectIdx, true);

            // 정답 보유할것.
            console.log("정답 보유함! =>" +selectIdx +"=>"+ str);
        }
        else{

            this.cardAnswerIdx = -1;
            this.isHaveShuffleAnswer = false;
            console.log("보유하지않음");
            // 정답 보유하지않음.
            for(var i = 0; i < this.minimobArr.length;i++){
                this.minimobArr[i].getComponent("MiniMob").InitMob(i, this.getRandomString(), -1, false);
            }
        }

        this.cardGameStatus = CARD_PICK_NUMBER;
    },

    IsGameCleared : function(){
        return Manager2.getInstance().IsCardGameClear();
    },


    CardGamePickNumber: function(){
        // 몇마리 보여줄지?
        this.mobPickArr.length = 0;
        var rCounter = 0;

        rCounter = this.GetRandomNumber(11);


        // 몇번 인덱스 보여줄지?
        var rIdx = [];
        
        // 중복값 안들어가게 수정.
        for(var i = 0; i < rCounter; i++){
            var pushNumber = this.GetRandomNumber(11);

            if(rIdx.length < 1){
                rIdx.push(pushNumber);
            }
            else{
                if(this.IsHaveSameNumber(rIdx, pushNumber )) continue;
                rIdx.push(pushNumber);

            }
        }

        this.mobPickArr = rIdx;
        this.cardGameStatus = CARD_SHOW;
    },

    IsHaveSameNumber: function(arr , number){
        for(var i = 0; i < arr.length; i++){
            if(arr[i] == number) return true;
        }
        return false;
    },


    WaitForTime: function(){
        if(this.waiting) return;

        this.startTime = Date.now();
        this.waiting = true;
        this.checkTime();
    },


    checkTime: function() {
        if (this.waiting) {
            let currentTime = Date.now();
            let elapsedTime = (currentTime - this.startTime) / 1000; // 초 단위로 변환
            if (elapsedTime > 3) {
                this.waiting = false;
                this.cardGameStatus = CARD_INIT;

                // 2초 후 실행할 코드 여기에 추가
                // console.log("3초가 지났습니다.");
            } else {
                // 계속해서 시간을 체크
                setTimeout(this.checkTime.bind(this), 30); // 30ms 후 다시 체크
            }
        }
    },



    getRandomString() {
        const randomIndex = Math.floor(Math.random() * this.stringPattern.length);
        return this.stringPattern[randomIndex];
    },

    AddEvnet: function(){
        this.btnO.node.on('click', this.btnOLogic,this );
        this.btnX.node.on('click', this.btnXLogic,this );
    },


    btnOLogic: function(){
        var pat = this.gamePattern[0];

    },

    btnXLogic: function(){
        console.log("BTN X LOGIC CLICK");
    },


    GetQuestionTitle: function(id){
        return this.jsonData[id].title;
    },

    GetQuestionAnswer: function(id){
        if(this.jsonData[id].type === TYPE_CARD_GAME){
            return this.jsonData[id].answer.split('');
        }
        else if(this.jsonData[id].type === TYPE_OX_GAME ){
            return this.jsonData[id].answer;
        }
        return -1;
    },

    GetDescription: function(id){
        var type = this.jsonData[id].type;
        var answer = this.jsonData[id].answer;

        if(type === TYPE_OX_GAME && answer === 'X'){
            return this.jsonData[id].description;
        }

        return -1;
    },

    /**
     * 선택된 Json에 정의된 게임타입을 불러옵니다.
     * @param {*} id 
     * @returns 
     */
    GetGameType: function(id){
        var type = this.jsonData[id].type;

        if(type === -1) return TYPE_NONE;

        return type;
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


    ShowCardGameTitle: function(str){
        this.typeText(this.cardGameLabel,str,this.CallTypingEnd.bind(this));
    },



    ShowOXGameTitle: function(str){
        this.typeText(this.oxGameLabel,str, this.CallTypingEnd.bind(this));
    },



    /**
     * 타이핑 끝났을때 처리해주는 함수 
     */
    CallTypingEnd: function() {
        // 타이핑 끝났을때 처리해주는 함수.
        // console.log("Typing Done");


        var self = this;

        

        if(this.CURRENT_GAME_TYPE === TYPE_CARD_GAME){
            
            setTimeout(function(){


                console.log(self.resultCardGameArr);
                self.cardManager.getComponent("CardManager").InitCards(self.resultCardGameArr);

                // for(var i = 0; i < self.resultCardGameArr.length; i++){
                //     self.cardArray[i].active = true;
                //     self.cardArray[i].getComponent(cc.Animation).play('card_off');
                // }
            },500);

            
           


        }
        else if(this.CURRENT_GAME_TYPE === TYPE_OX_GAME){

        }


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
