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
    let isCoReady: boolean = $state(false); // cocos 초기화 추적... 의미가 있는지 모르겠음

    $effect(() => { // ToDo: cocos 초기화 추적하여 화면 로드할때 활용
        if (isCoReady) {
            console.log('Cocos is ready');
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

    let opacity = $state(0); 
    let otherOpacity = $state(0);
    let otherOpacity2 = $state(0);
    let otherOpacity3 = $state(0);
    let otherOpacity4 = $state(0);
    let otherOpacity5 = $state(0);
    let mainOpacity = $state(0);
    let scale = $state(0);
    let scaleY = $state(0);

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

    });

    let markerId:any;

    function updateHighlight(HilightRow:any) { // Todo: svelte 반응성으로 호출되도록 HighlightRow 변수하나 파서 반응성 걸기
        const Range = ace.require('ace/range').Range;
        const session = editor.getSession();
        if (markerId !== undefined) {
          session.removeMarker(markerId);
        }
        const range = new Range(HilightRow - 3, 0, HilightRow - 3, 1);
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
            if (currentFrameIndex < framesData.length) {
                const frame = framesData[currentFrameIndex];
                progressController.value = currentFrameIndex.toString();
                updateHighlight(frame.line_num);
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
        <div class="relative" style="width:{widthMultiplier}px;">
            <div id="game-player-container" class="flex justify-center items-center h-full " style="width:{widthMultiplier}px;">
                <Cocos {gameMapDto} {isCoReady} on:ready="{e => isCoReady = e.detail.isCoReady}"/>
            </div>
            <a href="/game/1" class="absolute border-2 border-black w-fit top-[2%] left-[1%] z-[10]">뒤로가기</a>
            <div class="avatar top-[10%] left-[1%] absolute">
                <div class="w-16 rounded-full ring ring-primary ring-offset-base-100 ring-offset-2">
            </div>
            </div>
            <div class="flex flex-row absolute top-[20%] left-[1%] gap-2">
                <div class="border-2 h-fit">8</div>
                <div>
                    <div >홍길동</div>
                    <div >
                        <div>체력바</div>
                        <div>100/100</div>
                    </div>
                </div>
            </div>
            <div class="w-[10vw] h-[8vw] absolute border-2 border-black flex justify-center items-center absolute right-0 top-4 text-start" 
                  style="white-space:pre-wrap;">
                    {gameMapDto.clearGoal}
            </div>
            <div class="flex flex-row items-center absolute bottom-[4%] left-[auto] w-[95%] ml-[2.5%] gap-6" style="background-color:#181818;">
              <div id="volumeController" class="dropdown dropdown-top rounded-b-lg" style="background-color:#181818;width:2em;">
                  <div tabindex="0" role="button" class="m-1"><i class="fa-solid fa-volume-high" style="color:#6FC5F0"></div>
                  <input id="myRange" class="rotated-input dropdown-content absolute bottom-[0]" 
                        type='range' min='0' value='10' max='10' step='1'
                        style="transform:rotate(270deg);transform-origin: left top;bottom:-2px;"/>
              </div>
              <div class="flex flex-row gap-6 w-full">
                  <div class="flex items-center">
                      <i class="fa-solid fa-play cursor-pointer" style="color:#6FC5F0" on:click={handlePlay}></i>
                  </div>
                  <div class="flex items-center w-full">
                     <input id="progressController" type="range" min="0" max="0" value="0" class="w-[98%]" bind:this={progressController} on:change={handleProgressChange}/>
                  </div>
              </div>
            </div>
        </div>
        <div id="editor-container" class="relative" style="transform-origin:top right;transform:scale({scaleMultiplier})">
            <div bind:this={scan} class={`absolute top-0 left-0 w-full h-full z-[5] flex flex-col gap-8 items-center justify-center bg-gray-700 ${reverseScanning ? 'reverse-scanning-effect' : (scanning ? 'scanning-effect' : '')} hidden`}>
                <div class="w-[95%] h-1/4 border-2">
                    미션 설명
                </div>
                <div class="w-[95%] h-1/4 border-2">
                    미션 설명
                </div>
                <div class="w-[95%] h-1/4 border-2">
                    미션 설명
                </div>
                <button class="btn btn-wide" on:click={reverseScan}>시작하기</button>               
            </div>
            <!-- style="opacity:{mainOpacity};" -->
            <div class="w-[633px] h-[1080px] flex flex-col items-center" style="background-image:url('/img/inGame/ui_editor_frame.png'), url('/img/inGame/ui_editor_background.jpg');"> 
                <div class="flex flex-row justify-between h-[70px] w-full items-center">
                    <div>
                    </div>
                    <div class="flex flex-row gap-12 mr-10">
                        <div class="w-[34px] h-[34px] z-[50]" style="background-image:url('/img/inGame/btn_expand.png')"></div>
                        <div class="cursor-pointer w-[39px] h-[40px]" style="background-image:url('/img/inGame/btn_help.png')" on:click={showModal}></div>
                        <dialog bind:this={hintModal} id="my_modal_1" class="modal">
                          <div class="w-[600px] h-[90%] rounded-lg flex flex-col gap-8 items-center justify-center bg-gray-700">
                            <div class="w-[95%] h-1/4 border-2">
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
                            </div>
                          </div>
                        </dialog>
                        <div class="w-[38px] h-[38px]" style="background-image:url('/img/inGame/btn_reset.png')"></div>
                    </div>
                </div>

                <div class="flex justify-center">
                    <div id="editor" class="w-[551px] h-[609px]"></div>
                </div>

                <div class="flex flex-row justify-around items-center w-[551px] h-[110px] mt-4" style="background-image:url('/img/inGame/ui_editor_background3.png');background-size:cover;background-repeat:no-repeat">
                    <button class="w-[208px] h-[74px] text-[30px] font-[900] italic" style="background-image:url('/img/inGame/btn_start.png');color:rgb(64 226 225)" on:click={executePython}>실행</button>
                    <button class="w-[208px] h-[74px] text-[30px] font-[900] italic" style="background-image:url('/img/inGame/btn_complete.png');color:rgb(255 210 87)">완료</button>
                </div>
                <div class="flex justify-start items-center w-[602px] h-[250px]" style="background-image:url('/img/inGame/ui_editor_background4.png')">
                    <div class="border-2 border-black w-[400px] h-[200px] flex flex-col items-start pl-4 gap-2 pt-2">
                        {#each commandGuide as command}
                            {#if command === 'for i in range(3):'}
                                <div class="font-bold text-black border-2 border-black cursor-pointer" on:click={() => test(command + "\n\t")}>{command}</div>
                            {:else}
                                <div class="font-bold text-black border-2 border-black cursor-pointer" on:click={() => test(command + "\n")}>{command}</div>
                            {/if}
                        {/each}
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- <TransitioningOpenLayer isCoReady={isCoReady}/> -->