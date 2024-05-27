<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';

    const { data } = $props<{ data: { schoolDto: components['schemas']['SchoolDto'] } }>();
    const { schoolDto } = data;

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
    // let schoolsBox: HTMLDivElement | null = null;
    // let membersBox: HTMLDivElement | null = null;

    let focusRegion = $state(false);
    let focusAd = $state(false);
    // let focusAgency = $state(false);
    // let focusMember = $state(false);

    let regionInput = $state(schoolDto.region);
    let adInput = $state(schoolDto.administrativeDistrict);
    // let agencyInput = $state(schoolDto.schoolsNames) as components['schemas']['SchoolInputListDto'][];
    // let agencyInputText = $state('');
    // let memberInput = $state(schoolDto.responsibleMemberNames) as components['schemas']['MemberInputListDto'][];
    // let memberInputText = $state('');

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

    // async function loadSchool() {
    //     if (schools.length > 0) {
    //         focusAgency = true;
    //         return;
    //     }

    //     const { data } = await rq.apiEndPoints().GET('/api/v1/schools', {
    //     });

    //     schools = data?.data.schools || [];
    //     filteredSchools = schools;
    //     focusAgency = true;
    // }

    // async function loadMember() {
    //     if (members.length > 0) {
    //         focusMember = true;
    //         return;
    //     }

    //     const { data } = await rq.apiEndPoints().GET('/api/v1/members/school', {
    //     });

    //     members = data?.data.members || [];
    //     filteredMembers = members;
    //     focusMember = true;
    // }

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

    // function updateSchools(searchText: string) {
    //     const searchLower = searchText.toLowerCase();
    //     filteredSchools = [...schools].sort((a, b) => {
    //         const scoreA = similarityScore(a.schoolName ?? '', searchLower);
    //         const scoreB = similarityScore(b.schoolName ?? '', searchLower);
    //         return scoreB - scoreA; 
    //     });

    //     if (schoolsBox) schoolsBox.scrollTop = 0;
    // }

    // function updateMembers(searchText: string) {
    //     const searchLower = searchText.toLowerCase();
    //     filteredMembers = [...members].sort((a, b) => {
    //         const scoreA = similarityScore(a.name ?? '', searchLower);
    //         const scoreB = similarityScore(b.name ?? '', searchLower);
    //         return scoreB - scoreA; 
    //     });

    //     if (membersBox) membersBox.scrollTop = 0;
    // }

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


    async function submitModifySchoolForm(this: HTMLFormElement) {
        const form: HTMLFormElement = this;

        // if (form.schoolName.value.trim().length === 0) {
        //     rq.msgError('학교명을 입력해주세요.');
        //     form.schoolName.focus();
        //     return;
        // }

        // if (form.startDate.value === '' || form.endDate.value === '') {
        //     rq.msgError('학교기간을 입력해주세요.');
        //     return;
        // }

        if (regionInput.trim().length === 0) {
            rq.msgError('지역을 입력해주세요.');
            form.region.focus();
            return;
        }

        if (adInput.trim().length === 0) {
            rq.msgError('행정구를 입력해주세요.');
            form.ad.focus();
            return;
        }

        const { data, error } = await rq.apiEndPoints().PUT('/api/v1/schools/institute/modify', {
            body: {
                id: schoolDto.id,
                region: regionInput,
                administrativeDistrict: adInput,
                schoolName: form.schoolName.value,
                areaType: form.areaType.value,
                address: form.address.value,
                phoneNumber: form.phoneNumber.value
            }
        });

        if (data?.data) {
            rq.msgAndRedirect(data, undefined, '/adm/menu/institute');
        }
    }
</script>

<div class="w-[95%] flex justify-start mt-[-60px] text-[40px] font-bold border-b mb-10">
    기관 정보
</div>
<div class="w-full h-screen flex justify-center">
    <form class="flex flex-col gap-4 w-[900px] h-full" method="POST" on:submit|preventDefault={submitModifySchoolForm}>
        <div class="overflow-x-auto h-full">
            <table class="table">
              <tbody>
                <tr>
                  <td class="border-2 p-1 text-center font-bold text-[15px] w-[200px]">기관명</td>
                  <td class="border-2 p-1">
                    <input name="schoolName" type="text" placeholder="기관명" class="input input-bordered w-full" value={schoolDto.schoolName}/>
                  </td>
                </tr>
                <tr>
                  <td class="border-2 p-1 text-center font-bold text-[15px] w-[200px]">지역</td>
                  <td class="border-2 p-1">
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
                        </div>
                    </div>

                  </td>
                </tr>
                <tr>
                    <td class="border-2 p-1 text-center font-bold text-[15px] w-[200px]">지역규모</td>
                    <td class="border-2 p-1">
                        <input name="areaType" type="text" placeholder="지역규모" class="input input-bordered w-full" value={schoolDto.areaType}/>
                    </td>
                </tr>
                <tr>
                    <td class="border-2 p-1 text-center font-bold text-[15px] w-[200px]">전화번호</td>
                    <td class="border-2 p-1">
                        <input name="phoneNumber" type="text" placeholder="전화번호" class="input input-bordered w-full" value={schoolDto.phoneNumber}/>
                    </td>
                </tr>
                <tr>
                    <td class="border-2 p-1 text-center font-bold text-[15px] w-[200px]">주소</td>
                    <td class="border-2 p-1">
                        <input name="address" type="text" placeholder="주소" class="input input-bordered w-full" value={schoolDto.address}/>
                    </td>
                </tr>
                

              </tbody>
            </table>

            <div class="flex flex-row mt-40 justify-between gap-2">
                <div class="btn btn-block btn-error gap-1 w-[100px]" on:click={() => rq.goTo('/adm/menu/institute')}>
                    <span>목록</span>
                </div>
                <button class="btn btn-block btn-primary gap-1 w-[100px]" type="submit">
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