<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';

    let regions = $state([]) as components['schemas']['Region'][];
    let filteredRegions = $state([]) as components['schemas']['Region'][];
    let ads = $state([]) as components['schemas']['AdministrativeDistrict'][];
    let filteredAds = $state([]) as components['schemas']['AdministrativeDistrict'][];
    let schools = $state([]) as components['schemas']['SchoolInputListDto'][];
    let filteredSchools = $state([]) as components['schemas']['SchoolInputListDto'][];
    let members = $state([]) as components['schemas']['MemberInputListDto'][];
    let filteredMembers = $state([]) as components['schemas']['MemberInputListDto'][];
    let programs = $state([]) as components['schemas']['SchoolClassInputDto'][];
    let filteredPrograms = $state([]) as components['schemas']['SchoolClassInputDto'][];

    let regionsBox: HTMLDivElement | null = null;
    let adsBox: HTMLDivElement | null = null;
    let schoolsBox: HTMLDivElement | null = null;
    let membersBox: HTMLDivElement | null = null;

    let focusProgram = $state(false);
    
    let memberInput = $state([]) as components['schemas']['MemberInputListDto'][];
    let memberInputText = $state('');
    let programInput = $state([]) as components['schemas']['SchoolClassInputDto'][];
    let programInputText = $state('');

    let duplicateChecked = $state(false);

    let idInputText = $state('');

    async function loadMember() {
        if (members.length > 0) {
            focusProgram = true;
            return;
        }

        const { data } = await rq.apiEndPoints().GET('/api/v1/members/program', {
        });

        members = data?.data.members || [];
        filteredMembers = members;
        focusProgram = true;
    }

    loadProgram();
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

    function updateMembers(searchText: string) {
        const searchLower = searchText.toLowerCase();
        filteredMembers = [...members].sort((a, b) => {
            const scoreA = similarityScore(a.name ?? '', searchLower);
            const scoreB = similarityScore(b.name ?? '', searchLower);
            return scoreB - scoreA; 
        });

        if (membersBox) membersBox.scrollTop = 0;
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

    async function submitCreateProgramForm(this: HTMLFormElement) {
        const form: HTMLFormElement = this;

        if (form.username.value.trim().length === 0) {
            rq.msgError('아이디를 입력해주세요.');
            return;
        }
        if (!duplicateChecked) {
            rq.msgError('아이디 중복확인을 해주세요.');
            return;
        }

        if (form.password.value.trim().length === 0) {
            rq.msgError('비밀번호를 입력해주세요.');
            return;
        }

        if (form.membername.value.trim().length === 0) {
            rq.msgError('이름을 입력해주세요.');
            return;
        }



        // const agencyInput = schools.find(school => school.id == form.agency.value);
        // // if agencyInput is not found
        // if (!agencyInput) {
        //     rq.msgError('기관을 선택해주세요.');
        //     return;
        // }

        // // if isSpecial is checked
        // if((document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked){
        //     if (form.specialName.value.trim().length === 0) {
        //         rq.msgError('특수반명을 입력해주세요.');
        //         return;
        //     }
        // } else {
        //     //if grade is null
        //     if (form.grade.value.trim().length === 0) {
        //         rq.msgError('학년을 입력해주세요.');
        //         return;
        //     }
        //     // if grade is not number
        //     if (isNaN(form.grade.value)) {
        //         rq.msgError('학년은 숫자로 입력해주세요.');
        //         return;
        //     }

        //     //if classNo is null
        //     if (form.classNo.value.trim().length === 0) {
        //         rq.msgError('반을 입력해주세요.');
        //         return;
        //     }
        //     // if classNo is not number
        //     if (isNaN(form.classNo.value)) {
        //         rq.msgError('반은 숫자로 입력해주세요.');
        //         return;
        //     }
        // }

        // // if member is null
        // if (memberInput.length === 0) {
        //     rq.msgError('담당자를 입력해주세요.');
        //     return;
        // }
        
        // rq.msgError('done.');
        // return;

        // console.log(agencyInput, form.grade.value, form.classNo.value, (document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked, form.specialName.value, memberInput);
        

        const { data, error } = await rq.apiEndPoints().POST('/api/v1/members/student/new', {
            body: {
                username: form.username.value,
                password: form.password.value,
                name: form.membername.value,
                cellphoneNo: form.cellphoneNo.value,
                schools: programInput
            }
        });

        if (data?.data.resultCode == 1) {
            rq.msgAndRedirect(data, undefined, '/adm/menu/member/class');
        } else {
            rq.msgError(data?.msg??'오류가 발생했습니다.');
        }
    }

    function duplicateCheck() {
        const username = (document.getElementsByName('username')[0] as HTMLInputElement).value;
        if (username.trim().length === 0) {
            rq.msgError('아이디를 입력해주세요.');
            return;
        }
        rq.apiEndPoints().POST('/api/v1/members/duplicate', {
            body: {
                username: username
            }
        }).then(({ data }) => {
            if (data?.data.canUse === false) {
                duplicateChecked = false;
                rq.msgError('이미 사용중인 아이디입니다.');
            } else {
                duplicateChecked = true;
                rq.msgInfo('사용 가능한 아이디입니다.');
            }
        });
    }

    function updateId() {
        if((document.getElementsByName('agency')[0] as HTMLSelectElement).value == 'NONE') {
            return;
        }

        let yearSelectValue = (document.getElementsByName('year')[0] as HTMLSelectElement).value;
        let lastTwoDigitsOfYear = yearSelectValue.slice(-2);
        let numberSelectValue = (document.getElementsByName('number')[0] as HTMLSelectElement).value;
        let paddedNumber = numberSelectValue.padStart(3, '0');
        idInputText = (document.getElementsByName('agency')[0] as HTMLSelectElement).value + lastTwoDigitsOfYear + paddedNumber;
    
    }

    function validateInput(event: any) {
        const value = event.target.value;
        const isValid = /^\d{1,3}$/.test(value);
        if (!isValid) {
        event.target.value = value.slice(0, -1);
        }
    }

</script>

<div class="w-[95%] flex justify-start mt-[-60px] text-[40px] font-bold border-b mb-10">
    학생 생성
</div>
<div class="w-full h-screen flex justify-center">
    <form class="flex flex-col gap-4 w-[900px] h-full" method="POST" on:submit|preventDefault={submitCreateProgramForm}>
        <div class="overflow-x-auto h-full">
            <table class="table">
              <tbody>


                <tr>
                    <td class="border-2 p-1 text-center font-bold text-[15px] w-[200px]">기관</td>
                    <td class="border-2 p-1">
                        <div class="flex flex-col">
                            <div>
                                <select name="agency" class="ml-3 p-2" on:change={updateId} >
                                    <option value="NONE">선택</option>
                                    {#each programs as school}
                                        <option value={school.code}>{school.className}</option>
                                    {/each}
                                  </select>
                            </div>
                        </div>
                    </td>
                  </tr>

                  <tr>
                    <td class="border-2 p-1 text-center font-bold text-[15px] w-[200px]">연도</td>
                    <td class="border-2 p-1">
                        <select name="year" on:change={updateId}>
                            <!-- 최소 2024년부터 현재년도의 다음년도까지-->
                            {#each Array.from({length: new Date().getFullYear() + 2 - 2024}, (_, i) => 2024 + i) as year}
                                <option value={year}>{year}</option>
                            {/each}
                        </select>
                        </td>
                  </tr>

                  <tr>
                    <td class="border-2 p-1 text-center font-bold text-[15px] w-[200px]">번호</td>
                    <td class="border-2 p-1">
                        <input name="number" type="text" placeholder="번호" value="001" class="input input-bordered w-[200px] text-center" on:change={updateId} on:input={validateInput}/>
                        </td>
                  </tr>


                <tr>
                    <td class="border-2 p-1 text-center font-bold text-[15px] w-[200px]">아이디</td>
                    <td class="border-2 p-1">
                        <div class="flex flex-col">
                            <div>
                                <input name="username" type="text" placeholder="아이디" value="{idInputText}" class="input input-bordered w-[200px] text-center" readonly />
                            </div>
                            <div class="flex flex-row gap-2 mt-2">
                                {#if duplicateChecked}
                                    <i class="fa-solid fa-check text-green-500">사용가능</i>
                                {/if}
                                {#if !duplicateChecked}
                                <button class="btn btn-sm btn-primary" on:click={duplicateCheck} type="button">
                                    중복확인
                                </button>
                                {/if}
                        </div>
                    </td>
                  </tr>
                    <tr>
                        <td class="border-2 p-1 text-center font-bold text-[15px] w-[200px]">비밀번호</td>
                        <td class="border-2 p-1">
                            <div class="flex flex-col">
                                <div>
                                    <input name="password" type="text" placeholder="비밀번호" class="input input-bordered w-[200px] text-center" />
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="border-2 p-1 text-center font-bold text-[15px] w-[200px]">닉네임</td>
                        <td class="border-2 p-1">
                            <div class="flex flex-col">
                                <div>
                                    <input name="nickname" type="text" placeholder="닉네임" class="input input-bordered w-[200px] text-center" />
                                </div>
                            </div>
                        </td>
                      </tr>
                    


                
              </tbody>
            </table>

            <div class="flex flex-row mt-40 justify-between gap-2">
                <div class="btn btn-block btn-error gap-1 w-[100px]" on:click={() => rq.goTo('/adm/menu/member/class')}>
                    <span>목록</span>
                </div>
                <button class="btn btn-block btn-primary gap-1 w-[100px]" type="submit">
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