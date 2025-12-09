<script>
    import "$lib/yir/yirSlide.css";

    export let textVisible;
    export let playerName;
</script>

<div class="fade-container" class:visible={textVisible}>
    <h2>Hi, {playerName}.</h2>
    <h3>What a year+ it's been.</h3>
    <br />
    <h3>I've compiled some statistics about what you did on the server since it started ~15 months ago. Just a bit over a year.<br />Next December, if the server's still around, I'll recompile the data as it's changed since the New Year.</h3>
</div>