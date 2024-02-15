<script lang="ts">
	import rq from '$lib/rq/rq.svelte';
	import { onMount } from 'svelte';
	import '../app.pcss';
	import "tailwindcss/tailwind.css";

	const { children } = $props();

	onMount(() => {
		rq.initAuth();
	});
</script>

<!-- svelte-ignore a11y-click-events-have-key-events -->
<!-- svelte-ignore a11y-no-static-element-interactions -->
<main class="flex-1 flex flex-col">{@render children()}</main>