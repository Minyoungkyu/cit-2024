<script lang="ts">
	import { onMount } from "svelte";
    import CharactorStatusModal from "./CharactorStatusModal.svelte";
    import type { components } from '$lib/types/api/v1/schema';
	import rq from "$lib/rq/rq.svelte";

    const { gameMapId, stepsLevelCount, playerLogList, difficultySelectorMsg, difficultySelectorName } = 
        $props<{ gameMapId: number, stepsLevelCount: number, playerLogList: components['schemas']['PlayerLogDto'][], difficultySelectorMsg: string, difficultySelectorName: string }>();
    
    let dropdown: any;
    let dropdownVisible = $state(true);

    let routeGameMapDto: components['schemas']['GameMapDto'] | undefined = $state();
    let routeGameRequiredPartsList: components['schemas']['RequirePartsDto'][] | undefined = $state();

    const normalDifficultyThreshold = gameMapId - 1 + stepsLevelCount; // 노말 입장 조건
    const hardDifficultyThreshold = gameMapId - 1 + 2 * stepsLevelCount; // 하드 입장 조건

    const hasClearedNormal = playerLogList.some(log => log.gameMapId === normalDifficultyThreshold);
    const hasClearedHard = playerLogList.some(log => log.gameMapId === hardDifficultyThreshold);

    let difficulties = ['Easy']; // 난이도 선택 배열
    if (hasClearedNormal) {
        difficulties.push('Normal');
    } else {
        difficulties.push('Normal (잠김)')
    }
    if (hasClearedHard) {
        difficulties.push('Hard');
    } else {
        difficulties.push('Hard (잠김)')
    } 

    let difficultiesGameMapId: number[] = [gameMapId, gameMapId + stepsLevelCount, gameMapId + 2 * stepsLevelCount]; // 각 난이도 첫 맵 아이디

    let currentIndex :number = $state(0);

    function increaseDifficulty() { // 드롭다운 난이도 조절 함수
        if (currentIndex < difficulties.length - 1) {
        currentIndex += 1;
        }
    }

    function decreaseDifficulty() { // 드롭다운 난이도 조절 함수
            if (currentIndex > 0) {
            currentIndex -= 1;
            }
    }

    let characterStatusModal: HTMLDialogElement;
    
    function showCharactorStatusModal() {
            dropdownVisible = false; 
            characterStatusModal.showModal(); 
    }

    function closeCharactorStatusModal() {
            dropdownVisible = true;
            characterStatusModal.close(); 
    }

    async function routePlayerToLastGame() {
            const { data } = await rq.apiEndPointsWithFetch(fetch).GET(`/api/v1/playerLogs/gamesLastLog/{gameMapId}`, {
                params: {
                    path: {
                        gameMapId: difficultiesGameMapId[currentIndex]
                    }
                }
                });

                const playerLog = data!.data.playerLogDto;
                
                let routeGameMapId; // 가야할 게임맵 아이디

                if (playerLog == undefined) {
                    routeGameMapId = difficultiesGameMapId[currentIndex];
                } else {
                    if (playerLog.detailInt === 1) {
                        if (playerLog.gameMapId + 1 > difficultiesGameMapId[currentIndex] - 1 + stepsLevelCount) {
                            routeGameMapId = difficultiesGameMapId[currentIndex];
                        } else {
                            routeGameMapId = playerLog.gameMapId + 1;
                        }
                    } else if (playerLog.detailInt === 0) {
                        routeGameMapId = playerLog.gameMapId;
                    }
                }   

                const response = await rq.apiEndPointsWithFetch(fetch).GET(`/api/v1/gameMaps/gameMap/{id}`, {
                    params: {
                        path: {
                            id: routeGameMapId!
                        }
                    }
                });

                routeGameMapDto = response.data!.data.gameMapDto; // 가야할 게임맵 dto
                routeGameRequiredPartsList = response.data!.data.requirePartsDto; // 갸아할 게임 요구 장비부위 리스트
            }

    function onClickToStart() { // 시작 버튼 
        routePlayerToLastGame();
        showCharactorStatusModal();
    }
