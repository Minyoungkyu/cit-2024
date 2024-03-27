<div class="flex flex-col dropdown-content items-center pt-12 gap-12 border-2 h-full w-[450px] absolute right-[0] bg-gray-700 slide-in">
    <div class="flex justify-end w-full mr-16">
        <div class="text-[70px] font-extrabold text-white" style="text-shadow:5px 10px black">Tutorial</div>
    </div>
    <div class="flex flex-col w-full">
        <div class="w-full h-8 border-2"></div>
        <div class="border-2 border-black w-full h-[400px] flex justify-center">
            <div class="text-[25px] font-bold text-white mt-12" style="white-space:pre-wrap;">튜토리얼</div>
        </div>
    </div>
    <div class="flex flex-col gap-2 items-center p-2">
        <button class="btn btn-accent btn-wide" on:click={() => window.location.href = '/game/tutorial/1'}>시작</button>
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