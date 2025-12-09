<script>
    import "$lib/yir/yirSlide.css";
    import { getAnimalColor, invertHex } from "$lib/yir/colors.js";
    import { growX } from "$lib/yir/transitions.js";

    export let textVisible;
    export let mostDiedBoard;
    export let visible;

    const MIN_INTERNAL_PCT = 25;

    let max = mostDiedBoard[0].value;
    mostDiedBoard = mostDiedBoard.map((item) => ({
        ...item,
        pct: (item.value / max) * 100,
    }));

    console.log(mostDiedBoard);
</script>

<div
    class="fade-container"
    class:visible={textVisible}
    class:animateShadows={textVisible}
>
    <h1>Your most frequent murderers:</h1>
    <div class="bars-wrapper">
        {#each mostDiedBoard as entry}
            <div class="bar-container">
                {#if visible}
                    <div
                        transition:growX={{ duration: 1500 }}
                        class="bar"
                        style="width:max(18px,{entry.pct}%); background-color:{getAnimalColor(
                            entry.name,
                        )}; color:{invertHex(getAnimalColor(entry.name))};"
                        title={entry.value.toLocaleString()}
                    >
                        {#if entry.pct >= MIN_INTERNAL_PCT}
                            <div class="inside-label">
                                {entry.name.replace("_", " ")}
                            </div>
                        {/if}
                        <div class="value-label">
                            {entry.value.toLocaleString()}
                        </div>
                    </div>
                    {#if entry.pct < MIN_INTERNAL_PCT}
                        <div class="outside-label">
                            {entry.name.replace("_", " ")}
                        </div>
                    {/if}
                {/if}
            </div>
        {/each}
    </div>
</div>

<style>
    .value-label {
        position: absolute;
        writing-mode: vertical-rl;
        top: 0;
        height: 100%;
        right: 1px;
        font-size: 16px;
    }

    .inside-label {
        line-height: 80px;
        font-size: 25px;
    }

    .outside-label {
        padding-left: 10px;
        font-size: 25px;
        line-height: 80px;
    }

    .bars-wrapper {
        width: 100%;
        height: 480px;
    }

    .bar-container {
        display: flex;
        margin-bottom: 20px;
    }

    .bar-container:last-child {
        margin-bottom: 0;
    }

    .bar {
        position: relative;
        height: 80px;
        background-color: #ffffff;
        border-radius: 4px;
    }

    h1 {
        font-size: 30px;
        font-weight: 100;
        letter-spacing: 7px;
    }
    .fade-container.animateShadows {
        animation: textShadows 5s infinite steps(125);
    }

    .fade-container {
        width: 70%;
        color: #fff;
        border-radius: 10px;
        padding: 15px;
        text-shadow:
            0px 0px 0px #ff00b6,
            0px 0px 0px #078b0f,
            0px 0px 0px #ec5800,
            0px 0px 0px #16e5e5;
    }

    @keyframes textShadows {
        0% {
            box-shadow:
                10px 0px 1px 5px #ff00b6,
                0px 10px 1px 5px #078b0f,
                -10px 0px 1px 5px #ec5800,
                0px -10px 1px 5px #16e5e5;
        }
        25% {
            box-shadow:
                0px 10px 1px 5px #ff00b6,
                -10px 0px 1px 5px #078b0f,
                0px -10px 1px 5px #ec5800,
                10px 0px 1px 5px #16e5e5;
        }
        50% {
            box-shadow:
                -10px 0px 1px 5px #ff00b6,
                0px -10px 1px 5px #078b0f,
                10px 0px 1px 5px #ec5800,
                0px 10px 1px 5px #16e5e5;
        }
        75% {
            box-shadow:
                0px -10px 1px 5px #ff00b6,
                10px 0px 1px 5px #078b0f,
                0px 10px 1px 5px #ec5800,
                -10px 0px 1px 5px #16e5e5;
        }
        100% {
            box-shadow:
                10px 0px 1px 5px #ff00b6,
                0px 10px 1px 5px #078b0f,
                -10px 0px 1px 5px #ec5800,
                0px -10px 1px 5px #16e5e5;
        }
    }
</style>
