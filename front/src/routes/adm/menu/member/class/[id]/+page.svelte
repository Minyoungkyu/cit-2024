<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';

    const { data } = $props<{ data: { memberDto: components['schemas']['MemberDto'] } }>();
    const { memberDto } = data;

    let programs = $state([]) as components['schemas']['SchoolClassInputDto'][];
    let filteredPrograms = $state([]) as components['schemas']['SchoolClassInputDto'][];

    let membersBox: HTMLDivElement | null = null;

    let focusProgram = $state(false);

    let programInput = $state(memberDto.schoolClasses) as components['schemas']['SchoolClassInputDto'][];
    let programInputText = $state('');

    async function loadProgram() {
        // console.log('loadProgram');
        if (programs.length > 0) {
            console.log('loadProgram');
            focusProgram = true;
            return;
        }

        const { data } = await rq.apiEndPoints().GET('/api/v1/school/class/input', {
        });

        programs = data?.data.schools || [];
        filteredPrograms = programs;
        focusProgram = true;
    }

    function updateProgram(searchText: string) {
        console.log('updateProgram');
        const searchLower = searchText.toLowerCase();
        filteredPrograms = [...programs].sort((a, b) => {
            const scoreA = similarityScore(a.className ?? '', searchLower);
            const scoreB = similarityScore(b.className ?? '', searchLower);
            return scoreB - scoreA; 
        });

        if (membersBox) membersBox.scrollTop = 0;
    }


    function similarityScore(regionName: string, searchText: string): number {
        const nameLower = regionName.toLowerCase();
        if (nameLower.startsWith(searchText)) return 100; 
        if (nameLower.includes(searchText)) return searchText.length; 
        return 0; 
    }


    async function submitModifyProgramForm(this: HTMLFormElement) {
        const form: HTMLFormElement = this;
        

        const { data, error } = await rq.apiEndPoints().PUT('/api/v1/members/class/modify', {
            body: {
                id: memberDto.id,
                password: form.password.value,
                name: form.membername.value,
                cellphoneNo: form.cellphoneNo.value,
                schoolClasses: programInput
            }
        });

        if (data?.resultCode == '200') {
            rq.msgAndRedirect(data, undefined, '/adm/menu/member/class');
        } else {
            rq.msgError(data?.msg??'오류가 발생했습니다.');
        }
    }

</script>

<div class="w-[95%] flex justify-start mt-[-60px] text-[22px] border-b mb-1 pb-[14px] font-bold">
    학급관리자 정보
</div>
<div class="w-[95%] h-screen flex justify-center">
    <form class="flex flex-col gap-4 w-full h-full" method="POST" on:submit|preventDefault={submitModifyProgramForm}>
        <div class="overflow-x-auto h-full">
            <table class="table">
              <tbody>
                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">아이디</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            {memberDto.username}
                        </div>
                    </td>
                  </tr>
                  <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">비밀번호 변경</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            <input name="password" type="password" placeholder="비밀번호 변경" class="input input-bordered w-[200px] text-center" />
                        </div>
                    </td>
                  </tr>
                  <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">이름</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            <input name="membername" type="text" placeholder="이름" class="input input-bordered w-[200px] text-center" value={memberDto.name} />
                        </div>
                    </td>
                  </tr>
                    
                    <tr>
                        <td class="border-b p-1 text-[15px] w-[150px] font-bold">휴대폰</td>
                        <td class="border-b p-3">
                            <div class="flex flex-col">
                                <input name="cellphoneNo" type="text" placeholder="휴대폰" class="input input-bordered w-[200px] text-center" value={memberDto.cellphoneNo} />
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td class="border-b p-1 text-[15px] w-[150px] font-bold">담당기관</td>
                        <td class="border-b p-3">
                            <div class="flex flex-col">
                                <div>
                                    <input name="program" type="search" placeholder="담당기관" class="input input-bordered w-[200px] text-center" 
                                        bind:value={programInputText}
                                        on:focus={() => loadProgram()}
                                        on:input={(event) => event.target && updateProgram((event.target as HTMLInputElement).value)}
                                        on:blur={() => setTimeout(() => { focusProgram = false; }, 100)}
                                        />
                                        {#if focusProgram}
                                        <div bind:this={membersBox} class="w-[200px] h-[200px] mt-[-2px] absolute z-[99] rounded-xl border-2 flex flex-col items-center overflow-y-auto whitespace-pre-wrap bg-white">
                                            {#each filteredPrograms as program}
                                                <div class="options w-[80%] text-center p-1 cursor-pointer" 
                                                on:click={() => {
                                                    if (!programInput.some(m => m.id === program.id)) {
                                                        programInput.push(program);
                                                    }}}>
                                                    {program.className}
                                                </div>
                                            {/each}
                                        </div>
                                        {/if}
                                </div>
                                {#each programInput as program}
                                    <div class="flex flex-row gap-2 text-[15px] ml-4 mt-2">
                                        <div class="w-full text-left">
                                            {program.className}
                                            <span class="ml-2 cursor-pointer" 
                                            on:click={() => programInput.splice(programInput.indexOf(program), 1)}>
                                                <i class="fa-regular fa-trash-can text-red-500"></i>
                                            </span>
                                        </div>
                                    </div>
                                {/each}
                            </div>
                        </td>
                      </tr>
              </tbody>
            </table>

            <div class="flex flex-row mt-10 mb-10 justify-center gap-2">
                <button class="btn btn-block btn-outline border-gray-400 gap-1 w-[100px]" type="button" on:click={() => rq.goTo('/adm/menu/member/class')}>
                    <span>목록</span>
                </button>
                <button class="btn btn-block btn-success btn-outline gap-1 w-[100px]" type="submit">
                    <span>저장</span>
                </button>
            </div>
          </div>
    </form>
</div>

<style>
    .options:hover {
        border-bottom: 2px solid gray;
    }
</style>