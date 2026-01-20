<script>
    import { fade, scale, fly } from "svelte/transition";
    import { onMount } from "svelte";
    import { page } from "$app/stores";
    import { growX, growY } from "$lib/yir/transitions.js";
    import Intro from "$lib/yir/slides/Intro.svelte";
    import PlayTime from "$lib/yir/slides/PlayTime.svelte";
    import DistanceTraveled from "$lib/yir/slides/DistanceTraveled.svelte";
    import DistanceChart from "$lib/yir/slides/DistanceChart.svelte";
    import MostKilled from "$lib/yir/slides/MostKilled.svelte";
    import MostDied from "$lib/yir/slides/MostDied.svelte";
    import KilledBoard from "$lib/yir/slides/KilledBoard.svelte";
    import DiedBoard from "$lib/yir/slides/DiedBoard.svelte";
    import LedStat from "$lib/yir/slides/LedStat.svelte";
    import Summary from "$lib/yir/slides/Summary.svelte";

    import {
        animalColors,
        getAnimalColor,
        lighten,
        hexToRgba,
    } from "$lib/yir/colors.js";
    import { goto } from "$app/navigation";

    let loading = true;
    let error = null;

    let playerName;
    let info = [];

    let colors = [
        "#aa00aa",
        "#ffaa00",
        "#ff55ff",
        "#555555",
        "#990000",
        "#330000",
        "#000099",
        "#000033",
        "#ffaa00",
        "#1a0012"
    ];
    let index = 0;
    let fullCycle = false;

    let textVisible = false;

    let mostKilledColor;
    let mostDiedColor;

    let doLedStatFrame = false;
    let ledStatInfo;

    const fetchInfo = async () => {
        document.documentElement.style.setProperty("--from", "#aa00aa");
        document.documentElement.style.setProperty("--to", "#ffaa00");

        $: playerName = $page.params.playerName;

        try {
            const response = await fetch(`/api/yir/2025/${playerName}`);
            if (!response.ok)
                throw new Error("Failed to fetch player yir info");
            info = await response.json();
        } catch (err) {
            console.error(err);
            error = err.message;
        } finally {
            loading = false;
            textVisible = true;
            setTimeout(() => {
                visible = true;
            }, 100);
            mostKilledColor = getAnimalColor(info.mostKilled.name);
            mostDiedColor = getAnimalColor(info.mostDied.name);

            getListStats();
        }

        try {
            const response = await fetch(`/api/yir/2025/${playerName}/ledStat`);
            if (!response.ok)
                doLedStatFrame = false;
                else {
                ledStatInfo = await response.text();
                doLedStatFrame = true;
            }
        } catch (err) {
            console.warn(err);
            doLedStatFrame = false;
        }
    };

    let mostKilledBoard;
    let mostDiedBoard;

    async function getListStats() {
        try {
            const killedResponse = await fetch(
                `/api/yir/2025/${playerName}/maxStats/killed`,
            );
            const diedResponse = await fetch(
                `/api/yir/2025/${playerName}/maxStats/killed_by`,
            );
            if (!killedResponse.ok)
                throw new Error("Failed to fetch player mostKilled info");
            if (!diedResponse.ok)
                throw new Error("Failed to fetch player mostDied info");
            mostKilledBoard = await killedResponse.json();
            mostDiedBoard = await diedResponse.json();
        } catch (err) {
            console.error(err);
            error = err.message;
        }
    }

    onMount(fetchInfo);

    let switched = false;
    let active = false;
    const DELAY = 400;
    const DURATION = 1600;
    const MID = 1000;

    function triggerNextAnimation() {
        if (active) return;

        textVisible = false;
        visible = false;

        setTimeout(() => {
            active = true;
            switched = false;
            const from = colors[index];
            const to = colors[((index === 7 && !doLedStatFrame) ? 9 : index + 1) % colors.length];
            document.documentElement.style.setProperty("--from", from);
            document.documentElement.style.setProperty("--to", to);
        }, DELAY);
        setTimeout(() => {
            switched = true;
        }, MID);
        setTimeout(() => {
            active = false;
            index = (index + 1) % colors.length;
            if (index === 8 && !doLedStatFrame)
                index = 9;
            if (index === 9)
                fullCycle = true;
        }, DURATION);
        setTimeout(() => {
            visible = true;
            textVisible = true;
        }, DELAY + DURATION);
    }

    let visible = false;
    let animateShadows = false;
    let bordersVisible = false;
    let borders2Visible = false;

    function startShadowAnim() {
        animateShadows = false;
        requestAnimationFrame(() => (animateShadows = true));
    }

    function startBorderAnim() {
        bordersVisible = false;
        requestAnimationFrame(() => (bordersVisible = true));
    }

    function startBorders2Anim(node) {
        borders2Visible = false;
        requestAnimationFrame(() => (borders2Visible = true));
    }

    function yirProceed() {
        triggerNextAnimation();
    }

    function backButton() {
        goto("/yearInReview/2025");
    }
