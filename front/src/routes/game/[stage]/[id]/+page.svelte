<svelte:head>
    <script type="text/javascript" src="/brython-runner.bundle.js"></script>

    <title>{rq.SITE_NAME}</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
</svelte:head>

<script lang="ts">
    export const ssr = false; 
    import rq from '$lib/rq/rq.svelte';
	import { onMount } from 'svelte';
    import type { components } from '$lib/types/api/v1/schema';
    import { setupAceEditor } from '$lib/aceEdit/aceEditorSetup';
    import Cocos from '$lib/cocos/cocos.svelte';
    import { runPythonCode2 } from '$lib/pyodide/pyodide';
    import './page.css';
    import TransitioningOpenLayer from '$lib/game/TransitioningOpenLayer.svelte';

    const { data } = $props<{ data: { gameMapDto: components['schemas']['GameMapDto'] } }>();
    const { gameMapDto } = data;

    let audio: HTMLAudioElement;
    let editor: any;
    let hintModal: HTMLDivElement 
    let progressController: HTMLInputElement; 
    let commandGuide: string[] = gameMapDto.commandGuide.split(',');
    let framesData: [] = $state([]); // 파이썬 실행 결과 프레임 데이터
    let frameUpdateIntervalId: any = $state(null); // 에디터 하이라이터, 프로그레스바 업데이트 인터벌 아이디
    let isCoReady: boolean = $state(false); 
    let showStart: boolean = $state(false);
    let showGuide: boolean = $state(false);
    let showClearPopup: boolean = $state(false);
    let openLayer: boolean = $state(false);
    let showCompleteBtn: boolean = $state(false);
    let canExecute: boolean = $state(true);
    let isPause: boolean = $state(false);
    let startTyping: boolean = $state(false);
    let element: HTMLElement;
    let text: string;

    const stageString = gameMapDto.cocosInfo;
    const jsonObjectString = stageString.trim().substring("stage = ".length);
    const stageObject = JSON.parse(jsonObjectString);

    let clearGoalList = gameMapDto.clearGoal.split('\n');
    let clearGoalColorArray = $state(Array.from({length: clearGoalList.length}, () => 'rgb(64 226 255)'));

    $effect(() => {
        if (isCoReady) {
            console.log('isCoReady');
            setInterval(() => {
                if((window as any).IsCocosGameLoad()) {
                    setTimeout(() => {
                        showStart = true;
                        showGuide = true;
                        startTyping = true;
                    }, 1000);
                }
            }, 500);
        }
    });

    const explanation: String = gameMapDto.editorMessage;

    const customCompletions = gameMapDto.editorAutoComplete.split(',')
        .filter(command => command.trim() !== '') 
        .map(command => ({
            value: `${command}`, 
            score: 1000
        // meta: "custom" 
    }));

    async function executePython(): Promise<void> {
        console.time("executePythonTimer"); // ToDo: remove

        if (frameUpdateIntervalId !== null) { // 인터벌 초기화
            clearInterval(frameUpdateIntervalId);
            frameUpdateIntervalId = null;
        }
        
        let result: any = await runPythonCode2(gameMapDto.cocosInfo, editor.getValue());

        if(result.error) {
            let cocosInfoLength = gameMapDto.cocosInfo.split('\n').length;
            const longText = result.error;
    
            const regex = /File "<exec>", line (\d+)/g;

            let match;
            let lastNumber: any;

            while ((match = regex.exec(longText)) !== null) {
                lastNumber = match[1];
            }

            if (lastNumber) {
                updateErrorHighlight(Number(lastNumber - 515 - cocosInfoLength));
            } 
        }

        framesData = JSON.parse(result.result);
        const wrappedData = {
            data: framesData
        };

        (window as any).SendStreamData?.(wrappedData);
        progressController.max = (framesData.length - 1).toString();
        updateFrame(framesData, 0);
        console.timeEnd("executePythonTimer"); // ToDo: remove
    }

    async function batchPlayLog() {
        await rq.apiEndPointsWithFetch(fetch).POST(`/api/v1/playerLogs/batchPlayLog`, {
            body: {
                gameMapDto: gameMapDto,
                result: "clear"
            }
        });
    }
    
    let i = 0;
    function typeWriter() {
        if (i < text.length) {
            element.innerText += text.charAt(i);
            i++;
            setTimeout(typeWriter, 50); 
        }
    }

    $effect(() => {
        if(startTyping) {
            typeWriter();
        }
    });
    
    onMount(async () => {
        runPythonCode2( "", "");
    });

    const originalHeight = 1080;
    let scaleMultiplier = $state(0);
    let widthMultiplier = $state(1920);

    onMount(() => {
        audio = document.getElementById("myAudio") as HTMLAudioElement;
        audio.volume = 0.4;

        const updateScale = () => {
            const currentHeight = window.innerHeight;
            scaleMultiplier = (currentHeight / originalHeight);
            widthMultiplier = window.innerWidth - (633 * scaleMultiplier)
        };

        window.addEventListener('resize', updateScale);
        
        updateScale();
        
        editor = setupAceEditor('editor', customCompletions);
        editor.setValue(explanation, 1); 

        editor.getSession().on('change', function(e:any) {
            const session = editor.getSession();
            session.removeMarker(errorMarkerId);
            
            if (e.action === 'insert') {
                const totalLines = editor.getSession().getLength();
                const cursorPosition = editor.getCursorPosition();

                if (cursorPosition.row === totalLines - 1) {
                    editor.getSession().insert({row: totalLines, column: 0}, "\n");
                }
            }
        });

        editor.focus();

        element = document.getElementById('typing')!;
        text = element.innerText;
        element.innerText = ''; 

        showModal();
    });

    let markerId:any;
    let errorMarkerId:any;

    let test2 = $state(true);
    let playCanPause = $state(false);
    let volumeCanMute = $state(true);

    let hpTest = $state(100);
    let hpTest2 = $state(100);

    function hpTest3(hp: number) {
        hpTest = hp;
        setTimeout(() => {
            hpTest2 = hpTest;
        }, 150);
    }

    function updateHighlight(HilightRow:any) { // Todo: svelte 반응성으로 호출되도록 HighlightRow 변수하나 파서 반응성 걸기
        const Range = ace.require('ace/range').Range;
        const session = editor.getSession();
        session.removeMarker(errorMarkerId);
        if (markerId !== undefined) {
          session.removeMarker(markerId);
        }
        const range = new Range(HilightRow - 2, 0, HilightRow - 2, 1);
        markerId = session.addMarker(range, "editorHighlighter", "fullLine", false);
    }

    function updateErrorHighlight(HilightRow:any) { // Todo: svelte 반응성으로 호출되도록 HighlightRow 변수하나 파서 반응성 걸기
        const Range = ace.require('ace/range').Range;
        const session = editor.getSession();
        session.removeMarker(markerId);
        if (errorMarkerId !== undefined) {
          session.removeMarker(errorMarkerId);
        }
        const range = new Range(HilightRow - 2, 0, HilightRow - 2, 1);
        errorMarkerId = session.addMarker(range, "editorErrorHighlighter", "fullLine", false);
    }

    function updateClearGoal(frame:any) {
        for (let i = 0; i < clearGoalList.length; i++) {
            if(clearGoalList[i].includes('목표지점')) {
                const array1 = stageObject.stage.goal_list[0].pos;
                const array2 = frame.player.pos.map((value: number) => Math.round(value))
                if(array1.every((element:number, index:any) => element === array2[index])) {
                    clearGoalColorArray[i] = 'rgb(255 210 87)';
                }
                stageObject.stage.goal_list[0].pos === frame.player.pos
            }else if(clearGoalList[i].includes('보급품')) {
                let foodGoals = stageObject.stage.goal_list.filter((goal: any) => goal.type === 'food');
                if (frame.player.food_count >= foodGoals[0].count) {
                    clearGoalColorArray[i] = 'rgb(255 210 87)';
                }
            }else if(clearGoalList[i].includes('로켓부품')) {
                let rocketGoals = stageObject.stage.goal_list.filter((goal: any) => goal.type === 'rocket_parts');
                if (rocketGoals[0].count <= frame.player.rocket_parts_count) {
                    clearGoalColorArray[i] = 'rgb(255 210 87)';
                }
            }else if(clearGoalList[i].includes('줄 이하')) {
                let codeLineGoals = stageObject.stage.goal_list.filter((goal: any) => goal.goal === 'line');
                let guideTest = gameMapDto.editorMessage.split('\n').length;
                if (frame.line_num <= codeLineGoals[0].count + guideTest) {
                    clearGoalColorArray[i] = 'rgb(255 210 87)';
                } else if(frame.line_num > codeLineGoals[0].count) {
                    clearGoalColorArray[i] = 'rgb(64 226 255)';
                }
            } else if(clearGoalList[i].includes('장착하기')) {
                let setCountGoals = stageObject.stage.goal_list.filter((goal: any) => goal.goal === 'set');
                let items = stageObject.stage.init_item_list.filter((goal: any) => goal.type === 'liquid_fuel' || goal.type === 'solid_propellant' || goal.type === 'engines');
                let count = 0; 

                for (let i = 0; i < items.length; i++) {
                    for (let j = 0; j < frame.item_list.length; j++) {
                        if (items[i].id == j && frame.item_list[j] == 1) {
                            count++; 
                        }
                    }
                }

                if(count >= setCountGoals[0].count) {
                    clearGoalColorArray[i] = 'rgb(255 210 87)';
                } 
            }
        }

    }

    function handlePlay() {
        playCanPause = true;
        if(isPause) {
            (window as any).ExternalResumeGame ();
            isPause = false;
            updateFrame(framesData, parseInt(progressController.value));
        } else if(canExecute) {
            executePython();
        } else {
            (window as any).OnClickPlay();
            if(progressController.value === progressController.max) {
                progressController.value = "0";
            }
            updateFrame(framesData, parseInt(progressController.value));
        }
    }

    function handlePause() {
        (window as any).SetProgressId?.(parseInt(progressController.value));
        (window as any).ExternalPauseGame();
        playCanPause = false;
        isPause = true;
        clearInterval(frameUpdateIntervalId);
        frameUpdateIntervalId = null;
    }

    function handleProgressChange() { 
        canExecute = false;
        (window as any).SetProgressId?.(parseInt(progressController.value));
    }

    function updateFrame(framesData: any, currentFrameIndex: number) {
        const frameRate = 60; // ToDo: 실제 초당 프레임수로
        const success = framesData[framesData.length - 1].status === 1;
        frameUpdateIntervalId = setInterval(() => {
            playCanPause = true;
            canExecute = false;
            if (currentFrameIndex < framesData.length) {
                const frame = framesData[currentFrameIndex];
                progressController.value = currentFrameIndex.toString();
                updateHighlight(frame.line_num);
                updateClearGoal(frame);
                hpTest3(frame.player.hp);
                currentFrameIndex++;
            } else {
                playCanPause = false;
                canExecute = true;
                clearInterval(frameUpdateIntervalId); // 인터벌 종료, 초기화
                frameUpdateIntervalId = null; 
                if (success) {
                    showCompleteBtn = true;
                }
            }
        }, 1000 / frameRate);
    }

    function doComplete() {
        batchPlayLog();
        if((gameMapDto.level === 3 || (gameMapDto.difficulty === "0" && gameMapDto.level === 2))) {
            showClearPopup = true;
        }
        routeToNext();
    }

    function routeToNext() {
        const nextLevel = gameMapDto.id + 1;
        if((gameMapDto.level === 3 || (gameMapDto.difficulty === "0" && gameMapDto.level === 2))) {
            return;
        } else if (gameMapDto.difficulty === "0") {
            openLayer = true;
            setTimeout(() => {
                window.location.href = `/game/tutorial/${nextLevel}`;
            }, 500);
        } else {
            openLayer = true;
            setTimeout(() => {
                window.location.href = `/game/${gameMapDto.stage}/${nextLevel}`;
            }, 500);
        }
    }

    function routeToSage() {
        window.location.href = `/game/${gameMapDto.stage}`;
    }

    function showModal() {
        hintModal.classList.remove('hidden') // 모달을 보여주는 함수
    }
  
    function closeModal() {
        hintModal.classList.add('hidden') // 모달을 닫는 함수
    }

    function test(value: string) {
    let oldValue = editor.getValue();
    let newValue = oldValue + value;
    editor.setValue(newValue, 1);

    let newValueSplit = newValue.split('\n');
    let lastLineIndex = newValueSplit.length - 2; 
    let lastLine = newValueSplit[lastLineIndex];

    let tabCount = (lastLine.match(/\t/g) || []).length;

    let tabsToAdd = '\t'.repeat(tabCount);
    if (tabCount > 0) {
        editor.setValue(newValue + tabsToAdd, 1);
    }

    editor.focus();
}
</script>

