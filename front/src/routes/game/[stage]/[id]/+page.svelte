<svelte:head>
    <script type="text/javascript" src="/brython-runner.bundle.js"></script>

    <title>{rq.SITE_NAME} | 맵 이름</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</svelte:head>

<script lang="ts">
    import rq from '$lib/rq/rq.svelte';
	import { onMount } from 'svelte';
    import type { components } from '$lib/types/api/v1/schema';
    import { setupAceEditor } from '$lib/aceEdit/aceEditorSetup';
    import { runPythonCode } from '$lib/brython/brythonSetup';
    import TWEEN, { update } from '@tweenjs/tween.js';
    import Cocos from '$lib/cocos/cocos.svelte';
    import './page.css';

    const { data } = $props<{ data: { gameMapDto: components['schemas']['GameMapDto'] } }>();
    const { gameMapDto } = data;

    let editor: any;
    let hintModal: HTMLDialogElement 
    let scan: HTMLDivElement 

    const explanation: String = gameMapDto.editorMessage;

    const customCompletions = gameMapDto.editorAutoComplete.split(',')
    .filter(command => command.trim() !== '') 
    .map(command => ({
        value: `${command}`, 
        score: 1000, 
        meta: "custom" 
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

    function startHideAnimation() {
          new TWEEN.Tween({ opacity: 1, scaleY: 1, mainOpacity: 0 })
            .to({ opacity: 0, scaleY: 0, mainOpacity: 1 }, 1000) 
            .easing(TWEEN.Easing.Linear.None) 
            .onUpdate(({ opacity: updatedOpacity, scaleY: updatedHeight, mainOpacity: updatedMainOpacity }) => {
              opacity = updatedOpacity;
              scaleY = updatedHeight;
              mainOpacity = updatedMainOpacity;
            })
            .start();
        }

    let markerId:any;
    function updateHighlight(HilightRow:any) { // Todo: svelte 반응성으로 호출되도록 HighlightRow 변수하나 파서 반응성 걸기
        const Range = ace.require('ace/range').Range;
        const session = editor.getSession();
        if (markerId !== undefined) {
          session.removeMarker(markerId);
        }
        const range = new Range(HilightRow - 2, 0, HilightRow - 2, 1);
        markerId = session.addMarker(range, "editorHighlighter", "fullLine", false);
      }

    onMount(() => {
        editor = setupAceEditor('editor', customCompletions);
        editor.setValue(explanation, 1); 
        editor.focus();
        
        startScanning();

        new TWEEN.Tween({ opacity: 0, scaleY: 0})
          .to({ opacity: 1, scaleY: 1}, 1000) 
          .easing(TWEEN.Easing.Linear.None) 
          .onUpdate(({ opacity: updatedOpacity, scaleY: updatedScaleY }) => {
            opacity = updatedOpacity;
            scaleY = updatedScaleY;
          })
          .onComplete(() => { // 메인 애니메이션 완료 후
            new TWEEN.Tween({ otherOpacity: 0, scale: 1.2 })
              .to({ otherOpacity: 1, scale: 1 }, 500) 
              .easing(TWEEN.Easing.Back.Out)
              .onUpdate(({ otherOpacity: updatedOpacity, scale: updatedScale }) => {
                otherOpacity = updatedOpacity;
                scale = updatedScale;
              })
              .onComplete(() => {
                new TWEEN.Tween({ otherOpacity2: 0 })
                  .to({ otherOpacity2: 1 }, 500) 
                  .easing(TWEEN.Easing.Back.Out)
                  .onUpdate(({ otherOpacity2: updatedOpacity }) => {
                    otherOpacity2 = updatedOpacity;
                  })
                  .onComplete(() => {
                    new TWEEN.Tween({ otherOpacity3: 0 })
                      .to({ otherOpacity3: 1 }, 500) 
                      .easing(TWEEN.Easing.Back.Out)
                      .onUpdate(({ otherOpacity3: updatedOpacity }) => {
                        otherOpacity3 = updatedOpacity;
                      })
                      .onComplete(() => {
                        new TWEEN.Tween({ otherOpacity4: 0 })
                          .to({ otherOpacity4: 1 }, 500) 
                          .easing(TWEEN.Easing.Back.Out)
                          .onUpdate(({ otherOpacity4: updatedOpacity }) => {
                            otherOpacity4 = updatedOpacity;
                          })
                          .onComplete(() => {
                            new TWEEN.Tween({ otherOpacity5: 0 })
                              .to({ otherOpacity5: 1 }, 500) 
                              .easing(TWEEN.Easing.Back.Out)
                              .onUpdate(({ otherOpacity5: updatedOpacity }) => {
                                otherOpacity5 = updatedOpacity;
                              })
                              .start();
                          })
                          .start();
                      })
                      .start();                     
                  })
                  .start();
              })
              .start();
          })
          .start();

        function animate(time: number) {
          requestAnimationFrame(animate);
          TWEEN.update(time);
        }

        requestAnimationFrame(animate);
    });

    async function handleRunCode() {
        const capturedPrints: any[] = [];
        let currentFrameIndex = 0; 

        await runPythonCode(editor, gameMapDto.cocosInfo, capturedPrints);

        const framesData = JSON.parse(capturedPrints.pop()); // framesData cocos에게 전달
        console.log(framesData); // Todo: delete
        
        // 프레임 업데이트
        const frameRate = 30; // 초당 30프레임 가정
        const frameUpdateInterval = setInterval(() => {
            if (currentFrameIndex < framesData.length) {
                const frame = framesData[currentFrameIndex];
                updateHighlight(frame.line_num);
                currentFrameIndex++;
            } else {
                clearInterval(frameUpdateInterval); // 모든 프레임 처리 후 인터벌 종료
            }
        }, 1000 / frameRate);
    }

    function showModal() {
        hintModal.showModal(); // 모달을 보여주는 함수
    }
  
    function closeModal() {
        hintModal.close(); // 모달을 닫는 함수
    }

</script>

<div class="flex flex-col items-center justify-center">
    <div class="w-screen h-screen flex flex-row">
        <div class="border-2 border-black w-2/3 relative">
            <div id="game-player-container" class="flex justify-center items-center h-full">
                <!-- <Cocos /> -->
                <div id="pythonOutput">안녕</div>
            </div>
            <a href="/main/stage" class="absolute border-2 border-black w-fit top-[2%] left-[1%] z-[10]">뒤로가기</a>
            <div class="avatar top-[10%] left-[1%] absolute" style="opacity:{otherOpacity2};">
                <div class="w-16 rounded-full ring ring-primary ring-offset-base-100 ring-offset-2">
            </div>
            </div>
            <div class="flex flex-row absolute top-[20%] left-[1%] gap-2">
                <div class="border-2 h-fit" style="opacity:{otherOpacity3};">8</div>
                <div>
                    <div style="opacity:{otherOpacity4};">홍길동</div>
                    <div style="opacity:{otherOpacity5};">체력바</div>
                </div>
            </div>
            <div class="w-[10vw] h-[8vw] absolute border-2 border-black flex justify-center items-center absolute right-0 top-4" 
                  style="transform:scale({scale})">
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
                      <i class="fa-solid fa-play" style="color:#6FC5F0"></i>
                  </div>
                  <div class="flex items-center w-full">
                     <input type="range" min="0" max="100" value="0" class="w-[98%]"/>
                  </div>
              </div>
            </div>
        </div>
        <div class="border-2 border-black w-1/3 relative">
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
            <div class="w-full h-full"> 
                <div class="flex flex-row justify-between mx-4">
                    <div>
                        \\\\
                    </div>
                    <div class="flex flex-row gap-2">
                        <div>X</div>
                        <div class="cursor-pointer" on:click={showModal}>?</div>
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
                        <div>O</div>
                    </div>
                </div>
                <div id="editor" class="w-full h-2/3"></div>
                <div class="flex flex-row justify-around mt-4">
                    <button class="btn btn-outline" on:click={handleRunCode}>Run Python Code</button>
                    <button class="btn btn-outline">완료</button>
                </div>
                <div class="flex justify-start items-center mt-8 ml-8">
                    <div class="border-2 border-black w-[16vw] h-[8vw] flex justify-center items-center">
                        {gameMapDto.commandGuide}
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
