<script>
    import "$lib/yir/yirSlide.css";

    export let textVisible;
    export let info;
</script>

<div class="fade-container" class:visible={textVisible}>
    <h3>You've had some violent tendencies...</h3>
    <h2>You've killed</h2>
    <h1>{info.mostKilled.value.toLocaleString()}</h1>
    <h2>{info.mostKilled.name.replace("_", " ")}</h2>
</div>