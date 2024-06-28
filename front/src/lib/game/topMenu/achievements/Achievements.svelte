<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import type { components } from "$lib/types/api/v1/schema";

    // 도전과제, 업적 컴포넌트

    let { scaleMultiplier, resolution, openShop, openEncyclopedia } = $props<{ scaleMultiplier:number, resolution:number, openShop:() => void, openEncyclopedia:() => void }>();
    let adjustScale = $state(0);

    let achievements: components['schemas']['AchievementDto'][] = $state([]);

    $effect(() => {
        if(resolution >= 1.666) {
            adjustScale = scaleMultiplier + scaleMultiplier*0.25;
        } else {
            adjustScale = scaleMultiplier;
        }
    });

    async function load() {
        const { data } = await rq.apiEndPoints().GET('/api/v1/achievements', {
        });

        achievements = data!.data.achievementDtoList;
        achievements.sort((a, b) => {
            if (a.isAchieved === 1 && a.getReward === 0 && (b.isAchieved !== 1 || b.getReward !== 0)) {
                return -1; 
            }
            if (a.isAchieved === 1 && a.getReward === 1 && !(b.isAchieved === 1 && b.getReward === 1)) {
                return 1; 
            }
            if (a.isAchieved === 0 && b.isAchieved !== 0) {
                if (b.isAchieved === 1 && b.getReward === 1) {
                    return -1; 
                } else {
                    return 1; 
                }
            }
            return 0; 
        });
    }

    async function getReward(achievement: components['schemas']['AchievementDto']) {

        if (achievement.getReward === 1) {
            return;
        }
        
        rq.member.player.exp += achievement.rewardExp;
        rq.member.player.gems += achievement.rewardJewel;

        const { data } = await rq.apiEndPoints().PUT('/api/v1/players/getReward', {
            body: {
                achievement: achievement
            }
        });
        
        if (data) {
            if (data.data.profileInventoryDto) {
                rq.profileInventories.add(data.data.profileInventoryDto);
            }
        }

        load()
    }

    function formatDate(dateString: string) {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0'); 
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}/${month}/${day}`;
    }

    function canRoute(description:string) {
        return !description.includes('캐릭터 레벨');
    }

    function routeToAchieve(description:string) {
        if (description.startsWith('상점에서')) return  openShop();
        if (description.startsWith('코드도감')) return  openEncyclopedia();
        if (description.startsWith('1-') || description.startsWith('1스테이지') || description.startsWith('튜토리얼')) return window.location.replace('/game/1');
        if (description.startsWith('2-') || description.startsWith('2스테이지')) return window.location.replace('/game/2');
        if (description.startsWith('3-') || description.startsWith('3스테이지') || description.startsWith('우주해적') || description.startsWith('보스')) return window.location.replace('/game/3');
    }
</script>

<div class="flex justify-center items-end mt-[10%] relative" style="transform:scale(0.8) scale({adjustScale});pointer-events:auto;">
    <div class="w-[1442px] h-[819px] flex flex-col items-center relative" style="background-image:url('/img/setting/ui_option_bg.png');">
        {#await load()}
        {:then}
        <div class="flex justify-start w-full">
            <div class="text-[30px] italic m-5 ml-10" style="color:rgb(28 211 216);">
                클리어한 도전과제 {achievements!.filter(achievement => achievement.isAchieved).length}/{achievements.length}
            </div>
        </div>

        <div class="w-[98%] h-[17px]" style="background-image:url('/img/setting/ui_option_window.png');"></div>

        <div class="w-[96%] h-[76%] mt-5 grid grid-col gap-4 overflow-auto">
            {#each achievements as achievement}
            <div class="w-[1320px] h-[126px] flex flex-row" 
                style="background-image:url(/img/achievements/frame_challenge_{achievement.isAchieved === 1 ? 'clear':'n'}.png);">
                <div class="w-[70%] h-full flex flex-col">
                    <div class="text-[30px] font-[500] italic text-white ml-10 mt-4 mb-1">{achievement.name}</div>
                    <div class="text-[30px] font-[500] italic ml-32" 
                        style="color:rgb({achievement.isAchieved === 1 ? '255 210 87':'64 226 255'});">{achievement.description}</div>
                </div>
                <div class="w-[30%] h-full relative">
                    {#if achievement.createDate} 
                    <div class="text-right mr-5 mt-4 italic" 
                        style="color:rgb({achievement.isAchieved === 1 ? '255 210 87':'64 226 255'});">{formatDate(achievement.createDate)}</div>
                    {:else}
                    <div class="text-right mr-5 mt-4 italic" 
                        style="color:rgb({achievement.isAchieved === 1 ? '255 210 87':'64 226 255'});">미달성</div>
                    {/if}
                    <div class="flex flex-row gap-2 mr-2 justify-end">
                        <div class="w-[64px] h-[75px] flex justify-center items-center" 
                            style="background-image:url('/img/shop/ui_itemframe{achievement.isAchieved === 1 ? '2':''}.png');background-size:contain;background-position:bottom;background-repeat:no-repeat;">

                            {#if achievement.getReward === 1}
                            <div class="w-[64px] h-[75px] absolute" style="background-image:url('/img/achievements/ui_itemframe_check.png');background-repeat:no-repeat;background-position:bottom;background-size:contain;"></div>
                            {/if}

                            <div class="w-[59.2px] h-[66px] mt-[15px] ml-[1px] text-center leading-[120px] text-white font-bold italic text-[18px]" 
                                style="background-image:url('/img/inGame/clearPop/icon_gem.png');background-size:contain;background-repeat:no-repeat;">
                                <div class="text-border leading-[102px] pr-[4px]">{achievement.rewardJewel}</div>
                            </div>
                        </div>

                        <!-- <div class="h-[75px] w-[218px]" style="background-image:url('/img/achievements/btn_action3.png');background-size:contain;backgorund-repeat:no-repeat;"></div> -->
                        <!-- <div class="w-[75px] h-[75px]" 
                            style="background-image:url('/img/shop/ui_itemframe{achievement.isAchieved === 1 ? '2':''}.png');background-size:contain;">

                            {#if achievement.getReward === 1}
                            <div class="w-[75px] h-[75px] absolute" style="background-image:url('/img/achievements/ui_itemframe_check.png');"></div>
                            {/if}

                            <div class="w-[75px] h-[75px] flex justify-center items-center">
                                <div class="w-[74.4px] h-[34.8px] mt-[-8px] text-center leading-[90px] text-white font-bold italic text-[20px]" 
                                   style="background-image:url('/img/inGame/clearPop/icon_exp.png');background-size:contain;background-repeat:no-repeat;">
                                   <div class="text-border">{achievement.rewardExp}</div>
                                </div>
                           </div>  
                        </div> -->
                        {#if achievement.rewardIcon}
                        <div class="w-[64px] h-[75px]" 
                            style="background-image:url('/img/shop/ui_itemframe{achievement.isAchieved === 1 ? '2':''}.png');background-size:contain;background-repeat:no-repeat;background-position:bottom;">

                            {#if achievement.getReward === 1}
                            <div class="w-[64px] h-[75px] absolute" style="background-image:url('/img/achievements/ui_itemframe_check.png');background-size:contain;background-repeat:no-repeat;background-position:bottom;"></div>
                            {/if}

                            <div class="w-[64px] h-[75px] flex justify-center items-center" 
                                style="background-image:url('/img/icon/{achievement.rewardIcon.sourcePath}.png');background-size:contain;background-repeat:no-repeat;background-position:bottom;">
                           </div>  

                        </div>
                        {/if}

                        {#if achievement.isAchieved === 1}
                        <div class="w-[195px] h-[75px] flex justify-center items-center text-[35px] font-bold italic pr-4 text-black cursor-pointer" 
                            style="background-image:url('/img/achievements/btn_{achievement.getReward === 1 ? '4' : '3'}.png');
                                background-size:contain;background-repeat:no-repeat;background-position:bottom;{achievement.getReward === 1 ?'pointer-events:none;':''}"
                            on:click={() => getReward(achievement)}>
                            <div class="mt-[15px]">
                                받기
                            </div>
                        </div>
                        {:else}
                        <div class="w-[195px] h-[75px] flex {canRoute(achievement.description) ? 'justify-start pl-5 cursor-pointer' : 'justify-center pr-4'} items-center text-[35px] font-bold italic text-black" 
                            style="background-image:url('/img/achievements/btn_{canRoute(achievement.description) ? '2' : '1'}.png');
                                background-size:contain;background-repeat:no-repeat;background-position:bottom;{canRoute(achievement.description) ?'':'pointer-events:none;'}"
                            on:click={() => routeToAchieve(achievement.description)}
                                >
                            <div class="mt-[15px]">
                                {#if canRoute(achievement.description)}
                                    바로가기
                                {:else}
                                    받기
                                {/if}
                            </div>
                        </div>
                        {/if}

                    </div>
                </div>
            </div>
            {/each}

        </div>
        {/await}
    </div>
</div>

<style>
.text-border {
    color: white;
    font-weight: bold;
    font-style: italic;
    font-size: 20px;
    text-shadow:
        -2px -2px 0 #000,
        2px -2px 0 #000,
        -2px 2px 0 #000,
        2px 2px 0 #000;
}
</style>