</script>

<div class="flex flex-col dropdown-content items-center pt-12 gap-12 border-2 h-full w-[450px] absolute right-[0] bg-gray-700 slide-in">
    <div class="flex justify-end w-full mr-16">
        <div class="text-[70px] font-extrabold text-white" style="text-shadow:5px 10px black">{difficultySelectorName}</div>
    </div>
    <div class="flex flex-col w-full">
        <div class="w-full h-8 border-2"></div>
        <div class="border-2 border-black w-full h-[400px] flex justify-center">
            <div class="text-[25px] font-bold text-white mt-12" style="white-space:pre-wrap;">{difficultySelectorMsg}</div>
        </div>
    </div>
    <div class="flex flex-col gap-2 items-center p-2">
        <div class="flex flex-row gap-4">
            <button id="decrease" on:click={decreaseDifficulty} class="w-0 h-0" style="border-bottom:20px solid transparent;border-top: 20px solid transparent;border-left: 20px solid transparent;border-right: 30px solid skyblue;"></button>
            <div id="difficulty" class="border-2 border-black leading-[40px] px-8 w-[170px] text-center font-bold text-white">{difficulties[currentIndex]} {difficultiesGameMapId[currentIndex]}</div>
            <button id="increase" on:click={increaseDifficulty} class="w-0 h-0" style="border-bottom:20px solid transparent;border-top: 20px solid transparent;border-left: 30px solid skyblue;border-right: 20px solid transparent;"></button>
        </div>
    </div>
    {#if difficulties[currentIndex].includes('잠김')}
        <div class="border-2 border-black w-full h-20 flex justify-center items-center">
            <span class="text-white font-bold ">해당 난이도는 클리어 후 해제됩니다.</span>
        </div>
    {:else}    
        <button class="btn btn-accent btn-wide" on:click={onClickToStart}>시작</button> 
    {/if}
</div>

<CharactorStatusModal bind:charactorStatusModal={characterStatusModal} closeCharacterModal={closeCharactorStatusModal} 
                        gameMapDto={routeGameMapDto} requiredPartsList={routeGameRequiredPartsList}  />

<style>
    @keyframes slideIn {
        from {
            transform: translateX(100%); 
            opacity: 0;
        }
        to {
            transform: translateX(0); 
            opacity: 1;
        }
    }

    .slide-in {
        animation: slideIn 0.5s ease-out forwards; 
    }

    @keyframes fancySlideIn {
        0% {
            transform: translateX(100%) rotateY(90deg) scale(0.5);
            opacity: 0;
        }
        50% {
            transform: translateX(-10%) rotateY(-10deg) scale(1.1);
            opacity: 0.5;
        }
        100% {
            transform: translateX(0) rotateY(0deg) scale(1);
            opacity: 1;
        }
    }

    .fancy-slide-in {
        animation: fancySlideIn 1s ease-out forwards;
    }

    @keyframes superFancySlideIn {
        0% {
            transform: translateX(100%) scale(0) rotateZ(360deg);
            opacity: 0;
            filter: blur(10px);
            background-color: rgba(255, 255, 255, 0);
        }
        30% {
            transform: translateX(0) scale(1.2) rotateZ(-30deg);
            opacity: 0.5;
            filter: blur(5px);
            background-color: rgba(255, 255, 255, 0.2);
        }
        60% {
            transform: scale(0.9) rotateZ(15deg);
            opacity: 0.75;
            filter: blur(2px);
            background-color: rgba(255, 255, 255, 0.4);
        }
        100% {
            transform: scale(1) rotateZ(0deg);
            opacity: 1;
            filter: blur(0px);
            background-color: rgba(255, 255, 255, 1);
        }
    }

    .super-fancy-slide-in {
        animation: superFancySlideIn 1.5s cubic-bezier(0.22, 0.61, 0.36, 1) forwards;
    }
</style>