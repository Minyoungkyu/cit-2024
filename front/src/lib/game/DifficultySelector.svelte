<script lang="ts">
	import { onMount } from "svelte";
    import CharactorStatusModal from "./CharactorStatusModal.svelte";
    import type { components } from '$lib/types/api/v1/schema';
	import rq from "$lib/rq/rq.svelte";

    const { gameMapId, stepsLevelCount, playerLogList } = $props<{ gameMapId: number, stepsLevelCount: number, playerLogList: components['schemas']['PlayerLogDto'][] }>();
    
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
                
                let routeGameMapId;

                if (playerLog == undefined) {
                    console.log("가야할곳 : " + difficultiesGameMapId[currentIndex]);
                    routeGameMapId = difficultiesGameMapId[currentIndex];
                } else {
                    if (playerLog.detailInt === 1) {
                        if (playerLog.gameMapId + 1 > difficultiesGameMapId[currentIndex] - 1 + stepsLevelCount) {
                            console.log("가야할곳 : " + difficultiesGameMapId[currentIndex]);
                            routeGameMapId = difficultiesGameMapId[currentIndex];
                        } else {
                            console.log("가야할곳 : " + (playerLog.gameMapId + 1));
                            routeGameMapId = playerLog.gameMapId + 1;
                        }
                    } else if (playerLog.detailInt === 0) {
                        console.log("가야할곳 : " + playerLog.gameMapId);
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

                routeGameMapDto = response.data!.data.gameMapDto;
                routeGameRequiredPartsList = response.data!.data.requirePartsDto;
            }

    function onClickToStart() {
        routePlayerToLastGame();
        showCharactorStatusModal();
    }
</script>

<ul tabindex="0" class:hidden={!dropdownVisible} class="dropdown-content z-[1] menu bg-white">
    <div class="flex flex-col gap-2 items-center p-2">
        <div class="border-2 border-black w-full h-20 flex justify-center items-center">
            <span>스테이지 정보</span>
        </div>
        <div class="flex flex-row gap-4">
            <button id="decrease" on:click={decreaseDifficulty} class="w-0 h-0" style="border-bottom:20px solid transparent;border-top: 20px solid transparent;border-left: 20px solid transparent;border-right: 30px solid skyblue;"></button>
            <div id="difficulty" class="border-2 border-black leading-[40px] px-8 w-[170px] text-center">{difficulties[currentIndex]} {difficultiesGameMapId[currentIndex]}</div>
            <button id="increase" on:click={increaseDifficulty} class="w-0 h-0" style="border-bottom:20px solid transparent;border-top: 20px solid transparent;border-left: 30px solid skyblue;border-right: 20px solid transparent;"></button>
        </div>
        {#if difficulties[currentIndex].includes('잠김')}
            <div class="border-2 border-black w-full h-20 flex justify-center items-center">
                <span>해당 난이도는 클리어 후 해제됩니다.</span>
            </div>
        {:else}    
            <button class="btn btn-accent btn-wide" on:click={onClickToStart}>시작</button>
        {/if}
    </div>
</ul>

<CharactorStatusModal bind:charactorStatusModal={characterStatusModal} closeCharacterModal={closeCharactorStatusModal} 
                        gameMapDto={routeGameMapDto} requiredPartsList={routeGameRequiredPartsList}  />