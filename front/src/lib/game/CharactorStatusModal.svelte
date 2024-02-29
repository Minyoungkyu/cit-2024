<script lang="ts">
    import rq from '$lib/rq/rq.svelte';

    let { charactorStatusModal, closeCharacterModal, difficultiesGameMapId, stepsLevelCount } 
        = $props<{ charactorStatusModal: HTMLDialogElement, closeCharacterModal: () => void, difficultiesGameMapId: number, stepsLevelCount: number }>();

    async function routePlayerToLastGame() {
        const { data } = await rq.apiEndPointsWithFetch(fetch).GET(`/api/v1/playerLogs/gamesLastLog/{gameMapId}`, {
            params: {
                path: {
                    gameMapId: difficultiesGameMapId
                }
            }
            });

            const playerLogDtoList = data!.data.playerLogDtoList;

            if (playerLogDtoList.length === 0) {
                console.log("가야할곳 : " + difficultiesGameMapId);
            } else {
                for (let log of playerLogDtoList) {
                    if (log.logType === 'STAGECLEAR') {
                        if (log.detailInt === 1) {
                            if (log.gameMapId + 1 > difficultiesGameMapId - 1 + stepsLevelCount) {
                                console.log("가야할곳 : " + difficultiesGameMapId);
                            } else {
                                console.log("가야할곳 : " + (log.gameMapId + 1));
                            }
                        } else if (log.detailInt === 0) {
                            console.log("가야할곳 : " + log.gameMapId);
                        }
                    }
                }
            }
        }
</script>

<style>
    .modal[open] {
      opacity: 0;
      animation: dashboardActivation 1s forwards;
    }
    
      /* Data Loading Animation */
      @keyframes dashboardActivation {
      0% { opacity: 0; transform: scale(0.95); }
      100% { opacity: 1; transform: scale(1); }
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
                            <div class="border-2 w-[44px] h-[44px]">템사진</div>
                            <div class="border-2 w-[44px] h-[44px]">템사진</div>
                            <div class="border-2 w-[44px] h-[44px]">템사진</div>
                        </div>
                        <div class="border-2 w-[215px] h-[368px]">
                        </div>
                        <div class="flex flex-col">
                            <div class="border-2 w-[44px] h-[44px]">템사진</div>
                            <div class="border-2 w-[44px] h-[44px]">템사진</div>
                            <div class="border-2 w-[44px] h-[44px]">템사진</div>
                        </div>
                    </div>
                </div>
                <div class="flex flex-col gap-4">
                    <div class="flex justify-center items-start border-2 border-black w-[236px] h-[417px]">
                        <div class="grid grid-cols-3 gap-2">
                            <div class="flex flex-col items-center h-[76px]">
                                <div class="border-2 w-[56px] h-[56px]">템사진</div>
                                <div class="border-2 w-[56px] h-[20px] text-xs text-center cursor-pointer bg-orange-200">장착</div>
                            </div>
                            <div class="flex flex-col items-center h-[76px]">
                                <div class="border-2 w-[56px] h-[56px]">템사진</div>
                                <div class="border-2 w-[56px] h-[20px] text-xs text-center cursor-pointer bg-orange-200">장착</div>
                            </div>
                            <div class="flex flex-col items-center h-[76px]">
                                <div class="border-2 w-[56px] h-[56px]">템사진</div>
                                <div class="border-2 w-[56px] h-[20px] text-xs text-center cursor-pointer bg-orange-200">장착</div>
                            </div>
                            <div class="flex flex-col items-center h-[76px]">
                                <div class="border-2 w-[56px] h-[56px]">템사진</div>
                                <div class="border-2 w-[56px] h-[20px] text-xs text-center cursor-pointer bg-orange-200">장착</div>
                            </div>
                            <div class="flex flex-col items-center h-[76px]">
                                <div class="border-2 w-[56px] h-[56px]">템사진</div>
                                <div class="border-2 w-[56px] h-[20px] text-xs text-center cursor-pointer bg-orange-200">장착</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="flex flex-col itmes-between h-full">
                    <div class="flex flex-col items-center border-2 border-black w-[350px] h-[429px]">
                        <div class="flex justify-center items-center border-2 border-black w-[90%] h-1/3">
                            <span>아이템 이미지</span>
                        </div>
                        <div class="flex flex-col justify-center items-center border-2 border-black w-[90%] h-full">
                            <span>아이템 설명</span>
                            <span>아이템 설명</span>
                            <span>아이템 설명</span>
                            <span>아이템 설명</span>
                            <span>아이템 설명</span>
                            <span>아이템 설명</span>
                            <span>아이템 설명</span>
                            <span>아이템 설명</span>
                            <span>아이템 설명</span>
                            <span>아이템 설명</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="flex flex-row gap-8">
                <div class="w-[330px]"></div>
                <div class="flex justify-center w-[236px]">
                    <a class="btn btn-accent w-full" on:click={routePlayerToLastGame}>시작</a>
                </div>
                <div class="flex justify-center w-[350px]">
                    <button class="btn btn-accent w-full">장착</button>
                </div>
            </div>
        </div>
    </div>
    </dialog>