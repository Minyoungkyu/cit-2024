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
    let agencyInput: components['schemas']['SchoolInputListDto'] | 'NONE' = $state('NONE');
    let agencyInputText = $state('');
    let memberInput = $state([]) as components['schemas']['MemberInputListDto'][];
    let memberInputText = $state('');

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

    loadSchool();
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


        const agencyInput = schools.find(school => school.id == form.agency.value);
        // if agencyInput is not found
        if (!agencyInput) {
            rq.msgError('기관을 선택해주세요.');
            return;
        }

        // if isSpecial is checked
        if((document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked){
            if (form.specialName.value.trim().length === 0) {
                rq.msgError('특수반명을 입력해주세요.');
                return;
            }
        } else {
            //if grade is null
            if (form.grade.value.trim().length === 0) {
                rq.msgError('학년을 입력해주세요.');
                return;
            }
            // if grade is not number
            if (isNaN(form.grade.value)) {
                rq.msgError('학년은 숫자로 입력해주세요.');
                return;
            }

            //if classNo is null
            if (form.classNo.value.trim().length === 0) {
                rq.msgError('반을 입력해주세요.');
                return;
            }
            // if classNo is not number
            if (isNaN(form.classNo.value)) {
                rq.msgError('반은 숫자로 입력해주세요.');
                return;
            }
        }

        // if member is null
        if (memberInput.length === 0) {
            rq.msgError('담당자를 입력해주세요.');
            return;
        }
        
        // rq.msgError('done.');
        // return;

        // console.log(agencyInput, form.grade.value, form.classNo.value, (document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked, form.specialName.value, memberInput);
        

        const { data, error } = await rq.apiEndPoints().POST('/api/v1/school/class/new', {
            body: {
                agency: agencyInput,
                grade: form.grade.value,
                classNo: form.classNo.value,
                isSpecial: (document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked,
                specialName: form.specialName.value,
                member: memberInput
            }
        });

        if (data?.data.resultCode == 1) {
            rq.msgAndRedirect(data, undefined, '/adm/menu/schoolClass');
        } else {
            rq.msgError(data?.msg??'오류가 발생했습니다.');
        }
    }

    function isSpecialClick(){
        // if isSpecial is checked
        if((document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked){
            (document.getElementById('normalClass') as HTMLDivElement).style.display = 'none';
            (document.getElementById('specialClass') as HTMLDivElement).style.display = 'block';
        } else {
            (document.getElementById('normalClass') as HTMLDivElement).style.display = 'block';
            (document.getElementById('specialClass') as HTMLDivElement).style.display = 'none';
        }
    
    }

</script>

<div class="w-[95%] flex justify-start mt-[-60px] text-[40px] font-bold border-b mb-10">
    학급 개별 생성
</div>
<div class="w-full h-screen flex justify-center">
    <form class="flex flex-col gap-4 w-[900px] h-full" method="POST" on:submit|preventDefault={submitCreateProgramForm}>
        <div class="overflow-x-auto h-full">
            <table class="table">
              <tbody>
                
                <tr>
                    <td class="border-2 p-1 text-center font-bold text-[15px] w-[200px]">기관</td>
                    <td class="border-2 p-1">
                        <div class="flex flex-col">
                            <div>
                                <select name="agency" class="ml-3 p-2" >
                                    <option value="NONE">선택</option>
                                    {#each schools as school}
                                        <option value={school.id}>{school.schoolName} ({school.region} / {school.administrativeDistrict})</option>
                                    {/each}
                                  </select>
                                
                            </div>
                        </div>
                    </td>
                  </tr>
                  <tr>
                    <td class="border-2 p-1 text-center font-bold text-[15px] w-[200px]">학급명</td>
                    <td class="border-2 p-1">
                        <div id="normalClass">
                        <input name="grade" type="text" placeholder="학년" class="input input-bordered w-[200px] text-center" />
                        <input name="classNo" type="text" placeholder="반" class="input input-bordered w-[200px] text-center" />
                    </div>
                    <div id="specialClass" style="display: none;">
                        <input name="specialName" type="text" placeholder="특수반명" class="input input-bordered w-[200px] text-center" />
                        </div>
                        <input name="isSpecial" type="checkbox" class="checkbox checkbox-sm" on:click={()=>isSpecialClick()}/>
                        <label for="isSpecial">특수반</label>
                    </td>
                  </tr>
                <tr>
                    <td class="border-2 p-1 text-center font-bold text-[15px] w-[200px]">담당자</td>
                    <td class="border-2 p-1">
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
                                            <i class="fa-solid fa-x"></i>
                                        </span>
                                    </div>
                                </div>
                            {/each}
                        </div>
                    </td>
                  </tr>
              </tbody>
            </table>

            <div class="flex flex-row mt-40 justify-between gap-2">
                <div class="btn btn-block btn-error gap-1 w-[100px]" on:click={() => rq.goTo('/adm/menu/schoolClass')}>
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