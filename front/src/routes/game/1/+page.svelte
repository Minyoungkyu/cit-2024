<svelte:head>
    <title>{rq.SITE_NAME} | 스테이지</title>
</svelte:head>

<script lang="ts">
    import rq from '$lib/rq/rq.svelte';
	import { onMount } from 'svelte';
    import type { components } from '$lib/types/api/v1/schema';

    import DifficultySelector from '$lib/game/DifficultySelector.svelte';
    import TutorialSelector from '$lib/game//TutorialSelector.svelte';
    import TopMenuShop from '$lib/game//TopMenuShop.svelte';
    import TopMenuCharacter from '$lib/game//TopMenuCharacter.svelte';
    import TopMenuItem from '$lib/game//TopMenuItem.svelte';
    import TopMenuSetting from '$lib/game//TopMenuSetting.svelte';

    const { data } = $props<{ data: { playerLogList: components['schemas']['PlayerLogDto'][] } }>();
    const { playerLogList } = data;

    const clearedgameMapIds = playerLogList.map(log => log.gameMapId);
    const highestClearedgameMapId = Math.max(...clearedgameMapIds);
    
    console.log(highestClearedgameMapId)

    function isOpen(stageId: number) {
        return clearedgameMapIds.includes(stageId) || stageId === highestClearedgameMapId + 1;
    }

    onMount(() => {
        function adjustBackgroundContainer() {
            const contentContainer = document.querySelector('.content-container') as HTMLElement;
            const backgroundContainer = document.querySelector('.background-container') as HTMLElement;

            if (!contentContainer || !backgroundContainer) return;

            const contentWidth = contentContainer.offsetWidth;
            const contentHeight = contentContainer.offsetHeight;

            const targetHeight = contentWidth / 1.3333333333; // 원본 배경의 비율 입력(가로/세로)

            if (targetHeight <= contentHeight) {
            backgroundContainer.style.width = `${contentWidth}px`;
            backgroundContainer.style.height = `${targetHeight}px`;
            backgroundContainer.style.marginTop = `${(contentHeight - targetHeight) / 2}px`;
            backgroundContainer.style.marginBottom = `${(contentHeight - targetHeight) / 2}px`;
            backgroundContainer.style.marginLeft = `0`;
            backgroundContainer.style.marginRight = `0`;
            } else {
            const targetWidth = contentHeight * 1.3333333333;
            backgroundContainer.style.width = `${targetWidth}px`;
            backgroundContainer.style.height = `${contentHeight}px`;
            backgroundContainer.style.marginTop = `0`;
            backgroundContainer.style.marginBottom = `0`;
            backgroundContainer.style.marginLeft = `${(contentWidth - targetWidth) / 2}px`;
            backgroundContainer.style.marginRight = `${(contentWidth - targetWidth) / 2}px`;
            }
        }

        window.addEventListener('load', adjustBackgroundContainer);
        window.addEventListener('resize', adjustBackgroundContainer);

        adjustBackgroundContainer();
    });
</script>

<div class="content-container w-screen h-screen flex flex-col items-center justify-center bg-gray-500">
    <div class="flex flex-row justify-between w-full border-2 bg-white fixed top-0 z-[2]">
        <div class="navbar gap-2">
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
    <div class="background-container relative" style="background-image:url('/sampleImg.avif');background-position:center;background-size:100%;">

        <div class="dropdown dropdown-top dropdown-start absolute bottom-[8%] left-[4%]">
            
            <div tabindex="0" role="button" class="btn m-1 w-[6vw]" data-gameMapId="1">튜토리얼(열림)</div>
            <TutorialSelector/>
        </div>

        <div class="dropdown dropdown-top dropdown-start absolute bottom-[16%] left-[14%]">
            {#if isOpen(2)}
                <div tabindex="0" role="button" class="btn m-1 w-[6vw]" data-gameMapId="2">1-1(열림)</div>
                <DifficultySelector gameMapId={2} stepsLevelCount={3} playerLogList={playerLogList}/>
            {:else}
                <div tabindex="0" role="button" class="btn m-1 w-[6vw]" data-gameMapId="2">1-1(잠금)</div>
            {/if}
        </div>

        <div class="dropdown dropdown-top dropdown-start absolute bottom-[8%] left-[24%]">
            {#if isOpen(5)}
                <div tabindex="0" role="button" class="btn m-1 w-[6vw]" data-gameMapId="5">1-2(열림)</div>
                <DifficultySelector gameMapId={5} stepsLevelCount={3} playerLogList={playerLogList}/>
            {:else}
                <div tabindex="0" role="button" class="btn m-1 w-[6vw]" data-gameMapId="5">1-2(잠금)</div>
            {/if}
        </div>

        <div class="dropdown dropdown-top dropdown-start absolute bottom-[16%] left-[34%]">
            {#if isOpen(8)}
                <div tabindex="0" role="button" class="btn m-1 w-[6vw]" data-gameMapId="8">1-3(열림)</div>
                <DifficultySelector gameMapId={8} stepsLevelCount={3} playerLogList={playerLogList}/>
            {:else}
                <div tabindex="0" role="button" class="btn m-1 w-[6vw]" data-gameMapId="8">1-3(잠금)</div>
            {/if}
        </div>
    </div>
</div>




