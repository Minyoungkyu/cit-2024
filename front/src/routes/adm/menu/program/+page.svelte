<script lang="ts">
    import { page } from '$app/stores';

    import rq from '$lib/rq/rq.svelte';
	import type { components } from "$lib/types/api/v1/schema";
    import type { KwTypeV1 } from '$lib/types';
    import Pagination from '$lib/adm/Pagination.svelte';
	import { onMount } from 'svelte';


    let programs: components['schemas']['ProgramDto'][] = $state([]);
    let allChecked = $state(false);
    let individualChecks: boolean[] = $state([]);

    async function load() {
        const kw = $page.url.searchParams.get('kw') ?? '';
        const kwType = ($page.url.searchParams.get('kwType') ?? 'ALL') as KwTypeV1;
        const page_ = parseInt($page.url.searchParams.get('page') ?? '1');

        const { data } = await rq.apiEndPoints().GET('/api/v1/programs', {
            params: {
                query: {
                    kw: kw,
                    kwType: kwType,
                    page: page_
                }
            }
        });

        programs = data!.data.itemPage.content;
        individualChecks = programs.map(() => false);

        return data!;
    }

    function toggleAllChecks() {
        if (allChecked) {
            individualChecks = individualChecks.map(() => true);
        } else {
            individualChecks = individualChecks.map(() => false);
        }
    }

    function updateAllChecked() {
        allChecked = individualChecks.every(Boolean);
    }

    function getCheckedProgramIds(): number[] {
        return programs.filter((_, index) => individualChecks[index]).map(program => program.id);
    }

    async function handleCheckedPrograms() {
        const checkedIds = getCheckedProgramIds();
        
        if (checkedIds.length === 0) {
            rq.msgError('선택된 항목이 없습니다.');
            return;
        }

        const { data } = await rq.apiEndPoints().POST('/api/v1/programs/delete', {
            body: {
                programIds: checkedIds
            }
        });

        if(data?.data) {
            rq.msgInfo(data.msg);
            load();
        }
    }

</script>

<div class="w-full h-full flex flex-col">
    <div class="flex flex-row w-full my-4 justify-around">
        <div class="flex flex-row gap-4">
            <button class="btn btn-sm" on:click={() => window.location.href="/adm/menu/program/new"}>생성</button>
            <button class="btn btn-sm" on:click={() => handleCheckedPrograms()}>삭제</button>
            <button class="btn btn-sm">액셀 다운로드</button>
        </div>
        <div class="flex flex-row gap-2 items-center">
            {#if $page.url.searchParams.get('kw')}
                <a class="btn btn-sm" href={$page.url.pathname}>
                    <i class="fa-solid fa-xmark"></i> 전체보기
                </a>
            {/if}
            <div class="searching-box border-2 rounded-md bg-white border-gray-700 flex items-center">
                <form class="flex w-full" action={$page.url.pathname}>
                  <select name="kwType" class="ml-3 p-2 outline-none" value={$page.url.searchParams.get('kwType') ?? 'ALL'}>
                    <option value="ALL">전체</option>
                    <option value="TITLE">사업명</option>
                    <option value="REGION">지역</option>
                    <option value="INCHARGENAME">담당자</option>
                    <option value="AGENCY">사용기관</option>
                  </select>
                  <div class="search whitespace-nowrap w-full">
                    <input class="outline-none border-gray-400 w-full h-full ml-2" name="kw" type="search" value={$page.url.searchParams.get('kw') ?? ''}>
                  </div>
                  <div class="flex flex-row justify-end items-center w-1/2">
                    <button class="">
                      <i class="fa-solid fa-magnifying-glass mr-5"></i>
                    </button>
                  </div>
                </form>
            </div>
        </div>
    </div>
    {#await load()}
    {:then {data: {itemPage}}}
        <table cellpadding="15" cellspacing="15" width="80%" class="mx-auto">
            <thead>
                <tr class="border-b-2 border-t-2 border-gray-200 whitespace-nowrap text-sm lg:text-md">
                    <th>
                        <input type="checkbox" class="orderItemCheckboxAll checkbox checkbox-sm"
                            bind:checked={allChecked}
                            on:change={toggleAllChecks}>
                    </th>
                    <th>사업번호</th>
                    <th>사업명</th>
                    <th>사업 기간</th>
                    <th class="min-w-[150px]">지역</th>
                    <th>담당자</th>
                    <th>사용 기관</th>
                    <th>관리</th>
                </tr>
            </thead>

            <tbody>
                {#each programs as program, index}
                <tr class="text-center whitespace-nowrap border-b border-gray-200 text-sm lg:text-md" >
                    <td>
                        <input type="checkbox" class="orderItemCheckbox checkbox checkbox-sm"
                            bind:checked={individualChecks[index]}
                            on:change={updateAllChecked}>
                    </td>
                    <td >{program.id}</td>
                    <td >{program.name}</td>
                    <td >{program.startDate} - {program.endDate}</td>
                    <td >{program.city}&nbsp;{program.administrativeDistrict}</td>
                    <td >{program.responsibleMemberNames}</td>
                    <td >{program.schoolsNames}</td>
                    <td>
                        <a href="/adm/menu/program/{program.id}" class="btn btn-xs">수정</a>
                    </td>
                </tr>
                {/each}
            </tbody>
        </table>
        <div class="mt-4 mb-4">
            <Pagination page={itemPage} />
        </div>
    {/await}
</div>