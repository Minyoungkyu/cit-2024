<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
  import type { components } from '$lib/types/api/v1/schema';
  import Pagination from '$lib/adm/Pagination.svelte';
  import { page } from '$app/stores';
	import { onMount } from "svelte";

  let itemPage:any = $state();
  let pageDelta = 1;
  let pageValue = $state(1);
  let currentPage = $state(1);

  let programs = $state([{id:0, name:'전체'}]) as components['schemas']['ProgramInputDto'][];
  let filteredPrograms = $state([]) as components['schemas']['ProgramInputDto'][];
  let programInputText = $state('전체');
  let programInputId = $state(0);
  let programBox: HTMLDivElement | null = null;
  let focusProgram = $state(false);

  let schools = $state([{id:0, schoolName:'전체'}]) as components['schemas']['SchoolInputListDto'][];
  let filteredSchools = $state([]) as components['schemas']['SchoolInputListDto'][];
  let schoolInputText = $state('전체');
  let schoolInputId = $state(0);
  let schoolBox: HTMLDivElement | null = null;
  let focusSchool = $state(false);

  let gradeInput:HTMLSelectElement;

  const now = new Date();
  const threeMonthsAgo = new Date();
  threeMonthsAgo.setMonth(now.getMonth() - 3);
  let startDateTimeInput = $state(formatDateTimeLocal(threeMonthsAgo));
  let endDateTimeInput = $state(formatDateTimeLocal(new Date()));

  let statisticsData = $state([]) as components['schemas']['GameLogDto'][];


  async function loadProgram() {
    if (programs.length > 1) {
        focusProgram = true;
        return;
    }

    const { data } = await rq.apiEndPoints().GET('/api/v1/programs/input', {
    });

    if(data) programs = programs.concat(...(data.data.programs ?? []));
    
    filteredPrograms = programs;
    focusProgram = true;
  }

  async function loadSchool() {
    if (schools.length > 1) {
        focusSchool = true;
        return;
    }

    const { data } = await rq.apiEndPoints().GET('/api/v1/schools/input', {
    });

    if(data) schools = schools.concat(...(data.data.schools ?? []));

    filteredSchools = schools;
    focusSchool = true;
  }

  function updateProgram(searchText: string) {
      if (searchText === '') {
        programInputText = '전체';
        programInputId = 0;
        return;
      }

      const searchLower = searchText.toLowerCase();
      filteredPrograms = [...programs].sort((a, b) => {
          const scoreA = similarityScore(a.name ?? '', searchLower);
          const scoreB = similarityScore(b.name ?? '', searchLower);
          return scoreB - scoreA; 
      });

      if (programBox) programBox.scrollTop = 0;
  }

  function updateSchool(searchText: string) {
      if (searchText === '') {
        schoolInputText = '전체';
        schoolInputId = 0;
        return;
      }

      const searchLower = searchText.toLowerCase();
      filteredSchools = [...schools].sort((a, b) => {
          const scoreA = similarityScore(a.schoolName ?? '', searchLower);
          const scoreB = similarityScore(b.schoolName ?? '', searchLower);
          return scoreB - scoreA; 
      });

      if (schoolBox) schoolBox.scrollTop = 0;
  }
  

  function similarityScore(regionName: string, searchText: string): number {
      const nameLower = regionName.toLowerCase();
      if (nameLower.startsWith(searchText)) return 100; 
      if (nameLower.includes(searchText)) return searchText.length; 
      return 0; 
  }

  async function loadStatisticsData() {

    const { data } = await rq.apiEndPoints().GET('/api/v1/gameLogs/stat', {
      params: {
        query: {
          page: pageValue,
          programId: programInputId,
          schoolId: schoolInputId,
          grade: parseInt(gradeInput.value),
          startDateTime: startDateTimeInput,
          endDateTime: endDateTimeInput
        }
      }
    });

    itemPage = data!.data.itemPage;
    currentPage = itemPage.number;
    statisticsData = data!.data.itemPage.content;

    pageValue = 1;
  }

  function getCurrentDateTimeLocal() {
      const now = new Date();
      return now.toISOString().slice(0, 16);
  }

  function getThreeMonthsAgoDateTimeLocal() {
      const now = new Date();
      now.setMonth(now.getMonth() - 3);
      return now.toISOString().slice(0, 16);
  }

  function formatDateTimeLocal(date: Date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      return `${year}-${month}-${day}T${hours}:${minutes}`;
  }

  function formatJavaLocalDateTime(dateTimeString: string) {
      const [date, time] = dateTimeString.split('T');
      const [hours, minutes] = time.split(':');

      return `${date} ${hours}:${minutes}`;
  }

  function calculatePaginationRange(current: number, total: number, delta = 4) {
      const left = current - delta;
      const right = current + delta;
      const range = [] as { no: number; text: string }[];
  
      if (total <= 1) return [];
  
      for (let i = 1; i <= total; i++) {
        if (i === 1) {
          range.push({ no: i, text: `${i}` });
        } else if (i == left - 1) {
          range.push({ no: i, text: `...` });
        } else if (i >= left && i <= right) {
          range.push({ no: i, text: `${i}` });
        } else if (i === total) {
          range.push({ no: i, text: `${i}` });
        } else if (i == right + 1) {
          range.push({ no: i, text: `...` });
        }
      }
  
      return range;
    }
