<script lang="ts">
    import rq from '$lib/rq/rq.svelte';
</script>
  
  <slot />

<h1 class="text-red-500">Welcome to SvelteKit</h1>
<p>Visit <a href="https://kit.svelte.dev">kit.svelte.dev</a> to read the documentation</p>

<button class="btn btn-primary btn-wide" onclick={() => rq.msgInfo('안녕')}>버튼</button>

<p>이것은 CI/CD 테스트입니다.</p>

<div>이름: {rq.member.name}</div>
<div>권한: {rq.getAuthToString()}</div>
<div>번호: {rq.member.cellphoneNo}</div>

{#if rq.isLogin()}
    <button class="btn btn-primary btn-wide" onclick={() => rq.logoutAndRedirect('/')}>로그아웃</button>
{:else}
    <a href="/member/login" class="btn btn-primary btn-wide"><i class="fa-solid fa-right-to-bracket"></i>로그인</a>
{/if}
