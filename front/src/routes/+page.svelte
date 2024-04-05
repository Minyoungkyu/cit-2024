<svelte:head>
    <title>{rq.SITE_NAME}</title>
</svelte:head>

<script lang="ts">
    import rq from '$lib/rq/rq.svelte';
    import { onMount } from 'svelte';

    const originalHeight = 1080;
    let currentHeight = $state(1080);
    let scaleMultiplier = $state(1);
    let video: HTMLVideoElement;
    let showBgThumb = $state(true);

    onMount(() => {

    video = document.getElementById('backgroundVideo') as HTMLVideoElement;
    video.addEventListener('canplay', function() {
      showBgThumb = false;
      video.play();
    });

    const updateScale = () => {
      const currentHeight = window.innerHeight;
      scaleMultiplier = (Math.max(1, currentHeight / originalHeight));
    };

    window.addEventListener('resize', updateScale);
    
    updateScale();
  });

    let setNameCondition = $state(false);
    let setLoginCondition = $state(false);

    async function submitLoginForm(this: HTMLFormElement) {
    const form: HTMLFormElement = this;

    form.username.value = form.username.value.trim();

    if (form.username.value.length === 0) {
      rq.msgError('아이디를 입력해주세요.');
      form.username.focus();

      return;
    }

    if (form.username.value.length < 4) {
        rq.msgError('아이디를 4자 이상 입력해주세요.')
        form.username.focus();

        return;
    }


    form.password.value = form.password.value.trim();

    if (form.password.value.length === 0) {
      rq.msgError('비밀번호를 입력해주세요.');
      form.password.focus();

      return;
    }

    if (form.password.value.length < 4) {
      rq.msgError('비밀번호를 4자 이상 입력해주세요.');
      form.password.focus();

      return;
    }

    const { data, error } = await rq.apiEndPoints().POST('/api/v1/members/login', {
      body: {
        roleLevel: form.roleLevel.value,
        username: form.username.value,
        password: form.password.value
      }
    });

    if (error) rq.msgError(error.msg);
    else {
      if(data.data.item.player.nickname.length === 0) {
        setNameCondition = true;
        rq.setLogined(data.data.item)
        // rq.msgAndRedirect(data, undefined, '/member/setName', () => rq.setLogined(data.data.item))
      } else {
        setLoginCondition = true;
        setTimeout(() => {
          rq.msgAndRedirect(data, undefined, '/game/1', () => rq.setLogined(data.data.item));
        }, 600); 
      }
    }
  }

  let username = $state('');

  let lastCalled = 0;

  function throttledMsgError(message:string) {
    const now = Date.now();
    if (now - lastCalled > 1000) { 
      lastCalled = now;
      rq.msgError(message);
    }
  }

  function filterInput() {
    if(username.match(/[^a-zA-Z0-9]/g)) {
      throttledMsgError('아이디는 영문, 숫자만 입력 가능합니다.');
    }
    username = username.replace(/[^a-zA-Z0-9]/g, '');
  }

  async function submitSetNickNameForm(this: HTMLFormElement) {
    const form: HTMLFormElement = this;

    form.nickname.value = form.nickname.value.trim();

    if (form.nickname.value.length === 0) {
      rq.msgError('별명을 입력해주세요.');
      form.nickname.focus();

      return;
    }

    if (form.nickname.value.length < 4) {
        rq.msgError('별명을 4자 이상 입력해주세요.')
        form.nickname.focus();

        return;
    }

    const { data, error } = await rq.apiEndPoints().PUT('/api/v1/players/{id}/name', {
      params: { path: { id: rq.member.id } },
      body: { nickname: form.nickname.value }
    });

    if (error) rq.msgError(error.msg);
    else {
      setLoginCondition = true;
      setTimeout(() => {
        rq.msgAndRedirect(data, undefined, '/game/1', () => rq.member.player.nickname = data.data.item.nickname);
        }, 600); 
    }
  }

  
</script>

<!-- <audio class="myAudio" autoplay>
  <source src="/sound/login_sound.mp3" type="audio/mpeg">
</audio> -->
<video autoplay muted loop id="backgroundVideo">
  <source src="/img/login/background_login.mp4" type="video/mp4">
