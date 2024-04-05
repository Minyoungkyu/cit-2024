<svelte:head>
    <title>{rq.SITE_NAME}</title>
</svelte:head>

<script lang="ts">
    export const ssr = false; 
    import rq from '$lib/rq/rq.svelte';
	import { onMount } from 'svelte';
    import type { components } from '$lib/types/api/v1/schema';

    import DifficultySelector2 from '$lib/game/DifficultySelector2.svelte';
    import TutorialSelector2 from '$lib/game//TutorialSelector2.svelte';
    import TransitioningCloseLayer from '$lib/game/TransitioningCloseLayer.svelte';


    const { data } = $props<{ data: { playerLogList: components['schemas']['PlayerLogDto'][] } }>();
    const { playerLogList } = data;

    const clearedgameMapIds = playerLogList.map(log => log.gameMapId);
    const highestClearedgameMapId = Math.max(...clearedgameMapIds);

    const difficultySelectorMsgs = [ // 셀렉터 메시지
        '훈련을 마쳤다\n로켓 발사장으로 이동하자',
        '본격적으로 우주로\n나가기 위한 준비를 하자',
        '로켓을 수리하기위한 재료가 모두 모였다\n로켓을 수리해보자'
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

    let isDropdownOpen = $state([false, false, false, false]); // 드롭다운 메뉴 상태 추적

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

    const originalHeight = 1080;
    let currentHeight = $state(1080);
    let scaleMultiplier = $state(1);
    let animationStart = $state(false);

    onMount(() => {
        rq.fetchAndInitializeInventories();

        const updateScale = () => {
            const currentHeight = window.innerHeight;
            scaleMultiplier = (Math.max(1, currentHeight / originalHeight));

        };

        window.addEventListener('resize', updateScale);
        
        updateScale();

        window.addEventListener('click', function(event) { // 백그라운드 클릭과 드롭다운 메뉴 클릭 구분
            const isDropdownButton = (event.target as Element).closest('.dropdown');
            const isDropdownMenu = (event.target as Element).closest('.dropdown-content');
            const isBtn = (event.target as Element).closest('.stage_btn');
            const isEqBtn = (event.target as Element).closest('.equipbtn');
            const isCharM = (event.target as Element).closest('.charactorStatus');

            if (!isDropdownButton && !isDropdownMenu && !isBtn && !isEqBtn && !isCharM) {
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

            highlighter.style.bottom = `${bottomValues + 0.2}%`; // Todo: 실제 디자인에 따라 위치 조절
            highlighter.style.left = `${leftValues + 0.9}%`;  // Todo: 실제 디자인에 따라 위치 조절
            
            animationStart = true;
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

    let isTransitioning = $state(false);

    function activeTransitionAnimation() {
        isTransitioning = true;
    }

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

    @keyframes zoomInAndOut {
        0%, 100% {
        transform: scale(0.6); 
        opacity: 1; 
        }
        50% {
        transform: scale(0.8); 
        opacity: 0.8; 
        }
    }

    @keyframes shrinkAndMove {
        from {
            transform: scale(20);
            opacity: 0;
        }
        to {
            transform: scale(0.6);
            opacity: 1;
        }
    }

    .animatedHighlighter {
        animation: shrinkAndMove 1s forwards, zoomInAndOut 2s infinite 1s;
    }
    
    .stage-text {
        text-shadow: 
            8px 8px 6px black,
            7px 7px 6px black,
            6px 6px 6px black,
            -5px -5px 6px black,
            -5px 0px 6px black,
            -5px 8px 6px black,
            8px -5px 6px black;
    }

    @font-face {
        font-family: 'Raleway';
        src: url('/font/Raleway-VariableFont_wght.ttf') format('truetype');
        font-weight: 600;
        font-style: normal;
    }

    .inE {
        font-family: 'Raleway', sans-serif; 
    }

    .btn_stage:hover {
        background-image: url('/img/map/btn_stage_off.png') !important;
    }

    .btn_shop:hover {
        background-image: url('/img/map/btn_shop_off.png') !important;
        transform: scale(1.05) !important;
        transform-origin: bottom;
    }

    .btn_codebook:hover {
        background-image: url('/img/map/btn_coodbook_off.png') !important;
        transform: scale(1.05) !important;
        transform-origin: bottom;
    }

    .btn_challenge:hover {
        background-image: url('/img/map/btn_challenge_off.png') !important;
        transform: scale(1.05) !important;
        transform-origin: bottom;
    }

    .btn_rank:hover {
        background-image: url('/img/map/btn_ranking_off.png') !important;
        transform: scale(1.05) !important;
        transform-origin: bottom;
    }
</style>

<!-- <audio autoplay>
    <source src="/sound/map_sound.mp3" type="audio/mpeg">
</audio> -->
<div class="content-container relative w-screen h-screen flex flex-col items-center justify-center bg-gray-500 relative overflow-hidden">
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
    <div class="background-container w-screen h-screen" style="background-image:url('/background_1.png');background-position:center;background-size:cover;background-repeat:no-repeat;">
        <div class="absolute top-[0] left-[0] w-[1226px] h-[523px]"
             style="background-image:url('/img/map/ui_background_L.png');opacity:1;transform:scale(0.67);transform-origin:left top"></div> <!--좌상단 백그라운드 레이어-->
        <div class="absolute top-[0] right-[0] w-[1044px] h-[445px]"
             style="background-image:url('/img/map/ui_background_R.png');opacity:1;transform:scale(0.67);transform-origin:right top"></div> <!--우상단 백그라운드 레이어-->
        <div class="absolute top-[0] right-[0] w-[386px] h-screen" style="background-image:url('/img/map/ui_Gradation.png');"></div> <!-- 우측 백그라운드 레이어 -->
        <div class="absolute top-[50%] right-[1%] w-[83px] h-[124px] cursor-pointer" style="background-image:url('/img/map/btn_next.png');transform-origin:right;transform:scale(0.67)"></div> <!-- 다음 맵 버튼 -->
        <div class="flex flex-col absolute top-[2%] left-[2%]"> <!-- 좌상단 -->
            <div class="flex flex-row items-end gap-5" style="transform:scale(0.67) rotateZ(3deg) rotateY(5deg);transform-origin:left;">
                <div class="w-[160px] h-[160px] btn_stage" style="background-image:url('/img/map/btn_stage_off.png');transform:scale(1);background-repeat:no-repeat;background-size:contain;"></div>
                <div class="w-[134px] h-[134px] btn_shop" style="background-image:url('/img/map/btn_shop_on.png');transform:scale(1)"></div>
                <div class="w-[133px] h-[134px] btn_codebook" style="background-image:url('/img/map/btn_coodbook_on.png');transform:scale(1)"></div>
                <div class="w-[133px] h-[134px] btn_challenge" style="background-image:url('/img/map/btn_challenge_on.png');transform:scale(1)"></div>
                <div class="w-[133px] h-[134px] btn_rank" style="background-image:url('/img/map/btn_ranking_on.png');transform:scale(1)"></div>
            </div>
            <div class="test font-bold text-white text-[50px] mt-2" style="text-shadow:-5px 5px black">스테이지</div>
        </div>
        <div class="flex flex-col items-end absolute top-[4%] right-[0]"> <!-- 우상단 -->
            <div class="flex flex-row gap-1 mr-8" style="transform-origin:right;transform:scale(0.67);">
                <div class="w-[506px] h-[134px]" style="background-image:url('/img/map/ui_user_inf.png');">
                    <div class="text-white text-[40px] font-bold h-full flex flex-row items-center justify-between mr-4">
                        <div class="ml-16 stage-text">Lv {rq.getPlayerLeve()}</div>
                        <div class="mr-4 stage-text">{rq.member.player.nickname}</div>
                    </div>
                </div>
                <div class="w-[134px] h-[134px]" style="background-image:url('/img/map/btn_settomg.png');"></div>
            </div>
            <div class="font-bold relative text-white text-[25px] text-center w-[510px] h-[216px] top-[-25px]" 
                style="text-shadow:0px 5px black;background-image:url('/img/map/ui_gembox.png');transform-origin:right top;transform:scale(0.8)">
                <div class="absolute left-[53%] top-[15%] text-[50px] w-[230px] text-right" style="text-shadow:2px 8px black;">
                    {rq.member.player.gems.toLocaleString()}
                </div>
            </div>
        </div> 
        <div id="stageHighlighter" class="stage-highlighter w-[185.4px] h-[161.8px] absolute z-[10] {animationStart ? 'animatedHighlighter' : 'invisible'}" 
            style="background-image:url('/img/map/ui_aim.png');background-size:contain;pointer-events:none;background-repeat:no-repeat;transform:scale(0.6)"></div>



        <!-- <div class="btn absolute bottom-[8%] left-[4%]" data-gameMapId="1" on:click={() => toggleDropdown(0)}>튜토리얼(열림)</div> -->
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[8%] left-[4%] cursor-pointer" data-gameMapId="1" on:click={() => toggleDropdown(0)} 
            style="background-image: url(/img/map/ui_stage_{clearedgameMapIds.includes(2) ? (isDropdownOpen[0] ? '3' : '2') : (isDropdownOpen[0] ? '3' : '1')}.png); transform:scale(0.67);">
            <div class="stage-text absolute right-[1%] top-[-13px] text-[55px] text-white font-bold" style="">튜토리얼</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-white italic" style="">TUTORIAL</div>
        </div>
        {#if isDropdownOpen[0]}
            <div style="transform-origin:top right;transform:scale({scaleMultiplier})">
                <TutorialSelector2 activeTransitionAnimation={activeTransitionAnimation}/>
            </div>
        {/if}
        {#if isOpen(3)} <!--step 의 easy, 1레벨 맵 아이디-->
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[40%] left-[20%] cursor-pointer" on:click={() => toggleDropdown(1)} data-gameMapId="3"
            style="background-image: url(/img/map/ui_stage_{clearedgameMapIds.includes(5) ? (isDropdownOpen[1] ? '3' : '2') : (isDropdownOpen[1] ? '3' : '1')}.png); transform:scale(0.67);">
            <div class="stage-text absolute right-[1%] top-[-13px] text-[55px] text-white font-bold" style="">1 - 1</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-white italic" style="">ONE - ONE</div>
        </div>
        <!-- <div class="btn absolute bottom-[16%] left-[14%] w-[6vw]" data-gameMapId="3" on:click={() => toggleDropdown(1)}>1-1(열림)</div> -->
            {#if isDropdownOpen[1]}
            <!-- gameMapId : step 의 easy, 1레벨 맵아이디, stepsLevelCount : step 의 level 갯수, -->
            <div style="transform-origin:top right;transform:scale({scaleMultiplier})">
                <DifficultySelector2 gameMapId={3} stepsLevelCount={3} playerLogList={playerLogList} 
                difficultySelectorMsg={difficultySelectorMsgs[0]} difficultySelectorName={difficultySelectorNames[0]} activeTransitionAnimation={activeTransitionAnimation} />
            </div>
            {/if}
        {:else}
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[40%] left-[20%] cursor-pointer"
            style="background-image:url('/img/map/ui_stage_0.png');transform:scale(0.67)">
            <div class="stage-text absolute right-[7%] top-[-13px] text-[55px] text-gray-400 font-bold" style=""><i class="fa-solid fa-lock text-[30px] mr-4"></i>1 - 1</div>
            <div class="stage-text inE absolute right-[7%] top-[33%] text-[25px] text-gray-400 italic" style="">ONE - ONE</div>
        </div>
        {/if}

        {#if isOpen(12)}
        <!-- <div class="btn absolute bottom-[8%] left-[24%] w-[6vw]" data-gameMapId="12" on:click={() => toggleDropdown(2)}>1-2(열림)</div> -->
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[10%] left-[35%] cursor-pointer" on:click={() => toggleDropdown(2)} data-gameMapId="12"
            style="background-image: url(/img/map/ui_stage_{clearedgameMapIds.includes(14) ? (isDropdownOpen[2] ? '3' : '2') : (isDropdownOpen[2] ? '3' : '1')}.png); transform:scale(0.67);">            
            <div class="stage-text absolute right-[7%] top-[-13px] text-[55px] text-white font-bold" style="">1 - 2</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-white italic" style="">ONE - TWO</div>
        </div>
            {#if isDropdownOpen[2]}
            <div style="transform-origin:top right;transform:scale({scaleMultiplier})">
                <DifficultySelector2 gameMapId={12} stepsLevelCount={3} playerLogList={playerLogList} 
                difficultySelectorMsg={difficultySelectorMsgs[1]} difficultySelectorName={difficultySelectorNames[1]} activeTransitionAnimation={activeTransitionAnimation} />
            </div>
            {/if}
        {:else}
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[10%] left-[35%] cursor-pointer"
            style="background-image:url('/img/map/ui_stage_0.png');transform:scale(0.67)">
            <div class="stage-text absolute right-[7%] top-[-13px] text-[55px] text-gray-400 font-bold" style=""><i class="fa-solid fa-lock text-[30px] mr-4"></i>1 - 2</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-gray-400 italic" style="">ONE - TWO</div>
        </div>
        {/if}

        {#if isOpen(21)}
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[40%] left-[48%] cursor-pointer" on:click={() => toggleDropdown(3)} data-gameMapId="21"
            style="background-image: url(/img/map/ui_stage_{clearedgameMapIds.includes(23) ? (isDropdownOpen[3] ? '3' : '2') : (isDropdownOpen[3] ? '3' : '1')}.png); transform:scale(0.67);">            
            <div class="stage-text absolute right-[7%] top-[-13px] text-[55px] text-white font-bold" style="">1 - 3</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-white italic" style="">ONE - THREE</div>
        </div>
        <!-- <div class="btn absolute bottom-[16%] left-[34%] w-[6vw]" data-gameMapId="21" on:click={() => toggleDropdown(3)}>1-3(열림)</div> -->
            {#if isDropdownOpen[3]}
            <div style="transform-origin:top right;transform:scale({scaleMultiplier})">
                <DifficultySelector2 gameMapId={21} stepsLevelCount={3} playerLogList={playerLogList} 
                difficultySelectorMsg={difficultySelectorMsgs[2]} difficultySelectorName={difficultySelectorNames[2]} activeTransitionAnimation={activeTransitionAnimation}/>
            </div>
            {/if}
        {:else}
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[40%] left-[48%] cursor-pointer"
            style="background-image:url('/img/map/ui_stage_0.png');transform:scale(0.67)">
            <div class="stage-text absolute right-[7%] top-[-13px] text-[55px] text-gray-400 font-bold" style=""><i class="fa-solid fa-lock text-[30px] mr-4"></i>1 - 3</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-gray-400 italic" style="">ONE - THREE</div>
        </div>
        {/if}
    </div>
</div>

{#if isTransitioning}  
    <TransitioningCloseLayer />
{/if}

