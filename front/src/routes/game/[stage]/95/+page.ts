import type { Load } from '@sveltejs/kit';
import rq from '$lib/rq/rq.svelte';
import { redirect } from '@sveltejs/kit';

export const load: Load = async ({ params, fetch }) => {
  try {
    const { data } = await rq.apiEndPointsWithFetch(fetch).GET(`/api/v1/gameMaps/gameMap/{stage}/{id}`, {
      params: {
        path: {
          stage: '3',
          id: 95
        }
      }
    });

    if (!data) {
      throw new Error();
    }

  } catch (e) {
    const errorMessage = 'rejected';
    const timestamp = Date.now();
    const combinedParam = `${errorMessage}_${timestamp}`;
    const encodedParam = btoa(combinedParam); // Base64 인코딩

    throw redirect(302, `/game/3?err=${encodedParam}`);
  }
};
