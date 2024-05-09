<script lang="ts">
    import { page } from '$app/stores';

    import rq from '$lib/rq/rq.svelte';
	import type { components } from "$lib/types/api/v1/schema";
	import { onMount } from 'svelte';
    import type { KwTypeV1 } from '$lib/types';


    let programs: components['schemas']['ProgramDto'][] = $state([]);

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

        return data!;
    }
</script>

<div class="w-full h-full flex flex-col">
    <div class="flex flex-row w-full my-4 justify-around">
        <div class="flex flex-row gap-4">
            <button class="btn btn-sm">생성</button>
            <button class="btn btn-sm">삭제</button>
            <button class="btn btn-sm">액셀 다운로드</button>
        </div>
        <div class="searching-box border-2 rounded-md bg-white border-gray-700 flex items-center">
            <form action="/usr/lecture/list" class="flex w-full">
              <select name="choice" class="ml-3 p-2 outline-none">
                <option value="all">전체</option>
                <option value="subject">제목</option>
                <option value="body">내용</option>
                <option value="producer">강사명</option>
              </select>
              <div class="search whitespace-nowrap w-full">
                <input class="outline-none border-gray-400 w-full h-full ml-2" name="kw" type="search">
              </div>
              <div class="flex flex-row justify-end items-center w-1/2">
                <button class="">
                  <i class="fa-solid fa-magnifying-glass mr-5"></i>
                </button>
              </div>
            </form>
          </div>
    </div>
    <table cellpadding="15" cellspacing="15" width="80%" class="mx-auto">
        <thead>
            <tr class="border-b-2 border-t-2 border-gray-200 whitespace-nowrap text-sm lg:text-md">
                <th>
                    <input type="checkbox" class="orderItemCheckboxAll checkbox checkbox-sm">
                </th>
                <th>사업명</th>
                <th>사업 기간</th>
                <th class="min-w-[150px]">지역</th>
                <th>담당자</th>
                <th>사용 기관</th>
                <th>관리</th>
            </tr>
        </thead>

        {#await load()}
        {:then {data: {itemPage}}}
            <tbody>
                {#each programs as program}
                <tr class="text-center whitespace-nowrap border-b border-gray-200 text-sm lg:text-md" >
                    <td>
                        <input type="checkbox" class="orderItemCheckbox checkbox checkbox-sm">
                    </td>
                    <td >{program.id}</td>
                    <td >{program.startDate} - {program.endDate}</td>
                    <td >{program.city}&nbsp;{program.administrativeDistrict}</td>
                    <td ></td>
                    <td ></td>
                    <td>
                        <a href="#" class="btn btn-xs">수정</a>
                    </td>
                </tr>
                {/each}
            </tbody>
        {/await}

</div>