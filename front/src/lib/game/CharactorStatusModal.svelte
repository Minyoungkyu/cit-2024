<script lang="ts">
    import rq from '$lib/rq/rq.svelte';
	import type { components } from '$lib/types/api/v1/schema';
    
    let { charactorStatusModal, closeCharacterModal, gameMapDto, requiredPartsList } 
    = $props<{ charactorStatusModal: HTMLDialogElement, closeCharacterModal: () => void, 
        gameMapDto: components['schemas']['GameMapDto'] | undefined, requiredPartsList: components['schemas']['RequirePartsDto'][] | undefined }>();

    // 라우팅
    let stage = $state<string | undefined>(undefined);
    let id = $state<number | undefined>(undefined);

    $effect(() => {
            stage = gameMapDto?.stage;
            id = gameMapDto?.id;
        });
        
    let currentItem: components['schemas']['InventoryDto'] | undefined = $state(); // 우측 아이템 정보창에 띄울 아이탬

    let shoes = $state<components['schemas']['InventoryDto'] | undefined>(undefined);
    let gloves = $state<components['schemas']['InventoryDto'] | undefined>(undefined);
    let suit = $state<components['schemas']['InventoryDto'] | undefined>(undefined);
    let helmet = $state<components['schemas']['InventoryDto'] | undefined>(undefined);
    let weapon = $state<components['schemas']['InventoryDto'] | undefined>(undefined);
    let module = $state<components['schemas']['InventoryDto'] | undefined>(undefined);

    $effect(() => {
        shoes = rq.inventories.findEquippedByItemPartsId(1);
        gloves = rq.inventories.findEquippedByItemPartsId(2);
        suit = rq.inventories.findEquippedByItemPartsId(3);
        helmet = rq.inventories.findEquippedByItemPartsId(4);
        weapon = rq.inventories.findEquippedByItemPartsId(5);
        module = rq.inventories.findEquippedByItemPartsId(6);
    });

    $effect(() => {
        checkPartsAndHighlighting();
    });

    let startHighlighterHidden = $state(false); // 시작 하이라이터
    let highlighterHidden = $state(true); // 하이라이터 
    let highlighterLeftValue = $state(0); // 하이라이터
    let highlighterTopValue = $state(0); // 하이라이터

    function checkPartsAndHighlighting() { // 필수 장비부위 체크, 하이라이터 조절 
        if (!requiredPartsList) return;
        const highlighter = document.getElementById('itemHighlighter');
        const startHighlighter = document.getElementById('startHighlighter');
        let divElement: HTMLDivElement | undefined;

        for (let part of requiredPartsList) {
            const equippedItems = rq.inventories.all.filter(item => item.isEquipped && item.item.itemPartsId === part.itemPartsId);
            const unequippedItems = rq.inventories.all.filter(item => !item.isEquipped && item.item.itemPartsId === part.itemPartsId);

            if (equippedItems.length === 0 && unequippedItems.length > 0) {
                const divs = document.querySelectorAll(`div[data-partsid='${part.itemPartsId}']`);
                if (divs.length > 0) {
                    divElement = divs[divs.length-1] as HTMLDivElement; // divs[] -> 화살표로 몇번째 아이탬을 지정해줄지

                    startHighlighterHidden = true;
                    highlighterHidden = false;
                    highlighterTopValue = divElement.offsetTop;
                    highlighterLeftValue = divElement.offsetLeft;
                    break; 
                }
            } else { // 요구장비 다 장착했을때.
                startHighlighterHidden = false;
                highlighterHidden = true;
            }
        }
    }

    function equipBtnClick(inventory: components['schemas']['InventoryDto']) {
        rq.equipItem(inventory.id);
        currentItem = inventory;
    }

    function onClickStart() {
        window.location.href = '/game/' + stage + '/' + id;
    }
</script>

