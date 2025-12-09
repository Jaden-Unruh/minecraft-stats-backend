<script>
    export let textVisible;
    export let info;

    import { growX, growY } from "$lib/yir/transitions.js";
    import "$lib/yir/yirSlide.css";

    const helpArray = [
        ["climb", "Distance traveled up ladders or vines"],
        ["crouch", "Distance traveled while sneaking and not in water"],
        ["fall", "Distance fallen, excluding jumping"],
        [
            "fly",
            "Distance traveled upward and forward at the same time, while more than one block above the ground",
        ],
        ["sprint", "Distance sprinted"],
        ["swim", "Distance covered with sprint-swimming"],
        ["walk", "Distance walked"],
        [
            "walk_on_water",
            "Distance covered in any direction in water while the player's head is above water",
        ],
        [
            "walk_under_water",
            "Distance covered in any direction while the player's head is underwater",
        ],
        ["aviate", "Distance covered by elytra"],
        ["boat", "Distance covered while in boats"],
        [
            "happy_ghast",
            "Distance covered while riding a harnessed happy ghast",
        ],
        ["horse", "Distance covered while riding a horse"],
        ["minecart", "Distance covered while riding a minecart"],
        ["pig", "Distance covered while riding a saddled pig"],
        ["strider", "Distance covered while riding a saddled strider"],
    ];

    const labelArray = [
        "1cm",
        "10cm",
        "1m",
        "10m",
        "100m",
        "1km",
        "10km",
        "100km",
        "1Mm",
        "10Mm",
        "100Mm",
        "1Gm",
    ];

    let referenceLevels;

    const entries = Object.entries(info.distances)
        .map(([label, value]) => ({
            label,
            value,
            logValue: value > 0 ? Math.log10(value) : 0,
        }))
        .filter((entry) => entry.label != "total")
        .sort((a, b) => a.value - b.value);

    const maxLog = Math.max(...entries.map((e) => e.logValue ?? -Infinity));
    const usedMax = isFinite(maxLog) ? maxLog : 1;

    const maxEntry = entries.max;

    const MIN_INTERNAL_PCT = 25;

    $: {
        referenceLevels = [];
        for (let p = 0; p <= Math.ceil(usedMax); p++) {
            referenceLevels.push({
                label: labelArray[p],
                pct: (p / usedMax) * 100,
            });
        }
    }
</script>

<div class="fade-container" class:visible={textVisible}>
    <h1>And how'd you go that far?</h1>
    <div class="chart-wrapper">
        {#each referenceLevels as r}
            {#if r.pct < 100}
                <div class="gridline" style="bottom:{r.pct}%"></div>
                <div class="gridlabel" style="bottom:{r.pct}%">{r.label}</div>
            {/if}
        {/each}
        <div class="chart" style="width:{(entries.length - 1) * 46 + 30}">
            {#each entries as e}
                {#key e.label}
                    <div class="bar-container">
                        {#if (e.logValue / maxLog) * 100 < MIN_INTERNAL_PCT}
                            <div
                                class="outside-label"
                                transition:growY={{ duration: 400 }}
                            >
                                {e.label}
                            </div>
                        {/if}

                        <div
                            class="bar"
                            style="height:{(e.logValue / maxLog) * 100}%"
                            title={(e.value / 100_000).toFixed(3) + "km"}
                            transition:growY={{ duration: 400 }}
                        >
                            {#if (e.logValue / maxLog) * 100 >= MIN_INTERNAL_PCT}
                                <div
                                    class="inside-label"
                                    transition:growY={{ duration: 400 }}
                                >
                                    {e.label}
                                </div>
                            {/if}
                        </div>
                    </div>
                {/key}
            {/each}
        </div>
    </div>
    <div class="helpWrapper">
        <div class="help">
            <h3>What do these mean?</h3>
        </div>
        <div class="tooltip">
            <table>
                <tbody>
                    {#each helpArray as [label, description]}
                        <tr
                            ><td class="rightAlign">{label}</td><td
                                class="leftAlign">{description}</td
                            ></tr
                        >
                    {/each}
                </tbody>
            </table>
        </div>
    </div>
</div>

<style>
    .rightAlign {
        text-align: right;
        padding-right: 5px;
    }

    .leftAlign {
        text-align: left;
        padding-left: 5px;
    }

    .helpWrapper {
        position: relative;
        display: inline-block;
    }

    .help {
        padding: 10px;
        height: fit-content;
        width: fit-content;
        background-color: #eee;
        margin: 10px auto;
        border-radius: 6px;
    }

    .tooltip {
        position: absolute;
        bottom: 100%;
        left: 50%;
        transform: translateX(-50%);
        margin-bottom: 8px;

        background: #222;
        color: white;
        padding: 6px 10px;
        border-radius: 4px;
        white-space: nowrap;

        opacity: 0;
        pointer-events: none;
        transition: opacity 150ms ease;
    }

    .helpWrapper:hover .tooltip {
        opacity: 1;
    }

    table {
        border-collapse: collapse;
    }

    tr {
        border-bottom: 1px solid #eee;
    }

    tr:last-child {
        border-bottom: none;
    }

    .help h3 {
        font-style: italic;
        margin: 0;
    }

    .chart-wrapper {
        margin: 0 auto;
        position: relative;
        width: fit-content;
    }

    .chart {
        display: flex;
        align-items: flex-start;
        gap: 1rem;
        height: 500px;
        width: fit-content;
        border-left: 1px solid #aaa;
        border-bottom: 1px solid #aaa;
    }

    .gridline {
        position: absolute;
        left: 0;
        right: 0;
        border-top: 1px dashed #aaa;
        pointer-events: none;
    }

    .gridlabel {
        position: absolute;
        left: -2.5rem;
        transform: translateY(50%);
        font-size: 0.75rem;
        color: #eee;
    }

    .bar-container {
        height: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: flex-end;
    }

    .bar {
        width: 30px;
        background: #ffaa00;
        border-radius: 4px 4px 0 0;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .inside-label {
        writing-mode: vertical-rl;
        transform: rotate(180deg);
        color: #333;
        font-size: 1rem;
        padding: 4px;
        white-space: nowrap;
        font-weight: bold;
    }

    .outside-label {
        writing-mode: vertical-rl;
        transform: rotate(180deg);
        margin-top: 6px;
        color: #eee;
        font-size: 1rem;
        white-space: nowrap;
        font-weight: bold;
    }
</style>
