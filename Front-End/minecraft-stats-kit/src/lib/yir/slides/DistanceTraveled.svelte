<script>
    export let textVisible;
    export let info;
    import "$lib/yir/yirSlide.css";
</script>

<div class="fade-container" class:visible={textVisible}>
    <h3>You really got around. You've traveled</h3>
    <h2 class="hover">
        <span class="hoverPre">{(info.distances.total / 100_000).toFixed(2)} kilometers</span>
        <span class="hoverPost">{info.distances.total} cm</span>
    </h2>
    <h3>That's as far as to walk from Seattle to Portland</h3>
    <h2>{(info.distances.total / 27_996_600).toFixed(2)} times</h2>
    <h5>*note that some travel is likely double-counted*</h5>
</div>
