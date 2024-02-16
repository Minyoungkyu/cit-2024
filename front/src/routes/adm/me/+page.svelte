<svelte:head>
    <title>{rq.SITE_NAME} 관리자 | 계정정보</title>
</svelte:head>


<script lang="ts">

    import rq from '$lib/rq/rq.svelte';
    import SideMenu from '../main/sideMenu.svelte'; 
    import CheckPassword from '../checkPassword.svelte';
</script>

<div class="flex flex-row justify-center">
    <SideMenu />
    <div class="flex flex-col items-center justify-center p-8 w-screen h-screen">
        <div class="flex items-center justify-center w-full">
            <div class="max-w-2xl w-full px-4">
                <h1 class="mb-4">
                    <i class="fa-solid fa-user"></i>
                    마이페이지
                </h1>
        
                <div class="card bg-base-100 shadow-xl">
        
                    <div class="card-body">
                        <div class="flex flex-row justify-between">
                            <span class="text-gray-400 text-bold">기본정보</span>
                        </div>
                        <div class="flex flex-col gap-2">
                            <span class="text-xl font-bold">{rq.member.name}</span>
                            <div>
                                <span>{rq.member.username}</span>
                            </div>
                            <div><i class="fa-solid fa-mobile-screen-button"></i><span class="ml-4">{rq.member.cellphoneNo}</span></div>
                        </div>
                        <div class="divider"></div> 
                        <span class="text-gray-400 text-bold">권한정보</span>
                        {#each rq.getAuthToString() as authority}
                            <div>{authority}</div>
                        {/each}
                    </div>
                </div>
        
                <div class="mt-6">
                </div>
            </div>
        </div>
    </div>
    
</div>



