
const Controller = require("../Controller");

cc.Class({
    extends: cc.Component,

    properties: {

        particle_1 : cc.Node,
        partileFire: {
            default: [],
            type:[cc.Node]
        },

        movingTarget: cc.Node,
        spaceShip: cc.Node,

        objectCamera : cc.Camera,
        // 미니게임 UI 레이아웃
        miniGameLayout : cc.Node,
        // Error Page
        errorPange: cc.Node,
        /**
         * Phase 1 Objects
         */
        phaseLeftParent: cc.Node,
        phaseLeftParent2 : cc.Node,

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

        phase2EnergyEmptyObject: cc.Node,
        phase2EnergyFillObject : cc.Node,

        phase2Object: cc.Node,

        limitLeftObject : cc.Node,
        limitRightObject : cc.Node,


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

        levelStatus: [],

    },


    start () {

        Controller.getInstance().finalIndex = true;
        this.particle_1.active = false;

        this.partileFire[0].active = false;
        this.partileFire[1].active = false;

        this.levelStatus = [
            false,
            false,
            false
        ]

        this.levelVector = [
            cc.v2(-90,90),
            cc.v2(-45,90),
            cc.v2(-90,0)
        ];

        this.dialog = [
            "모든 준비가 끝났습니다. 발사각도를 조정하고, 연료를 주입해주세요.",
        ];

        this.AddEvent();
        this.GameUpdator();
    },


    SpaceShipUpdator: function(){
        if(this.status == 1){
            this.spaceShip.active = false;
            var rotate = 0;

            var rotateAction = cc.rotateTo(0.8, 0);
            var delayAction = cc.delayTime(0.5);

            var fadeOutAction = cc.fadeOut(0.5);
            var callbackDuringFade = cc.callFunc(()=>{
                // 동시에 수행
                this.spaceShip.active = true;

                // 카메라 이동
                var targetPosition = cc.v2(0,-80);
                var moveAction = cc.moveTo(0.5, targetPosition);

// 객체에 액션 실행
                this.objectCamera.node.runAction(moveAction);
            }, this);
            var fadeAndCall = cc.spawn(fadeOutAction, callbackDuringFade);

            var finalCallbackAction = cc.callFunc(()=>{
                // 애니메이션 후 팝업 창 보임
                this.ShowPopupMessage("발사체에 연료 주입하기 (3회)");
            }, this);

            var sequenceAction = cc.sequence(rotateAction, delayAction, fadeAndCall, finalCallbackAction);
            this.movingTarget.runAction(sequenceAction);
        }
        else if(this.status == 3){
            // 보여주자.
            var targetPos = cc.v2(0,-250);

            var moveUp = cc.moveTo(0.5,targetPos);

            var callFunc = cc.callFunc(()=>{
                this.objectCamera.zoomRatio = 2;
                this.partileFire[0].active = true;
                this.partileFire[1].active = true;
            });

            var spawn = cc.spawn(callFunc,moveUp );


            var shaker = cc.callFunc(()=>{
                this.ShakeObject(this.objectCamera.node ,false);
            });

            var camPos = cc.v2(0,1500);

            var camM = cc.moveTo(0.5,camPos);

            var camcall = cc.callFunc(()=>{
                this.objectCamera.node.runAction(camM);
            });

            var delay = cc.delayTime(0.1);
            var spawn2 = cc.spawn(shaker, delay);
            var repeat = cc.repeat(spawn2, 80);
            var seq = cc.sequence(spawn, repeat, camcall);

            this.objectCamera.node.runAction(seq);

            var vectors = [
                cc.v2(-5, -400 ),
                cc.v2(-5,200),
                cc.v2(-5,600),
                cc.v2(-5,1000),
                cc.v2(-5,2500)
            ];


            var v1 = cc.moveTo(4, vectors[0]);
            var v2 = cc.moveTo(3, vectors[1]);
            var v3 = cc.moveTo(1,vectors[2])
            var v4 = cc.moveTo(1,vectors[3]);
            var v5 = cc.moveTo(5,vectors[4]);

            var del = cc.delayTime(0.2);
            var func = cc.callFunc(()=>{
               this.particle_1.active = true;
            });



            var hide = cc.callFunc(()=>{
               this.particle_1.active = false;
            });



            var sp1 = cc.spawn(del,func);
            var sp2 = cc.spawn(hide,v3);


            var final = cc.callFunc(()=>{
                this.status = 10;
                this.ShowPopupMessage("미션 성공! 우주에서 새로운 모험을 떠나세요!");
            });

            var seq = cc.sequence(sp1,v1,v2,sp2, v4, v5 ,final);

            this.spaceShip.runAction(seq);





        // TODO
        }

    },


    GetPhase2LevelUpdate: function(){
        if(this.phase2Level >= 3) return;

        switch(this.phase2Level){
            case 0:
                this.leftLimit = 0.3;
                this.rightLimit = 0.7;
                break;
            case 1:
                this.leftLimit = 0.4;
                this.rightLimit = 0.7;
                break;
            case 2: case 3:
                this.leftLimit = 0.3;
                this.rightLimit = 0.4;
                break;
        }

        this.limitLeftObject.position = cc.v2(this.levelVector[this.phase2Level].x, 0);
        this.limitRightObject.position = cc.v2(this.levelVector[this.phase2Level].y, 0);

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



    HidePhase1Pannel: function(){
        this.phase1Object.active = false;
        this.phaseLeftParent.active = false;

    },


    /**
     * 미니게임 Phase1 시작시 객체 보여주는 효과임.
     * @constructor
     */
    ShowPhase1LeftPannel: function(){
        this.phaseLeftParent2.active = false;
        this.phaseLeftParent.active= true;

        var initialDelay = 0.2;
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
            this.ShowPopupMessage("발사각도를 90도로 조정해주세요!");
            this.DegreeGameStart();


            // this.phase1StopBtn.active = true;
        }, this, "Hello World");

        var seq = cc.sequence(scaleAction, delay , callFuncAction );

        this.phase1Object.runAction(seq);
    },
    LaunchDegree: function(){

        // this.phase1Object.active = true;

        this.phase2Object.active = false;
        this.ShowPhase1LeftPannel();

        this.HidePhase2Pannel();
    },

    HidePhase2Pannel: function(){
        this.phase2Object.active =false;
    },


    FilledOiled : function(index){

        var obj = null;
        if(this.levelStatus[index] == false){
            this.levelStatus[index] = true;

            switch (index){
                case 0: obj = this.phase2Nodes[5];
                    break;
                case 1: obj = this.phase2Nodes[6];
                    break;
                case 2: obj = this.phase2Nodes[7];
                    break;
            }
            obj.getComponents(cc.Sprite).spriteFrame = this.rocket_energy[1];

            this.EnergyVisualize(index);
        }

    },

    EnergyVisualize: function(level) {
        var targets = [0, 0]; // 이 배열은 [target, target2]를 저장합니다.
        var obj = null;
        switch (level) {
            case 0:
                targets = [0.5, 0.3];
                 obj = this.phase2Nodes[5].getComponent(cc.Sprite);
                break;
            case 1:
                targets = [1, 0.5];
                obj = this.phase2Nodes[6].getComponent(cc.Sprite);
                break;
            case 2:
                targets = [1, 1];
                obj = this.phase2Nodes[7].getComponent(cc.Sprite);
                break;
        }

        obj.spriteFrame = this.rocket_energy[1];

        this.updateSpriteFillRange(this.phase2EnergyEmptyObject.getComponent(cc.Sprite), targets[0]);
        this.updateSpriteFillRange(this.phase2EnergyFillObject.getComponent(cc.Sprite), targets[1]);
    },

    updateSpriteFillRange: function(sprite, targetFillRange) {
        var interval = setInterval(function() {
            if (sprite.fillRange >= targetFillRange) {
                sprite.fillRange = targetFillRange;
                clearInterval(interval);
            } else {
                sprite.fillRange += 0.01;
            }
        }, 5); // 여기서 30ms는 0.03ms보다 현실적인 타이머 간격입니다.
    },




    ShowPhase2LeftPannel: function(){
        this.phaseLeftParent2.active = true;
        this.phase2Object.active = false;
        this.phase2Object.opacity = 0;

        var initialDelay = 0.2;
        var totalNodes = this.phase2Nodes.length;

        var self = this;

        this.limitLeftObject.active = false;
        this.limitRightObject.active = false;

        this.phase2Nodes.forEach((node, index) => {
            node.opacity = 0;
            // 각 노드의 딜레이 계산 (index * initialDelay)
            var delayTime = cc.delayTime(index * initialDelay);
            var fadeIn = cc.fadeIn(0.5);

            // 모든 노드의 애니메이션이 끝난 후 실행할 작업 정의
            var onAllAnimationsComplete = cc.callFunc(() => {
                if (index === totalNodes - 1) {  // 마지막 노드의 애니메이션 완료시

                    var lastNode = this.phase2Nodes[this.phase2Nodes.length - 1];

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
                    var spr = this.phase2EnergyFrame.getComponent(cc.Sprite);
                    spr.fillRange = 0;  // 초기 fillRange 설정
                     var inter = setInterval(()=>{

                        if(spr.fillRange >= 1){
                            clearInterval(inter);
                            self.Phase2RightPannel();
                        }
                        else{
                            spr.fillRange += 0.1;
                        }
                    },15);

                }
            });

            var sequence = cc.sequence(delayTime, fadeIn, onAllAnimationsComplete);

            // 시퀀스에 애니메이션 완료 콜백 추가
            // 애니메이션 실행
            node.runAction(sequence);
        });
    },

    Phase2RightPannel: function(){

        this.phase2Object.active = true;
        this.phase2StopBtn.node.active = false;

        var fadeIn = cc.fadeIn(0.8);
        var delayTime = cc.delayTime(0.5);


        this.limitLeftObject.active = true;
        this.limitRightObject.active = true;


        var func = cc.callFunc(()=>{
            this.status = 4;
            this.ShowPopupMessage("중앙 게이지바에 타이밍에 맞춰 연료를 주입해주세요.");
        });

        var seq = cc.sequence(delayTime, fadeIn, delayTime, func);
        this.phase2Object.runAction(seq);
        // this.ProgresGameStart();
    },



    LaunchBoost: function(){
        this.HidePhase1Pannel();

        this.ShowPhase2LeftPannel();

        this.phase2Object.active = true;
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




    AcceptButtonAction: function(){
        this.popupObject.active = false;

        if(this.dialongIndex >= this.dialog.length - 1){

            if(this.status === 4){
                this.phase2StopBtn.node.active = true;
                this.ProgresGameStart();
            }
            else if(this.status === 3){

                this.SpaceShipUpdator();
                this.miniGameLayout.active = false;
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
            else if(this.status === 10){
                /// TODO 게임 종료 처리.
                Controller.getInstance().minigameDone = true;
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

    ShakeObject : function(object , isError = true) {
        var magnitude = 15.2;
        let shakes = [];

        var originalPosition = object.position;


        if(isError){
            this.errorPange.active = true;
        }

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
            this.SpaceShipUpdator();
            this.phase1StopBtn.node.active = false;
            this.phaseLeftParent.active = false;
            this.phase1Object.active = false;
        }
        else{
            this.ShakeObject(this.phase1Object);
        }
    },

    Phase2StopAction : function(){

        if(this.progressValue >= this.leftLimit && this.progressValue <= this.rightLimit){

            if(this.phase2Level == 2){
                this.FilledOiled(this.phase2Level);

                this.StopProgressLooper();
                this.progressValue = 0.4;

                this.ShowPopupMessage("성공하였습니다! 발사가 곧 시작됩니다.");
                this.status = 3;
                this.phase2StopBtn.node.active = false;
            }
            else{
                this.FilledOiled(this.phase2Level);
                this.phase2Level++;
                this.progressValue = 0;
            }
        }
        else{
            this.ShakeObject(this.phase2Object);
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
            self.GetPhase2LevelUpdate();

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

        },15);

    },

    IncreaseProgress: function(){
        if(this.progressValue >= 1.0){
            this.isProgressReverse = true;
            return;
        }

        if(this.phase2Level == 0){
            this.progressValue += 0.005;
        }
        else if(this.phase2Level == 1){
            this.progressValue += 0.01;
        }
        else{
            this.progressValue += 0.02;
        }

    },

    DecreaseProgress: function(){
        if(this.progressValue <= 0){
            this.isProgressReverse = false;
            return;
        }

        if(this.phase2Level === 0){
            this.progressValue -= 0.005;
        }
        else if(this.phase2Level === 1){
            this.progressValue -= 0.01;
        }
        else{
            this.progressValue -= 0.02;
        }

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

        },5);
    },

    IncreaseDegree: function(){
        if(this.degree >= 90){
            this.isDegreeReverse = true;
            return;
        }
        this.degree += 0.7;
    },

    DecreaseDegree: function(){
        if(this.degree <= 0){
            this.isDegreeReverse = false;
            return;
        }
        this.degree -= 0.5;
    },

});
