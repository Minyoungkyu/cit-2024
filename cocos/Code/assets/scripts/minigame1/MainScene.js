cc.Class({
    extends: cc.Component,

    properties: {

        mainCamear : cc.Camera,
        // 미니게임 UI 레이아웃
        miniGameLayout : cc.Node,

        /**
         * Phase 1 Objects
         */

        phaseLeftParent: cc.Node,

        phase1Nodes: {
            default: [],
            type: [cc.Node]
        },



        phase1Object: cc.Node,
        phase2Object: cc.Node,

        //각도 조정하는 객체
        sliderBar : cc.ProgressBar,
        targetObject : cc.Node,


        phase1StopBtn: cc.Button,
        phase2StopBtn: cc.Button,


        isDegreeReverse : false,
        isProgressReverse :false,

        isStopDegree : false,
        isStopProgress: false,
        degree: 90,
        progressValue : 0,

        dialog: [],
        dialongIndex : 0,


        // 팝업에 대한 정보들.
        popupObject : cc.Node,
        popupLabel: cc.Label,
        popupBtn: cc.Button,
        status : 0,
    },


    start () {
        this.dialog = [
            "모든 준비가 끝났습니다. 여러분이 직접 발사각도, 부스터게이지를 조정해주세요.",
        ];

        this.AddEvent();
        this.GameUpdator();
    },

    onLoad () {
        this.InitUI();
    },

    GameUpdator: function(){
        this.ShowMessageBox(0);
    },

    InitUI : function(){
        this.miniGameLayout.active = false;
        this.phase1Object.active = false;
        this.phase2Object.active = false;

    },


    /**
     * UI Event 추가
     * @constructor
     */
    AddEvent: function(){

        this.sliderBar.interactable = false;

        this.phase1StopBtn.node.on("click", this.Phase1StopAction, this);
        this.phase2StopBtn.node.on("click",this.Phase2StopAction, this);

        this.popupBtn.node.on("click", this.AcceptButtonAction, this);
    },


    /**
     * 미니게임 Phase1 초기화 하는 부분임.
     * @constructor
     */
    InitPhase1LeftPannel: function(){

    },

    /**
     * 미니게임 Phase1 시작시 객체 보여주는 효과임.
     * @constructor
     */
    ShowPhase1LeftPannel: function(){

        var initialDelay = 0.8;
        var totalNodes = this.phase1Nodes.length;

        var self = this;

        this.phase1Nodes.forEach((node, index) => {
            node.opacity = 0;
            // 각 노드의 딜레이 계산 (index * initialDelay)
            var delayTime = cc.delayTime(index * initialDelay);
            var fadeIn = cc.fadeIn(0.8);

            // 모든 노드의 애니메이션이 끝난 후 실행할 작업 정의
            var onAllAnimationsComplete = cc.callFunc(() => {
                if (index === totalNodes - 1) {  // 마지막 노드의 애니메이션 완료시
                    var lastNode = self.phase1Nodes[self.phase1Nodes.length - 1];

                    var fadeOut = cc.fadeOut(0.2);
                    var delay = cc.delayTime(0.1);
                    var fadeIn = cc.fadeIn(0.2);

                    // 페이드인, 딜레이, 페이드아웃 순서로 시퀀스 생성
                    var sequence = cc.sequence(fadeOut, delay, fadeIn);

                    // 위 시퀀스를 4번 반복
                    var repeatAction = cc.repeat(sequence, 2);

                    // 애니메이션 실행
                    lastNode.runAction(repeatAction);
                    // 여기에 추가적으로 실행할 코드 작성
                }
            });

            var sequence = cc.sequence(delayTime, fadeIn, onAllAnimationsComplete);

            // 시퀀스에 애니메이션 완료 콜백 추가
            // 애니메이션 실행
            node.runAction(sequence);
        });
    },




    LaunchDegree: function(){

        this.ShowPhase1LeftPannel();

        this.phase2Object.active = false;

        this.phase1Object.active = true;
        this.DegreeGameStart();
    },


    LaunchBoost: function(){
        this.phase1Object.active = false;

        this.phase2Object.active = true;
        this.ProgresGameStart();
    },


    // 타이핑 효과를 위한 공통 함수
    typeText: function(label, text, onComplete) {
        label.string = ""; // 레이블 초기화
        let index = 0; // 현재 문자열의 위치 인덱스

        const typeChar = () => {
            if (index < text.length) {
                label.string += text.charAt(index); // 현재 인덱스의 문자 추가
                index++; // 인덱스 증가
                setTimeout(typeChar, 30); // 다음 문자를 위한 시간 지연 설정 (100ms)
            } else if (onComplete) {
                onComplete(); // 모든 문자 출력 완료 시 호출할 함수
            }
        };

        typeChar(); // 타이핑 시작
    },

    ShowMessageBox: function(idx) {
        this.popupObject.active = true;
        this.popupBtn.node.active = false;

        var str = this.dialog[idx];
        this.typeText(this.popupLabel, str, this.CallTypingEnd.bind(this));
    },

    ShowPopupMessage: function(str) {
        this.popupObject.active = true;
        this.popupBtn.node.active = false;

        this.typeText(this.popupLabel, str, this.CallTypingEnd.bind(this));
    },

    CallTypingEnd: function() {
        this.popupBtn.node.active = true;
    },

    SpaceShipLaunchMovie: function(){

    },


    AcceptButtonAction: function(){
        this.popupObject.active = false;

        if(this.dialongIndex >= this.dialog.length - 1){

            if(this.status === 2){
                // TODO 우주선 발쏴아

                this.miniGameLayout.active = false;

                this.SpaceShipLaunchMovie();

            }
            else if(this.status === 1){
                this.miniGameLayout.active = true;
                this.LaunchBoost();
            }
            else if(this.status === 0){
                this.miniGameLayout.active = true;
                this.LaunchDegree();
            }
        }
        else{
            this.dialongIndex++;

            var self = this;

            setTimeout(function(){
                self.ShowMessageBox(self.dialongIndex);
            },1000);
        }
    },

    Phase1StopAction :function(){

        if(this.degree <= 15){
            this.status = 1;
            this.StopDegreeLooper();

            this.ShowPopupMessage("부스터 게이지를 80% 이상 조정하기");
        }
    },

    Phase2StopAction : function(){
        if(this.progressValue >= 0.85){
            this.status = 2;
            this.StopProgressLooper();

            this.ShowPopupMessage("성공했습니다! \n 이제 우주에서 여정을 시작합니다");

        }
    },

    StopDegreeLooper:function(){
        this.isStopDegree = true;
    },

    StopProgressLooper: function(){
        this.isStopProgress = true;
    },


    ProgresGameStart: function(){
        var self = this;

        var progressInter = setInterval(()=>{
            if(self.isStopProgress) {
                clearInterval(progressInter);
            }

            if(self.isProgressReverse){
                self.DecreaseProgress();
            }
            else{
                self.IncreaseProgress();
            }
            self.sliderBar.progress = self.progressValue;

        },0.03);

    },

    IncreaseProgress: function(){
        if(this.progressValue >= 1.0){
            this.isProgressReverse = true;
            return;
        }
        this.progressValue += 0.01;
    },

    DecreaseProgress: function(){
        if(this.progressValue <= 0){
            this.isProgressReverse = false;
            return;
        }
        this.progressValue -= 0.01;
    },

    DegreeGameStart: function(){
        var self = this;
        var degreeInterval = setInterval(()=>{
            if(self.isStopDegree) {
                clearInterval(degreeInterval);
            }

            if(self.isDegreeReverse){
                self.DecreaseDegree();
            }
            else{
                self.IncreaseDegree();
            }
            self.targetObject.angle = self.degree;

        },0.03);
    },

    IncreaseDegree: function(){
        if(this.degree >= 90){
            this.isDegreeReverse = true;
            return;
        }
        this.degree += 0.5;
    },

    DecreaseDegree: function(){
        if(this.degree <= 0){
            this.isDegreeReverse = false;
            return;
        }
        this.degree -= 0.5;
    },





});