</script>

{#if loading}
    <p>Loading...</p>
{:else}
    <div
        class:animating={active}
        class="color-box"
        aria-hidden="true"
        class:switched
    >
        <button
            class="yir"
            type="button"
            aria-label="year-in-review"
            on:click={yirProceed}
        >
            {#if index == 0 && visible}
                <div
                    class="introBox {animateShadows ? 'shadow-anim' : ''}"
                    transition:fade={{ duration: 300 }}
                    on:introend={startShadowAnim}
                >
                    <Intro {textVisible} {playerName} />
                </div>
                {#if fullCycle}
                    <div
                        class="backButton {animateShadows ? 'shadow-anim' : ''}"
                        transition:fade={{duration: 300 }}
                        on:introend={startShadowAnim}
                        on:click={backButton}
                    >  
                        <h2>View another player's Review?</h2>
                    </div>
                {/if}
            {:else if index == 1 && visible}
                <div
                    class="zoomBox {bordersVisible ? 'shadowed' : ''}"
                    in:scale={{ start: 0.1, duration: 300 }}
                    out:scale={{ duration: 150 }}
                    on:introend={startBorderAnim}
                >
                    <PlayTime {textVisible} {info} />
                </div>
            {:else if index == 2 && visible}
                <div
                    class="borders2Box {borders2Visible
                        ? 'animate-borders'
                        : ''}"
                    in:fly={{ x: -140, y: 40, duration: 450 }}
                    out:fly={{ x: 140, y: -40, duration: 450 }}
                    on:introend={(e) => startBorders2Anim(e.target)}
                >
                    <DistanceTraveled {textVisible} {info} />
                </div>
            {:else if index == 3}
                <DistanceChart {textVisible} {info} {visible} />
                {#if visible}
                    <div
                        class="decoBar bar1"
                        transition:growY={{ duration: 690 }}
                    ></div>
                    <div
                        class="decoBar bar2"
                        transition:growY={{ duration: 1200 }}
                    ></div>
                    <div
                        class="decoBar bar3"
                        transition:growY={{ duration: 1050 }}
                    ></div>
                    <div
                        class="decoBar bar4"
                        transition:growY={{ duration: 810 }}
                    ></div>
                {/if}
            {:else if index == 4 && visible}
                <div
                    class="weirdBox"
                    style="background:{lighten(
                        mostKilledColor,
                    )}; --shadow:{mostKilledColor}; --color:rgba({hexToRgba(
                        mostKilledColor,
                        0.6,
                    )}); --light:rgba({hexToRgba(mostKilledColor, 0.3)});"
                    transition:scale={{ duration: 250, start: 0.1 }}
                >
                    <MostKilled {textVisible} {info} />
                </div>
                <div
                    class="faller"
                    style="--left: 5%; --duration: 5s; --delay: 0s; --color:{mostKilledColor};"
                    out:fly={{ x: 0, y: 200, duration: 400 }}
                ></div>
                <div
                    class="faller"
                    style="--left: 15%; --duration: 7s; --delay: 1s; --color:{mostKilledColor};"
                    out:fly={{ x: 0, y: 200, duration: 400 }}
                ></div>
                <div
                    class="faller"
                    style="--left: 10%; --duration: 11s; --delay: 2s; --color:{mostKilledColor};"
                    out:fly={{ x: 0, y: 200, duration: 400 }}
                ></div>
                <div
                    class="faller"
                    style="--right: 5%; --duration: 4s; --delay: 0s; --color:{mostKilledColor};"
                    out:fly={{ x: 0, y: 200, duration: 400 }}
                ></div>
                <div
                    class="faller"
                    style="--right: 15%; --duration: 13s; --delay: 0.5s; --color:{mostKilledColor};"
                    out:fly={{ x: 0, y: 200, duration: 400 }}
                ></div>
                <div
                    class="faller"
                    style="--right: 10%; --duration: 6s; --delay: 4s; --color:{mostKilledColor};"
                    out:fly={{ x: 0, y: 200, duration: 400 }}
                ></div>
            {:else if index == 5}
                <KilledBoard {textVisible} {mostKilledBoard} {visible}/>
            {:else if index == 6 && visible}
                <div
                    class="weirdBox"
                    style="background:{lighten(
                        mostDiedColor,
                    )}; --shadow:{mostDiedColor}; --color:rgba({hexToRgba(
                        mostDiedColor,
                        0.6,
                    )}); --light:rgba({hexToRgba(mostDiedColor, 0.3)});"
                    transition:scale={{ duration: 250, start: 0.1 }}
                >
                    <MostDied {textVisible} {info} />
                </div>
                <div
                    class="faller"
                    style="--left: 5%; --duration: 5s; --delay: 0s; --color:{mostDiedColor};"
                    out:fly={{ x: 0, y: 200, duration: 400 }}
                ></div>
                <div
                    class="faller"
                    style="--left: 15%; --duration: 7s; --delay: 1s; --color:{mostDiedColor};"
                    out:fly={{ x: 0, y: 200, duration: 400 }}
                ></div>
                <div
                    class="faller"
                    style="--left: 10%; --duration: 11s; --delay: 2s; --color:{mostDiedColor};"
                    out:fly={{ x: 0, y: 200, duration: 400 }}
                ></div>
                <div
                    class="faller"
                    style="--right: 5%; --duration: 4s; --delay: 0s; --color:{mostDiedColor};"
                    out:fly={{ x: 0, y: 200, duration: 400 }}
                ></div>
                <div
                    class="faller"
                    style="--right: 15%; --duration: 13s; --delay: 0.5s; --color:{mostDiedColor};"
                    out:fly={{ x: 0, y: 200, duration: 400 }}
                ></div>
                <div
                    class="faller"
                    style="--right: 10%; --duration: 6s; --delay: 4s; --color:{mostDiedColor};"
                    out:fly={{ x: 0, y: 200, duration: 400 }}
                ></div>
            {:else if index == 7}
                <DiedBoard {textVisible} {mostDiedBoard} {visible}/>
            {:else if index == 8}
                <LedStat {textVisible} {ledStatInfo} {visible} />
            {:else if index == 9}
                <Summary {playerName} {textVisible} {info} {visible} />
            {/if}
        </button>
    </div>
{/if}

