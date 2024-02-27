<svelte:head>
    <script type="text/javascript" src="/brython-runner.bundle.js"></script>

    <title>{rq.SITE_NAME} | 맵 이름</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</svelte:head>

<script lang="ts">
    import rq from '$lib/rq/rq.svelte';
    import { onMount } from 'svelte';
    import { setupAceEditor } from '$lib/aceEdit/aceEditorSetup';
    import { runPythonCode } from '$lib/brython/brythonSetup';
    import TWEEN from '@tweenjs/tween.js';
    import Cocos from '$lib/cocos/cocos.svelte';
    import { getContext } from 'svelte';
    import type { Writable } from 'svelte/store';
    const isInitialized = getContext<Writable<boolean>>('isInitialized');

    let readyToShow = false;
    
    $: $isInitialized ? readyToShow = true : null;
  
    // style="opacity:{opacity};transform:scaleY({scaleY});transform-origin:center;"

    let editor: any;
    let modal: HTMLDialogElement;
    let scan: HTMLDivElement;

    const explanation: String = `#로켓의 재료를 향해 가세요.\n#폭탄을 피하세요!\n#아래에 코드를 입력하고 완료되면 실행을 클릭합니다.\n\n`;// TODO : 문제에 맞게 코드넣기(fetch)
    const customCompletions = [ // TODO : 문제에 맞게 코드넣기(fetch)
            {value: "hero.moveUp();", score: 1000},
            {value: "hero.moveDown();", score: 1000},
            {value: "hero.moveLeft();", score: 1000},
            {value: "hero.moveRight();", score: 1000, meta: "custom"}
    ];

    let opacity = 0; 
    let otherOpacity = 0;
    let otherOpacity2 = 0;
    let otherOpacity3 = 0;
    let otherOpacity4 = 0;
    let otherOpacity5 = 0;
    let mainOpacity = 0;
    let scale = 0;
    let scaleY = 0;

    let scanning = false;
    let reverseScanning = false;

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
        await runPythonCode(editor);

    }

    function showModal() {
      modal.showModal(); // 모달을 보여주는 함수
    }
  
    function closeModal() {
      modal.close(); // 모달을 닫는 함수
    }

</script>
  
 
	
    

<div class="flex flex-col items-center justify-center">
    <div class="w-screen h-screen flex flex-row">
        <div class="border-2 border-black w-2/3 relative">
            <div id="game-player-container">
                <Cocos />
            </div>
            <a href="/main/stage" class="absolute border-2 border-black w-fit top-[2%] left-[1%]">뒤로가기</a>
            <div class="avatar top-[10%] left-[1%]" style="opacity:{otherOpacity2};">
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
            <div class="w-[10vw] h-[8vw] border-2 border-black flex justify-center items-center absolute right-0 top-4" 
                  style="transform:scale({scale})">
                미션정보
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
            <div bind:this={scan} class={`absolute top-0 left-0 w-full h-full z-[5] flex flex-col gap-8 items-center justify-center bg-gray-700 ${reverseScanning ? 'reverse-scanning-effect' : (scanning ? 'scanning-effect' : '')}`}>
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
            <div class="w-full h-full" style="opacity:{mainOpacity};">
                <div class="flex flex-row justify-between mx-4">
                    <div>
                        \\\\
                    </div>
                    <div class="flex flex-row gap-2">
                        <div>X</div>
                        <div class="cursor-pointer" on:click={showModal}>?</div>
                        <dialog bind:this={modal} id="my_modal_1" class="modal">
                          <div class="w-[600px] h-[90%] rounded-lg flex flex-col gap-8 items-center justify-center bg-gray-700">
                            <div class="w-[95%] h-1/4 border-2">
                              미션 설명
                            </div>
                            <div class="w-[95%] h-1/4 border-2">
                                미션 설명
                            </div>
                            <div class="w-[95%] h-1/4 border-2">
                                미션 설명
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
                        코드힌트
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<style>
    @keyframes scan {
        0% {
            clip-path: inset(0 0 100% 0);
        }
        100% {
            clip-path: inset(0 0 0 0);
        }
    }

    .scanning-effect {
        animation: scan 3s linear forwards;
    }

    @keyframes reverseScan {
        0% {
            clip-path: inset(0 0 0 0);
        }
        100% {
            clip-path: inset(0 0 100% 0);
        }
    }

    .reverse-scanning-effect {
        animation: reverseScan 3s linear forwards;
    }

