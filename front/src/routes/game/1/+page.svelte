<svelte:head>
    <title>{rq.SITE_NAME} | 스테이지</title>
</svelte:head>

<script lang="ts">
    import rq from '$lib/rq/rq.svelte';
	import { onMount } from 'svelte';
    import type { components } from '$lib/types/api/v1/schema';

    import DifficultySelector2 from '$lib/game/DifficultySelector2.svelte';
    import DifficultySelector from '$lib/game/DifficultySelector.svelte';
    import TutorialSelector2 from '$lib/game//TutorialSelector2.svelte';
    import TopMenuShop from '$lib/game//TopMenuShop.svelte';
    import TopMenuCharacter from '$lib/game//TopMenuCharacter.svelte';
    import TopMenuItem from '$lib/game//TopMenuItem.svelte';
    import TopMenuSetting from '$lib/game//TopMenuSetting.svelte';

    const { data } = $props<{ data: { playerLogList: components['schemas']['PlayerLogDto'][] } }>();
    const { playerLogList } = data;

    const clearedgameMapIds = playerLogList.map(log => log.gameMapId);
    const highestClearedgameMapId = Math.max(...clearedgameMapIds);

    const difficultySelectorMsgs = [ // 셀렉터 메시지
        "본격적으로 우주로\n나가기 위한 준비를 하자",
        '본격적으로 우주로\n나가기 위한 준비를 하자2',
        '본격적으로 우주로\n나가기 위한 준비를 하자3'
    ]


    const difficultySelectorNames = [ // 셀렉터 이름
        '1-1',
        '1-2',
        '1-3'
    ]

    const stageStartIds = [3, 12, 21]; // Todo: 각 단계의 easy 난이도 1레벨 맵의 id를 입력
    const stageNeedIds = [2, 5, 14]; // Todo: 각 step, easy 난이도 마지막 레벨 맵의 id를 입력

    function findHighestStageStartId(highestClearedId: number): number { // 클리어한 최고 gameMapId 로 해금 스테이지 구하기 함수
        for (let i = stageNeedIds.length - 1; i >= 0; i--) {
            if (highestClearedId >= stageNeedIds[i]) {
                return stageStartIds[i]; 
            }
        }
        return 1; //stageStartIds[0];  
    }

    function isOpen(stageId: number) { // 스테이지 해금 여부 확인 함수
        return clearedgameMapIds.includes(stageId) || clearedgameMapIds.includes(stageNeedIds[stageStartIds.indexOf(stageId)]);
    }

    let isDropdownOpen = $state([false, false, false, false, false]); // 드롭다운 메뉴 상태 추적

    function toggleDropdown(index: number) { // 드롭다운 열기, 스테이지하이라이터 상태 조절 함수
        isDropdownOpen = isDropdownOpen.map((_, i) => i === index ? !isDropdownOpen[i] : false);
        const allClosed = isDropdownOpen.every(open => !open);
        const highlighter = document.getElementById('stageHighlighter');
        if (highlighter) {
            highlighter.classList.toggle('hidden', !allClosed);
        }
    }

    function closeAllDropdowns() { // 백그라운드 클릭으로 드롭다운 닫을때 사용하는 함수
        isDropdownOpen = isDropdownOpen.map(() => false);
        const allClosed = isDropdownOpen.every(open => !open);
        const highlighter = document.getElementById('stageHighlighter');
        if (highlighter) {
            highlighter.classList.toggle('hidden', !allClosed);
        }
    }

    onMount(() => {
        rq.fetchAndInitializeInventories();

        window.addEventListener('click', function(event) { // 백그라운드 클릭과 드롭다운 메뉴 클릭 구분
            const isDropdownButton = (event.target as Element).closest('.dropdown');
            const isDropdownMenu = (event.target as Element).closest('.dropdown-content');
            const isBtn = (event.target as Element).closest('.btn');
            const isEqBtn = (event.target as Element).closest('.equipbtn');


            if (!isDropdownButton && !isDropdownMenu && !isBtn && !isEqBtn) {
                closeAllDropdowns();
            }
        });

        function adjustStageHighlighter(stageElement: Element) { // 하이라이터 위치 조절 함수
            const highlighter = document.getElementById('stageHighlighter');
            if (!highlighter || !stageElement) return;

            var bottomValues: any;
            var leftValues: any;
            var classList = stageElement.classList;

            classList.forEach((className) => {
                var bottomMatch = className.match(/^bottom-\[(\d+)%\]$/);
                if (bottomMatch) {
                    bottomValues = (parseInt(bottomMatch[1], 10)); 
                }

                var leftMatch = className.match(/^left-\[(\d+)%\]$/);
                if (leftMatch) {
                    leftValues = (parseInt(leftMatch[1], 10));
                }
            });

            highlighter.style.bottom = `${bottomValues + 6}%`; // Todo: 실제 디자인에 따라 위치 조절
            highlighter.style.left = `${leftValues + 3}%`;  // Todo: 실제 디자인에 따라 위치 조절

        }

        const highestStageElement = document.querySelector(`div[data-gameMapId="${findHighestStageStartId(highestClearedgameMapId)}"]`);
        adjustStageHighlighter(highestStageElement!);

        // 배경 이미지 비율에 따라 배경 컨테이너 크기 조절 함수 였던 것
        // function adjustBackgroundContainer() { 
        //     const contentContainer = document.querySelector('.content-container') as HTMLElement;
        //     const backgroundContainer = document.querySelector('.background-container') as HTMLElement;

        //     if (!contentContainer || !backgroundContainer) return;

        //     const contentWidth = contentContainer.offsetWidth;
        //     const contentHeight = contentContainer.offsetHeight;

        //     const targetHeight = contentWidth / 1.999; // 원본 배경의 비율 입력(가로/세로)

        //     if (targetHeight <= contentHeight) {
        //     backgroundContainer.style.width = `${contentWidth}px`;
        //     backgroundContainer.style.height = `${targetHeight}px`;
        //     backgroundContainer.style.marginTop = `${(contentHeight - targetHeight) / 2}px`;
        //     backgroundContainer.style.marginBottom = `${(contentHeight - targetHeight) / 2}px`;
        //     backgroundContainer.style.marginLeft = `0`;
        //     backgroundContainer.style.marginRight = `0`;
        //     } else {
        //     const targetWidth = contentHeight * 1.999;
        //     backgroundContainer.style.width = `${targetWidth}px`;
        //     backgroundContainer.style.height = `${contentHeight}px`;
        //     backgroundContainer.style.marginTop = `0`;
        //     backgroundContainer.style.marginBottom = `0`;
        //     backgroundContainer.style.marginLeft = `${(contentWidth - targetWidth) / 2}px`;
        //     backgroundContainer.style.marginRight = `${(contentWidth - targetWidth) / 2}px`;
        //     }
        // }

        // window.addEventListener('load', adjustBackgroundContainer);
        // window.addEventListener('resize', adjustBackgroundContainer);

        // adjustBackgroundContainer();
    });