</script>

<div class="w-[95%] flex justify-start mt-[-60px] text-[22px] border-b mb-1 pb-[14px] font-bold">
  통계
</div>

<div class="w-[95%] h-screen flex justify-center">
  <form class="flex flex-col gap-4 w-full h-full">
      <div class="overflow-x-auto h-full">
          <table class="table">
            <tbody>
              <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">사업 명</td>
                  <td class="border-b p-3">
                      <div class="flex flex-row items-center gap-2">
                        <div>
                          <input name="program" type="search" placeholder="사업 명" class="input input-bordered w-[500px] text-center" 
                              bind:value={programInputText}
                              on:focus={() => loadProgram()}
                              on:input={(event) => event.target && updateProgram((event.target as HTMLInputElement).value)}
                              on:blur={() => setTimeout(() => { focusProgram = false; }, 100)}
                              />
                              {#if focusProgram}
                              <div bind:this={programBox} class="w-[500px] h-[200px] mt-[-2px] absolute z-[99] rounded-xl border-2 flex flex-col items-center overflow-y-auto whitespace-pre-wrap bg-white">
                                  {#each filteredPrograms as program}
                                      <div class="options w-[80%] text-center p-1 cursor-pointer" 
                                      on:click={() => {programInputText = program.name; programInputId = program.id;}}>
                                          {program.name}
                                      </div>
                                  {/each}
                              </div>
                              {/if}
                      </div>
                      </div>
                  </td>
                </tr>
                  <tr>
                      <td class="border-b p-1 text-[15px] w-[150px] font-bold">학교 명</td>
                      <td class="border-b p-3">
                          <div class="flex flex-col">
                            <div>
                              <input name="school" type="search" placeholder="학급 명" class="input input-bordered w-[500px] text-center" 
                                  bind:value={schoolInputText}
                                  on:focus={() => loadSchool()}
                                  on:input={(event) => event.target && updateSchool((event.target as HTMLInputElement).value)}
                                  on:blur={() => setTimeout(() => { focusSchool = false; }, 100)}
                                  />
                                  {#if focusSchool}
                                  <div bind:this={schoolBox} class="w-[500px] h-[200px] mt-[-2px] absolute z-[99] rounded-xl border-2 flex flex-col items-center overflow-y-auto whitespace-pre-wrap bg-white">
                                      {#each filteredSchools as school}
                                          <div class="options w-[80%] text-center p-1 cursor-pointer" 
                                          on:click={() => {schoolInputText = school.schoolName!; schoolInputId = school.id!;}}>
                                          {school.schoolName} {#if school.region} ({school.region}/{school.administrativeDistrict}) {/if}
                                          </div>
                                      {/each}
                                  </div>
                                  {/if}
                          </div>
                          </div>
                      </td>
                  </tr>
                  <tr>
                      <td class="border-b p-1 text-[15px] w-[150px] font-bold">학년</td>
                      <td class="border-b p-3">
                        <select name="school" bind:this={gradeInput} class="select select-bordered w-[200px] text-center">
                          <option value=0 selected>전체</option>
                          <option value=1>1 학년</option>
                          <option value=2>2 학년</option>
                          <option value=3>3 학년</option>
                          <option value=4>4 학년</option>
                          <option value=5>5 학년</option>
                          <option value=6>6 학년</option>
                        </select>
                      </td>
                    </tr>
                  <tr>
                      <td class="border-b p-1 text-[15px] w-[150px] font-bold">기간</td>
                      <td class="border-b p-3">
                        <div>
                          <input name="school" type="datetime-local" placeholder="시작날짜" bind:value={startDateTimeInput} class="input input-bordered w-[300px] text-center">

                          <span class="text-[25px] mx-10">~</span>
            
                          <input name="school" type="datetime-local" placeholder="끝날짜" bind:value={endDateTimeInput} class="input input-bordered w-[300px] text-center">
                        </div>
                      </td>
                  </tr>
            </tbody>
          </table>
          <button class="btn btn-lg btn-outline rounded-md border-gray-400">생성</button>
        </div>
  </form>
</div>

<div class="flex flex-col w-full h-full items-center">
    <table class="table w-[95%]">
        <tbody>
          <tr>
            <td class="border-2 border-gray-300 p-1 text-center font-bold text-[15px] w-[200px] h-[40px] bg-gray-100">사업 명</td>
            <td class="border-2 border-gray-300 p-1">
              <div class="flex flex-col">
                <div>
                    <input name="program" type="search" placeholder="사업 명" class="input input-bordered w-[500px] text-center my-2 ml-2" 
                        bind:value={programInputText}
                        on:focus={() => loadProgram()}
                        on:input={(event) => event.target && updateProgram((event.target as HTMLInputElement).value)}
                        on:blur={() => setTimeout(() => { focusProgram = false; }, 100)}
                        />
                        {#if focusProgram}
                        <div bind:this={programBox} class="w-[500px] h-[200px] mt-[-2px] absolute z-[99] rounded-xl border-2 flex flex-col items-center overflow-y-auto whitespace-pre-wrap bg-white">
                            {#each filteredPrograms as program}
                                <div class="options w-[80%] text-center p-1 cursor-pointer" 
                                on:click={() => {programInputText = program.name; programInputId = program.id;}}>
                                    {program.name}
                                </div>
                            {/each}
                        </div>
                        {/if}
                </div>
              </div>
            </td>
          </tr>
          <tr>
            <td class="border-2 border-gray-300 p-1 text-center font-bold text-[15px] w-[200px] h-[40px] bg-gray-100">학교</td>
            <td class="border-2 border-gray-300 p-1">
              <div class="flex flex-col">
                <div>
                    <input name="school" type="search" placeholder="학급 명" class="input input-bordered w-[500px] my-2 ml-2 text-center h-[35px]" style="border-radius:5px;" 
                        bind:value={schoolInputText}
                        on:focus={() => loadSchool()}
                        on:input={(event) => event.target && updateSchool((event.target as HTMLInputElement).value)}
                        on:blur={() => setTimeout(() => { focusSchool = false; }, 100)}
                        />
                        {#if focusSchool}
                        <div bind:this={schoolBox} class="w-[500px] h-[200px] mt-[-2px] absolute z-[99] rounded-xl border-2 flex flex-col items-center overflow-y-auto whitespace-pre-wrap bg-white">
                            {#each filteredSchools as school}
                                <div class="options w-[80%] text-center p-1 cursor-pointer" 
                                on:click={() => {schoolInputText = school.schoolName!; schoolInputId = school.id!;}}>
                                {school.schoolName} {#if school.region} ({school.region}/{school.administrativeDistrict}) {/if}
                                </div>
                            {/each}
                        </div>
                        {/if}
                </div>
              </div>
            </td>
          </tr>
          <tr>
            <td class="border-2 border-gray-300 p-1 text-center font-bold text-[15px] w-[200px] h-[40px] bg-gray-100">학년</td>
            <td class="border-2 border-gray-300 p-1">
              <select name="school" bind:this={gradeInput} class="select select-bordered w-[200px] text-center my-2 ml-2">
                <option value=0 selected>전체</option>
                <option value=1>1 학년</option>
                <option value=2>2 학년</option>
                <option value=3>3 학년</option>
                <option value=4>4 학년</option>
                <option value=5>5 학년</option>
                <option value=6>6 학년</option>
              </select>
            </td>
          </tr>
          <tr>
            <td class="border-2 border-gray-300 p-1 text-center font-bold text-[15px] w-[200px] h-[40px] bg-gray-100">기간</td>
            <td class="border-2 border-gray-300 p-1">
              <input name="school" type="datetime-local" placeholder="시작날짜" bind:value={startDateTimeInput} class="input input-bordered w-[300px] text-center my-2 ml-2">

              <span class="text-[25px] mx-10">~</span>

              <input name="school" type="datetime-local" placeholder="끝날짜" bind:value={endDateTimeInput} class="input input-bordered w-[300px] text-center my-2 ml-2">
            </td>
          </tr>
        </tbody>
      </table>

      <div class="w-full flex justify-start my-6">
        <div class="btn btn-wide ml-10" on:click={() => loadStatisticsData()}>검색 시작</div>
      </div>

      {#if statisticsData.length > 0}
      <table class="border-2 w-[95%]">
        <thead class="border-2">
          <tr class="border-2 text-[16px] bg-base-200">
            <td class="border-2 border-gray-300  w-[250px] text-center">학생 ID</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">시간</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">맵</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">단계</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">레벨</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">난이도</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">자동완성</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">자동닫기</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">결과</td>
          </tr>
        </thead>
        <tbody>
          {#each statisticsData as data }
          <tr class="border-2 text-[16px]">
            <td class="border-2 border-gray-300  w-[250px] text-center bg-base-200">{data.username}</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">{formatJavaLocalDateTime(data.createDate)}</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">{data.gameMapStage}</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">{data.gameMapStep}</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">{data.gameMapLevel}</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">{data.gameMapDifficulty}</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">{data.editorAutoComplete == 1 ? '사용' : '미사용'}</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">{data.editorAutoClose == 1 ? '사용' : '미사용'}</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">{data.result == 1 ? '성공' : '실패'}</td>
          </tr>
          {/each}
        </tbody>
      </table>

      <div class="flex justify-center mt-5">
        <div class="join">
          {#each calculatePaginationRange(itemPage.number, itemPage.totalPagesCount, pageDelta) as pageNumber}
            <button
              class={`join-item btn ${pageNumber.no == currentPage ? 'text-red-300' : ''}`}
              on:click={() => {pageValue = pageNumber.no; loadStatisticsData();}}
            >
              {pageNumber.text}
            </button>
          {/each}
        </div>
      </div>
      {:else}
      <div>검색된 데이터가 없습니다.</div>
      {/if}
      
</div>

