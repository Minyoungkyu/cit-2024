<svelte:head>
    <title>{rq.SITE_NAME} | 관리자페이지</title>
</svelte:head>

<script lang="ts">
    import rq from '$lib/rq/rq.svelte';
    import SideMenu from '../../sideMenu.svelte';
    import TopMenu from '../../topMenu.svelte';

    console.log(rq.member.id);
</script>

<div class="flex flex-row">
    <SideMenu />
    <div class="flex flex-col items-center justify-center w-screen h-screen">
        <div class="flex flex-row justify-end items-center pr-4 gap-4 bg-gray-800 text-gray-200 w-full h-[8vh]">
            <div>마이페이지</div>
            <div>로그아웃</div>
        </div>
        <div class="w-full h-full contentBody bg-gray-800 overflow-y-auto">
            <div class="flex justify-center items-center h-[1200px] border-2 bg-white rounded-l-xl z-99">
                관리자 페이지
            </div>
        </div>
    </div>
</div>

<style>
    .contentBody::-webkit-scrollbar-track {
        background-color: #f0f0f0; /* 트랙의 배경색 */
    }

    .contentBody::-webkit-scrollbar-thumb {
        background-color: #888; /* 썸의 배경색 */
    }

    .contentBody::-webkit-scrollbar {
        width: 10px; /* 스크롤 바의 너비 */
    }

    .contentBody::-webkit-scrollbar-button {
        display: none; /* 화살표 없애기 */
    }
</style>
