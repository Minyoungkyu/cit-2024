<script lang="ts">
    import type { components } from '$lib/types/api/v1/schema';

    const { data } = $props<{ data: { progressRateList: components['schemas']['ProgramProgressDto'][] } }>();
    const { progressRateList } = data;

</script>

<div class="w-[95%] flex justify-start mt-[-60px] text-[40px] font-bold border-b mb-10">
    대시보드
</div>

<div class="w-[95%] flex flex-col items-center">
    {#each progressRateList as progressRate}
    {#if progressRate.schoolProgressDtoList}
    <div class="w-[80%] flex flex-col justify-start items-start">
        <div class="mr-5 text-left text-[30px] font-bold">{progressRate.programName}</div>
        <div class="w-full">
            <progress class="progress progress-primary h-[15px]" 
                value={progressRate.schoolProgressDtoList
                    .reduce((sum, dto) => sum + dto.clearRate!, 0)} 
                max={progressRate.schoolProgressDtoList
                    .reduce((sum, dto) => sum + dto.studentCount!, 0) * 94}></progress>
        </div>
        <div class="w-full flex justify-end">
            <div class="text-[15px]">{progressRate.startDate} ~ {progressRate.endDate}</div>
        </div>
        <div class="w-full flex flex-col items-end">
            {#if progressRate.schoolProgressDtoList}
                {#each progressRate.schoolProgressDtoList as schoolProgressRate}
                    {#if schoolProgressRate.id != 0}
                    <div class="w-[80%] flex flex-col justify-start items-start">
                        <div class="mr-5 text-left text-[30px]">{schoolProgressRate.schoolName}</div>
                        <div class="w-full">
                            <progress class="progress progress-error h-[15px]" 
                                value={schoolProgressRate.clearRate!} 
                                max={schoolProgressRate.studentCount! * 94}></progress>
                        </div>
                    </div>
                    {/if}
                {/each}
            {/if}
        </div>
    </div>
    {/if}
    {/each}
</div>

<!-- <div class="w-[95%] flex flex-col items-center">
    <div class="w-[80%] flex flex-col justify-start items-start">
        <div class="mr-5 text-left text-[30px] font-bold">무슨무슨 사업</div>
        <div class="w-full">
            <progress class="progress progress-primary h-[15px]" value="60" max="100"></progress>
        </div>
        <div class="w-full flex justify-end">
            <div class="text-[15px]">2024-05-30 ~ 2024-05-30</div>
        </div>
        <div class="w-full flex flex-col items-end">
            <div class="w-[80%] flex flex-col justify-start items-start">
                <div class="mr-5 text-left text-[30px]">무슨무슨 학교</div>
                <div class="w-full">
                    <progress class="progress progress-error h-[15px]" value="60" max="100"></progress>
                </div>
            </div>
        </div>
    </div>
</div> -->