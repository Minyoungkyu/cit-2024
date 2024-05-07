<script lang="ts">
    import './page.css'

    let { scaleMultiplier, resolution } = $props<{ scaleMultiplier:number, resolution:number }>();
    let adjustScale = $state(0);

    $effect(() => {
        if(resolution >= 1.666) {
            adjustScale = scaleMultiplier + scaleMultiplier*0.25;
        } else {
            adjustScale = scaleMultiplier;
        }
    });
</script>

<div class="flex justify-center items-end mt-[10%] relative" style="transform:scale(0.8) scale({adjustScale});pointer-events:auto;">
    <div class="w-[1442px] h-[819px] flex flex-col items-center relative" style="background-image:url('/img/setting/ui_option_bg.png');">
        <div class="w-full h-[401px] flex flex-col items-center">
            <div class="flex justify-start text-[40px] font-bold italic text-white w-full pt-[30px] pl-[50px]" style="color:rgb(64 226 255)">
                사운드 옵션
            </div>
            <div class="w-full flex flex-row gap-8 justify-center items-center mt-8">
                <div class="w-[52px] h-[52px]" style="background-image:url('/img/inGame/btn_Volume_on.png');"></div>
                <input class="cursor-grab" type="range" min="0" max="100" value="40" step="5" />
                <div>
                    <div class="flex flex-row items-center justify-center gap-4">
                        <input id="checkbox1" type="checkBox" name="roleLevel" value="1" hidden/>
                        <label for="checkbox1" class="radio-custom" style=""></label>
                    </div>
                </div>
            </div>
            <div class="w-full flex flex-row gap-8 justify-center items-center mt-8">
                <div class="w-[52px] h-[52px]" style="background-image:url('/img/inGame/btn_Volume_on.png');"></div>
                <input class="cursor-grab" type="range" min="0" max="100" value="40" step="5" />
                <div>
                    <div class="flex flex-row items-center justify-center gap-4">
                        <input id="checkbox2" type="checkBox" name="roleLevel" value="1" hidden/>
                        <label for="checkbox2" class="radio-custom" style=""></label>
                    </div>
                </div>
            </div>
        </div>
        <div class="w-[98%] h-[17px]" style="background-image:url('/img/setting/ui_option_window.png');"></div>
        <div class="w-full h-[401px]">
            <div class="flex justify-start text-[40px] font-bold italic text-white w-full pt-[30px] pl-[50px]" style="color:rgb(64 226 255)">
                에디터 옵션
            </div>
            <div class="flex flex-row justify-center mt-8">
                <div class="flex flex-row items-center justify-center gap-4">
                    <input id="checkbox3" type="checkBox" name="roleLevel" value="1" hidden/>
                    <label for="checkbox3" class="radio-custom" style=""></label>
                </div>
                <div class="ml-12 w-[511px] h-[64px] flex justify-center items-center text-[35px] text-white font-bold italic" 
                    style="background-image:url('/img/setting/ui_option_frame.png');color:rgb(64 226 255)">
                    자동완성 활성화
                </div>
            </div>
            <div class="flex flex-row justify-center mt-8">
                <div class="flex flex-row items-center justify-center gap-4">
                    <input id="checkbox4" type="checkBox" name="roleLevel" value="1" hidden/>
                    <label for="checkbox4" class="radio-custom" style=""></label>
                </div>
                <div class="ml-12 w-[511px] h-[64px] flex justify-center items-center text-[35px] text-white font-bold italic" 
                    style="background-image:url('/img/setting/ui_option_frame.png');color:rgb(64 226 255)">
                    자동 기능
                </div>
            </div>
        </div>
    </div>
</div>