<style>
    .modal[open] {
      opacity: 0;
      animation: dashboardActivation 1s forwards;
    }
    
    @keyframes dashboardActivation {
      0% { opacity: 0; transform: scale(0.95); }
      100% { opacity: 1; transform: scale(1); }
    }

    @keyframes Xbounce {
        0%, 20%, 50%, 80%, 100% {
            transform: translateX(0);
        }
        60% {
            transform: translateX(30px);
        }
    }

    @keyframes Ybounce {
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

    @keyframes shrinkAndMoveFromRight {
        from {
            transform: scale(5) translate(50%, 100%);
            opacity: 0;
        }
        to {
            transform: scale(1) translate(0, 0);
            opacity: 1;
        }
    }

    @keyframes shrinkAndMoveFromTop {
        from {
            transform: scale(5) translate(50%, -100%);
            opacity: 0;
        }
        to {
            transform: scale(1) translate(0, 0);
            opacity: 1;
        }
    }

    .animatedItemHighlighter {
        animation: shrinkAndMoveFromRight 1s forwards, Xbounce 2s infinite 1s;
    }

    .animatedHighlighter {
        animation: shrinkAndMoveFromTop 1s forwards, Ybounce 2s infinite 1s;
    }
</style>

<dialog bind:this={charactorStatusModal} class="modal">
    <div class="border-2 w-[60%] h-[60%] absolute top-0">
        <div class="flex flex-col justify-end items-center h-full bg-white">
            <div>
                <button class="btn btn-sm" on:click={closeCharacterModal}>닫기</button>
            </div>
            <div class="flex flex-row gap-8">
                <div class="flex flex-col gap-4"> 
                    <div class="border-2 flex flex-row justify-center border-black w-[330px] h-[450px]">
                        <div class="flex flex-col justify-start">
                            {#if helmet}  
                            <div class="border-2 w-[56px] h-[56px] cursor-pointer {currentItem?.id == helmet.id ? 'bg-blue-200' : '' }" 
                                on:dblclick={() => rq.unEquipItem(helmet?.id)}
                                on:click={() => currentItem = helmet}>
                                {helmet?.item.sourcePath}</div>
                            {:else}
                            <div class="border-2 w-[56px] h-[56px]">부위사진</div>
                            {/if}
                            {#if gloves}  
                            <div class="border-2 w-[56px] h-[56px] cursor-pointer {currentItem?.id == gloves.id ? 'bg-blue-200' : '' }" 
                                on:dblclick={() => rq.unEquipItem(gloves?.id)}
                                on:click={() => currentItem = gloves}>
                                {gloves?.item.sourcePath}</div>
                            {:else}
                            <div class="border-2 w-[56px] h-[56px]">부위사진</div>
                            {/if}
                            {#if shoes}  
                            <div class="border-2 w-[56px] h-[56px] cursor-pointer {currentItem?.id == shoes.id ? 'bg-blue-200' : '' }" 
                                on:dblclick={() => rq.unEquipItem(shoes?.id)}
                                on:click={() => currentItem = shoes}>
                                {shoes?.item.sourcePath}</div>
                            {:else}
                            <div class="border-2 w-[56px] h-[56px]">부위사진</div>
                            {/if}
                        </div>
                        <div class="border-2 w-[215px] h-[368px]">
                        </div>
                        <div class="flex flex-col">
                            {#if module}  
                            <div class="border-2 w-[56px] h-[56px] cursor-pointer {currentItem?.id == module.id ? 'bg-blue-200' : '' }" 
                                on:dblclick={() => rq.unEquipItem(module?.id)}
                                on:click={() => currentItem = module}>
                                {module?.item.sourcePath}</div>
                            {:else}
                            <div class="border-2 w-[56px] h-[56px]">부위사진</div>
                            {/if}
                            {#if suit}  
                            <div class="border-2 w-[56px] h-[56px] cursor-pointer {currentItem?.id == suit.id ? 'bg-blue-200' : '' }" 
                                on:dblclick={() => rq.unEquipItem(suit?.id)}
                                on:click={() => currentItem = suit}>
                                {suit?.item.sourcePath}</div>
                            {:else}
                            <div class="border-2 w-[56px] h-[56px]">부위사진</div>
                            {/if}
                            {#if weapon}  
                            <div class="border-2 w-[56px] h-[56px] cursor-pointer {currentItem?.id == weapon.id ? 'bg-blue-200' : '' }" 
                                on:dblclick={() => rq.unEquipItem(weapon?.id)}
                                on:click={() => currentItem = weapon}>
                                {weapon?.item.sourcePath}</div>
                            {:else}
                            <div class="border-2 w-[56px] h-[56px]">부위사진</div>
                            {/if}
                        </div>
                    </div>
                </div>
                <div class="flex flex-col gap-4">
                    <div class="flex justify-center items-start border-2 border-black w-[236px] h-[417px]">
                        <div id="itemHighlighter" class="item-highlighter border-2 w-[50px] h-[50px] absolute bg-black z-[10] animatedItemHighlighter {highlighterHidden ? 'hidden' : ''}"
                                style="top: {highlighterTopValue}px; left: {highlighterLeftValue + 60}px;"></div>
                        <div class="grid grid-cols-3 gap-2">
                            {#each rq.inventories.unequipped as inventory (inventory.id)}
                            <div class="flex flex-col items-center h-[76px] {currentItem?.id == inventory.id ? 'bg-blue-200' : '' }" data-partsId={inventory.item.itemPartsId} >
                                <div class="border-2 w-[56px] h-[56px] cursor-pointer" on:dblclick={() => rq.equipItem(inventory.id)}
                                    on:click={() => currentItem = inventory}>{inventory.item.sourcePath}</div>
                                <div class="equipbtn border-2 w-[56px] h-[20px] text-xs text-center cursor-pointer bg-orange-200" on:click={() => equipBtnClick(inventory)}>장착</div>
                            </div>
                            {/each}
                        </div>
                    </div>
                </div>
                <div class="flex flex-col itmes-between h-full">
                    <div class="flex flex-col items-center border-2 border-black w-[350px] h-[429px]">
                        <div class="flex justify-center items-center border-2 border-black w-[90%] h-1/3">
                            {#if currentItem}
                            <span>{currentItem.item.sourcePath}</span>
                            {/if}
                        </div>
                        <div class="flex flex-col justify-center items-center border-2 border-black w-[90%] h-[50px]">
                            {#if currentItem}
                            <span>{currentItem.item.name}</span>
                            {/if}
                        </div>
                        <div class="flex flex-col justify-center items-center border-2 border-black w-[90%] h-full">
                            {#if currentItem}
                            <span>{currentItem.item.description}</span>
                            {/if}
                        </div>
                    </div>
                </div>
            </div>
            <div class="flex flex-row gap-8 relative">
                <div class="w-[330px]"></div>
                <div id="startHighlighter" class="start-highlighter border-2 w-[50px] h-[50px] absolute z-[10] left-[46.5%] top-[-120%] animatedHighlighter {startHighlighterHidden ? 'hidden' : ''}"></div>
                <div class="flex justify-center w-[236px]">
                    <button id="startButton" class="btn btn-accent w-full {startHighlighterHidden ? 'btn-disabled' : ''}" on:click={() => onClickStart()}>시작</button>
                </div>
                <div class="flex justify-center w-[350px]">
                    {#if currentItem}
                        {#if currentItem?.isEquipped}
                        <button class="btn w-full" on:click={() => rq.unEquipItem(currentItem!.id)}>해제</button>
                        {:else}
                        <button class="btn btn-accent w-full" on:click={() => rq.equipItem(currentItem!.id)}>장착</button>
                        {/if}
                    {/if}
                </div>
            </div>
        </div>
    </div>
    </dialog>