<svelte:head>
    <title>{rq.SITE_NAME} | 로그인</title>
</svelte:head>

<script lang="ts">

    import rq from '$lib/rq/rq.svelte';

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
        console.log(data.data.item.player.nickname.trim.length)
        console.log(data.data.item.player.nickname)
        rq.msgAndRedirect(data, undefined, '/member/setName', () => rq.setLogined(data.data.item))
      } else {
        rq.msgAndRedirect(data, undefined, '/main/stage', () => rq.setLogined(data.data.item))
      }
    }
  }
</script>




<div class="flex flex-col items-center justify-center p-8">
    <div class="border-2 border-black w-full h-[40vh] flex justify-center items-center">
        <span>타이틀 이미지</span>
    </div>
    <div class="flex justify-center border-2 p-4 mt-4">
        <form class="flex flex-col gap-6" method="POST" on:submit|preventDefault={submitLoginForm}>
            <div class="flex items-center gap-4">
                <input type="radio" name="roleLevel" value="1" class="radio-sm radio-primary" checked=""/>학생
                <input type="radio" name="roleLevel" value="2" class="radio-sm radio-primary"/>선생님
            </div>
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
                <input class="input input-bordered" maxlength="30" name="password" placeholder="비밀번호">
            </div>

            <div class="flex flex-col gap-2">
                <button class="btn btn-block btn-primary gap-1">
                    <span>시작</span>
                </button>
            </div>
        </form>      
    </div>
</div>
