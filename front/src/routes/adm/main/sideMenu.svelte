<script lang="ts">
    import { page } from '$app/stores';
    import { derived } from 'svelte/store';

    // 현재 페이지 경로를 반응형으로 추적
    $: currentPagePath = $page.url.pathname;

    // 메뉴 항목의 경로와 현재 페이지 경로가 일치하는지 확인하는 함수
    function isActive(path: string) {
        return currentPagePath  === path;
    }
</script>

<ul class="menu bg-base-200 min-w-[170px] w-[10vw] shadow-md h-screen relative flex flex-col p-0">
    <li class="text-xl mt-[20vh]"><a href="/adm/main">관리자 페이지</a></li>
    <li class="{isActive('/adm/me') ? 'bg-blue-500 text-white' : ''}"><a href="/adm/me">마이 페이지</a></li>
    <li class="{isActive('/adm/school') ? 'bg-blue-500 text-white' : ''}"><a href="/adm/schools">학교 관리</a></li>
    <li class="{isActive('/adm/class') ? 'bg-blue-500 text-white' : ''}"><a href="/adm/classes">학급 관리</a></li>
</ul>