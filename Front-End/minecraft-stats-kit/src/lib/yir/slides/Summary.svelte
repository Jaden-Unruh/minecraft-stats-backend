<script>
    import { getPlayerColor, getAnimalColor, invertHex } from "$lib/yir/colors.js";
    import BannerPath from "$lib/yir/BannerPath.svelte";

    export let playerName;
    export let textVisible;
    export let info;
    export let visible;

    let isStatic = true;
</script>

<div class="fade-container" class:visible={textVisible}>
    <div id="shot-container">
        <div class="border" style="background{getPlayerColor(playerName)};">
            <div class="banner">
                <BannerPath {isStatic} />
            </div>
            <div class="name">
                <h1>{playerName}</h1>
            </div>
            <div class="minutes">
                <h2>{Math.floor(info.playTime / 1200).toLocaleString()}</h2>
                <h4>minutes played</h4>
            </div>
            <div class="km">
                <h2>
                    {parseFloat(
                        (info.distances.total / 100_000).toFixed(2),
                    ).toLocaleString()}
                </h2>
                <h4>km traveled</h4>
            </div>
            <div class="killed" style="--color:{getAnimalColor(info.mostKilled.name)}; --invert:{invertHex(getAnimalColor(info.mostKilled.name))};">
                <img class="egg" src="/assets/textures/{info.mostKilled.name}_spawn_egg.png" />
                <h3 class="mobValue">{info.mostKilled.value.toLocaleString()}</h3>
                <h4 class="mobName">{info.mostKilled.name} slain</h4>
            </div>
            <div class="died" style="--color:{getAnimalColor(info.mostDied.name)}; --invert:{invertHex(getAnimalColor(info.mostDied.name))};">
                <img class="egg" src="/assets/textures/{info.mostDied.name}_spawn_egg.png" />
                <h3 class="mobValue">{info.mostDied.value.toLocaleString()}</h3>
                <h4 class="mobName">{info.mostDied.name} died to</h4>
            </div>
            <div class="sessions">
                <h3>In</h3>
                <h2>{info.sessions} sessions</h2>
                <h3>On the J3L+ SMP</h3>
            </div>
            <div class="logo">
                <img
                    class="yir-logo"
                    src="/assets/logo.png"
                />
            </div>
        </div>
    </div>
</div>

<style>
    .banner {
        background-color: #333;
        border-radius: 5px;
        grid-area: banner;
    }

    .name {
        height: 35px;
        grid-area: name;
    }

    .name h1 {
        margin-top: 7px;
    }

    .minutes h2, .minutes h4, .km h2, .km h4 {
        margin: 10px;
    }

    .minutes,  .km {
        position: relative;
        top: 25px;
        width: fit-content;
        height: fit-content;
        margin: 0 auto;
        border-radius: 5px;
    }

    .minutes {
        background-image: linear-gradient(to left, #999, #eee);
        grid-area: minutes;
        color: #111;
    }

    .km {
        background-image: linear-gradient(to right, #666, #111);
        grid-area: km;
        color: #eee;
    }

    .egg {
        grid-area: egg;
        image-rendering: pixelated;
        margin: auto 0px auto auto;
        height: 72px;
    }

    .mobName {
        grid-area: name;
        text-align: left;
        margin: 4px auto auto 10px;
    }

    .mobValue {
        grid-area: number;
        text-align: left;
        margin: auto auto 4px 10px;
        font-size: 24px;
    }

    .killed, .died {
        background-color: var(--color);
        color: var(--invert);
        margin: auto;
        padding: 10px;
        width: fit-content;
        display: grid;
        grid-template-areas:
            "egg number"
            "egg name";
        grid-template-columns: 1fr 2fr;
        grid-template-rows: 1fr 1fr;
        border-radius: 10px;
        border: 2px solid var(--invert);
        box-shadow: 0 0 3px 3px var(--invert);
    }

    .killed {
        grid-area: killed;
    }

    .died {
        grid-area: died;
    }

    .sessions {
        text-align: right;
        grid-area: sessions;
        padding: 20px 0;
    }

    .logo {
        display: flex;
        grid-area: logo;
    }

    .sessions h2 {
        margin: 10px;
    }

    .sessions h3 {
        margin: 0 10px 0 auto;
    }

    h1 {
        color: #111;
        text-shadow: -3px -3px 5px #eee;
        margin: 0;
    }

    .yir-logo {
        margin: auto auto auto 10px;
        height: 60px;
        border-radius: 4px;
    }

    .border {
        display: grid;
        grid-template-areas:
            "banner banner"
            "name name"
            "minutes km"
            "killed killed"
            "died died"
            "sessions logo";
        grid-template-columns: 1fr 1fr;
        grid-template-rows: 2fr 1fr 4fr 4fr 4fr 3fr;

        margin: 20px;
        width: 420px;
        height: 540px;
        padding: 10px;

        border-radius: 20px;

        box-shadow:
            0 0 0 5px #ff00b6,
            0 0 0 10px #078b0f,
            0 0 0 15px #ec5800,
            0 0 0 20px #16e5e5;
    }

    #shot-container {
        overflow: hidden;
        position: relative;
        display: flex;
        width: 480px;
        height: 600px;

        font-family: Monaco, "Lucida Console", monospace;
    }
</style>
