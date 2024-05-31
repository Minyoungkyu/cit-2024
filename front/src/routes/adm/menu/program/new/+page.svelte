<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';

    let regions = $state([]) as components['schemas']['Region'][];
    let filteredRegions = $state([]) as components['schemas']['Region'][];
    let ads = $state([]) as components['schemas']['AdministrativeDistrict'][];
    let filteredAds = $state([]) as components['schemas']['AdministrativeDistrict'][];
    let schools = $state([]) as components['schemas']['SchoolInputListDto'][];
    let filteredSchools = $state([]) as components['schemas']['SchoolInputListDto'][];
    let members = $state([]) as components['schemas']['MemberInputListDto'][];
    let filteredMembers = $state([]) as components['schemas']['MemberInputListDto'][];

    let regionsBox: HTMLDivElement | null = null;
    let adsBox: HTMLDivElement | null = null;
    let schoolsBox: HTMLDivElement | null = null;
    let membersBox: HTMLDivElement | null = null;

    let focusRegion = $state(false);
    let focusAd = $state(false);
    let focusAgency = $state(false);
    let focusMember = $state(false);

    let regionInput = $state('');
    let adInput = $state('');
    let agencyInput = $state([]) as components['schemas']['SchoolInputListDto'][];
    let agencyInputText = $state('');
    let memberInput = $state([]) as components['schemas']['MemberInputListDto'][];
    let memberInputText = $state('');

    let duplicateChecked = $state(false);

    async function loadRegion() {
        if (regions.length > 0) {
            focusRegion = true;
            return;
        }

        const { data } = await rq.apiEndPoints().GET('/api/v1/regions', {
        });

        regions = data?.data.regions || [];
        filteredRegions = regions;
        focusRegion = true;
    }

    async function loadAd() {
        if (!isValidRegionInput()) return

        const { data } = await rq.apiEndPointsWithFetch(fetch).GET('/api/v1/ads', {
            params: {
                query: {
                    regionCode: regions.find(region => region.name === regionInput)?.code
                }
            }
        });

        ads = data?.data.ads || [];
        filteredAds = ads;
        focusAd = true;
    }

    async function loadSchool() {
        if (schools.length > 0) {
            focusAgency = true;
            return;
        }

        const { data } = await rq.apiEndPoints().GET('/api/v1/schools/input', {
        });

        schools = data?.data.schools || [];
        filteredSchools = schools;
        focusAgency = true;
    }

    async function loadMember() {
        if (members.length > 0) {
            focusMember = true;
            return;
        }

        const { data } = await rq.apiEndPoints().GET('/api/v1/members/program', {
        });

        members = data?.data.members || [];
        filteredMembers = members;
        focusMember = true;
    }

    function isValidRegionInput(): boolean {
        return regions.some(region => region.name === regionInput);
    }

    function updateRegions(searchText: string) {
        const searchLower = searchText.toLowerCase();
        filteredRegions = [...regions].sort((a, b) => {
            const scoreA = similarityScore(a.name ?? '', searchLower);
            const scoreB = similarityScore(b.name ?? '', searchLower);
            return scoreB - scoreA; 
        });

        if (regionsBox) regionsBox.scrollTop = 0;
    }

    function updateSchools(searchText: string) {
        const searchLower = searchText.toLowerCase();
        filteredSchools = [...schools].sort((a, b) => {
            const scoreA = similarityScore(a.schoolName ?? '', searchLower);
            const scoreB = similarityScore(b.schoolName ?? '', searchLower);
            return scoreB - scoreA; 
        });

        if (schoolsBox) schoolsBox.scrollTop = 0;
    }

    function updateMembers(searchText: string) {
        const searchLower = searchText.toLowerCase();
        filteredMembers = [...members].sort((a, b) => {
            const scoreA = similarityScore(a.name ?? '', searchLower);
            const scoreB = similarityScore(b.name ?? '', searchLower);
            return scoreB - scoreA; 
        });

        if (membersBox) membersBox.scrollTop = 0;
    }

    function updateAds(searchText: string) {
        const searchLower = searchText.toLowerCase();
        filteredAds = [...ads].sort((a, b) => {
            const scoreA = similarityScore(a.name ?? '', searchLower);
            const scoreB = similarityScore(b.name ?? '', searchLower);
            return scoreB - scoreA; 
        });

        if (adsBox) adsBox.scrollTop = 0;
    }

    function similarityScore(regionName: string, searchText: string): number {
        const nameLower = regionName.toLowerCase();
        if (nameLower.startsWith(searchText)) return 100; 
        if (nameLower.includes(searchText)) return searchText.length; 
        return 0; 
    }


    async function submitCreateProgramForm(this: HTMLFormElement) {
        const form: HTMLFormElement = this;

        if (form.programName.value.trim().length === 0) {
            rq.msgError('사업명을 입력해주세요.');
            form.programName.focus();
            return;
        }

        if (!duplicateChecked) {
            rq.msgError('사업명 중복확인을 해주세요.');
            return;
        }

        if (form.startDate.value === '' || form.endDate.value === '') {
            rq.msgError('사업기간을 입력해주세요.');
            return;
        }

        // if (regionInput.trim().length === 0) {
        //     rq.msgError('지역을 입력해주세요.');
        //     form.region.focus();
        //     return;
        // }

        // if (adInput.trim().length === 0) {
        //     rq.msgError('행정구를 입력해주세요.');
        //     form.ad.focus();
        //     return;
        // }

        // if (agencyInput.length === 0) {
        //     rq.msgError('사용 기관을 입력해주세요.');
        //     form.agency.focus();
        //     return;
        // }

        // if (memberInput.length === 0) {
        //     rq.msgError('담당자를 입력해주세요.');
        //     form.member.focus();
        //     return;
        // }

        const { data, error } = await rq.apiEndPoints().POST('/api/v1/programs/new', {
            body: {
                name: form.programName.value,
                startDate: form.startDate.value,
                endDate: form.endDate.value,
                region: regionInput,
                ad: adInput,
                agency: agencyInput,
                member: memberInput
            }
        });

        if (data?.data) {
            rq.msgAndRedirect(data, undefined, '/adm/menu/program');
        }
    }

    function duplicateCheck() {
        const programName = (document.getElementsByName('programName')[0] as HTMLInputElement).value;
        if (programName.trim().length === 0) {
            rq.msgError('사업명을 입력해주세요.');
            return;
        }
        rq.apiEndPoints().POST('/api/v1/programs/duplicate', {
            body: {
                programName: programName
            }
        }).then(({ data }) => {
            if (data?.resultCode == '200') {
                duplicateChecked = true;
                rq.msgInfo('사용 가능한 사업명입니다.');
            } else {
                duplicateChecked = false;
                rq.msgError('이미 사용중인 사업명입니다.');
            }
        });
    }

