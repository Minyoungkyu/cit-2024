<script lang="ts">
	import { onMount } from "svelte";


    let difficulties = ['쉬움', '보통', '어려움'];
    let currentIndex = 0;
    let dropdown: any;

    onMount(() => {
        dropdown = document.querySelector('.dropdown-content'); 
    });
    
    // 난이도 증가 함수
    function increaseDifficulty() {
        // 현재 인덱스가 배열의 마지막 요소보다 작은 경우에만 인덱스를 1 증가
        if (currentIndex < difficulties.length - 1) {
        currentIndex += 1;
        }
    }

  // 난이도를 감소시키는 함수
  function decreaseDifficulty() {
        // 현재 인덱스가 0보다 큰 경우에만 인덱스를 1 감소
        if (currentIndex > 0) {
        currentIndex -= 1;
        }
  }

  let characterStatusModal: HTMLDialogElement; // 타입스크립트를 사용하여 modal 변수의 타입을 지정합니다.
  
  function showCharactorStatusModal() {
        dropdown.classList.add('hidden');
        characterStatusModal.showModal(); // 모달을 보여주는 함수
  }

  function closeCharactorStatusModal() {
        dropdown.classList.remove('hidden');
        characterStatusModal.close(); // 모달을 닫는 함수
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

<ul tabindex="0" class="dropdown-content z-[1] menu bg-white">
    <div class="flex flex-col gap-2 items-center p-2">
        <div class="border-2 border-black w-full h-20 flex justify-center items-center">
            <span>스테이지 정보</span>
        </div>
        <div class="flex flex-row gap-4">
            <button id="decrease" on:click={decreaseDifficulty} class="w-0 h-0" style="border-bottom:20px solid transparent;border-top: 20px solid transparent;border-left: 20px solid transparent;border-right: 30px solid skyblue;"></button>
            <div id="difficulty" class="border-2 border-black leading-[40px] px-8 w-[150px] text-center">
                {difficulties[currentIndex]}
            </div>
            <button id="increase" on:click={increaseDifficulty} class="w-0 h-0" style="border-bottom:20px solid transparent;border-top: 20px solid transparent;border-left: 30px solid skyblue;border-right: 20px solid transparent;"></button>
        </div>
        <button class="btn btn-accent btn-wide" on:click={showCharactorStatusModal}>시작</button>
    </div>
</ul>

<dialog bind:this={characterStatusModal} class="modal">
    <div class="border-2 w-[60%] h-[60%] absolute top-0">
        <div class="flex flex-col justify-end items-center h-full bg-white">
            <div>
                <button class="btn btn-sm" on:click={closeCharactorStatusModal}>닫기</button>
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
                    <a class="btn btn-accent w-full" href="/game/play">시작</a>
                </div>
                <div class="flex justify-center w-[350px]">
                    <button class="btn btn-accent w-full">장착</button>
                </div>
            </div>
        </div>
    </div>
    </dialog>