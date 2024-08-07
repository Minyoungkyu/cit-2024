import type { Load } from '@sveltejs/kit';
import rq from '$lib/rq/rq.svelte';

export const load: Load = async ({ fetch }) => {
    const { data } = await rq.apiEndPointsWithFetch(fetch).GET(`/api/v1/members/adm/me`, {
    });

    return {
      memberDto: data!.data.item
    };
};