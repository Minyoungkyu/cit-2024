<script lang="ts">
	import { onMount } from "svelte";
    import CharactorStatusModal from "./CharactorStatusModal.svelte";
    import type { components } from '$lib/types/api/v1/schema';

    const { gameMapId, stepsLevelCount, playerLogList } = $props<{ gameMapId: number, stepsLevelCount: number, playerLogList: components['schemas']['PlayerLogDto'][] }>();
    
    let dropdown: any;

    const normalDifficultyThreshold = gameMapId - 1 + stepsLevelCount;
    const hardDifficultyThreshold = gameMapId - 1 + 2 * stepsLevelCount;

    const hasClearedNormal = playerLogList.some(log => log.gameMapId === normalDifficultyThreshold);
    const hasClearedHard = playerLogList.some(log => log.gameMapId === hardDifficultyThreshold);

    let difficulties = ['Easy'];
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

    let difficultiesGameMapId: number[] = [gameMapId, gameMapId + stepsLevelCount, gameMapId + 2 * stepsLevelCount];

    onMount(() => {
        dropdown = document.querySelector('.dropdown-content'); 
    });

    let currentIndex :number = $state(0);

    function increaseDifficulty() {
        if (currentIndex < difficulties.length - 1) {
        currentIndex += 1;
        }
    }

  function decreaseDifficulty() {
        if (currentIndex > 0) {
        currentIndex -= 1;
        }
  }

  let characterStatusModal: HTMLDialogElement;
  
  function showCharactorStatusModal() {
        dropdown.classList.add('hidden');
        characterStatusModal.showModal(); 
  }

  function closeCharactorStatusModal() {
        dropdown.classList.remove('hidden');
        characterStatusModal.close(); 
  }
</script>

<ul tabindex="0" class="dropdown-content z-[1] menu bg-white">
    <div class="flex flex-col gap-2 items-center p-2">
        <div class="border-2 border-black w-full h-20 flex justify-center items-center">
            <span>스테이지 정보</span>
        </div>
        <div class="flex flex-row gap-4">
            <button id="decrease" on:click={decreaseDifficulty} class="w-0 h-0" style="border-bottom:20px solid transparent;border-top: 20px solid transparent;border-left: 20px solid transparent;border-right: 30px solid skyblue;"></button>
            <div id="difficulty" class="border-2 border-black leading-[40px] px-8 w-[170px] text-center">{difficulties[currentIndex]}</div>
            <button id="increase" on:click={increaseDifficulty} class="w-0 h-0" style="border-bottom:20px solid transparent;border-top: 20px solid transparent;border-left: 30px solid skyblue;border-right: 20px solid transparent;"></button>
        </div>
        {#if difficulties[currentIndex].includes('잠김')}
            <div class="border-2 border-black w-full h-20 flex justify-center items-center">
                <span>해당 난이도는 클리어 후 해제됩니다.</span>
            </div>
        {:else}    
            <button class="btn btn-accent btn-wide" on:click={showCharactorStatusModal}>시작</button>
        {/if}
    </div>
</ul>

<CharactorStatusModal bind:charactorStatusModal={characterStatusModal} closeCharacterModal={closeCharactorStatusModal} 
                        difficultiesGameMapId={difficultiesGameMapId[currentIndex]} stepsLevelCount={stepsLevelCount} />