</script>

<style>
    @keyframes bounce {
        0%, 20%, 50%, 80%, 100% {
            transform: translateY(0);
        }
        40% {
            transform: translateY(-30px);
        }
        60% {
            transform: translateY(-15px);
        }
    }

    @keyframes shrinkAndMove {
        from {
            transform: scale(5) translate(50%, -100%);
            opacity: 0;
        }
        to {
            transform: scale(1) translate(0, 0);
            opacity: 1;
        }
    }

    .animatedHighlighter {
        animation: shrinkAndMove 1s forwards, bounce 2s infinite 1s;
    }
</style>

<div class="content-container w-screen h-screen flex flex-col items-center justify-center bg-gray-500 relative overflow-hidden">
    <!-- 탑바 였던 것-->
    <!-- <div class="flex flex-row justify-between w-full border-2 bg-white fixed top-0 z-[2]">
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
    </div> -->
    <!--          -->
    <div class="background-container relative w-screen h-screen" style="background-image:url('/background_1.png');background-position:center;background-size:cover;backgroudn-repeat:no-repeat;">
        <div class="flex flex-col absolute top-[6%] left-[4%]"> <!-- 좌상단 -->
            <div class="flex flex-row gap-5">
                <div class="w-[80px] h-[80px] border-2"></div>
                <div class="w-[80px] h-[80px] border-2"></div>
                <div class="w-[80px] h-[80px] border-2"></div>
                <div class="w-[80px] h-[80px] border-2"></div>
                <div class="w-[80px] h-[80px] border-2"></div>
            </div>
            <div class="font-bold text-white text-[40px] mt-4" style="text-shadow:-5px 5px black">스테이지</div>
        </div>
        <div class="flex flex-col items-end absolute top-[6%] right-[4%]"> <!-- 우상단 -->
            <div class="flex flex-row gap-1">
                <div class="w-[320px] h-[80px] border-2"></div>
                <div class="w-[80px] h-[80px] border-2"></div>
            </div>
            <div class="font-bold text-white text-[25px] text-center w-[240px] h-[40px] mt-4 border-2" style="text-shadow:0px 5px black">1,234</div>
        </div> 
        <div id="stageHighlighter" class="stage-highlighter border-2 w-[50px] h-[50px] absolute z-[10] animatedHighlighter"></div>
        <div class="btn absolute bottom-[8%] left-[4%]" data-gameMapId="1" on:click={() => toggleDropdown(0)}>튜토리얼(열림)</div>
        {#if isDropdownOpen[0]}
            <TutorialSelector2 />
        {/if}
        {#if isOpen(3)} <!--step 의 easy, 1레벨 맵 아이디-->
        <div class="btn absolute bottom-[16%] left-[14%] w-[6vw]" data-gameMapId="3" on:click={() => toggleDropdown(1)}>1-1(열림)</div>
            {#if isDropdownOpen[1]}
            <!-- gameMapId : step 의 easy, 1레벨 맵아이디, stepsLevelCount : step 의 level 갯수, -->
            <DifficultySelector2 gameMapId={3} stepsLevelCount={3} playerLogList={playerLogList} difficultySelectorMsg={difficultySelectorMsgs[0]} difficultySelectorName={difficultySelectorNames[0]}/>
            {/if}
        {:else}
        <div tabindex="0" role="button" class="btn absolute bottom-[16%] left-[14%] w-[6vw]">1-1(잠금)</div>
        {/if}

        {#if isOpen(12)}
        <div class="btn absolute bottom-[8%] left-[24%] w-[6vw]" data-gameMapId="12" on:click={() => toggleDropdown(2)}>1-2(열림)</div>
            {#if isDropdownOpen[2]}
            <DifficultySelector2 gameMapId={12} stepsLevelCount={3} playerLogList={playerLogList} difficultySelectorMsg={difficultySelectorMsgs[1]} difficultySelectorName={difficultySelectorNames[1]}/>
            {/if}
        {:else}
        <div tabindex="0" role="button" class="btn absolute bottom-[8%] left-[24%] w-[6vw]">1-2(잠금)</div>
        {/if}

        {#if isOpen(21)}
        <div class="btn absolute bottom-[16%] left-[34%] w-[6vw]" data-gameMapId="21" on:click={() => toggleDropdown(3)}>1-3(열림)</div>
            {#if isDropdownOpen[3]}
            <DifficultySelector2 gameMapId={21} stepsLevelCount={3} playerLogList={playerLogList} difficultySelectorMsg={difficultySelectorMsgs[2]} difficultySelectorName={difficultySelectorNames[2]}/>
            {/if}
        {:else}
        <div tabindex="0" role="button" class="btn absolute bottom-[16%] left-[34%] w-[6vw]">1-3(잠금)</div>
        {/if}
    </div>
</div>




