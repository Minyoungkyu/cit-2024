<script lang="ts">
    
    const { activeTransitionAnimation } = $props<{ activeTransitionAnimation: () => void }>();

    let test = $state(false);

    function onClickToStart() {
        activeTransitionAnimation();
        setTimeout(() => {
            window.location.href = '/game/tutorial/1';
        }, 500);
    }
</script>

<div class="flex flex-col dropdown-content items-end pt-12 gap-12 h-screen w-[628px] absolute top-[0] right-[0] slide-in" 
    style="background-image:url('/img/map/ui_stage_Gradation.png');">
    <div class="flex flex-col w-[501px]">
        <div class="w-full mr-16">
            <div class="text-[70px] font-extrabold text-white text-right mr-[50px]" style="text-shadow:5px 10px black">Tutorial</div>
        </div>
        <div class="flex flex-col items-end w-full">
            <div class="w-[501px] h-[52px]" style="background-image:url('/img/map/ui_mission_top.png');"></div>
            <div class="w-[450px] h-[400px] flex justify-start">
                <div class="text-[25px] font-bold text-white mt-12" style="white-space:pre-wrap;">튜토리얼</div>
            </div>
        </div>
        <div class="flex gap-2 justify-center w-[501px] p-2" >
            <button class="w-[333px] h-[116px]" on:click={() => onClickToStart()} style="background-image:url('/img/map/btn_action.png')"></button>
        </div>
    </div>
</div>

<style>
    @keyframes slideIn {
        from {
            transform: translateX(100%); 
            opacity: 0;
        }
        to {
            transform: translateX(0); 
            opacity: 1;
        }
    }

    .slide-in {
        animation: slideIn 0.5s ease-out forwards; 
    }

    @keyframes fancySlideIn {
        0% {
            transform: translateX(100%) rotateY(90deg) scale(0.5);
            opacity: 0;
        }
        50% {
            transform: translateX(-10%) rotateY(-10deg) scale(1.1);
            opacity: 0.5;
        }
        100% {
            transform: translateX(0) rotateY(0deg) scale(1);
            opacity: 1;
        }
    }

    .fancy-slide-in {
        animation: fancySlideIn 1s ease-out forwards;
    }

    @keyframes superFancySlideIn {
        0% {
            transform: translateX(100%) scale(0) rotateZ(360deg);
            opacity: 0;
            filter: blur(10px);
            background-color: rgba(255, 255, 255, 0);
        }
        30% {
            transform: translateX(0) scale(1.2) rotateZ(-30deg);
            opacity: 0.5;
            filter: blur(5px);
            background-color: rgba(255, 255, 255, 0.2);
        }
        60% {
            transform: scale(0.9) rotateZ(15deg);
            opacity: 0.75;
            filter: blur(2px);
            background-color: rgba(255, 255, 255, 0.4);
        }
        100% {
            transform: scale(1) rotateZ(0deg);
            opacity: 1;
            filter: blur(0px);
            background-color: rgba(255, 255, 255, 1);
        }
    }

    .super-fancy-slide-in {
        animation: superFancySlideIn 1.5s cubic-bezier(0.22, 0.61, 0.36, 1) forwards;
    }
</style>