<style>
    .backButton h2 {
        margin: 0;
    }

    .faller {
        top: -40px;
        position: absolute;
        left: var(--left, auto);
        right: var(--right, auto);
        width: var(--size, 40px);
        height: var(--size, 40px);
        border-radius: 50%;
        background: var(--color, #ccc);

        animation: fall var(--duration, 6s) linear var(--delay, 0s) infinite;
    }

    @keyframes fall {
        0% {
            top: -40px;
        }
        100% {
            top: calc(100vh + 40px);
        }
    }

    .weirdBox {
        padding: 10px;
        width: fit-content;
        margin: 0 auto;
        border-radius: 8px;

        box-shadow:
            0 0 0 6px var(--shadow),
            0 0 12px 10px var(--shadow),
            0 0 20px 4px var(--color),
            0 0 40px 12px var(--color),
            0 0 36px 8px var(--light),
            0 0 70px 20px var(--light);

        animation: wobble 7s infinite ease-in-out;
    }

    @keyframes wobble {
        0% {
            box-shadow:
                0 0 0 6px var(--shadow),
                0 0 12px 10px var(--shadow),
                0 0 20px 4px var(--color),
                0 0 40px 12px var(--color),
                0 0 36px 8px var(--light),
                0 0 70px 20px var(--light);
        }
        33% {
            box-shadow:
                6px -4px 0 6px var(--shadow),
                -10px 8px 14px 10px var(--shadow),
                -4px 6px 24px 6px var(--color),
                10px -8px 48px 14px var(--color),
                4px -6px 40px 10px var(--light),
                -14px 10px 80px 24px var(--light);
        }
        66% {
            box-shadow:
                -6px 6px 0 6px var(--shadow),
                12px -10px 16px 10px var(--shadow),
                6px -4px 24px 6px var(--color),
                -12px 10px 52px 14px var(--color),
                -4px 4px 44px 12px var(--light),
                16px -12px 90px 26px var(--light);
        }
        100% {
            box-shadow:
                0 0 0 6px var(--shadow),
                0 0 12px 10px var(--shadow),
                0 0 20px 4px var(--color),
                0 0 40px 12px var(--color),
                0 0 36px 8px var(--light),
                0 0 70px 20px var(--light);
        }
    }

    .decoBar {
        position: absolute;
        bottom: -10px;
        width: 30px;
        border-radius: 4px 4px 0 0;
    }

    .bar1 {
        height: 230px;
        background-color: #ff00b6;
        left: 10%;
    }

    .bar2 {
        height: 400px;
        background-color: #078b0f;
        left: 15%;
    }

    .bar3 {
        height: 350px;
        background-color: #ec5800;
        right: 15%;
    }

    .bar4 {
        height: 270px;
        background-color: #16e5e5;
        right: 10%;
    }

    .borders2Box {
        background: #ddd;
        padding: 10px;
        width: fit-content;
        margin: 0 auto;
        border-radius: 8px;

        animation-fill-mode: forwards;

        box-shadow: none;
    }

    .animate-borders {
        animation: borderBlast 900ms ease-out forwards;
    }

    @keyframes borderBlast {
        0% {
            box-shadow:
                0 0 0 0 #ff00b6,
                0 0 0 0 #078b0f,
                0 0 0 0 #ec5800,
                0 0 0 0 #16e5e5;
        }
        25% {
            box-shadow:
                -12px -12px 0 6px #ff00b6,
                12px -12px 0 6px #078b0f,
                -12px 12px 0 6px #ec5800,
                12px 12px 0 6px #16e5e5;
        }
        60% {
            box-shadow:
                -32px -32px 0 12px #ff00b6,
                32px -32px 0 12px #078b0f,
                -32px 32px 0 12px #ec5800,
                32px 32px 0 12px #16e5e5;
        }
        100% {
            box-shadow:
                0 0 0 8px #ff00b6,
                0 0 0 16px #078b0f,
                0 0 0 24px #ec5800,
                0 0 0 32px #16e5e5;
        }
    }

    .zoomBox {
        background: #ddd;
        padding: 10px;
        width: fit-content;
        margin: 0 auto;
        border-radius: 8px;

        transition: box-shadow 400ms ease-out;
        box-shadow: 0 0 0 0 #000000;
    }

    .shadowed {
        box-shadow:
            0 0 0 8px #ff00b6,
            0 0 0 16px #078b0f,
            0 0 0 24px #ec5800,
            0 0 0 32px #16e5e5;
    }

    .backButton {
        position: relative;
        top: 100px;
        width: fit-content;
        margin: 0 auto;
        padding: 15px;
        background: #ddd;
        border-radius: 8px;

        box-shadow:
            0 0 0 0 #ff00b6,
            0 0 0 0 #078b0f,
            0 0 0 0 #ec5800,
            0 0 0 0 #16e5e5;
    }

    .backButton:hover {
        animation: rays 0.6s ease-out reverse;
    }

    .introBox {
        background: #ddd;
        padding: 10px;
        width: fit-content;
        margin: 0 auto;
        border-radius: 8px;

        box-shadow:
            0 0 0 0 #ff00b6,
            0 0 0 0 #078b0f,
            0 0 0 0 #ec5800,
            0 0 0 0 #16e5e5;
    }

    .shadow-anim {
        animation: rays 0.6s ease-out forwards;
    }

    @keyframes rays {
        0% {
            box-shadow:
                0 0 0 0 #ff00b6,
                0 0 0 0 #078b0f,
                0 0 0 0 #ec5800,
                0 0 0 0 #16e5e5;
        }
        100% {
            box-shadow:
                15px 15px 0 5px #ff00b6,
                30px 30px 0 5px #078b0f,
                45px 45px 0 5px #ec5800,
                60px 60px 0 5px #16e5e5;
        }
    }

    .yir {
        width: 100%;
        height: 100%;
        margin: 0 auto;
        border: none;
        padding: 0;
        background-color: transparent;
    }

    .color-box {
        width: 100%;
        height: calc(100% - 20px);
        padding: 10px 0;

        background: var(--from);
        position: relative;
        overflow: hidden;
    }

    .color-box::after {
        content: "";
        position: absolute;
        inset: 0;

        background: linear-gradient(90deg, var(--from), var(--to));

        opacity: 0;

        pointer-events: none;
    }

    .color-box.animating::after {
        opacity: 1;
        animation: slide 1.2s linear forwards;
    }

    .color-box.switched {
        background: var(--to);
    }

    @keyframes slide {
        0% {
            transform: translateX(100%);
        }
        100% {
            transform: translateX(-100%);
        }
    }
</style>
