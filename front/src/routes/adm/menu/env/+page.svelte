<script lang="ts">
    import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
	import { load } from "../dashBoard/+page";

    let bolen:boolean = $state(false);
    let inputWord:string = $state('');

    async function loadForbiddenWord() {
        const { data } = await rq.apiEndPoints().GET("/api/v1/envs/ForbiddenWords", {});

        if (data) {
            inputWord = data!.data.forbiddenWords!
        }
    }

    async function addForbiddenWord() {
        const { data } = await rq.apiEndPoints().PUT("/api/v1/envs/ForbiddenWords", {
            body: {
                word: inputWord
            }
        });

        rq.msgInfo('저장 성공')

        loadForbiddenWord();
    }

    onMount(() => {
        loadForbiddenWord();
    });
</script>

<div class="w-[95%] flex justify-start mt-[-60px] text-[40px] font-bold border-b mb-10">
    환경설정
</div>

<div class="flex flex-col w-full items-center">
    <table class="table w-[95%]">
        <tbody>
          <tr>
            <td class="border-2 border-black p-1 text-center font-bold text-[15px] w-[200px] h-[40px] bg-gray-200">사이트 제목</td>
            <td class="border-2 border-black p-1">
                코드이썬
            </td>
          </tr>
          <tr>
            <td class="border-2 border-black p-1 text-center font-bold text-[15px] w-[200px] h-[600px] bg-gray-200">금지어 목록</td>
            <td class="border-2 border-black p-1 ">
                <textarea class="textarea textarea-bordered w-full h-[600px] resize-none" bind:value={inputWord} placeholder="금지어"></textarea>
            </td>
          </tr>
        </tbody>
    </table>
</div>

<div>
    <div class="btn btn-wide mt-5" on:click={() => addForbiddenWord()}>저장</div>
</div>

