<script>
    import "$lib/yir/yirSlide.css";

    export let textVisible;
    export let info;
</script>

<div class="fade-container" class:visible={textVisible}>
    <h3>They're out to get you...</h3>
    <h2>You've died to</h2>
    <h2>{info.mostDied.name.replace("_", " ")}</h2>
    <h1>{info.mostDied.value.toLocaleString()}</h1>
    <h3>times</h3>
</div>