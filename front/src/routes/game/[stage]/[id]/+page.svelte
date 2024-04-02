<svelte:head>
    <script type="text/javascript" src="/brython-runner.bundle.js"></script>

    <title>{rq.SITE_NAME} | 맵 이름</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
</svelte:head>

<script lang="ts">
    import rq from '$lib/rq/rq.svelte';
	import { onMount } from 'svelte';
    import type { components } from '$lib/types/api/v1/schema';
    import { setupAceEditor } from '$lib/aceEdit/aceEditorSetup';
    import TWEEN, { update } from '@tweenjs/tween.js';
    import Cocos from '$lib/cocos/cocos.svelte';
    import { runPythonCode2 } from '$lib/pyodide/pyodide';
    import './page.css';
    import TransitioningOpenLayer from '$lib/game/TransitioningOpenLayer.svelte';

    const { data } = $props<{ data: { gameMapDto: components['schemas']['GameMapDto'] } }>();
    const { gameMapDto } = data;

    let editor: any;
    let hintModal: HTMLDialogElement 
    let scan: HTMLDivElement 
    let progressController: HTMLInputElement; 
    let commandGuide: string[] = gameMapDto.commandGuide.split(',');
    let framesData: [] = $state([]); // 파이썬 실행 결과 프레임 데이터
    let frameUpdateIntervalId: any = $state(null); // 에디터 하이라이터, 프로그레스바 업데이트 인터벌 아이디
    let isCoReady: boolean = $state(false); 
    let showStart: boolean = $state(false);
    let showGuide: boolean = $state(false);

    $effect(() => {
        if (isCoReady) {
            setTimeout(() => {
                showStart = true;
                setTimeout(() => {
                    showGuide = true;
                }, 1000);
            }, 1000);
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

    let mainOpacity = $state(0);
    
    let scanning = $state(false);
    let reverseScanning = $state(false);

    function startScanning() {
        scanning = true;
        reverseScanning = false;

        setTimeout(() => {
            scanning = false;
        }, 3000); // 애니메이션 시간과 동일하게 설정
    }

    function reverseScan() {
        reverseScanning = true; // 역방향 스캐닝 활성화
        scanning = false;
        mainOpacity = 1;

        setTimeout(() => {
            // reverseScanning = false; // 애니메이션이 끝나면 상태를 다시 false로 설정
        }, 3000); // 역방향 애니메이션 시간 설정
    }

    async function executePython(): Promise<void> {
        console.time("executePythonTimer"); // ToDo: remove

        if (frameUpdateIntervalId !== null) { // 인터벌 초기화
            clearInterval(frameUpdateIntervalId);
            frameUpdateIntervalId = null;
        }
        
        let result: any = await runPythonCode2(gameMapDto.cocosInfo, editor.getValue());
        framesData = JSON.parse(result.result);
        const wrappedData = {
            data: framesData
        };

        console.log(wrappedData);
        (window as any).SendStreamData?.(wrappedData);
        progressController.max = (framesData.length - 1).toString();
        updateFrame(framesData, 0);
        console.timeEnd("executePythonTimer"); // ToDo: remove
    }

    onMount(async () => {
        runPythonCode2( "", "");
    });

    const originalHeight = 1080;
    let currentHeight = $state(1080);
    let scaleMultiplier = $state(0);
    let widthMultiplier = $state(1920);

    onMount(() => {
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
            if (e.action === 'insert') {
                const totalLines = editor.getSession().getLength();
                const cursorPosition = editor.getCursorPosition();

                if (cursorPosition.row === totalLines - 1) {
                    editor.getSession().insert({row: totalLines, column: 0}, "\n");
                }
            }
        });

        editor.focus();

        startScanning();
        showModal();

    });

    let markerId:any;

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
        console.log(hpTest);
    }

    function updateHighlight(HilightRow:any) { // Todo: svelte 반응성으로 호출되도록 HighlightRow 변수하나 파서 반응성 걸기
        const Range = ace.require('ace/range').Range;
        const session = editor.getSession();
        if (markerId !== undefined) {
          session.removeMarker(markerId);
        }
        const range = new Range(HilightRow - 2, 0, HilightRow - 2, 1);
        markerId = session.addMarker(range, "editorHighlighter", "fullLine", false);
    }

    function handlePlay() {
        (window as any).OnClickPlay();
        updateFrame(framesData, parseInt(progressController.value));
    }

    function handleProgressChange() { 
        (window as any).SetProgressId?.(parseInt(progressController.value));
    }

    function updateFrame(framesData: any, currentFrameIndex: number) {
        const frameRate = 30; // ToDo: 실제 초당 프레임수로
        frameUpdateIntervalId = setInterval(() => {
            playCanPause = true;
            if (currentFrameIndex < framesData.length) {
                const frame = framesData[currentFrameIndex];
                progressController.value = currentFrameIndex.toString();
                updateHighlight(frame.line_num);
                hpTest3(frame.player.hp);
                currentFrameIndex++;
            } else {
                clearInterval(frameUpdateIntervalId); // 인터벌 종료, 초기화
                frameUpdateIntervalId = null; 
            }
        }, 1000 / frameRate);
    }

    function showModal() {
        hintModal.showModal(); // 모달을 보여주는 함수
    }
  
    function closeModal() {
        hintModal.close(); // 모달을 닫는 함수
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

<div class="flex flex-col items-center justify-center overflow-hidden">
    <div class="w-screen h-screen flex flex-row">
        <div class="relative w-full bg-gray-500" style="width:{widthMultiplier}px;">
            <div id="game-player-container" class="flex justify-center items-center h-full"> <!-- ToDo: remove hidden-->
                <Cocos {gameMapDto} {isCoReady} on:ready="{e => isCoReady = e.detail.isCoReady}"/>
            </div>
            <!-- 뒤로가기 -->
            <a href="/game/{gameMapDto.stage}" class="absolute w-[134px] h-[134px] top-[2%] left-[1%] z-[10]" 
                style="background-image:url('/img/inGame/btn_back.png');transform:scale(0.4); transform-origin:left top"></a>

            <!-- charator status -->
            <div class="w-[401px] h-[150px] flex flex-col absolute top-[15%] left-[0] gap-2" 
                style="background-image:url('/img/inGame/ui_player_status.png');transform-origin:left top;transform:scale(0.6)">
                <div class="w-full h-[54px]"></div> <!-- top margin -->
                <div class="flex flex-row">
                    <div class="w-[76px] h-[36px]" style="background-image:url('/img/inGame/icon_hp.png')"></div>
                    <div id="health-bar" class="flex items-center border-4 w-[280px] ml-4" data-total="100" data-value="100">
                        <div class="bar background-bar absolute w-[{hpTest2}%]" style="width:{hpTest2}%"></div> 
                        <div class="bar foreground-bar absolute w-[{hpTest}%] {hpTest <= 50 ? hpTest <= 30 ? 'yellow-bar' : 'green-bar' : ''}" style="width:{hpTest}%"></div> 
                    </div>
                </div> 
            </div>

            <!-- stage status -->
            <div class="absolute flex flex-col items-end justify-start absolute right-1 top-4 text-start hidden" 
                  style="white-space:pre-wrap;">
                  <div class="flex flex-row gap-2 w-full scale-[0.87] origin-top-right">
                    <div class="w-[506px] h-[134px]" style="background-image:url('/img/inGame/ui_stage_title.png')"></div>
                    <div class="w-[134px] h-[134px]" style="background-image:url('/img/map/btn_settomg_2.png')"></div>
                  </div>
                  <div class="flex flex-col">
                    <div class="w-[547px] h-[23px]" style="background-image:url('/img/inGame/ui_goal_start.png');"></div>
                    <div class="flex flex-col {test2 ? 'scanning-effect' : 'reverse-scanning-effect'}" >
                        <div class="w-[547px]" style="background-image:url('/img/inGame/ui_goal_middle.png');">
                            <div>{gameMapDto.clearGoal}</div>
                        </div>
                    </div>
                    <div class="w-[547px] h-[58px] flex justify-end items-center" style="background-image:url('/img/inGame/ui_goal_end.png')">
                        <div class="w-[35px] h-[24px] mr-4 cursor-pointer" 
                        style="background-image:{test2 ? 'url("/img/inGame/ui_up.png");' : 'url("/img/inGame/ui_down.png"'}" on:click={() => test2 = !test2}></div>
                    </div>
                  </div>
            </div>
            <!-- w-[150%] transform:scale(0.6);transform-origin:left-->
            <div class="flex flex-row items-center absolute bottom-[4%] left-[0] gap-6 w-[95%] ml-[2.5%]" style="background-color:#181818;">
              <!-- <div id="volumeController" class="dropdown dropdown-top rounded-b-lg" style="background-color:#181818;width:2em;">
                  <div tabindex="0" role="button" class="m-1"><i class="fa-solid fa-volume-high" style="color:#6FC5F0"></div>
                  <input id="myRange" class="rotated-input dropdown-content absolute bottom-[0]" 
                        type='range' min='0' value='10' max='10' step='1'
                        style="transform:rotate(270deg);transform-origin: left top;bottom:-2px;"/>
              </div> -->
                <div class="flex flex-row w-full gap-2">
                    <div class="w-[61px] h-[52px] cursor-pointer" style="background-image:{volumeCanMute ? 'url("/img/inGame/btn_Volume_on.png");' : 'url("/img/inGame/btn_Volume_mute.png");' }" on:click={() => volumeCanMute = !volumeCanMute}></div>
                    <div class="w-[61px] h-[52px] cursor-pointer" style="background-image:{playCanPause ? 'url("/img/inGame/btn_Control_Pause.png");' : 'url("/img/inGame/btn_Control_Play.png");' }" on:click={handlePlay}></div>
                    <div class="flex items-center w-full">
                        <input id="progressController" type="range" min="0" max="0" value="0" class="w-[98%]" bind:this={progressController} on:change={handleProgressChange}/>
                    </div>
                </div>
            </div>
        </div>
        <div id="editor-container" class="relative" style="transform-origin:top right;transform:scale({scaleMultiplier})">
            <!-- style="opacity:{mainOpacity};" -->
            <div class="w-[633px] h-[1080px] flex flex-col items-center absolute" style="background-image:url('/img/inGame/ui_editor_frame.png'), url('/img/inGame/ui_editor_background.jpg');"> 
                <div class="flex flex-row justify-between h-[70px] w-full items-center">
                    <div></div> <!-- 빈 div (for flex)-->
                    <div class="flex flex-row gap-12 mr-10">
                        <div class="w-[34px] h-[34px] z-[50] scale-[0.8] hidden" style="background-image:url('/img/inGame/btn_expand.png')"></div> <!-- Todo: remove hidden -->
                        <div class="cursor-pointer w-[39px] h-[40px] scale-[0.8]" style="background-image:url('/img/inGame/btn_help.png')" on:click={showModal}></div>
                        <dialog bind:this={hintModal} id="" class="modal">
                            <div class="w-[702px] h-[1080px] rounded-lg flex flex-col items-center justify-center absolute top-0 right-0 origin-top-right {showGuide ? '' : 'hidden'}" 
                                style="background-image:url('/img/inGame/ui_help_background.png');transform:scale({scaleMultiplier + 0.01})">
                                <!-- <div class="w-[95%] h-1/4 border-2">
                                    {gameMapDto.guideImage}
                                </div>
                                <div class="w-[95%] h-1/4 border-2">
                                    {gameMapDto.guideText}
                                </div>
                                <div class="w-[95%] h-1/4 border-2">
                                    {gameMapDto.commandGuide}
                                </div>
                                <div class="modal-action">
                                <button class="btn" on:click={closeModal}>Close</button>
                                </div> -->
                                <!-- hint modal -->
                                <div class="flex flex-col items-center justify-center ml-[73px]">
                                    <div class="font-[900] text-[50px] absolute top-[8px] left-[180px]" style="color:rgb(64 226 255)">가이드</div>
                                    <div class="w-[46px] h-[46px] absolute top-[70px] right-[10px] cursor-pointer" style="background-image:url('/img/inGame/btn_popup_close.png')" on:click={() => closeModal()}></div>
                                    <div class="h-[600px]"></div>
                                    <div class="w-[602px] h-[250px]" style="background-image:url('/img/inGame/ui_editor_background4.png');transform:scale(0.8)"></div>
                                    <div class="w-[300px] h-[94px] flex items-center justify-center cursor-pointer"
                                        style="background-image:url('/img/inGame/ui_editor_background3.png');background-size:100% 100%" on:click={() => closeModal()}>
                                        <div class="w-[208px] h-[74px]" style="background-image:url('/img/inGame/btn_start.png')">
                                            <div class="font-[900] text-[50px] flex justify-center leading-[83px]" style="color:rgb(64 226 255)">시작</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </dialog>
                        <div class="w-[38px] h-[38px] hidden" style="background-image:url('/img/inGame/btn_reset.png');transform:scale(0.8);"></div> <!-- Todo: remove hidden -->
                    </div>
                </div>

                <div class="flex justify-center">
                    <div id="editor" class="w-[590px] h-[609px]"></div>
                </div>

                <div class="flex flex-row justify-around items-center w-[601px] h-[94px] mt-4" style="background-image:url('/img/inGame/ui_editor_background3.png');background-size:cover;background-repeat:no-repeat">
                    <button class="w-[208px] h-[74px] text-[30px] font-[900] italic" style="background-image:url('/img/inGame/btn_start.png');color:rgb(64 226 225)" on:click={executePython}>실행</button>
                    <button class="w-[208px] h-[74px] text-[30px] font-[900] italic" style="background-image:url('/img/inGame/btn_complete.png');color:rgb(255 210 87)">완료</button>
                </div>
                <div class="flex justify-start items-center w-[602px] h-[250px] mt-4" style="background-image:url('/img/inGame/ui_editor_background4.png')">
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

<TransitioningOpenLayer isCoReady={showStart}/>