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
  
    let editor: any;

    const explanation: String = `#로켓의 재료를 향해 가세요.\n#폭탄을 피하세요!\n#아래에 코드를 입력하고 완료되면 실행을 클릭합니다.\n\n`;// TODO : 문제에 맞게 코드넣기(fetch)
    const customCompletions = [ // TODO : 문제에 맞게 코드넣기(fetch)
            {value: "hero.moveUp();", score: 1000},
            {value: "hero.moveDown();", score: 1000},
            {value: "hero.moveLeft();", score: 1000},
            {value: "hero.moveRight();", score: 1000, meta: "custom"}
    ];

    onMount(() => {
        editor = setupAceEditor('editor', customCompletions);
        editor.setValue(explanation, 1); 
        editor.focus();
    });

    async function handleRunCode() {
        await runPythonCode(editor);

    }

</script>
  
 
	
    

<div class="flex flex-col items-center justify-center">
    <div class="w-screen h-screen flex flex-row">
        <div class="border-2 border-black w-2/3 relative">
            <div id="game-player-container"></div>
            <a href="/main/stage" class="absolute border-2 border-black w-fit top-[2%] left-[1%]">뒤로가기</a>
            <div class="avatar top-[10%] left-[1%]">
                <div class="w-16 rounded-full ring ring-primary ring-offset-base-100 ring-offset-2">
                </div>
            </div>
            <div class="flex flex-row absolute top-[20%] left-[1%] gap-2">
                <div class="border-2 h-fit">8</div>
                <div>
                    <div>홍길동</div>
                    <div>체력바</div>
                </div>
            </div>
            <div class="w-[10vw] h-[8vw] border-2 border-black flex justify-center items-center absolute right-0 top-4 fadeInSlideUp">
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
            <div class="flex flex-row justify-between mx-4">
                <div>
                    \\\\
                </div>
                <div class="flex flex-row gap-2">
                    <div>X</div>
                    <div>?</div>
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

<style>
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


