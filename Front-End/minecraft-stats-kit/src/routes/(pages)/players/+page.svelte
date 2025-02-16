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
		display: grid;
		grid-template-columns: 2fr 1fr 1fr;
		gap: 5px;
		grid-template-rows: 1fr 1fr;
		justify-content: space-between;
		padding: 0.5rem 0;
		border-bottom: 2px solid #fa0;
	}
	
	.player-item:last-child {
		border-bottom: none;
	}
	
	.player-name {
		grid-column: 1;
		grid-row: 1;
		font-weight: bold;
		font-size: 24px;
		margin: 0;
	}
	
	.player-uuid {
		grid-column: 1;
		grid-row: 2;
	}
	
	.button {
		grid-column: 3;
		grid-row: 1 / 3;
		display: inline-block;
		border-radius: 4px;
		background-color: #ccc;
		border: 3px solid #a0a;
		color: #000;
		text-align: center;
		font-size: 16px;
		padding: 10px;
		width: 100%;
		transition: all 0.5s;
		cursor: pointer;
		margin: 5px;
		overflow: hidden;
	}
	
	.button:hover {
		background-color: #a0a;
		color: #fff;
	}
	
	.button span {
		cursor: pointer;
		display: inline-block;
		position: relative;
		transition: 0.5s;
	}
	
	.button span:after {
		content: '\00bb';
		position: absolute;
		opacity: 0;
		top: 0;
		right: -10px;
		transition: 0.5s;
		color: #fa0;
	}
	
	.button:hover span {
		padding-right: 10px;
	}
	
	.button:hover span:after {
		opacity: 1;
		right: 0;
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
				<div class="player-name">{name}</div>
				<div class="player-uuid">{uuid}</div>
				<button type="button" class="button" on:click={() => {location.href='/players/' + name;}}><span>View {name}</span></button>
			</div>
		{/each}
	{/if}
</div>