<audio id="myAudio" autoplay>
    <source src="/sound/inGame_sound.mp3" type="audio/mpeg">
</audio>
<div class="flex flex-col items-center justify-center overflow-hidden">
    <div class="w-screen h-screen relative flex flex-row">

        {#if showClearPopup}
        <div class="absolute top-[50%] left-[50%] w-[1172px] h-[871px] z-[80]" style="background-image:url('/img/inGame/clearPop/ui_popup_clear_background.png');transform:translate(-50%, -50%) scale({scaleMultiplier - 0.2});">
            <div class="text-[43px] font-[900] italic absolute top-[14px] left-[165px]" style="color:rgb(64 226 255)">미션 승리</div>
            <div class="w-[46px] h-[46px] absolute right-[20px] top-[65px] cursor-pointer" style="background-image:url('/img/inGame/clearPop/btn_popup_close.png');" on:click={() => showClearPopup = false}></div>
            <div class="w-[1030px] h-[446px] absolute top-[165px] left-[110px]" style="background-image:url('/img/inGame/clearPop/ui_clear_background2.png');">
                <div class="absolute w-full top-[55px] left-[-145px]" style="transform:scale(0.7)">
                    <div class="text-[50px] font-[900] italic absolute top-[50px] left-[50px]" style="color:rgb(64 226 255)">획득 보상</div>
                    <div id="star1" class="w-[203px] h-[203px] absolute top-[175px] left-[50px]" style="background-image:url('/img/inGame/clearPop/ui_itemframe.png');transform:scale(1);">
                        <div class="w-[124px] h-[58px] absolute top-[50px] left-[40px]" style="background-image:url('/img/inGame/clearPop/icon_exp.png');transform:scale(1.7);"></div>
                        <div class="text-[60px] text-white font-[900] absolute top-[110px] w-full text-center" style="text-shadow:1px 0 black, -1px 0 black, 0 1px black, 0 -1px black;">{gameMapDto.rewardExp}</div>
                    </div>
                    <div id="star2" class="w-[203px] h-[203px] absolute top-[175px] left-[300px]" style="background-image:url('/img/inGame/clearPop/ui_itemframe.png');transform:scale(1);">
                        <div class="w-[102px] h-[110px] absolute top-[30px] left-[55px]" style="background-image:url('/img/inGame/clearPop/icon_gem.png');transform:scale(1.6)"></div>
                        <div class="text-[60px] text-white font-[900] absolute top-[110px] w-full text-center" style="text-shadow:">{gameMapDto.rewardJewel}</div>
                    </div>
                    <div id="star3" class="w-[203px] h-[203px] absolute top-[175px] left-[550px]" style="background-image:url('/img/inGame/clearPop/ui_itemframe.png');transform:scale(1);">
                        <div class="w-[203px] h-[203px] absolute top-[10px] left-[10px]" style="background-image:url('{gameMapDto.rewardItem?.sourcePath}');background-size:contain;background-repeat:no-repeat;"></div>
                    </div>
                </div>
                <div id="star4" class="w-[346px] h-[289px] absolute right-[55px] top-[95px]" style="background-image:url('/img/inGame/clearPop/icon_complete.png');transform:scale(0.6);"></div>          
            </div>
            <div class="w-[202px] h-[67px] absolute bottom-[60px] left-[110px]" style="background-image:url('/img/inGame/clearPop/ui_lv_background.png');">
                <div class="font-[900] text-[50px] text-white flex justify-center ml-[85px] leading-[1.2] h-full">{rq.getPlayerLeve()}</div>
            </div>
            <div class="w-[252px] h-[51px] absolute bottom-[75px] left-[400px]" style="background-image:url('/img/inGame/clearPop/ui_gembox2.png');transform:scale(1.3);">
                <div class="font-[700] text-[40px] text-white flex justify-center ml-[75px] leading-[1.4] h-full">{rq.member.player.gems.toLocaleString()}</div>
            </div>
            <div class="w-[299px] h-[102px] absolute bottom-[50px] right-[85px] cursor-pointer" style="background-image:url('/img/inGame/clearPop/btn_action2.png');transform:scale(1.2)">
                <div class="w-[299px] h-[102px] absolute top-[9px] left-[-10px]" style="">
                    <div class="font-[900] text-[40px] leading-[2.1] italic" style="color:rgb(9 13 24);" on:click={() => routeToSage()}>계속</div>
                </div>
            </div>
        </div>
        {/if}

        <div class="relative w-full bg-gray-500" style="width:{widthMultiplier}px;">
            <div id="game-player-container" class="flex justify-center items-center h-full"> 
                <Cocos {gameMapDto} {isCoReady} on:ready="{e => isCoReady = e.detail.isCoReady}"/>
            </div>

            <a href="/game/{gameMapDto.stage}" class="absolute w-[134px] h-[134px] top-[2%] left-[1%] z-[10]" 
                style="background-image:url('/img/inGame/btn_back.png');transform:scale(0.4); transform-origin:left top"></a>

            <div class="w-[401px] h-[150px] flex flex-col absolute top-[15%] left-[0] gap-2" 
                style="background-image:url('/img/inGame/ui_player_status.png');transform-origin:left top;transform:scale(0.6)">
                <div class="w-full h-[54px]"></div> 
                <div class="flex flex-row absolute top-[72px]">
                    <div class="w-[76px] h-[36px] ml-2" style="background-image:url('/img/inGame/icon_hp.png');transform:scale(0.8)"></div>
                    <div id="health-bar" class="flex items-center border-4 w-[260px] ml-4" data-total="100" data-value="100">
                        <div class="bar background-bar absolute w-[{hpTest2}%]" style="width:{hpTest2}%"></div> 
                        <div class="bar foreground-bar absolute w-[{hpTest}%] {hpTest <= 50 ? hpTest <= 30 ? 'yellow-bar' : 'green-bar' : ''}" style="width:{hpTest}%"></div> 
                    </div>
                </div> 
            </div>

            <div class="w-[1044px] h-[445px] absolute top-[0] right-[0]" style="background-image:url('/img/inGame/ui_background_R.png');transform-origin:top right;transform:scale(0.45)"></div>
            <div class="absolute flex flex-col items-end justify-start absolute right-[10px] top-[2%] text-start" 
                  style="white-space:pre-wrap;transform-origin:top right;transform:scale(0.6);">
                  <div class="flex flex-row gap-2 w-full scale-[0.87] origin-top-right">
                    <div class="w-[506px] h-[134px] flex justify-center items-center italic" style="background-image:url('/img/inGame/ui_stage_title.png')">
                        <div class="text-[50px] font-[900]" style="color:rgb(64 226 255)">{gameMapDto.step} {#if gameMapDto.difficulty !== "0"} {gameMapDto.difficulty} {/if}</div>
                    </div>
                    <div class="w-[134px] h-[134px]" style="background-image:url('/img/map/btn_settomg_2.png')"></div>
                  </div>
                  <div class="flex flex-col" style="transform-origin:top right;transform:scale(1.03);">
                    <div class="w-[547px] h-[76px]" style="background-image:url('/img/inGame/ui_goal_start.png');">
                        {#if showCompleteBtn}
                        <div class="text-[35px] ml-8 font-[900] mt-[15px]" style="color:rgb(255 210 87);">목표 : 달성</div>
                        {:else}
                        <div class="text-[35px] ml-8 font-[900] mt-[15px]" style="color:rgb(64 226 255);">목표 : 미달성</div>
                        {/if}
                    </div>
                    <div class="w-[547px] pl-4" style="background-image:url('/img/inGame/ui_goal_middle.png');">
                        {#each clearGoalList as goal, index}
                            <div class="text-[30px] font-[900] flex flex-row ml-[30px]" style="color:{clearGoalColorArray[index]};">
                                <div class="w-[40px] h-[20px]">
                                    {#if clearGoalColorArray[index] === 'rgb(255 210 87)'}
                                        ◆
                                    {:else}
                                        ◇
                                    {/if}
                                </div> 
                                <div>{goal}</div>   
                            </div>
                        {/each}
                    </div>
                    <div class="w-[547px] h-[71px]" style="background-image:url('/img/inGame/ui_goal_end.png');"></div>
                  </div>
            </div>
            
            <div class="flex flex-row items-center absolute bottom-[4%] left-[0] gap-6 w-[95%] ml-[2.5%]" style="background-color:#181818;">
                <div class="flex flex-row w-full gap-2">
                    <div class="w-[38px] h-[38px] cursor-pointer" 
                        style="background-image:{volumeCanMute ? 'url("/img/inGame/btn_Volume_on.png");' : 'url("/img/inGame/btn_Volume_mute.png");' }background-size:contain;background-repeat:no-repeat;"
                        on:click={() => volumeCanMute = !volumeCanMute}></div>
                    <div class="w-[38px] h-[38px] cursor-pointer" 
                        style="background-image:{playCanPause ? 'url("/img/inGame/btn_Control_Pause.png");' : 'url("/img/inGame/btn_Control_Play.png");' }background-size:contain;background-repeat:no-repeat;" 
                        on:click={() => {playCanPause ? handlePause() : handlePlay()}}></div> 
                    <div class="flex items-center w-full">
                        <input id="progressController" type="range" min="0" max="0" value="0" class="w-[98%]" bind:this={progressController} on:change={handleProgressChange}/>
                    </div>
                </div>
            </div>
        </div>
        <div id="editor-container" class="relative" style="transform-origin:top right;transform:scale({scaleMultiplier})">
            <div class="w-[633px] h-[1080px] flex flex-col items-center absolute" style="background-image:url('/img/inGame/ui_editor_frame.png'), url('/img/inGame/ui_editor_background.jpg');"> 
                <div class="flex flex-row justify-between h-[70px] w-full items-center">
                    <div></div> 
                    <div class="flex flex-row gap-[2rem] mr-10 mt-[-10px]">
                        <div class="w-[34px] h-[34px] z-[50] scale-[0.8] hidden" style="background-image:url('/img/inGame/btn_expand.png')"></div>
                        <div class="cursor-pointer w-[34px] h-[34px] scale-[0.8]" style="background-image:url('/img/inGame/btn_help.png')" on:click={showModal}></div>
                            <div bind:this={hintModal} class="w-[702px] h-[1080px] rounded-lg flex flex-col items-center justify-center absolute z-[99] top-0 right-0 origin-top-right {showGuide ? '' : ''}" 
                                style="background-image:url('/img/inGame/ui_help_background.png');">
                                <div class="absolute text-[35px] top-[630px] left-[90px] text-white">핵심내용</div>
                                <div class="flex flex-col items-center justify-center ml-[73px]">
                                    <div class="font-[900] text-[50px] absolute top-[12px] left-[200px]" style="color:rgb(64 226 255)">가이드</div>
                                    <div class="w-[46px] h-[46px] absolute top-[70px] right-[10px] cursor-pointer" style="background-image:url('/img/inGame/btn_popup_close.png')" on:click={() => closeModal()}></div>
                                    <div id="typing" class="h-[600px] w-[602px] pt-[150px] ml-[50px] text-[25px] mr-[35px] font-bold text-white text-left" style="white-space:pre-wrap;">
                                        {gameMapDto.guideText}
                                    </div>
                                    <div class="w-[602px] h-[250px] text-[22px] font-bold text-white text-left" 
                                        style="background-image:url('/img/inGame/ui_editor_background4.png');transform:scale(0.8)">
                                        <div class="w-full h-full flex items-start justify-start ml-10 mt-[30px]" style="white-space:pre-wrap;">
                                            {gameMapDto.guideImage}
                                        </div>
                                    </div>
                                    <div class="w-[299px] h-[102px] flex items-center justify-center cursor-pointer"
                                        style="background-image:url('/img/inGame/btn_action4.png');background-size:100% 100%" on:click={() => closeModal()}>  <!--closeModal()-->
                                        <div class="w-[208px] h-[74px]" style="">
                                            <div class="font-[900] text-[35px] flex justify-center leading-[80px]" style="color:rgb(9 13 24)">시작</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        <div class="w-[34px] h-[34px] hidden" style="background-image:url('/img/inGame/btn_reset.png');transform:scale(0.8);"></div> 
                    </div>
                </div>

                <div id="editorWrapper" class="flex justify-center w-[601px] h-[609px] pt-[40px] mt-[4px]" style="background-image:url('/img/inGame/ui_editor_background2.png');">
                    <div id="editor" class="w-[590px] h-[529px]"></div>
                </div>

                <div class="flex flex-row justify-around items-center w-[601px] h-[100px] mt-[14px]" style="background-image:url('/img/inGame/ui_editor_background3.png');background-size:cover;background-repeat:no-repeat">
                    <button class="w-[299px] h-[102px] text-[44px] font-[900] italic leading-[2.5]" style="background-image:url('/img/inGame/btn_action4.png');color:rgb(9 13 24);transform:scale(0.8);{canExecute ? '' : 'pointer-events: none;'}" on:click={executePython}>실행</button>
                    <button class="w-[299px] h-[102px] text-[44px] font-[900] italic leading-[2.5] {showCompleteBtn ? 'cursor-pointer' : 'cursor-default'}" 
                            style="background-image:{showCompleteBtn ? 'url("/img/inGame/btn_action2.png");' : 'url("/img/inGame/btn_action3.png");'}color:{showCompleteBtn ? 'rgb(9 13 24)' : 'rgb(9 13 24)'};transform:scale(0.8);"
                            on:click={() => {showCompleteBtn ? doComplete() : ''}}>완료</button>
                </div>
                <div class="flex justify-start items-center w-[602px] h-[250px] mt-[20px]" style="background-image:url('/img/inGame/ui_editor_background4.png')">
                    <div class="w-[400px] h-[200px] flex flex-col items-start pl-10 gap-2 pt-2">
                        {#each commandGuide as command}
                            {#if command === 'for i in range(3):'}
                                <div class="scale-up-on-hover font-bold text-white text-[22px] font-[900] cursor-pointer" on:click={() => test(command + "\n\t")}>{command}</div>
                            {:else}
                                <div class="scale-up-on-hover font-bold text-white text-[22px] font-[900] cursor-pointer" on:click={() => test(command + "\n")}>{command}</div>
                            {/if}
                        {/each}
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<TransitioningOpenLayer isCoReady={showStart} openLayer={openLayer}/>
