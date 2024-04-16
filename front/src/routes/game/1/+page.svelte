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
	import { scale } from 'svelte/transition';


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
    let scaleMultiplier2 = $state(1);
    let animationStart = $state(false);
    let highlighterBottom = $state(0);
    let highlighterLeft = $state(0);
    let widthValue = $state(0);
    let showLogoutModal = $state(false);

    onMount(() => {
        rq.fetchAndInitializeInventories();

        const updateScale = () => {
            const currentHeight = window.innerHeight;
            // scaleMultiplier = (Math.max(1, currentHeight / originalHeight));

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
                    highlighterBottom = bottomValues;
                }

                var leftMatch = className.match(/^left-\[(\d+)%\]$/);
                if (leftMatch) {
                    leftValues = (parseInt(leftMatch[1], 10));
                    highlighterLeft = leftValues;
                }
            });

            highlighter.style.bottom = `${bottomValues}%`; // Todo: 실제 디자인에 따라 위치 조절
            highlighter.style.left = `${leftValues}%`;  // Todo: 실제 디자인에 따라 위치 조절
            
            animationStart = true;
        }

        const highestStageElement = document.querySelector(`div[data-gameMapId="${findHighestStageStartId(highestClearedgameMapId)}"]`);
        adjustStageHighlighter(highestStageElement!);

        // 배경 이미지 비율에 따라 배경 컨테이너 크기 조절 함수 였던 것
        function adjustBackgroundContainer() { 
            const contentContainer = document.querySelector('.content-container') as HTMLElement;
            const backgroundContainer = document.querySelector('.background-container') as HTMLElement;

            let resolution = $state(0);
            if(window.innerWidth / window.innerHeight >= 1.6) {
                if(window.innerWidth / window.innerHeight >= 2.1) resolution = 2.1;
                else resolution = window.innerWidth / window.innerHeight;
            } else {
                resolution = 1.333;
            }

            if (!contentContainer || !backgroundContainer) return;

            const contentWidth = contentContainer.offsetWidth;
            const contentHeight = contentContainer.offsetHeight;

            const targetHeight = contentWidth / resolution; 

            if (targetHeight <= contentHeight) {
            backgroundContainer.style.width = `${contentWidth}px`;
            backgroundContainer.style.height = `${targetHeight}px`;
            backgroundContainer.style.marginTop = `${(contentHeight - targetHeight) / 2}px`;
            backgroundContainer.style.marginBottom = `${(contentHeight - targetHeight) / 2}px`;
            backgroundContainer.style.marginLeft = `0`;
            backgroundContainer.style.marginRight = `0`;
            widthValue = contentWidth;
            } else {
            const targetWidth = contentHeight * resolution;
            backgroundContainer.style.width = `${targetWidth}px`;
            backgroundContainer.style.height = `${contentHeight}px`;
            backgroundContainer.style.marginTop = `0`;
            backgroundContainer.style.marginBottom = `0`;
            backgroundContainer.style.marginLeft = `${(contentWidth - targetWidth) / 2}px`;
            backgroundContainer.style.marginRight = `${(contentWidth - targetWidth) / 2}px`;
            widthValue = targetWidth;
            }
            
            scaleMultiplier2 = (backgroundContainer.offsetWidth/1920);
            scaleMultiplier = (backgroundContainer.offsetHeight / originalHeight);
        }

        window.addEventListener('load', adjustBackgroundContainer);
        window.addEventListener('resize', adjustBackgroundContainer);

        adjustBackgroundContainer();
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

    .btn_setting:hover {
        background-image: url('/img/map/btn_settomg_on.png') !important;
        transform: scale(1.05) !important;
        transform-origin: bottom;
    }

    .btn_logout:hover {
        background-image: url('/img/map/btn_logout_on.png') !important;
        transform: scale(1.05) !important;
        transform-origin: bottom;
    }

    .hovering:hover {
        cursor: pointer;
        transform: scale(1.1);
    }
</style>

<audio autoplay>
    <source src="/sound/map_sound.mp3" type="audio/mpeg">
