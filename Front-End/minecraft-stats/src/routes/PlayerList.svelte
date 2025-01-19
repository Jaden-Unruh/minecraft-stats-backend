<script>
	import { onMount} from "svelte";
	
	let players = [];
	let loading = true;
	let error = null;
	
	const fetchPlayers = async () => {
		try {
			const response = await fetch("/api/players");
			if (!response.ok) throw new Error(`Error: ${response.statusText}`);
			const data = await response.json();
			
			players = Object.entries(data).map(([uuid, name]) => ({ uuid, name }));
		} catch (err) {
			error = err.message;
		} finally {
			loading = false;
		}
	};
	
	onMount(fetchPlayers);
</script>

<style>
	.player-list {
		margin: 2rem auto;
		max-width: 600px;
		padding: 1rem;
		border: 1px solid #ddd;
		border-radius: 8px;
		background: #f9f9f9;
	}
	
	.player-item {
		display: flex;
		justify-content: space-between;
		padding: 0.5rem 0;
		border-bottom: 1px solid #ddd;
	}
	
	.player-item:last-child {
		border-bottom: none;
	}
</style>

<div class="player-list">
	{#if loading}
		<p>Loading players...</p>
	{:else if error}
		<p>Error loading players: {error}</p>
	{:else if players.length === 0}
		<p>No players found.</p>
	{:else}
		{#each players as {uuid, name}}
			<div class="player-item">
				<table>
				<tr><th><strong>Name:</strong> {name};</th><th><strong>UUID:</strong> {uuid}</th></tr>
				<tr><td><a href={`/stats/${name}`}>view player's stats</a></td><td><a href={`/inventory/${name}`}>view player's inventory</a></td></tr>
				</table>			
			</div>
		{/each}
	{/if}
</div>