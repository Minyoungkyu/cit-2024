<script lang="ts">
    let { scaleMultiplier, resolution } = $props<{ scaleMultiplier:number, resolution:number }>();
    let adjustScale = $state(0);

    $effect(() => {
        if(resolution >= 1.666) {
            adjustScale = scaleMultiplier + scaleMultiplier*0.25;
        } else {
            adjustScale = scaleMultiplier;
        }
    });

    let items = Array.from({ length: 35 }, (_, i) => i + 1);
</script>

<div class="flex justify-center items-end mt-[10%] relative" style="transform:scale(0.8) scale({adjustScale});pointer-events:auto;">
    <div class="w-[1442px] h-[819px] flex flex-row items-center relative" style="background-image:url('/img/setting/ui_option_bg.png');">

        <div class="w-[35%] h-full flex flex-col gap-4 items-center">
            <div class="mt-6 relative">
                <div class="w-[480px] h-[480px] flex items-center justify-center">
                    <div class="w-[260px] h-[260px]" style="background-image:url('/img/icon/img_icon1.png');background-size:contain;"></div>
                    <div class="w-[480px] h-[480px] absolute top-0 left-0" style="background-image:url('/img/ranking/icon_frame_1.png');background-size:contain;"></div>
                </div>
            </div>
            <div class="w-[404.8px] h-[107.2px] flex items-center justify-center" style="background-image:url('/img/map/ui_user_inf_off.png');background-size:contain;">
                <div class="flex flex-row justify-between w-full px-14">
                    <div class="text-[40px] front-bold italic" style="color:rgb(64 226 255);">Lv 123</div>
                    <div class="text-[40px] front-bold italic" style="color:rgb(64 226 255);">홍길금</div>
                </div>
            </div>
            <div class="w-[404.8px] h-[107.2px] flex items-center justify-center" style="background-image:url('/img/map/ui_user_inf_off.png');background-size:contain;">
                <div class="text-[40px] front-bold italic" style="color:rgb(64 226 255);">내 순위 &nbsp; 1위</div>
            </div>
        </div>

        <div class="w-[10px] h-[757px]" style="background-image:url('/img/ranking/ui_itme_window6.png');"></div>

        <div class="w-[60%] h-[94%] grid grid-col gap-6 justify-center overflow-y-scroll pt-10 content-start">
            {#each items as item, index}
            {#if index === 0}
            <div class="w-[785px] h-[126px] flex flex-row" style="background-image:url('/img/ranking/frame_Rank_g.png');">
                <div class="w-[110px] h-full text-[50px] font-[500] italic leading-[100px] ml-4" style="color:rgb(255 210 87)">1위</div>
                <div class="w-[170px] h-[170px] mt-[-22px] flex items-center justify-center relative">
                    <div class="w-[90px] h-[90px] absolute" style="background-image:url('/img/icon/img_icon1.png');background-size:contain;"></div>
                    <div class="w-[180px] h-[180px] absolute" style="background-image:url('/img/ranking/icon_frame_1.png');background-size:contain;"></div>
                </div>
                <div class="h-full ml-10 text-[40px] font-[500] italic leading-[125px]" style="color:rgb(255 210 87);">홍길금</div>
            </div>
            {:else if index === 1}
            <div class="w-[785px] h-[126px] flex flex-row" style="background-image:url('/img/ranking/frame_Rank_s.png');">
                <div class="w-[110px] h-full text-[50px] font-[500] italic leading-[100px] ml-4" style="color:rgb(180 235 255)">2위</div>
                <div class="w-[170px] h-[170px] mt-[-22px] flex items-center justify-center relative">
                    <div class="w-[90px] h-[90px] absolute" style="background-image:url('/img/icon/img_icon1.png');background-size:contain;"></div>
                    <div class="w-[180px] h-[180px] absolute" style="background-image:url('/img/ranking/icon_frame_2.png');background-size:contain;"></div>
                </div>
                <div class="h-full ml-10 text-[40px] font-[500] italic leading-[125px]" style="color:rgb(180 235 255);">홍길은</div>
            </div>
            {:else if index === 2}
            <div class="w-[785px] h-[126px] flex flex-row" style="background-image:url('/img/ranking/frame_Rank_b.png');">
                <div class="w-[110px] h-full text-[50px] font-[500] italic leading-[100px] ml-4" style="color:rgb(161 125 91)">3위</div>
                <div class="w-[170px] h-[170px] mt-[-22px] flex items-center justify-center relative">
                    <div class="w-[90px] h-[90px] absolute" style="background-image:url('/img/icon/img_icon1.png');background-size:contain;"></div>
                    <div class="w-[180px] h-[180px] absolute" style="background-image:url('/img/ranking/icon_frame_3.png');background-size:contain;"></div>
                </div>
                <div class="h-full ml-10 text-[40px] font-[500] italic leading-[125px]" style="color:rgb(161 125 91);">홍길동</div>
            </div>
            {:else}
            <div class="w-[785px] h-[126px] flex flex-row" style="background-image:url('/img/ranking/frame_Rank_n.png');">
                <div class="w-[110px] h-full text-[50px] font-[500] italic leading-[100px] ml-4" style="color:rgb(64 226 255)">{index + 1}위</div>
                <div class="w-[170px] h-[170px] mt-[-22px] flex items-center justify-center relative">
                    <div class="w-[90px] h-[90px] absolute" style="background-image:url('/img/icon/img_icon1.png');background-size:contain;"></div>
                    <div class="w-[170px] h-[170px] absolute" style="background-image:url('/img/ranking/icon_frame_n.png');background-size:contain;"></div>
                </div>
                <div class="h-full ml-10 text-[40px] font-[500] italic leading-[125px]" style="color:rgb(64 226 255);">홍길순{index}</div>
            </div>
            {/if}
            {/each}


        </div>
    
    </div>
</div>

