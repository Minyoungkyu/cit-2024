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
    <div class="flex flex-col items-center justify-center p-8 w-screen h-screen">
        <div class="w-full h-full">
            <TopMenu />
            <div class="h-[85vh] border-2 rounded-b-2xl">

            </div>
        </div>
    </div>
</div>