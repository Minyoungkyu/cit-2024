import type { Load } from '@sveltejs/kit';
import rq from '$lib/rq/rq.svelte';

export const load: Load = async ({ params, fetch }) => {
  try {
    const { data } = await rq.apiEndPointsWithFetch(fetch).GET(`/api/v1/gameMaps/gameMap/test/{gameInfo}`, {
      params: {
        path: {
          gameInfo: params.stageInfo!,
        }
      }
    });

    return {
      gameMapDto: data!.data.gameMapDto
    };

  } catch (e) {
    // throw redirect(302, '/error') 뭐 이런게 가능함
  }
};