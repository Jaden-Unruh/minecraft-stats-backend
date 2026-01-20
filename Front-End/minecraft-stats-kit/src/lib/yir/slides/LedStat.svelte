<script>
    export let textVisible;
    export let ledStatInfo;
    import "$lib/yir/yirSlide.css";
    import CustomLeaderboard from "$lib/CustomLeaderboard.svelte";

    let category = ledStatInfo.split("/")[1];
    let key = ledStatInfo.split("/")[0];

    const verb = {
        "used" : "Using",
        "killed by" : "Being killed by",
        "dropped" : "Dropping",
        "mined" : "Mining",
        "killed" : "Killing",
        "crafted" : "Crafting",
        "picked_up" : "Picking up"
    }[category] || "Unknown";
</script>

<div class="fade-container" class:visible={textVisible}>
    <h3>In 2025, you led the server in one stat in particular:</h3>
    <CustomLeaderboard selectedCategory={category} selectedKey={key} showChart={true} showBoard={false} />
</div>