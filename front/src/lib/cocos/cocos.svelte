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

  <link rel="stylesheet" type="text/css" href="/web-desktop/style-desktop.css"/>
  <link rel="icon" href="/web-desktop/favicon.ico"/>
</svelte:head>

<script lang="ts">
  import { onMount } from 'svelte';

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
    try {
      await loadScript('/web-desktop/src/settings.js', 'settings-script');

      const debug: boolean = (window as any)._CCSettings?.debug ?? false;
      await loadScript(debug ? '/web-desktop/cocos2d-js.js' : '/web-desktop/cocos2d-js-min.js', 'cocos-script');

      await loadScript('/web-desktop/main.js', 'main-script');

      if (typeof (window as any).boot === 'function') {
        (window as any).boot();
      }
    } catch (error) {
      console.error('Failed to load scripts', error);
    }
  });
</script>

<h1 class="header">Code</h1>

<div id="GameDiv" style="width:1280px; height: 720px;">
  <canvas id="GameCanvas" width="1280" height="720"></canvas>
  <div id="splash">
    <div class="progress-bar stripes">
      <span style="width: 0%"></span>
    </div>
  </div>
</div>

<p class="footer">Made with <a href="https://www.cocos.com/products#CocosCreator" title="cocos creator">Cocos Creator</a></p>