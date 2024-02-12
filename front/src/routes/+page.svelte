<script lang="ts">
    import rq from '$lib/rq/rq.svelte';
</script>
  
  <slot />

<h1 class="text-red-500">Welcome to SvelteKit</h1>
<p>Visit <a href="https://kit.svelte.dev">kit.svelte.dev</a> to read the documentation</p>

<button class="btn btn-primary btn-wide" onclick={() => rq.msgInfo('안녕')}>버튼</button>

<p>이것은 CI/CD 테스트입니다.</p>

{#if rq.isLogin()}
  <button class="btn btn-primary btn-wide" onclick={() => rq.msgInfo('로그아웃')}>로그아웃</button>
{:else}
  <button class="btn btn-primary btn-wide" onclick={() => rq.msgInfo('로그인')}>로그인</button>
{/if}
