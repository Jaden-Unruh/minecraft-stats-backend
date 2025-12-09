<script>
    export let textVisible;
    export let info;
    import "$lib/yir/yirSlide.css";

    const dayFormatter = new Intl.NumberFormat("en-US", {
        style: "unit",
        unit: "day",
        unitDisplay: "long"
    });
    const hourFormatter = new Intl.NumberFormat("en-US", {
        style: "unit",
        unit: "hour",
        unitDisplay: "long"
    });
    const minFormatter = new Intl.NumberFormat("en-US", {
        style: "unit",
        unit: "minute",
        unitDisplay: "long"
    });
    const secFormatter = new Intl.NumberFormat("en-US", {
        style: "unit",
        unit: "second",
        unitDisplay: "long"
    });

    let days = Math.floor(info.playTime / 1728000);
    let hours = Math.floor(info.playTime / 72000) % 24;
    let longminutes = Math.floor(info.playTime / 1200);
    let minutes = Math.floor(info.playTime / 1200) % 60;
    let seconds = (info.playTime / 20) % 60;

    let filmTimes = (info.playTime / 858000).toFixed(2) + " times";
</script>

<div class="fade-container" class:visible={textVisible}>
    <h3>Over the past 15 months, you played on J3L+ on</h3>
    <h2>{info.sessions} occasion{(info.sessions != 1) ? "s" : ""}</h2>
    <h3>For a total of</h3>
    <h2 class=hover>
        <span class="hoverPre">{minFormatter.format(longminutes)}</span>
        <span class="hoverPost">{days > 0 ? dayFormatter.format(days) + " " : ""}{hourFormatter.format(hours)} {minFormatter.format(minutes)} {secFormatter.format(seconds)}</span>
    </h2>
    <h3>That's enough time to watch all 3 extended Lord of the Rings films</h3>
    <h2>{filmTimes}</h2>
</div>