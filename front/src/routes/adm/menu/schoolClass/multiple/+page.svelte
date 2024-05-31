<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';
    // import { subTableRows, teacherOptions, addSubTableRow, deleteSubTableRow, updateTeacher, fetchTeacherOptions } from './store.js';
    import { get } from 'svelte/store';


    let regions = $state([]) as components['schemas']['Region'][];
    let filteredRegions = $state([]) as components['schemas']['Region'][];
    let ads = $state([]) as components['schemas']['AdministrativeDistrict'][];
    let filteredAds = $state([]) as components['schemas']['AdministrativeDistrict'][];
    let schools = $state([]) as components['schemas']['SchoolInputListDto'][];
    let filteredSchools = $state([]) as components['schemas']['SchoolInputListDto'][];
    let members = $state([]) as components['schemas']['MemberInputListDto'][];
    let filteredMembers = $state([]) as components['schemas']['MemberInputListDto'][];

    let regionsBox: HTMLDivElement | null = null;
    let adsBox: HTMLDivElement | null = null;
    let schoolsBox: HTMLDivElement | null = null;
    let membersBox: HTMLDivElement | null = null;

    let focusRegion = $state(false);
    let focusAd = $state(false);
    let focusAgency = $state(false);
    let focusMember = $state(false);

    let regionInput = $state('');
    let adInput = $state('');
    let agencyInput: components['schemas']['SchoolInputListDto'] | 'NONE' = $state('NONE');
    let agencyInputText = $state('');
    let memberInput = $state([]) as components['schemas']['MemberInputListDto'][];
    let memberInputText = $state('');

    async function loadRegion() {
        if (regions.length > 0) {
            focusRegion = true;
            return;
        }

        const { data } = await rq.apiEndPoints().GET('/api/v1/regions', {
        });

        regions = data?.data.regions || [];
        filteredRegions = regions;
        focusRegion = true;
    }

    async function loadAd() {
        if (!isValidRegionInput()) return

        const { data } = await rq.apiEndPointsWithFetch(fetch).GET('/api/v1/ads', {
            params: {
                query: {
                    regionCode: regions.find(region => region.name === regionInput)?.code
                }
            }
        });

        ads = data?.data.ads || [];
        filteredAds = ads;
        focusAd = true;
    }

    loadSchool();
    async function loadSchool() {
        if (schools.length > 0) {
            focusAgency = true;
            return;
        }

        const { data } = await rq.apiEndPoints().GET('/api/v1/schools/input', {
        });

        schools = data?.data.schools || [];
        filteredSchools = schools;
        focusAgency = true;
    }

    initMember();
    async function initMember() {
        const { data } = await rq.apiEndPoints().GET('/api/v1/members/program', {
          });

        members = data?.data.members || [];
        filteredMembers = members;

        subTableRows.update(rows => {
            return rows.map(row => {
                return { ...row, teacher: members[0].id };
            });
        });
    }

    async function loadMember() {
        if (members.length > 0) {
            focusMember = true;
            return;
        }

        initMember();

        focusMember = true;
    }

    function isValidRegionInput(): boolean {
        return regions.some(region => region.name === regionInput);
    }

    function updateRegions(searchText: string) {
        const searchLower = searchText.toLowerCase();
        filteredRegions = [...regions].sort((a, b) => {
            const scoreA = similarityScore(a.name ?? '', searchLower);
            const scoreB = similarityScore(b.name ?? '', searchLower);
            return scoreB - scoreA; 
        });

        if (regionsBox) regionsBox.scrollTop = 0;
    }

    function updateSchools(searchText: string) {
        const searchLower = searchText.toLowerCase();
        filteredSchools = [...schools].sort((a, b) => {
            const scoreA = similarityScore(a.schoolName ?? '', searchLower);
            const scoreB = similarityScore(b.schoolName ?? '', searchLower);
            return scoreB - scoreA; 
        });

        if (schoolsBox) schoolsBox.scrollTop = 0;
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

    function updateAds(searchText: string) {
        const searchLower = searchText.toLowerCase();
        filteredAds = [...ads].sort((a, b) => {
            const scoreA = similarityScore(a.name ?? '', searchLower);
            const scoreB = similarityScore(b.name ?? '', searchLower);
            return scoreB - scoreA; 
        });

        if (adsBox) adsBox.scrollTop = 0;
    }

    function similarityScore(regionName: string, searchText: string): number {
        const nameLower = regionName.toLowerCase();
        if (nameLower.startsWith(searchText)) return 100; 
        if (nameLower.includes(searchText)) return searchText.length; 
        return 0; 
    }


    async function submitCreateProgramForm(this: HTMLFormElement) {
        const form: HTMLFormElement = this;

        // if (form.programName.value.trim().length === 0) {
        //     rq.msgError('사업명을 입력해주세요.');
        //     form.programName.focus();
        //     return;
        // }

        // if (form.startDate.value === '' || form.endDate.value === '') {
        //     rq.msgError('사업기간을 입력해주세요.');
        //     return;
        // }

        // if (regionInput.trim().length === 0) {
        //     rq.msgError('지역을 입력해주세요.');
        //     form.region.focus();
        //     return;
        // }

        // if (adInput.trim().length === 0) {
        //     rq.msgError('행정구를 입력해주세요.');
        //     form.ad.focus();
        //     return;
        // }

        // if (agencyInput.length === 0) {
        //     rq.msgError('사용 기관을 입력해주세요.');
        //     form.agency.focus();
        //     return;
        // }

        // if (memberInput.length === 0) {
        //     rq.msgError('담당자를 입력해주세요.');
        //     form.member.focus();
        //     return;
        // }

        const agencyInput = schools.find(school => school.id == form.agency.value);
        // if agencyInput is not found
        if (!agencyInput) {
            rq.msgError('기관을 선택해주세요.');
            return;
        }

        // if grade is not number
        if (get(subTableRows).some(row => isNaN(row.grade))) {
            rq.msgError('학년은 숫자로 입력해주세요.');
            return;
        }

        // classNo should be in the format of "1-5,8-10,13-35"
        // dash right number should be less than left number
        if (get(subTableRows).some(row => !validateClassNo(row.classNoMultiple))) {
            rq.msgError('반은 1-5,8-10 과 같은 형식으로 입력해주세요.');
            return;
        }

        // if (get(subTableRows).some(row => !/^\d+-\d+(,\d+-\d+)*$/.test(row.classNoMultiple))) {
        //     rq.msgError('반은 1-5,8-10 과 같은 형식으로 입력해주세요.');
        //     return;
        // }

        // if row memberId is -1
        if (get(subTableRows).some(row => row.memberId == "-1")) {
            rq.msgError('담당자를 입력해주세요.');
            return;
        }

        // rq.msgError('done.');
        // return;
        

        // console.log(agencyInput, form.grade.value, form.classNo.value, (document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked, form.specialName.value, memberInput);
        

        const { data, error } = await rq.apiEndPoints().POST('/api/v1/school/class/multiple', {
            body: {
                agencyId: agencyInput.id!,
                rows: get(subTableRows).map(row => {
                    return {
                        id: row.id,
                        grade: row.grade,
                        classNoMultiple: row.classNoMultiple,
                        memberId: parseInt(row.memberId)
                    }
                })
            }
        });

        if (data?.data.resultCode == 1) {
            rq.msgAndRedirect(data, undefined, '/adm/menu/schoolClass');
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

    function isSpecialClick(){
        // if isSpecial is checked
        if((document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked){
            (document.getElementById('normalClass') as HTMLDivElement).style.display = 'none';
            (document.getElementById('specialClass') as HTMLDivElement).style.display = 'block';
        } else {
            (document.getElementById('normalClass') as HTMLDivElement).style.display = 'block';
            (document.getElementById('specialClass') as HTMLDivElement).style.display = 'none';
        }
    
    }

  import { writable } from 'svelte/store';

  // 서버에서 가져온 DTO 데이터 예시
  let teacherOptions = [];

  // subTableRows를 위한 writable 스토어 생성
  export const subTableRows = writable([
    { id: 1, grade: 1, classNoMultiple: "1-5", memberId: "-1"}
  ]);

  let nextId = 2; // 다음 행에 사용할 ID

  // 행을 추가하는 함수
  function addSubTableRow() {
    subTableRows.update(rows => {
      // 마지막 행을 가져와서 복사
      const lastRow = rows[rows.length - 1] || { grade: 1, classNoMultiple: "1-5", memberId: 0 };
      const newRow = { id: nextId, grade: lastRow.grade+1, classNoMultiple: lastRow.classNoMultiple, memberId: lastRow.memberId };
      nextId += 1;
      return [...rows, newRow];
    });

    // console.log(get(subTableRows));

    // subTableRows.update(rows => {
    //   return [...rows, { id: nextId, grade: "", class: "", teacher: "" }];
    // });
    // nextId += 1;
  }

  // 행을 삭제하는 함수
  function deleteSubTableRow(id: number) {
    subTableRows.update(rows => {
      return rows.filter(row => row.id !== id);
    });
  }

  // 담당자 값을 업데이트하는 함수
  function updateTeacher(id: number, newTeacher: string) {
    console.log(id, newTeacher);
    subTableRows.update(rows => {
      console.log(rows);
      return rows.map(row => row.id == id ? { ...row, teacher: newTeacher } : row);
    });
  }

  // 컴포넌트가 마운트된 후 서버에서 데이터 로드
  onMount(() => {
    console.log('Component has been loaded.');
  });

</script>

<div class="w-[95%] flex justify-start mt-[-60px] text-[40px] font-bold border-b mb-10">
    학급 일괄 생성
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
                                <select name="agency" class="ml-3 p-2"  >
                                    <option value="NONE">선택</option>
                                    {#each schools as school}
                                        <option value={school.id}>{school.schoolName} ({school.region} / {school.administrativeDistrict})</option>
                                    {/each}
                                  </select>
                                
                            </div>
                        </div>
                    </td>
                  </tr>
                  <tr>
                    <td class="border-2 p-1 text-center font-bold text-[15px] w-[200px]">학급명</td>
                    <td class="border-2 p-1">
                        
                       <!-- 서브 테이블 -->
        <table class="sub-table">
            <thead>
              <tr>
                <th>학년</th>
                <th>반</th>
                <th>담당자</th>
                <th>삭제</th>
              </tr>
            </thead>
            <tbody>
              {#each $subTableRows as row, index (row.id)}
                <tr>
                  <td>
                    <input type="text" bind:value={row.grade} />
                  </td>
                  <td><input type="text" bind:value={row.classNoMultiple} /></td>
                  <td>
                    <select bind:value={row.memberId} >
                      <option value="-1" >선택</option>
                      {#each filteredMembers as member}
                        <option value={member.id}>{member.name}</option>
                      {/each}
                    </select>
                  </td>
                  <td>
                    {#if index !== 0}
                    <button type="button" on:click={() => deleteSubTableRow(row.id)}>삭제</button>
                    {/if}
                  </td>
                </tr>
              {/each}
            </tbody>
          </table>
          <button type="button" on:click={addSubTableRow}>추가</button>
                    </td>
                  </tr>
                
              </tbody>
            </table>

            <div class="flex flex-row mt-40 justify-between gap-2">
                <div class="btn btn-block btn-error gap-1 w-[100px]" on:click={() => rq.goTo('/adm/menu/schoolClass')}>
                    <span>목록</span>
                </div>
                <button class="btn btn-block btn-primary gap-1 w-[100px]" type="submit">
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