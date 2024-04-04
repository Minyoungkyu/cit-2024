<script lang="ts">

    let { isCoReady, openLayer } = $props<{ isCoReady: boolean, openLayer: boolean }>();
</script>

<div class="absolute w-screen h-screen z-[10] overflow-hidden { isCoReady ? 'hide-layer' : '' } { openLayer ? 'open-layer' : ''}">
    <div class="absolute top-slide bg-black h-[50%]"></div>
    <div class="absolute bottom-slide bg-black h-[50%]"></div>
    <div class="loading-container z-[99]">
        <div class="loading-bar"></div>
        <div class="loading-bar"></div>
        <div class="loading-bar"></div>
        <div class="loading-bar"></div>
    </div>
</div>

<style>

.hide-layer {
    pointer-events: none;
}

.hide-layer .loading-container {
    animation: hidding 0.5s ease-in-out forwards;
}

.hide-layer .top-slide {
    animation: slideOut1 0.5s ease-in-out forwards;
}

.hide-layer .bottom-slide {
    animation: slideOut2 0.5s ease-in-out forwards;
}

.open-layer .loading-container {
    animation: showing 0.5s ease-in-out forwards;
}

.open-layer .top-slide {
    animation: slideIn1 0.5s ease-in-out forwards;
}

.open-layer .bottom-slide {
    animation: slideIn2 0.5s ease-in-out forwards;
}

@keyframes hidding {
    0% {
        opacity: 1;
    }
    100% {
        opacity: 0;
        display:none;
    }
}

@keyframes showing {
    0% {
        opacity: 0;
        display:block;
    }
    100% {
        opacity: 1;
    }

}

.top-slide, .bottom-slide {
    position: absolute;
    width: 100%;
    background-color: black;
}

.top-slide {
    top: 0;
    height: 50vh; /* 뷰포트 높이의 50% */
    transform: translateY(0%); /* 위쪽에서 시작 */
}

.bottom-slide {
    bottom: 0;
    height: 50vh; /* 뷰포트 높이의 50% */
    transform: translateY(0%); /* 아래쪽에서 시작 */
}

.open-layer .top-slide {
    top: 0;
    height: 50vh; /* 뷰포트 높이의 50% */
    transform: translateY(-100%); /* 위쪽에서 시작 */
}

.open-layer .bottom-slide {
    bottom: 0;
    height: 50vh; /* 뷰포트 높이의 50% */
    transform: translateY(100%); /* 아래쪽에서 시작 */
}

@keyframes slideOut1 {
    to {
    transform: translateY(-100%); /* 원래 위치로 이동 */
    }
}

@keyframes slideOut2 {
    to {
    transform: translateY(100%); /* 원래 위치로 이동 */
    }
}

@keyframes slideIn1 {
    to {
    transform: translateY(0%); /* 원래 위치로 이동 */
    }
}

@keyframes slideIn2 {
    to {
    transform: translateY(0%); /* 원래 위치로 이동 */
    }
}







    .loading-container {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100vh; /* 전체 화면을 채우도록 설정 */
        background-color:unset;
    }

    .loading-bar {
        width: 10px; /* 로딩 바의 너비 */
        height: 40px; /* 로딩 바의 높이 */
        background-color: #4B5563; /* 로딩 바 색상 */
        margin: 0 2px; /* 로딩 바 사이의 간격 */
        animation: loadingAnimation 0.6s ease-in-out infinite; /* 에니메이션 속도를 0.6초로 단축 */
    }

    /* 첫 번째 로딩 바 */
    .loading-bar:nth-child(1) {
        animation-delay: 0s;
    }

    /* 두 번째 로딩 바 */
    .loading-bar:nth-child(2) {
        animation-delay: 0.1s;
    }

    /* 세 번째 로딩 바 */
    .loading-bar:nth-child(3) {
        animation-delay: 0.2s;
    }

    /* 네 번째 로딩 바 */
    .loading-bar:nth-child(4) {
        animation-delay: 0.3s;
    }

    @keyframes loadingAnimation {
        0%, 100% {
        transform: scaleY(0.2); /* 최소 크기를 더 작게 조정 */
        }
        50% {
        transform: scaleY(1.5); /* 최대 크기를 더 크게 조정 */
        }
    }

    .loading-container {
        transition: opacity 0.5s ease-in-out; /* opacity 변화에 대한 전환 효과 */
    }


</style>