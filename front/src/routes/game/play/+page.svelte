<svelte:head>
    <script type="text/javascript" src="/brython-runner.bundle.js"></script>

    <title>{rq.SITE_NAME} | 맵 이름</title>
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
  
 
	
    

<div class="flex flex-col items-center justify-center p-8 h-[90vh]">
    <div class="border-2 border-black w-full h-full flex flex-row">
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
            <div class="w-[10vw] h-[8vw] border-2 border-black flex justify-center items-center absolute right-0 top-4">
                미션정보
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
            <div id="editor" style="height: 500px; width: 100%;"></div>
            <div class="flex flex-row justify-around">
                <button class="btn btn-outline" on:click={handleRunCode}>Run Python Code</button>
                <button class="btn btn-outline">완료</button>
            </div>
            <div class="flex justify-center items-center mt-[4vh]">
                <div class="border-2 border-black w-[16vw] h-[8vw] flex justify-center items-center">
                    코드힌트
                </div>
            </div>
        </div>
    </div>
</div>
