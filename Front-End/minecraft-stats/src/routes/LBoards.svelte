<script>
	import { onMount } from 'svelte';
	import Leaderboard from '../components/Leaderboard.svelte';
	
	let allStats = [];
	let players = [];
	let loading = true;
	let error = null;
	
	const categories = [
		{ name: "Playtime", key: "minecraft:play_time", location: "custom", formatter: (v) => `${(v / 72000).toFixed(1)} hours` },
		{ name: "Distance Walked", key: "minecraft:walk_one_cm", location: "custom", formatter: (v) => `${(v / 100000).toFixed(2)} km`},
		{ name: "Distance by Elytra", key: "minecraft:aviate_one_cm", location: "custom", formatter: (v) => `${(v / 100000).toFixed(2)} km`},
		{ name: "Mob Kills", key: "minecraft:mob_kills", location: "custom", formatter: (v) => v },
		{ name: "Deaths", key: "minecraft:deaths", location: "custom", formatter: (v) => v },
		{ name: "Blocks Mined", key: "total_blocks_mined", location: "computed", formatter: (v) => v },
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
				const blocksMined = Object.values(playerStats["stats"]["minecraft:mined"] || {}).reduce((sum, val) => sum + val, 0);
				return {
					uuid,
					name: players[uuid],
					custom: playerStats["stats"]["minecraft:custom"] || {},
					total_blocks_mined: blocksMined,
				};
			});
			
			console.log(allStats);
		} catch (err) {
			error = err.message;
			console.error(err.stack);
		} finally {
			loading = false;
		}
	});
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
</style>

<div>
	{#if loading}
		<p>Loading leaderboards...</p>
	{:else if error}
		<p>Error: {error}</p>
	{:else}
		<h1>Leaderboards</h1>
		<div class="grid">
			{#each categories as {name, key, location, formatter }}
				<Leaderboard {name} {key} {location} {formatter} {allStats} />
			{/each}
		</div>
	{/if}
</div>