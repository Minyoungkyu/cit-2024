<svelte:head>
  <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1, minimum-scale=1,maximum-scale=1"/>
  <meta name="apple-mobile-web-app-capable" content="yes"/>
  <meta name="full-screen" content="yes"/>
  <meta name="screen-orientation" content="portrait"/>
  <meta name="x5-fullscreen" content="true"/>
  <meta name="360-fullscreen" content="true"/>
  
  <meta name="renderer" content="webkit"/>
  <meta name="force-rendering" content="webkit"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

  <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
  <meta http-equiv="Pragma" content="no-cache">
  <meta http-equiv="Expires" content="0">

  <link rel="stylesheet" type="text/css" href="/web-desktop/style-desktop.css"/>
  <link rel="icon" href="/web-desktop/favicon.ico"/>
</svelte:head>

<script lang="ts">
  import { onMount, createEventDispatcher  } from 'svelte';
	import type { components } from '$lib/types/api/v1/schema';

  let { gameMapDto, isCoReady } 
    = $props<{ gameMapDto: components['schemas']['GameMapDto'] | undefined, isCoReady:boolean }>();

  const dispatch = createEventDispatcher();

  async function initializeGame() {
    try {
      await loadScript('/web-desktop/src/settings.js', '/web-desktop/settings-script');

      const debug: boolean = (window as any)._CCSettings?.debug ?? false;
      await loadScript(debug ? '/web-desktop/cocos2d-js.js' : '/web-desktop/cocos2d-js-min.js', '/web-desktop/cocos-script');

      await loadScript('/web-desktop/main.js', '/web-desktop/main-script');

      let attempts = 0;

      const interval = setInterval(() => {
        
        if (gameMapDto !== undefined) {
          clearInterval(interval);
          
          const stageString = gameMapDto?.cocosInfo;
          const jsonObjectString = stageString!.trim().substring("stage = ".length);
          const stageObject = JSON.parse(jsonObjectString);
          
          (window as any).SendInitData?.(stageObject);
        } else if (attempts++ > 100) { 
          clearInterval(interval);
        }
      }, 40); 

      if (typeof (window as any).boot === 'function') {
        (window as any).boot();
        isCoReady = true;  // cocos 초기화 추적... 의미가 있는지 모르겠음
        dispatch('ready', { isCoReady }); 
      }

    } catch (error) {
      console.error('Failed to load scripts', error);
    }
  }

  function loadScript(src: string, id: string): Promise<void> {
    return new Promise((resolve, reject) => {
      if (document.getElementById(id)) {
        resolve();
        return;
      }

      const script: HTMLScriptElement = document.createElement('script');
      script.id = id;
      script.src = src;
      script.async = true;
      script.onload = () => resolve();
      script.onerror = () => reject(new Error(`Failed to load script ${src}`));
      document.body.appendChild(script);
    });
  }


  onMount(async () => {
    initializeGame();
  });

</script>

<h1 class="header">Code</h1>

<canvas id="GameCanvas" width="1280" height="720"></canvas>
<div id="splash">
  <div class="progress-bar stripes">
    <span style="width: 0%"></span>
  </div>
</div>

<p class="footer">Made with <a href="https://www.cocos.com/products#CocosCreator" title="cocos creator">Cocos Creator</a></p>