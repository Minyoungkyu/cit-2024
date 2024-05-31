<script lang="ts">
    import { page } from '$app/stores';
    import { menuItems } from './menuItems';
    import rq from '$lib/rq/rq.svelte';

    // 현재 페이지 경로를 반응형으로 추적
    $: currentPagePath = $page.url.pathname;

    // 메뉴 항목의 경로와 현재 페이지 경로가 일치하는지 확인하는 함수
    function isActive(path: string) {
        if(path === '/adm/menu/school' && currentPagePath.startsWith('/adm/menu/schoolClass')) {
            return false;
        }
        // console.log(currentPagePath, path);
        return currentPagePath.startsWith(path);
    }
</script>

<ul class="menu sideMenuContainer bg-gray-100 text-gray-800 min-w-[170px] w-[270px] h-screen relative p-0 overflow-hidden">
    <li class="text-[30px] font-bold mt-6 mb-[6vh] items-center"><a href="/game/1">CODE-YTHON</a></li>
    {#each menuItems as { label, path }}
        <li class="{isActive(path) ? 'active' : ''} sideMenuContent text-base w-[230px]"><a href="{path}" class="btn-ghost rounded-full">{label}</a></li>
    {/each}
    {#if rq.isSuperAdmin()}
        <li class="{isActive('/adm/me') ? 'active' : ''} sideMenuContent active w-[270px]"><a href="#">마이 페이지</a></li>
    {/if}
</ul>

<style>
    .active {
        background-color: rgb(92, 115, 165);
        border-radius: 0 20px 20px 0; 
        /* margin-right: 10px; */
        /* border-left: 4px solid rgba(255, 255, 255, 0.479); */
        color: white;
    }

    .sideMenuContent:hover {
        background-color: rgba(92, 115, 165, 0.748);
        border-radius: 0 20px 20px 0; 
        /* margin-right: 10px; */
        /* border-left: 4px solid white; */
        color: white;
    }

    /* @media (max-width: 1466px) {
        .sideMenuContainer {
            display: none;
        }
    } */
</style>