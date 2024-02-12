<script lang="ts">
	import rq from '$lib/rq/rq.svelte';
	import '../app.pcss';

	const { children } = $props();
</script>

<!-- svelte-ignore a11y-click-events-have-key-events -->
<!-- svelte-ignore a11y-no-static-element-interactions -->
<header onclick={() => rq.msgInfo('헤더 클릭됨')}>헤더</header>
<main class="flex-1 flex flex-col">{@render children()}</main>
<footer>푸터</footer>