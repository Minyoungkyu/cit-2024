<svelte:head>
    <title>{rq.SITE_NAME} | 스테이지</title>
</svelte:head>

<script lang="ts">
    import rq from '$lib/rq/rq.svelte';
    import DifficultySelector from './DifficultySelector.svelte';
    import DifficultySelectorCenter from './DifficultySelectorCenter.svelte';
    import TopMenuShop from './TopMenuShop.svelte';
    import TopMenuCharacter from './TopMenuCharacter.svelte';
    import TopMenuItem from './TopMenuItem.svelte';
    import TopMenuSetting from './TopMenuSetting.svelte';
</script>


<div class="flex flex-col items-center justify-center p-8 h-[90vh]">
    <div class="border-2 border-black w-full h-full relative">
        <div class="flex flex-row justify-between">
            <div class="navbar bg-base-100 gap-2">
                <a class="btn btn-primary text-xl">스테이지</a>
                <a class="btn btn-outline text-xl">프로필</a>
                <a class="btn btn-outline text-xl">도전과제</a>
                <a class="btn btn-outline text-xl">랭킹</a>
            </div>
            <div class="navbar flex flex-row justify-end gap-2">
                <TopMenuShop />
                <TopMenuItem />
                <TopMenuCharacter />
                <TopMenuSetting />
            </div>
        </div>

        <div class="dropdown dropdown-top dropdown-start top-[80%] left-[1%]">
            <div tabindex="0" role="button" class="btn m-1">튜토리얼</div>
            <DifficultySelector />
        </div>
        <div class="dropdown dropdown-top dropdown-start top-[70%] left-[5%]">
            <div tabindex="0" role="button" class="btn m-1">1-1</div>
            <DifficultySelectorCenter />
        </div>
        <div class="dropdown dropdown-top dropdown-start top-[80%] left-[5%]">
            <div tabindex="0" role="button" class="btn m-1">1-2</div>
            <DifficultySelectorCenter />
        </div>
        <div class="dropdown dropdown-top dropdown-start top-[70%] left-[5%]">
            <div tabindex="0" role="button" class="btn m-1">1-3</div>
            <DifficultySelectorCenter />
        </div>
        <div class="dropdown dropdown-top dropdown-start top-[80%] left-[5%]">
            <div tabindex="0" role="button" class="btn m-1">1-4</div>
            <DifficultySelectorCenter />
        </div>
    </div>
    
    
</div>