</audio>
<div class="content-container w-screen h-screen flex flex-col items-center justify-center bg-gray-500 overflow-hidden">
    <div class="background-container w-screen h-screen relative overflow-hidden" style="background-image:url('/background_1.png');background-position:center;background-size:cover;background-repeat:no-repeat;">
        <div class="absolute top-[0] left-[0] w-[1226px] h-[523px]"
             style="background-image:url('/img/map/ui_background_L.png');opacity:1;transform:scale(0.67) scale({scaleMultiplier2});transform-origin:left top"></div> <!--좌상단 백그라운드 레이어-->
        <div class="absolute top-[0] right-[0] w-[1044px] h-[445px]"
             style="background-image:url('/img/map/ui_background_R.png');opacity:1;transform:scale(0.67) scale({scaleMultiplier2});transform-origin:right top"></div> <!--우상단 백그라운드 레이어-->
        <div class="absolute top-[0] right-[0] w-[386px] h-screen" style="transform:scale({scaleMultiplier});transform-origin:top right;">
            <div class="absolute top-[0] right-[0] w-[386px] h-[1080px]" style="background-image:url('/img/map/ui_Gradation.png');"></div> <!-- 우측 백그라운드 레이어 -->
        </div>
        <div class="absolute top-[50%] right-[1%] w-[83px] h-[124px] cursor-pointer" style="background-image:url('/img/map/btn_next.png');transform-origin:top right;transform:scale(0.67) scale({scaleMultiplier2});"></div> <!-- 다음 맵 버튼 -->
        <div class="flex flex-col absolute top-[2%] left-[2%]" style="transform-origin:top left;transform:scale({scaleMultiplier2});"> <!-- 좌상단 -->
            <div class="flex flex-row items-end gap-5" style="transform:scale(0.67) rotateZ(3deg) rotateY(5deg);transform-origin:left;">
                <div class="w-[160px] h-[160px] btn_stage" style="background-image:url('/img/map/btn_stage_off.png');transform:scale(1);background-repeat:no-repeat;background-size:contain;"></div>
                <div class="w-[134px] h-[134px] btn_shop" style="background-image:url('/img/map/btn_shop_on.png');transform:scale(1)"></div>
                <div class="w-[133px] h-[134px] btn_codebook" style="background-image:url('/img/map/btn_coodbook_on.png');transform:scale(1)"></div>
                <div class="w-[133px] h-[134px] btn_challenge" style="background-image:url('/img/map/btn_challenge_on.png');transform:scale(1)"></div>
                <div class="w-[133px] h-[134px] btn_rank" style="background-image:url('/img/map/btn_ranking_on.png');transform:scale(1)"></div>
            </div>
            <div class="test font-bold text-white text-[50px] mt-2" style="text-shadow:-5px 5px black">스테이지</div>
        </div>
        <div class=" flex flex-col items-end absolute top-[4%] right-[0]" style="transform-origin:top right;transform:scale({scaleMultiplier2});"> <!-- 우상단 -->
            <div class="flex flex-row gap-3 mr-4" style="transform-origin:right;transform:scale(0.67);">
                <div class="w-[506px] h-[134px]" style="background-image:url('/img/map/ui_user_inf.png');">
                    <div class="text-white text-[40px] font-bold h-full flex flex-row items-center justify-between mr-4">
                        <div class="ml-16 stage-text">Lv {rq.getPlayerLeve()}</div>
                        <div class="mr-4 stage-text">{rq.member.player.nickname}</div>
                    </div>
                </div>
                <div class="w-[134px] h-[134px] btn_setting cursor-pointer" style="background-image:url('/img/map/btn_settomg_off.png');"></div>
                <div class="w-[134px] h-[134px] btn_logout cursor-pointer" style="background-image:url('/img/map/btn_logout.png');" on:click={() => showLogoutModal=true}></div> <!-- 로그아웃 자리 -->
            </div>
            <div class=" font-bold relative text-white text-[25px] text-center w-[510px] h-[216px] top-[-25px]" 
                style="text-shadow:0px 5px black;background-image:url('/img/map/ui_gembox.png');transform-origin:right top;transform:scale(0.8)">
                <div class="absolute left-[53%] top-[15%] text-[50px] w-[230px] text-right" style="text-shadow:2px 8px black;">
                    {rq.member.player.gems.toLocaleString()}
                </div>
            </div>
        </div> 

        {#if showLogoutModal}
        <div class="h-full absolute flex items-center justify-center bg-black bg-opacity-50 z-[99]" style="width:{widthValue}px;">
            <div class="flex justify-center items-center z-[90]" style="transform:scale(0.4) scale({scaleMultiplier});">
                <div class="w-[80px] h-[904px]" style="background-image:url('/img/inventory/ui_popup_start.jpg');"></div>
                <div class="w-[1200px] h-[904px] text-white font-[900] text-[50px] flex flex-col items-center justify-around whitespace-nowrap" style="background-image:url('/img/inventory/ui_popup_middle.jpg');">
                    <div class=" ml-[100px] flex flex-col h-full items-center whitespace-nowrap">
                        <div class="text-[150px] mt-[20px]">
                            로그아웃
                        </div>
                        <div class="absolute w-full h-[19px] top-[260px] left-[62px]" style="background-image:url('/img/inventory/ui_itme_window3.png');background-repeat:no-repeat;transform:scale(2.8);transform-origin:left;"></div>
                        <div class="flex w-full h-1/4 items-start justify-center text-[75px] mt-[220px]">
                            정말 로그아웃 하시겠습니까?
                        </div>
                        <div class="flex flex-row w-full justify-around gap-12 text-[100px] mt-[30px]">
                            <div class="hovering text-red-500" on:click={() => showLogoutModal=false}>취소</div>
                            <div class="hovering" on:click={() => rq.logoutAndRedirect('/')}>로그아웃</div>
                        </div>
                    </div>
                </div>
                <div class="w-[228px] h-[904px]" style="background-image:url('/img/inventory/ui_popup_end.png');background-repeat:no-repeat;"></div>
                <div class="absolute bg-gray-900 w-[90%] h-full z-[-1]" 
                    style="clip-path:polygon(10% 0, 100% 0, 100% 90%, 90% 100%, 0 100%, 0 10%);"></div>
            </div>
        </div>
        {/if}



        <div id="stageHighlighter" class=" stage-highlighter absolute z-[10] {animationStart ? 'animatedHighlighter' : 'invisible'}" 
            style="width:{185 * scaleMultiplier2}px;height:{161 * scaleMultiplier2}px;background-image:url('/img/map/ui_aim.png');background-size:contain;pointer-events:none;background-repeat:no-repeat;bottom:{highlighterBottom - 3}%;left:{highlighterLeft - 2.5}%;"></div>


        <!-- <div class="btn absolute bottom-[8%] left-[4%]" data-gameMapId="1" on:click={() => toggleDropdown(0)}>튜토리얼(열림)</div> -->
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[8%] left-[4%] cursor-pointer" data-gameMapId="1" on:click={() => toggleDropdown(0)} 
            style="background-image: url(/img/map/ui_stage_{clearedgameMapIds.includes(2) ? (isDropdownOpen[0] ? '3' : '2') : (isDropdownOpen[0] ? '3' : '1')}.png); transform:scale(0.67) scale({scaleMultiplier2});transform-origin:bottom left;">
            <div class="stage-text absolute right-[1%] top-[-13px] text-[55px] text-white font-bold" style="">튜토리얼</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-white italic" style="">TUTORIAL</div>
        </div>
        {#if isDropdownOpen[0]}
            <div class="absolute right-[0] top-[0] z-[98]" style="transform-origin:top right;transform:scale({scaleMultiplier})">
                <TutorialSelector2 activeTransitionAnimation={activeTransitionAnimation}/>
            </div>
        {/if}
        {#if isOpen(3)} <!--step 의 easy, 1레벨 맵 아이디-->
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[40%] left-[20%] cursor-pointer" on:click={() => toggleDropdown(1)} data-gameMapId="3"
            style="background-image: url(/img/map/ui_stage_{clearedgameMapIds.includes(5) ? (isDropdownOpen[1] ? '3' : '2') : (isDropdownOpen[1] ? '3' : '1')}.png); transform:scale(0.67) scale({scaleMultiplier2});transform-origin:bottom left;">
            <div class="stage-text absolute right-[1%] top-[-13px] text-[55px] text-white font-bold" style="">1 - 1</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-white italic" style="">ONE - ONE</div>
        </div>
        <!-- <div class="btn absolute bottom-[16%] left-[14%] w-[6vw]" data-gameMapId="3" on:click={() => toggleDropdown(1)}>1-1(열림)</div> -->
            {#if isDropdownOpen[1]}
            <!-- gameMapId : step 의 easy, 1레벨 맵아이디, stepsLevelCount : step 의 level 갯수, -->
                <DifficultySelector2 widthValue={widthValue} scaleMultiplier={scaleMultiplier} gameMapId={3} stepsLevelCount={3} playerLogList={playerLogList} 
                difficultySelectorMsg={difficultySelectorMsgs[0]} difficultySelectorName={difficultySelectorNames[0]} activeTransitionAnimation={activeTransitionAnimation} />
            {/if}
        {:else}
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[40%] left-[20%] cursor-pointer"
            style="background-image:url('/img/map/ui_stage_0.png');transform:scale(0.67) scale({scaleMultiplier2});transform-origin:bottom left;">
            <div class="stage-text absolute right-[7%] top-[-13px] text-[55px] text-gray-400 font-bold" style=""><i class="fa-solid fa-lock text-[30px] mr-4"></i>1 - 1</div>
            <div class="stage-text inE absolute right-[7%] top-[33%] text-[25px] text-gray-400 italic" style="">ONE - ONE</div>
        </div>
        {/if}

        {#if isOpen(12)}
        <!-- <div class="btn absolute bottom-[8%] left-[24%] w-[6vw]" data-gameMapId="12" on:click={() => toggleDropdown(2)}>1-2(열림)</div> -->
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[10%] left-[35%] cursor-pointer" on:click={() => toggleDropdown(2)} data-gameMapId="12"
            style="background-image: url(/img/map/ui_stage_{clearedgameMapIds.includes(14) ? (isDropdownOpen[2] ? '3' : '2') : (isDropdownOpen[2] ? '3' : '1')}.png); transform:scale(0.67) scale({scaleMultiplier2});transform-origin:bottom left;">            
            <div class="stage-text absolute right-[7%] top-[-13px] text-[55px] text-white font-bold" style="">1 - 2</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-white italic" style="">ONE - TWO</div>
        </div>
            {#if isDropdownOpen[2]}
                <DifficultySelector2 widthValue={widthValue} scaleMultiplier={scaleMultiplier} gameMapId={12} stepsLevelCount={3} playerLogList={playerLogList} 
                difficultySelectorMsg={difficultySelectorMsgs[1]} difficultySelectorName={difficultySelectorNames[1]} activeTransitionAnimation={activeTransitionAnimation} />
            {/if}
        {:else}
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[10%] left-[35%] cursor-pointer"
            style="background-image:url('/img/map/ui_stage_0.png');transform:scale(0.67) scale({scaleMultiplier2});transform-origin:bottom left;">
            <div class="stage-text absolute right-[7%] top-[-13px] text-[55px] text-gray-400 font-bold" style=""><i class="fa-solid fa-lock text-[30px] mr-4"></i>1 - 2</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-gray-400 italic" style="">ONE - TWO</div>
        </div>
        {/if}

        {#if isOpen(21)}
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[45%] left-[52%] cursor-pointer" on:click={() => toggleDropdown(3)} data-gameMapId="21"
            style="background-image: url(/img/map/ui_stage_{clearedgameMapIds.includes(23) ? (isDropdownOpen[3] ? '3' : '2') : (isDropdownOpen[3] ? '3' : '1')}.png); transform:scale(0.67) scale({scaleMultiplier2});transform-origin:bottom left;">            
            <div class="stage-text absolute right-[7%] top-[-13px] text-[55px] text-white font-bold" style="">1 - 3</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-white italic" style="">ONE - THREE</div>
        </div>
        <!-- <div class="btn absolute bottom-[16%] left-[34%] w-[6vw]" data-gameMapId="21" on:click={() => toggleDropdown(3)}>1-3(열림)</div> -->
            {#if isDropdownOpen[3]}
                <DifficultySelector2 widthValue={widthValue} scaleMultiplier={scaleMultiplier} gameMapId={21} stepsLevelCount={3} playerLogList={playerLogList} 
                difficultySelectorMsg={difficultySelectorMsgs[2]} difficultySelectorName={difficultySelectorNames[2]} activeTransitionAnimation={activeTransitionAnimation}/>
            {/if}
        {:else}
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[40%] left-[48%] cursor-pointer"
            style="background-image:url('/img/map/ui_stage_0.png');transform:scale(0.67) scale({scaleMultiplier2});transform-origin:bottom left;">
            <div class="stage-text absolute right-[7%] top-[-13px] text-[55px] text-gray-400 font-bold" style=""><i class="fa-solid fa-lock text-[30px] mr-4"></i>1 - 3</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-gray-400 italic" style="">ONE - THREE</div>
        </div>
        {/if}
    </div>
</div>

{#if isTransitioning}  
    <TransitioningCloseLayer />
{/if}

