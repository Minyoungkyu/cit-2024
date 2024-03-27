<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";

    let dropdown: any;

    onMount(() => {
        dropdown = document.querySelector('.dropdown-content'); 
    });
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

<ul tabindex="0" class="dropdown-content z-[1] menu bg-white absolute left-[0]">
    <div class="flex flex-col gap-2 items-center p-2">
        <div class="border-2 border-black w-full h-20 flex justify-center items-center">
            <span>스테이지 정보</span>
        </div>
        <div class="flex flex-row gap-4">
        </div>
        <button class="btn btn-accent btn-wide" on:click={() => window.location.href = '/game/tutorial/1'}>시작</button>
    </div>
</ul>
