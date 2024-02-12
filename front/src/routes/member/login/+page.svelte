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
        username: form.username.value,
        password: form.password.value
      }
    });

    rq.msgAndRedirect(data, error, '/');
  }

</script>




<div class="flex-1 flex items-center justify-center">
    <div class="max-w-2xl w-full px-4">
        <h1 class="mb-4">
            <i class="fa-solid fa-arrow-right-to-bracket"></i>
            로그인
        </h1>

        <form class="flex flex-col gap-6" method="POST" on:submit|preventDefault={submitLoginForm}>
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
                    <i class="fa-solid fa-arrow-right-to-bracket"></i>
                    <span>로그인</span>
                </button>

                <div class="text-center">
                    <a class="btn btn-link" href="/usr/member/join">회원가입</a>
                    <a class="btn btn-link" href="/usr/member/findUsername">아이디 찾기</a>
                    <a class="btn btn-link" href="/usr/member/findPassword">비밀번호 찾기</a>
                </div>
            </div>
        </form>      
    </div>
</div>
