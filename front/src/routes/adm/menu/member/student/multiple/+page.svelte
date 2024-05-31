<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';

    let classes = $state([]) as components['schemas']['SchoolClassInputDto'][];
    let filteredClasses = $state([]) as components['schemas']['SchoolClassInputDto'][];

    let focusClass = $state(false);

    let classInput = $state([]) as components['schemas']['SchoolClassInputDto'][];
    let classInputText = $state('');

    let duplicateChecked = $state(false);

    let idInputText = $state('');

    loadProgram();
    async function loadProgram() {
        // console.log('loadProgram');
        if (classes.length > 0) {
            console.log('loadProgram');
            focusClass = true;
            return;
        }

        const { data } = await rq.apiEndPoints().GET('/api/v1/school/class/input', {
        });

        classes = data?.data.schools || [];
        filteredClasses = classes;
        focusClass = true;
    }

    async function submitCreateProgramForm(this: HTMLFormElement) {
        const form: HTMLFormElement = this;

        if((document.getElementsByName('class')[0] as HTMLSelectElement).value == 'NONE') {
            rq.msgError('학급을 선택해주세요.');
            return;
        }

        if (form.studentNumberMultiple.value.trim().length === 0) {
            rq.msgError('번호를 입력해주세요.');
            return;
        }

        if (!validateClassNo(form.studentNumberMultiple.value)) {
            rq.msgError('번호는 1-5,8-10 과 같은 형식으로 입력해주세요.');
            return;
        }

        const { data, error } = await rq.apiEndPoints().POST('/api/v1/members/student/multiple', {
            body: {
                schoolClassCode: (document.getElementsByName('class')[0] as HTMLSelectElement).value,
                studentYear: form.year.value,
                studentNumberMultiple: form.studentNumberMultiple.value,
            }
        });

        if (data?.resultCode == "200") {
            rq.msgAndRedirect(data, undefined, '/adm/menu/member/student');
        } else {
            rq.msgError(data?.msg??'오류가 발생했습니다.');
        }
    }

    function validateClassNo(classNo: string) {
        // 정규식으로 형식 검사
        const pattern = /^(\d+-\d+,)*(\d+-\d+)$/;
        if (!pattern.test(classNo)) {
            return false;
        }

        // 쉼표로 구분하여 각 범위를 분리
        const ranges = classNo.split(',');

        // 각 범위를 검사
        for (const range of ranges) {
            const [left, right] = range.split('-').map(Number);

            // 범위의 오른쪽 숫자가 왼쪽 숫자보다 큰지 검사
            if (right <= left) {
            return false;
            }
        }

        return true;
    }

    function duplicateCheck() {
        const username = (document.getElementsByName('username')[0] as HTMLInputElement).value;
        if((document.getElementsByName('class')[0] as HTMLSelectElement).value == 'NONE') {
            rq.msgError('학급을 선택해주세요.');
            return;
        }
        
        if ((document.getElementsByName('number')[0] as HTMLInputElement).value.trim().length === 0) {
            rq.msgError('번호를 입력해주세요.');
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


    function validateInput(event: any) {
        // 숫자, "-", "," 만 입력 가능
        event.target.value = event.target.value.replace(/[^0-9,-]/g, '');
    }

</script>

<div class="w-[95%] flex justify-start mt-[-60px] text-[22px] border-b mb-1 pb-[14px] font-bold">
    학생 일괄 생성
</div>
<div class="w-[95%] h-screen flex justify-center">
    <form class="flex flex-col gap-4 w-full h-full" method="POST" on:submit|preventDefault={submitCreateProgramForm}>
        <div class="overflow-x-auto h-full">
            <table class="table">
              <tbody>


                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">학급</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            <div>
                                <select name="class" class="ml-3 p-2">
                                    <option value="NONE">선택</option>
                                    {#each classes as school}
                                        <option value={school.code}>{school.className}</option>
                                    {/each}
                                  </select>
                            </div>
                        </div>
                    </td>
                  </tr>

                  <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">연도</td>
                    <td class="border-b p-3">
                        <select name="year">
                            <!-- 최소 2024년부터 현재년도의 다음년도까지-->
                            {#each Array.from({length: new Date().getFullYear() + 2 - 2024}, (_, i) => 2024 + i) as year}
                                <option value={year}>{year}</option>
                            {/each}
                        </select>
                        </td>
                  </tr>

                  <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">번호</td>
                    <td class="border-b p-3">
                        <input name="studentNumberMultiple" type="text" placeholder="번호" value="1-10" class="input input-bordered w-[200px] text-center" on:input={validateInput}/>
                        </td>
                  </tr>

                
              </tbody>
            </table>

            <div class="flex flex-row mt-10 mb-10 justify-center gap-2">
                <button class="btn btn-block btn-outline border-gray-400 gap-1 w-[100px]" type="button" on:click={() => rq.goTo('/adm/menu/member/student')}>
                    <span>목록</span>
                </button>
                <button class="btn btn-block btn-success btn-outline gap-1 w-[100px]" type="submit">
                    <span>일괄 생성</span>
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