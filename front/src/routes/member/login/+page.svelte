<svelte:head>
    <title>{rq.SITE_NAME} | 시작하기</title>
</svelte:head>

<script lang="ts">

    import rq from '$lib/rq/rq.svelte';

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




<div class="flex flex-col items-center justify-center overflow-hidden">
    <div class="w-screen h-screen flex justify-center relative">
        <span class="absolute">타이틀 이미지</span>
        <img src="" alt="" class="absolute w-full h-full object-fill z-[-1]">
        <div class="flex justify-center items-start border-2 pt-44 right-[0] h-screen w-[450px] absolute {setNameCondition ? 'slide-out-right' : ''} {setLoginCondition ? 'slide-out-right' : ''}">
            <form class="flex flex-col gap-12 w-[80%]" method="POST" on:submit|preventDefault={submitLoginForm}>
                <div class="flex items-center gap-4">
                    <input type="radio" name="roleLevel" value="1" class="radio-sm radio-primary" checked=""/>학생
                    <input type="radio" name="roleLevel" value="2" class="radio-sm radio-primary"/>선생님
                </div>
                <div>
                  <div class="form-control">
                      <label class="label">
                          <span class="label-text">아이디</span>
                      </label>
                      <input class="input input-bordered" maxlength="30"
                             name="username" placeholder="아이디" type="text">
                  </div>
      
                  <div class="form-control">
                      <label class="label">
                          <span class="label-text">비밀번호</span>
                      </label>
                      <input class="input input-bordered" type="password" maxlength="30" name="password" placeholder="비밀번호">
                  </div>
                </div>
    
                <div class="flex flex-col gap-2">
                    <button class="btn btn-block btn-primary gap-1">
                        <span>시작</span>
                    </button>
                </div>
            </form>      
        </div>
        <div class="flex flex-col items-center gap-6 border-2 pt-44 h-full w-[450px] absolute right-[0] {setNameCondition ? 'slide-in-right' : 'hidden'} {setLoginCondition ? 'slide-out-right' : ''}" style="transform:translateX(100%);">
          <div class="border-2 border-black w-full h-[15vh] flex justify-center items-center">
              <div>코드이썬 설명</div>
          </div>
          <form class="flex flex-col justify-center gap-6 w-[80%]" method="POST" on:submit|preventDefault={submitSetNickNameForm}>
              <div class="form-control">
                  <label class="label">
                      <span class="label-text">닉네임</span>
                  </label>
                  <input class="input input-bordered" maxlength="30"
                         name="nickname" placeholder="닉네임" type="text">
              </div>
  
              <div class="flex flex-col gap-2">
                  <button class="btn btn-block btn-primary gap-1">
                      <span>시작</span>
                  </button>
              </div>
          </form>      
      </div>
    </div>
</div>


<style>
  @keyframes slideOutRight {
    from {
      transform: translateX(0);
      opacity: 1;
    }
    to {
      transform: translateX(100%);
      opacity: 0;
      display:none;
    }
  }

  .slide-out-right {
    animation: slideOutRight 0.5s ease-in-out forwards !important;
  }
  
  @keyframes slideInRight {
    from {
      transform: translateX(100%);
      opacity: 0;
    }
    to {
      transform: translateX(0);
      opacity: 1;
    }
  }

  .slide-in-right {
    display: flex;
    animation: slideInRight 0.5s ease-in-out forwards;
    animation-delay: 0.5s;
  }
</style>