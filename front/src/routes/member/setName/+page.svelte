<svelte:head>
    <title>{rq.SITE_NAME} | 환영합니다</title>
</svelte:head>

<script lang="ts">
    import rq from '$lib/rq/rq.svelte';

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
      rq.msgAndRedirect(data, undefined, '/main/stage', () => rq.member.player.nickname = data.data.item.nickname)    
    }
  }
</script>


<div class="flex flex-col items-center justify-center">
    <div class="w-screen h-screen flex justify-center relative">
        <span>타이틀 이미지</span>
        <img src="" alt="" class="absolute w-full h-full object-fill z-[-1]">
        <div class="flex flex-col items-center gap-6 border-2 p-4 w-[450px] absolute bottom-[4vh]">
            <div class="border-2 border-black w-full h-[15vh] flex justify-center items-center">
                <div>코드이썬 설명</div>
            </div>
            <form class="flex flex-col gap-6 w-2/3" method="POST" on:submit|preventDefault={submitSetNickNameForm}>
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
