cc.Class({
    extends: cc.Component,

    properties: {

        mainCamear : cc.Camera,
        // 미니게임 UI 레이아웃
        miniGameLayout : cc.Node,


        // Error Page
        errorPange: cc.Node,
        /**
         * Phase 1 Objects
         */
        phaseLeftParent: cc.Node,

        phase1Nodes: {
            default: [],
            type: [cc.Node]
        },



        phase1Object: cc.Node,


        // 에너지 filled
        energy_img: {
          default:[],
          type: [cc.SpriteFrame]
        },

        // 로켓 내부에 이미지
        rocket_energy: {
            default:[],
            type: [cc.SpriteFrame]
        },

        phase2Nodes: {
            default: [],
            type: [cc.Node]
        },
        phase2EnergyFrame: cc.Node,


        phase2Object: cc.Node,



        //각도 조정하는 객체
        sliderBar : cc.Slider,
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


        phase2Level : 0,

        levelVector: [],

        leftLimit : 0.3,
        rightLimit : 0.7,

        limitLeftPosition: [],
        limitrightPosition: [],


    },


    start () {

        this.levelVector = [
            cc.v2(0.3,0.7),
            cc.v2(0.7,0.9),
            cc.v2(0.2,0.3)
        ];

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

                    this.Phase1RightPannel();
                }
            });

            var sequence = cc.sequence(delayTime, fadeIn, onAllAnimationsComplete);

            // 시퀀스에 애니메이션 완료 콜백 추가
            // 애니메이션 실행
            node.runAction(sequence);
        });
    },
    Phase1RightPannel: function(){
        this.phase1Object.active = true;
        this.phase1StopBtn.node.active = false;
        this.phase1Object.scale = cc.v2(0,0);

        var scaleAction = cc.scaleTo(0.5, 1, 1);
        var delay = cc.delayTime(0.8);

        var callFuncAction = cc.callFunc(()=>{
            this.status = 2;
            this.ShowPopupMessage("각도를 90도에 비슷하게 맞춰주세요!");
            this.DegreeGameStart();


            // this.phase1StopBtn.active = true;
        }, this, "Hello World");

        var seq = cc.sequence(scaleAction, delay , callFuncAction );

        this.phase1Object.runAction(seq);
    },
    LaunchDegree: function(){
        this.phase2Object.active = false;
        this.ShowPhase1LeftPannel();
    },


    ShowPhase2LeftPannel: function(){

        var initialDelay = 0.8;
        var totalNodes = this.phase2Nodes.length;

        var self = this;

        this.phase2Nodes.forEach((node, index) => {
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

                    this.Phase2RightPannel();
                }
            });

            var sequence = cc.sequence(delayTime, fadeIn, onAllAnimationsComplete);

            // 시퀀스에 애니메이션 완료 콜백 추가
            // 애니메이션 실행
            node.runAction(sequence);
        });
    },

    Phase2RightPannel: function(){

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

        this.popupObject.scale = cc.v2(1,0);

        var scaleAction = cc.scaleTo(0.5, 1, 1);
// 애니메이션이 완료된 후 호출할 함수 정의
        var onActionComplete = cc.callFunc(() => {
            // 여기에 추가적으로 실행할 함수를 호출할 수 있습니다.
            var str = this.dialog[idx];
            this.typeText(this.popupLabel, str, this.CallTypingEnd.bind(this));
        });

// 스케일 액션과 완료 콜백을 시퀀스로 결합
        var sequence = cc.sequence(scaleAction, onActionComplete);

// 스케일 액션 실행
        this.popupObject.runAction(sequence);

    },

    ShowPopupMessage: function(str) {
        this.popupLabel.string = '';
        this.popupObject.active = true;
        this.popupBtn.node.active = false;

        this.popupObject.scale = cc.v2(1,0);
        var scaleAction = cc.scaleTo(0.5, 1, 1);
// 애니메이션이 완료된 후 호출할 함수 정의
        var onActionComplete = cc.callFunc(() => {
            // 여기에 추가적으로 실행할 함수를 호출할 수 있습니다.
            this.typeText(this.popupLabel, str, this.CallTypingEnd.bind(this));
        });

// 스케일 액션과 완료 콜백을 시퀀스로 결합
        var sequence = cc.sequence(scaleAction, onActionComplete);

// 스케일 액션 실행
        this.popupObject.runAction(sequence);

    },

    CallTypingEnd: function() {
        this.popupBtn.node.active = true;
        var scaleUp = cc.scaleTo(0.3, 1.05, 1.05);
        var scaleDown = cc.scaleTo(0.3, 0.95, 0.95);
        var sequence = cc.sequence(scaleUp, scaleDown);

        var repeatAction = cc.repeatForever(sequence);

        var backgroundNode = this.popupBtn.node.getChildByName('Background');
        // 'Label' 이라는 이름의 자식 노드를 찾기
        var labels = backgroundNode.getChildByName('Label');
        labels.runAction(repeatAction);
    },

    SpaceShipLaunchMovie: function(){

    },


    AcceptButtonAction: function(){
        this.popupObject.active = false;

        if(this.dialongIndex >= this.dialog.length - 1){

            if(this.status === 3){
                // TODO 우주선 발쏴아

                this.miniGameLayout.active = false;
                this.SpaceShipLaunchMovie();
            }
            else if(this.status === 2){
                this.phase1StopBtn.node.active = true;
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


    /**
     * 유저가 잘못된 클릭을 했을때 효과
     * @constructor
     */

    ShakeObject : function(object) {
        var magnitude = 15.2;
        let shakes = [];

        var originalPosition = object.position;


        this.errorPange.active = true;
        for (let i = 0; i < 4; i++) {
            // 무작위 위치 변경
            let shakeX = (Math.random() - 0.5) * 2 * magnitude;
            let shakeY = (Math.random() - 0.5) * 2 * magnitude;
            let delay = cc.delayTime(0.02);  // 쉐이크 간격
            let move = cc.moveBy(0.02, cc.v2(shakeX, shakeY));
            let restore = cc.moveBy(0.02, cc.v2(-shakeX, -shakeY));
            shakes.push(delay, move, restore);
        }

        // 모든 쉐이크 후에 카메라 원래 위치로 복귀
        let restoreOriginal = cc.callFunc(() => {
            this.errorPange.active = false;
            object.position = originalPosition;
        });

        // 시퀀스 생성 및 실행
        let sequence = cc.sequence(shakes[0],shakes[1],shakes[2],shakes[3], restoreOriginal);
        object.runAction(sequence);
    },


    Phase1StopAction :function(){

        if(this.degree <= 5){
            this.status = 1;
            this.StopDegreeLooper();
            this.ShowPopupMessage("부스터 게이지를 80% 이상 조정하기");
            this.phase1StopBtn.node.active = false;
            this.phaseLeftParent.active = false;
            this.phase1Object.active = false;
        }
        else{
            this.ShakeObject(this.phase1Object);
        }
    },

    Phase2StopAction : function(){
        if(this.progressValue >= 0.85){
            this.status = 2;
            this.StopProgressLooper();

            this.ShowPopupMessage("성공했습니다! \n 이제 우주에서 여정을 시작합니다");
        }
        else{
            //this.ShakeObject()
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
