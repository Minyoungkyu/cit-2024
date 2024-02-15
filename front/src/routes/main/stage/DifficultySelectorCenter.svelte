<script lang="ts">
    let difficulties = ['쉬움', '보통', '어려움'];
    let currentIndex = 0;

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
</script>
<ul tabindex="0" class="dropdown-content z-[1] menu p-2 shadow bg-base-100 rounded-box right-[-210%]">
    <div class="flex flex-col gap-2 items-center border-2 border-black p-2">
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
        <div class="btn btn-accent btn-wide">시작</div>
    </div>
</ul>
