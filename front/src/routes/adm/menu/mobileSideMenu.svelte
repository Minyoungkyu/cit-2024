<script lang="ts">
    import { page } from '$app/stores';
    import { menuItems } from './menuItems';
    import rq from '$lib/rq/rq.svelte';

    // 현재 페이지 경로를 반응형으로 추적
    $: currentPagePath = $page.url.pathname;

    // 메뉴 항목의 경로와 현재 페이지 경로가 일치하는지 확인하는 함수
    function isActive(path: string) {
        return currentPagePath === path;
    }
</script>

<div class="drawer z-[99] overflow-hidden">
    <input id="my-drawer" type="checkbox" class="drawer-toggle" />
    <div class="drawer-content overflow-hidden">
        <label class="btn btn-square btn-ghost" for="my-drawer">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" class="inline-block w-5 h-5 stroke-current"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"></path></svg>
        </label>
    </div> 
    <div class="drawer-side flex flex-col overflow-hidden">
        <label for="my-drawer" aria-label="close sidebar" class="drawer-overlay"></label>
        <ul class="menu w-full h-full bg-gray-800 text-gray-200 flex flex-col overflow-hidden">
        <div class="flex flex-row justify-between items-center w-full">
            <div class="text-[30px]" on:click={() => window.location.href="/adm/menu"}>관리자페이지</div>
            <label for="my-drawer" class="btn btn-square btn-ghost flex justify-end">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="w-6 h-6"><path d="M18 6L6 18M6 6l12 12"></path></svg>
            </label>
        </div>
        {#each menuItems as { label, path }}
            <li class="{isActive(path) ? 'active' : ''} sideMenuContent text-base"><a href="{path}" on:click={() => (document.getElementById('my-drawer') as HTMLInputElement).checked = false}>{label}</a></li>
        {/each}
        {#if rq.isSuperAdmin()}
            <li class="{isActive('/adm/me') ? 'active' : ''} sideMenuContent active"><a href="#">마이 페이지</a></li>
        {/if}
        </ul>
    </div>
</div>

<style>
    .active {
        background-color: rgb(92, 115, 165);
        border-radius: 0 20px 20px 0; 
        margin-right: 10px;
        border-left: 4px solid white;
        color: white;
    }

    .sideMenuContent:hover {
        background-color: rgb(92, 115, 165);
        border-radius: 0 20px 20px 0; 
        margin-right: 10px;
        border-left: 4px solid white;
        color: white;
    }

    /* @media (max-width: 1466px) {
        .sideMenuContainer {
            display: none;
        }
    } */
</style>