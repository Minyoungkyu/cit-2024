<svelte:head>
    <title>{rq.SITE_NAME} | 관리자페이지</title>
</svelte:head>


<script lang="ts">

    import rq from '$lib/rq/rq.svelte';
    import SideMenu from '../sideMenu.svelte';

</script>

<div class="flex">
    <SideMenu />
    <div class="flex flex-col items-center justify-center p-8 w-full h-screen">
        <div>
            관리자 메인 페이지 입니다.
        </div>
    </div>
    
</div>



