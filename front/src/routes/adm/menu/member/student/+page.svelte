<script lang="ts">
    import { page } from '$app/stores';

    import rq from '$lib/rq/rq.svelte';
	import type { components } from "$lib/types/api/v1/schema";
    import type { KwTypeV1 } from '$lib/types';
    import Pagination from '$lib/adm/Pagination.svelte';
	import { onMount } from 'svelte';


    let memberList: components['schemas']['MemberDto'][] = $state([]);
    let allChecked = $state(false);
    let individualChecks: boolean[] = $state([]);

    async function load() {
        const kw = $page.url.searchParams.get('kw') ?? '';
        const kwType = ($page.url.searchParams.get('kwType') ?? 'ALL');
        const page_ = parseInt($page.url.searchParams.get('page') ?? '1');

        const { data } = await rq.apiEndPoints().GET('/api/v1/members/student', {
            params: {
                query: {
                    kw: kw,
                    kwType: kwType,
                    page: page_
                }
            }
        });

        memberList = data!.data.itemPage.content;
        individualChecks = memberList.map(() => false);

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
        return memberList.filter((_, index) => individualChecks[index]).map(schoolClass => schoolClass.id);
    }

    async function handleCheckedPrograms() {
        const checkedIds = getCheckedProgramIds();
        
        if (checkedIds.length === 0) {
            rq.msgError('선택된 항목이 없습니다.');
            return;
        }

        if (confirm('선택된 학생 계정을 삭제하시겠습니까?')) {

            const { data } = await rq.apiEndPoints().POST('/api/v1/members/student/delete', {
                body: {
                    studentIds : checkedIds
                }
            });
    
            if(data?.data) {
                rq.msgInfo(data.msg);
                load();
            }
            
        }

    }

    function truncateProgramName(name: string) {
        if (name.length > 20) {
            return name.slice(0, 20) + '...';
        }
        return name;
    }

</script>

<div class="w-[95%] h-full flex flex-col mt-[-60px]">
    <div class="flex flex-row min-w-[1000px] w-full justify-between border-b pb-[14px] mb-1">
        <div class="flex flex-row gap-4 items-center">
            <div class="text-[22px] mr-4 font-bold">
                학생 계정
            </div>
            {#if rq.member.authorities.length >= 2}
            <button class="btn btn-sm btn-outline rounded-md border-gray-400" on:click={() => window.location.href="/adm/menu/member/student/multiple"}>일괄 생성</button>
            <button class="btn btn-sm btn-outline rounded-md border-gray-400" on:click={() => window.location.href="/adm/menu/member/student/new"}>개별 생성</button>
            {/if}
            {#if rq.member.authorities.length >= 3}
            <button class="btn btn-sm btn-outline rounded-md border-gray-400" on:click={() => handleCheckedPrograms()}>삭제</button>
            {/if}
            {#if rq.member.authorities.length >= 2}
            <!-- <button class="btn btn-sm">엑셀 다운로드</button> -->
            <a href="{import.meta.env.VITE_CORE_API_BASE_URL}/api/v1/members/student/download/csv" class="btn btn-sm btn-outline rounded-md border-gray-400">엑셀 다운로드</a>
            {/if}
        </div>
        <div class="flex flex-row gap-2 items-center">
            {#if $page.url.searchParams.get('kw')}
                <a class="btn btn-sm" href={$page.url.pathname}>
                    <i class="fa-solid fa-xmark"></i> 전체보기
                </a>
            {/if}
            <div class="searching-box border rounded-md bg-white border-gray-400 flex items-center">
                <form class="flex w-full" action={$page.url.pathname}>
                  <select name="kwType" class="ml-3 p-2 outline-none text-gray-500" value={$page.url.searchParams.get('kwType') ?? 'ALL'}>
                    <option value="ALL">전체</option>
                    <option value="학교명">학교명</option>
                    <option value="학년">학년</option>
                    <option value="반">반</option>
                    <option value="특수반명">특수반명</option>
                    <option value="아이디">아이디</option>
                    <option value="닉네임">닉네임</option>
                  </select>
                  <div class="search whitespace-nowrap w-full">
                    <input class="outline-none border-gray-500 w-full h-full ml-2" name="kw" type="search" value={$page.url.searchParams.get('kw') ?? ''}>
                  </div>
                  <div class="flex flex-row justify-end items-center w-1/2">
                    <button class="">
                      <i class="fa-solid fa-magnifying-glass mr-5 text-gray-500"></i>
                    </button>
                  </div>
                </form>
            </div>
        </div>
    </div>
    {#await load()}
    {:then {data: {itemPage}}}
        <table cellpadding="15" cellspacing="15" width="100%" class="mx-auto min-w-[1000px]">
            <thead>
                <tr class="border-b border-gray-200 whitespace-nowrap text-sm lg:text-md">
                    <th class="w-[50px]">
                        <input type="checkbox" class="orderItemCheckboxAll checkbox checkbox-sm rounded-md"
                            bind:checked={allChecked}
                            on:change={toggleAllChecks}>
                    </th>
                    <th>학교명</th>
                    <th>학급명</th>
                    <th>아이디</th>
                    <th>비밀번호</th>
                    <th>닉네임</th>
                    <th>생성일</th>
                    <th class="w-[100px]">관리</th>
                </tr>
            </thead>

            <tbody>
                {#each memberList as member, index}
                <tr class="text-center whitespace-nowrap border-b border-gray-200 text-sm lg:text-md" >
                    <td>
                        <input type="checkbox" class="orderItemCheckbox checkbox checkbox-sm"
                            bind:checked={individualChecks[index]}
                            on:change={updateAllChecked}>
                    </td>
                    <!-- <td >{program.id}</td> -->
                    <td >{member.studentClassSchool}</td>
                    <td >{member.studentClass}</td>
                    <td >{member.username}</td>
                    <td >{member.studentPassword}</td>
                    <td >{member.studentNickName}</td>
                    <td >{member.createDate}</td>
                    <td>
                        <a href="/adm/menu/member/student/{member.id}" class="btn btn-xs btn-outline rounded-md border-gray-400">수정</a>
                    </td>
                </tr>
                {/each}
                {#if memberList.length === 0}
                    <tr>
                        <td colspan="7" class="text-center pt-[70px] pb-[70px] border-b">데이터가 없습니다.</td>
                    </tr>
                {/if}
            </tbody>
        </table>
        <div class="mt-6 mb-6">
            <Pagination page={itemPage} />
        </div>
    {/await}
</div>