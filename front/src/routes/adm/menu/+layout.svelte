<svelte:head>
    <title>{rq.SITE_NAME} | 관리자페이지</title>
</svelte:head>

<script lang="ts">
    import { page } from '$app/stores';
    import rq from '$lib/rq/rq.svelte';
	import { onMount } from 'svelte';
    import SideMenu from './sideMenu.svelte';
    import MobileSideMenu from './mobileSideMenu.svelte';

    let isMobile = $state(false);

    onMount(() => {
        const query = window.matchMedia('(max-width: 1466px)');

        function checkMatch() {
            if (query.matches) {
                isMobile = true;
            } else {
                isMobile = false;
            }
            console.log(isMobile)
        }

        query.addListener(checkMatch);
        checkMatch();

        return () => {
            query.removeListener(checkMatch);
        };
    });

    let isActiveMyPage = $state(false);
    $effect(() => { // 현재 페이지의 URL을 반응형으로 관찰
        isActiveMyPage = $page.url.pathname === '/adm/menu/my'; // set 메소드로 상태 업데이트
    });
</script>

<div class="flex flex-row">
    {#if !isMobile}
        <SideMenu />
    {/if}
    <div class="flex flex-col items-center justify-center w-screen h-screen">
        <div class="flex flex-row {isMobile ? 'justify-between' : 'justify-end'} items-center pr-4 gap-4 bg-gray-800 text-gray-200 w-full h-[8vh]">
            <div class="{isMobile ? '' : 'hidden'}">
                <MobileSideMenu />
            </div>
            <div class="flex flex-row items-center gap-8 mr-4">
                <div class="top-content cursor-pointer {isActiveMyPage ? 'active' : ''}"><a href="/adm/menu/checkPass">마이페이지</a></div>
                <div class="top-content cursor-pointer">로그아웃</div>
            </div>
        </div>
        <div class="w-full h-full contentBody bg-gray-800 overflow-y-auto">
            <div class="flex justify-center items-center h-full border-2 bg-white {isMobile ? '' : 'rounded-l-xl'} z-99">
                <slot></slot>
            </div>
        </div>
    </div>
</div>

<style>
    .contentBody::-webkit-scrollbar-track {
        background-color: #f0f0f0; 
    }

    .contentBody::-webkit-scrollbar-thumb {
        background-color: #888;
    }

    .contentBody::-webkit-scrollbar {
        width: 10px; 
    }

    .contentBody::-webkit-scrollbar-button {
        display: none; 
    }

    .top-content:hover {
        border-bottom: 2px solid rgb(92, 115, 165);;
    }

    .top-content.active {
        border-bottom: 2px solid rgb(92, 115, 165); 
    }
</style>
