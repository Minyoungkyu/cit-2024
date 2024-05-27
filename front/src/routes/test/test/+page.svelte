<script lang="ts">
	import { onMount } from 'svelte';
    import { fly } from 'svelte/transition';

    let visible = false;
    
    const order = [7, 14, 3, 10, 6, 13, 1, 11, 2, 15, 5, 9, 4, 12, 8];

    const tipArray = [
        'Tip. 맛만보자 맛만보자 맛만보자 맛만보자 맛만보자1',
        'Tip. 맛만보자 맛만보자 맛만보자 맛만보자 맛만보자2',
        'Tip. 맛만보자 맛만보자 맛만보자 맛만보자 맛만보자3',
        'Tip. 맛만보자 맛만보자 맛만보자 맛만보자 맛만보자4',
        'Tip. 맛만보자 맛만보자 맛만보자 맛만보자 맛만보자5'
    ]

    let currentTip = getRandomTip();
    
    let elements = order.map((id, index) => ({
        id: id,
        delay: index * 50 + 2000
    }));

    onMount(() => {
        visible = true;

        // setTimeout(() => {
        //     visible = false;
        // }, 5000);
    });

    function getRandomTip() {
        return tipArray[Math.floor(Math.random() * tipArray.length)];
    }

    function changeTip() {
        let newTip;
        do {
            newTip = getRandomTip();
        } while (newTip === currentTip);
        currentTip = newTip;
    }

    function handleOutroEnd(id: number) {
        elements = elements.filter(el => el.id !== id);
    }

    function handleOutroStart(elementId: number, elementDelay: number) {
    setTimeout(() => {
      document.getElementById(`element-${elementId}`)!.classList.add('rounded-3xl');
    }, elementDelay + 100); 
  }
</script>

<div class="overLayout charactorStatus w-screen h-screen absolute bg-white overflow-hidden z-[99] flex flex-col items-center justify-end" on:click={changeTip}>
    {#if visible}
    <div 
        in:fly="{{ x: -5000, duration: 2000 }}" 
        out:fly="{{ x: 5000, duration: 2000 }}"
        class="w-full h-full absolute z-[99]" 
        style="background-image:url('/img/setting/ani.gif');background-size:contain;background-repeat:no-repeat;transform:scale(0.3);">
    </div>
    <div 
        out:fly={{ duration: 2000 }}
        class="loader z-[99] mb-[15vh] ml-[4vw] w-full text-center">
    </div>
    <div 
        out:fly={{ duration: 2000 }}
        class="z-[99] text-[2vw] text-white mb-[8vh]">
        {currentTip}
    </div>
    {/if}

    {#each elements as element (element.id)}
        {#if visible}
            <div
                out:fly={{ x: 5000, duration: 500, delay: element.delay }}
                on:outroend={() => handleOutroEnd(element.id)}
                on:outrostart={() => handleOutroStart(element.id, element.delay)}
                id={`element-${element.id}`}
                class="w-full h-[6.7vh] absolute bg-black element"
                style="bottom: {(element.id - 1) * 6.7}vh;">
            </div>
        {/if}
    {/each}
</div>

<style>
/* 
    .element {
        transition: border-radius 2s;
    } */
    /* HTML: <div class="loader"></div> */
    .loader {
      width: fit-content;
      font-weight: bold;
      font-family: monospace;
      font-size: 3vw;
      clip-path: inset(0 3ch 0 0);
      animation: l4 1s steps(4) infinite;
      color:#fff;
    }
    .loader:before {
      content:"Loading..."
    }
    @keyframes l4 {to{clip-path: inset(0 -1ch 0 0)}}
    </style>