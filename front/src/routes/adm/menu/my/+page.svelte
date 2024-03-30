<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";

    // onMount(() => {
    //     if(!rq.verifiedPassword) {
    //         rq.goTo('/adm/menu/checkPass');
    //     }
    // });
</script>

<div>
    <div class="flex flex-col gap-4 w-[40vh]">
        <label class="input input-bordered flex items-center gap-2">
            <i class="fa-regular fa-address-card"></i>
            <div class="grow"></div>
        </label>
        <label class="input input-bordered flex items-center gap-2">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4 opacity-70"><path fill-rule="evenodd" d="M14 6a4 4 0 0 1-4.899 3.899l-1.955 1.955a.5.5 0 0 1-.353.146H5v1.5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1-.5-.5v-2.293a.5.5 0 0 1 .146-.353l3.955-3.955A4 4 0 1 1 14 6Zm-4-2a.75.75 0 0 0 0 1.5.5.5 0 0 1 .5.5.75.75 0 0 0 1.5 0 2 2 0 0 0-2-2Z" clip-rule="evenodd" /></svg>
            <input type="password" class="grow" value="password" />
        </label>
        <label class="input input-bordered flex items-center gap-2">
            <i class="fa-solid fa-user"></i>
            <div class="grow"></div>
        </label>
        <label class="input input-bordered flex items-center gap-2">
            <i class="fa-solid fa-user-lock"></i>
            <div class="grow"></div>
        </label>
        <label class="input input-bordered flex items-center gap-2">
            <i class="fa-solid fa-business-time"></i>
            <div class="grow"></div>
        </label>
        <label class="input input-bordered flex items-center gap-2">
            <i class="fa-solid fa-mobile-screen"></i>
            <div class="grow"></div>
        </label>
        <label class="input input-bordered flex items-center gap-2">
            <i class="fa-solid fa-building-user"></i>
            <div class="grow"></div>
        </label>
        <label class="input input-bordered flex items-center gap-2">
            <i class="fa-solid fa-sitemap"></i>
            <div class="grow"></div>
        </label>
        <label class="input input-bordered flex items-center gap-2">
            <i class="fa-solid fa-phone"></i><span class="text-sm ml-2">내선번호</span>
            <div class="grow text-right">www</div>
        </label>


        
    </div>
</div>