<script>
    import RatioLeaderboard from '$lib/RatioLeaderboard.svelte';
    import { onMount } from 'svelte';

    let allStats = [];
    let players = [];
    let loading = true;
    let error = null;

    const categories = [
        { name: "Deaths per play hour", key1: "minecraft:deaths", location1: "custom", key2: "minecraft:play_time", location2: "custom", multiplier: 72_000 },
        { name: "Blocks mined per block placed", key1: "total_blocks_mined", location1: "computed", key2: "total_blocks_placed", location2: "computed", multiplier: 1 },
        { name: "Damage dealt per damage taken", key1: "minecraft:damage_dealt", location1: "custom", key2: "minecraft:damage_taken", location2: "custom", multiplier: 1 },
        { name: "Rockets crafted per elytra km", key1: "minecraft:firework_rocket", location1: "crafted", key2: "minecraft:aviate_one_cm", location2: "custom", multiplier: 100_000 },
        { name: "Player KDR", key1: "minecraft:player", location1: "killed", key2: "minecraft:player", location2: "killed_by", multiplier: 1 },
        { name: "Hours played per session", key1: "minecraft:play_time", location1: "custom", key2: "minecraft:leave_game", location2: "custom", multiplier: (1/72_000) },
        { name: "Km walked per elytra km", key1: "minecraft:walk_one_cm", location1: "custom", key2: "minecraft:aviate_one_cm", location2: "custom", multiplier: 1}
    ];

    onMount(async () => {
        try {
            const response = await fetch("/api/allStats");
            if (!response.ok) throw new Error("Failed to fetch stats");
            const statsData = await response.json();

            console.log(statsData);
            const pResponse = await fetch("/api/players");
            if (!pResponse.ok) throw new Error("Failed to fetch players");
            players = await pResponse.json();

            allStats = Object.entries(statsData).map(([uuid, playerStats]) => {

				const minedStats = playerStats["stats"]["minecraft:mined"] || {};
				const usedStats = playerStats["stats"]["minecraft:used"] || {};

				console.log(minedStats);
				console.log(usedStats);

				const blocksMined = Object.values(minedStats).reduce((sum, val) => sum + val, 0);
				const blocksPlaced = Object.entries(usedStats)
					.filter(([block, _]) => minedStats[block])
					.reduce((sum, [_, val]) => sum + val, 0);

				return {
					uuid,
					name: players[uuid],
					custom: playerStats["stats"]["minecraft:custom"] || {},
                    killed: playerStats["stats"]["minecraft:killed"] || {},
                    killed_by: playerStats["stats"]["minecraft:killed_by"] || {},
                    crafted: playerStats["stats"]["minecraft:crafted"] || {},
					total_blocks_mined: blocksMined,
					total_blocks_placed: blocksPlaced
				};
			});
        } catch (error) {
            console.error("Error fetching data: ", error);
        } finally {
            loading = false;
        }
    })
</script>

<style>
    .grid {
		display: grid;
		grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
		gap: 1rem;
		margin-top: 1rem;
	}
	
	h1 {
		text-align: center;
		margin-bottom: 1rem;
	}

	h2 {
		text-align: center;
		margin-bottom: 1rem;
	}
</style>

<div>
    {#if loading}
        <p>Loading ratio leaderboards...</p>
    {:else if error}
        <p>Error: {error}</p>
    {:else}
        <h1>Ratio leaderboards</h1>
        <div class="grid">
            {#each categories as {name, key1, location1, key2, location2, multiplier}}
                <RatioLeaderboard {name} {key1} {location1} {key2} {location2} {multiplier} {allStats}/>
            {/each}
        </div>
        <p>These leaderboards are designed to give less benefit to those with higher play time. Some players may evaluate to zero or infinity if they haven't done one of the criterion (e.g. flown with elytra). If they haven't done either, they won't be listed.</p>
    {/if}
</div>