</script>

<div class="w-[95%] flex justify-start mt-[-60px] text-[22px] border-b mb-1 pb-[14px] font-bold">
    사업 생성
</div>
<div class="w-[95%] h-screen flex justify-center">
    <form class="flex flex-col gap-4 w-full h-full" method="POST" on:submit|preventDefault={submitCreateProgramForm}>
        <div class="overflow-x-auto h-full">
            <table class="table">
              <tbody>
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">사업명</td>
                  <td class="border-b p-3">
                    <div class="flex flex-row items-center gap-2">
                        <input name="programName" type="text" placeholder="사업명" class="input input-bordered w-[250px]" on:input={()=>{duplicateChecked=false;}} />
                        {#if duplicateChecked}
                            <i class="fa-solid fa-check text-green-500 ml-3"></i><span class="text-green-500">사용가능</span>
                        {/if}
                        {#if !duplicateChecked}
                        <button class="btn btn-sm btn-error btn-outline ml-3" on:click={duplicateCheck} type="button">
                            중복확인
                        </button>
                        {/if}
                    </div>
                  </td>
                </tr>
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">사업기간</td>
                  <td class="border-b p-3">
                    <input name="startDate" type="date" placeholder="사업명" class="input input-bordered w-[200px]" />
                    <span class="text-[20px]">&nbsp; ~ &nbsp;</span>
                    <input name="endDate" type="date" placeholder="사업명" class="input input-bordered w-[200px]" />
                  </td>
                </tr>
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">지역</td>
                  <td class="border-b p-3">
                    <div class="flex flex-row gap-6">
                        <div>
                            <input name="region" type="text" placeholder="시도" class="input input-bordered w-[150px] text-center" 
                                bind:value={regionInput}
                                on:focus={() => loadRegion()}
                                on:input={(event) => event.target && updateRegions((event.target as HTMLInputElement).value)}
                                on:blur={() => setTimeout(() => { focusRegion = false; }, 100)}
                                />
                            {#if focusRegion}
                            <div bind:this={regionsBox} class="w-[150px] h-[200px] mt-[-2px] absolute z-[99] rounded-xl border-2 flex flex-col items-center overflow-y-auto bg-white">
                                {#each filteredRegions as region}
                                    <div class="options w-[80%] text-center p-1 cursor-pointer" 
                                        on:click={() => regionInput = (region.name ?? '')}>
                                        {region.name}
                                    </div>
                                {/each}
                            </div>
                            {/if}
                        </div>
                        <div>
                            {#if isValidRegionInput()}
                            <input name="ad" type="text" placeholder="행정구" class="input input-bordered w-[150px] text-center" 
                                bind:value={adInput}
                                on:focus={() => loadAd()}
                                on:input={(event) => event.target && updateAds((event.target as HTMLInputElement).value)}
                                on:blur={() => setTimeout(() => { focusAd = false; }, 150)}
                                />
                            {#if focusAd}
                            <div bind:this={adsBox} class="w-[150px] h-[200px] mt-[-2px] absolute z-[99] rounded-xl border-2 flex flex-col items-center overflow-y-auto bg-white">
                                {#each filteredAds as ad}
                                    <div class="options w-[80%] text-center p-1 cursor-pointer" 
                                        on:click={() => adInput = (ad.name ?? '')}>
                                        {ad.name}
                                    </div>
                                {/each}
                            </div>
                            {/if}
                            {/if}
                        </div>
                    </div>

                  </td>
                </tr>
                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">사용 기관</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            <div>
                                <input name="agency" type="search" placeholder="사용 기관" class="input input-bordered w-[300px] text-center" 
                                    bind:value={agencyInputText}
                                    on:focus={() => loadSchool()}
                                    on:input={(event) => event.target && updateSchools((event.target as HTMLInputElement).value)}
                                    on:blur={() => setTimeout(() => { focusAgency = false; }, 100)}
                                    />
                                    {#if focusAgency}
                                    <div bind:this={schoolsBox} class="w-[330px] h-[200px] mt-[-2px] absolute z-[99] rounded-xl border-2 flex flex-col items-center overflow-y-auto whitespace-pre-wrap bg-white">
                                        {#each filteredSchools as school}
                                            <div class="options w-[80%] text-center p-1 cursor-pointer" 
                                            on:click={() => {
                                                if (!agencyInput.some(a => a.id === school.id)) {
                                                    agencyInput.push(school);
                                                }}}>
                                                {school.schoolName} ({school.region} / {school.administrativeDistrict})
                                            </div>
                                        {/each}
                                    </div>
                                    {/if}
                            </div>
                            {#each agencyInput as agency}
                                <div class="flex flex-row gap-2 text-[15px] ml-4 mt-2">
                                    <div class="w-full text-left">
                                        {agency.schoolName} ({agency.region} / {agency.administrativeDistrict})
                                        <span class="ml-2 cursor-pointer" 
                                        on:click={() => agencyInput.splice(agencyInput.indexOf(agency), 1)}>
                                            <i class="fa-regular fa-trash-can text-red-500"></i>
                                        </span>
                                    </div>
                                </div>
                            {/each}
                        </div>
                    </td>
                  </tr>
                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">담당자</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            <div>
                                <input name="member" type="search" placeholder="담당자" class="input input-bordered w-[200px] text-center" 
                                    bind:value={memberInputText}
                                    on:focus={() => loadMember()}
                                    on:input={(event) => event.target && updateMembers((event.target as HTMLInputElement).value)}
                                    on:blur={() => setTimeout(() => { focusMember = false; }, 100)}
                                    />
                                    {#if focusMember}
                                    <div bind:this={membersBox} class="w-[200px] h-[200px] mt-[-2px] absolute z-[99] rounded-xl border-2 flex flex-col items-center overflow-y-auto whitespace-pre-wrap bg-white">
                                        {#each filteredMembers as member}
                                            <div class="options w-[80%] text-center p-1 cursor-pointer" 
                                            on:click={() => {
                                                if (!memberInput.some(m => m.id === member.id)) {
                                                    memberInput.push(member);
                                                }}}>
                                                {member.name} ({member.username})
                                            </div>
                                        {/each}
                                    </div>
                                    {/if}
                            </div>
                            {#each memberInput as member}
                                <div class="flex flex-row gap-2 text-[15px] ml-4 mt-2">
                                    <div class="w-full text-left">
                                        {member.name} ({member.username})
                                        <span class="ml-2 cursor-pointer" 
                                        on:click={() => memberInput.splice(memberInput.indexOf(member), 1)}>
                                            <i class="fa-regular fa-trash-can text-red-500"></i>
                                        </span>
                                    </div>
                                </div>
                            {/each}
                        </div>
                    </td>
                  </tr>
              </tbody>
            </table>

            <div class="flex flex-row mt-10 mb-10 justify-center gap-2">
                <button class="btn btn-block btn-outline border-gray-300 gap-1 w-[100px]" type="button" on:click={() => rq.goTo('/adm/menu/program')}>
                    <span>목록</span>
                </button>
                <button class="btn btn-block btn-success btn-outline gap-1 w-[100px]" type="submit">
                    <span>저장</span>
                </button>
            </div>
          </div>
    </form>
</div>

<style>
    .options:hover {
        border-bottom: 2px solid gray;
    }
</style>