</video>
<div class="w-full h-full absolute {showBgThumb ? '' : 'hidden'}" style="background-image:url('/img/login/bg_thumnail.jpg');background-size:100% 100%;background-repeat:no-repeat;background-position:bottom;"></div>
<div class="flex flex-col items-center justify-center overflow-hidden">
    <div class="w-screen h-screen flex justify-center relative">
      <div id="logoContainer" class="absolute w-[903px] h-[300px] left-[40px] top-[40px]" 
          style="background-image:url('/img/login/title.png');background-repeat:no-repeat;background-size:contain;"></div> <!-- Todo: 에니메이션 작업-->
        <div id="side_bar_1" class="flex justify-center items-start pt-44 right-[0] h-screen w-[459px] absolute {setNameCondition ? 'slide-out-right' : ''} {setLoginCondition ? 'slide-out-right' : ''}"
            style="background-image:url('/img/login/loginbox_frame.png'), url('/img/login/loginbox.jpg');transform-origin:top right;transform:scale({scaleMultiplier}); --scaleMultiplier: {scaleMultiplier}">
            <form class="flex flex-col gap-12" method="POST" on:submit|preventDefault={submitLoginForm}>
                <div class="flex items-center gap-4">
                  <div class="flex flex-row items-center justify-center gap-4">
                    <input id="radio1" type="radio" name="roleLevel" value="1" hidden checked=""/>
                    <label for="radio1" class="radio-custom" style=""></label>
                    <div class="w-[58px] h-[58px] text-[25px] text-white font-bold leading-[59px]">학생</div>
                  </div>
                  <div class="flex flex-row items-center gap-4">
                    <input id="radio2" type="radio" name="roleLevel" value="2" hidden />
                    <label for="radio2" class="radio-custom" style=""></label>
                    <div class="w-[58px] h-[58px] text-[25px] text-white font-bold leading-[59px]">선생님</div>
                  </div>
                </div>
                <div>
                  <div class="form-control">
                      <label class="label">
                          <span class="label-text text-white text-lg">아이디</span>
                      </label>
                      <input class="input w-[412px] h-[79px] text-white text-[25px] pl-[35px]" style="background-image:url('/img/login/login.png');background-color:unset" maxlength="30"
                             name="username" type="text" autocomplete="off" bind:value={username} on:input={filterInput}>
                  </div>
      
                  <div class="form-control">
                      <label class="label">
                          <span class="label-text text-white text-lg">비밀번호</span>
                      </label>
                      <input class="input inP w-[412px] h-[79px] text-white text-lg pl-[35px]" style="background-image:url('/img/login/login.png');background-color:unset" 
                            type="password" maxlength="30" name="password" >
                  </div>
                </div>
    
                <div class="flex justify-center gap-2">
                    <button class="w-[333px] h-[116px]" style="background-image:url('/img/login/btn_action.png')">
                    </button>
                </div>
            </form>      
        </div>
        {#if setNameCondition}
        <div id="side_bar_2" class="flex flex-col items-center justify-start pt-60 gap-10 h-full w-[459px] absolute right-[0] slide-in-right {setLoginCondition ? 'slide-out-right' : ''}" 
            style=" background-image:url('/img/login/loginbox_frame.png'), url('/img/login/loginbox.jpg');transform-origin:top right;transform:translateX(200%) scale({scaleMultiplier}); --scaleMultiplier: {scaleMultiplier}">
          <div class="w-[420px] h-[22px] flex justify-center items-center" style="background-image:url('/img/login/window_1.png')">
          </div>
          <form class="flex flex-col items-center gap-[6.5rem] w-[80%]" method="POST" on:submit|preventDefault={submitSetNickNameForm}>
            <div class="form-control">
              <label class="label">
                  <span class="label-text text-white text-lg">닉네임</span>
              </label>
              <input class="input w-[412px] h-[79px] text-white text-[25px] pl-[35px]" style="background-image:url('/img/login/login.png');background-color:unset" maxlength="30"
                     name="nickname" type="text" autocomplete="off">
            </div>
  
            <div class="flex justify-center">
              <button class="w-[333px] h-[116px]" style="background-image:url('/img/login/btn_action.png')">
              </button>
            </div>
          </form>      
        </div>
        {/if}
    </div>
</div>


<style>
  @keyframes slideOutRight {
    from {
      transform: translateX(0) scale(var(--scaleMultiplier)); /* scale 값을 추가 */
      opacity: 1;
    }
    to {
      transform: translateX(100%) scale(var(--scaleMultiplier)); /* scale 값을 추가 */
      opacity: 0;
    }
  }

  .slide-out-right {
    animation: slideOutRight 0.5s ease-in-out forwards !important;
  }
  
  @keyframes slideInRight {
  from {
    transform: translateX(200%) scale(var(--scaleMultiplier)); /* scale 값을 추가 */
    opacity: 0;
  }
  to {
    transform: translateX(0) scale(var(--scaleMultiplier)); /* scale 값을 추가 */
    opacity: 1;
  }
}

  .slide-in-right {
    display: flex;
    animation: slideInRight 0.5s ease-in-out forwards;
    animation-delay: 0.5s;
  }

  .radio-custom {
    display: inline-block;
    width: 48px; 
    height: 48px; 
    background-image: url('/img/login/slect_off.png'); 
    cursor: pointer;
  }

  input[type="radio"]:checked + .radio-custom {
    background-image: url('/img/login/slect_on.png'); 
  }

  /* #backgroundVideo {
    position: fixed; 
    top: 50%; 
    left: 50%; 
    transform: translate(-50%, -50%);
    min-width: 100%; 
    min-height: 100%; 
    width: auto; 
    height: auto;
    z-index: -100; 
    object-fit: cover; 
  } */

  #backgroundVideo {
    position: fixed; 
    top: 0;
    left: 0;
    width: 100%; 
    height: 100%; 
    z-index: -100;
    object-fit: fill; 
    background-color: black; 
  }



.floating {
  animation: floating 3s ease-in-out infinite;
}

@keyframes floating {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-20px); }
}

.inP {
  font-family: 'Raleway', sans-serif; 
}

</style>