.rotated-input {
        transform: rotate(270deg);
    }

:root {
  --base-color: #71c8f1;
  --background-color: #181818;
  --border-color: #242424;
}

legend {
  font-size: 1.8em;
  font-family: "Lobster", sans-serif;
  padding-left: .3em;
  padding-right: .2em;
  color: #ACE;
  text-shadow: 
    -.125em .05em 0 #214893,
    -.25em .1em 0 #313131;
}

#myRange::-webkit-slider-thumb,
#myRange::-moz-range-thumb,
#myRange::-ms-thumb {
    cursor: ns-resize;
    margin: 4.5em -4.5em;
    display: inline-block;
}

/* Slider */
#myRange {
  display: block;
  margin: 0; padding: 0;
  font-size: inherit;
  width: 6em; height: 2em;
  border-radius: .25em;
  border: .2em solid var(--border-color);
  background-color: var(--border-color);
  background-size: 100% 100%;
  background-repeat: no-repeat;
  overflow: hidden;
  cursor: pointer;
  transition: box-shadow .2s linear;
  box-shadow: 0 0 0 0 transparent;
}

/* input[type='range']:focus { 
  box-shadow: 0 0 0 .1em  #AAAAAA; 
}

input[type='range']:hover { 
  box-shadow: 0 0 0 .15em #6FC5F0; 
} */

/* Slider::-track */
#myRange::-webkit-slider-runnable-track,
#myRange::-moz-range-track,
#myRange::-ms-track {
  border: none;
  background: none;
  height: 100%;
  width: 100%;
}

/* Slider::-thumb */
#myRange::-webkit-slider-thumb,
#myRange::-moz-range-thumb,
#myRange::-ms-thumb {
  margin: .05em; padding: 0;
  height: .9em; width: .9em;
  border-radius: .1em;
  box-sizing: border-box;
  border: none;
  background-color: #6FC5F0;
  cursor: ew-resize;
}

/* Browser tweaks */
/* webkit */
#myRange,
#myRange::-webkit-slider-runnable-track, 
#myRange::-webkit-slider-thumb {
  -webkit-appearance: none;
}

/* IE */
#myRange::-ms-track {
  color: transparent;
}
#myRange::-ms-fill-lower, 
#myRange::-ms-fill-upper, 
#myRange::-ms-tooltip {
  display: none;
}

#myRange::-webkit-slider-thumb {
  margin: .05em; 
  padding: 0;
  height: 2.9em; 
  width: .9em;
  border-radius: .1em;
  box-sizing: border-box;
  border: none;
  background-color: #6FC5F0;
  box-shadow:
    -10em 0 0 0 #313131, -9em 0 0 0 #313131,
    -8em 0 0 0 #2F343F, -7em 0 0 0 #283F6B,
    -6em 0 0 0 #214893, -5em 0 0 0 #1A52BC,
    -4em 0 0 0 #2769D3, -3em 0 0 0 #3E87DC,
    -2em 0 0 0 #55A5E6, -1em 0 0 0 #6FC5F0,

    1em 0 0 0 var(--background-color), 2em 0 0 0 var(--background-color),
    3em 0 0 0 var(--background-color), 4em 0 0 0 var(--background-color),
    5em 0 0 0 var(--background-color), 6em 0 0 0 var(--background-color),
    7em 0 0 0 var(--background-color), 8em 0 0 0 var(--background-color),
    9em 0 0 0 var(--background-color);
  cursor: pointer;
}
</style>


