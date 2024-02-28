<svelte:head>
    <title>{rq.SITE_NAME} | 스테이지</title>
</svelte:head>

<script lang="ts">
    import rq from '$lib/rq/rq.svelte';
    import DifficultySelector from './DifficultySelector.svelte';
    import TopMenuShop from './TopMenuShop.svelte';
    import TopMenuCharacter from './TopMenuCharacter.svelte';
    import TopMenuItem from './TopMenuItem.svelte';
    import TopMenuSetting from './TopMenuSetting.svelte';
	import { onMount } from 'svelte';

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
            <div tabindex="0" role="button" class="btn m-1 w-[6vw]">튜토리얼</div>
            <DifficultySelector/>
        </div>

        <div class="dropdown dropdown-top dropdown-start absolute bottom-[16%] left-[14%]">
            <div tabindex="0" role="button" class="btn m-1 w-[6vw]">1-1</div>
        </div>

        <div class="dropdown dropdown-top dropdown-start absolute bottom-[8%] left-[24%]">
            <div tabindex="0" role="button" class="btn m-1 w-[6vw]">1-2</div>
        </div>

        <div class="dropdown dropdown-top dropdown-start absolute bottom-[16%] left-[34%]">
            <div tabindex="0" role="button" class="btn m-1 w-[6vw]">1-3</div>
        </div>
    </div>
</div>




