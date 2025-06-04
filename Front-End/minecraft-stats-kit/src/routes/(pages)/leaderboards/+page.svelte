<script>
	import { onMount } from 'svelte';
	import Leaderboard from '$lib/Leaderboard.svelte';
	import CustomLeaderboard from '$lib/CustomLeaderboard.svelte';
	import { Autocomplete } from '@skeletonlabs/skeleton';
	import { AutocompleteOption } from '@skeletonlabs/skeleton';
	
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
		{ name: "Blocks Placed", key: "total_blocks_placed", location: "computed", formatter: (v) => v }
	];
	
	let categoryInput = '';
	
	const statCategoryOptions = [
		{ label: 'General', value: 'custom', keywords: 'custom, general' },
		{ label: 'Picked Up', value: 'picked_up', keywords: 'items, blocks' },
		{ label: 'Used', value: 'used', keywords: 'placed, items, blocks' },
		{ label: 'Killed', value: 'killed', keywords: 'mobs, monsters, entities' },
		{ label: 'Dropped', value: 'dropped', keywords: 'items, blocks' },
		{ label: 'Mined', value: 'mined', keywords: 'items, blocks' },
		{ label: 'Crafted', value: 'crafted', keywords: 'items, blocks' },
		{ label: 'Killed By', value: 'killed_by', keywords: 'mobs, monsters, entities' },
		{ label: 'Broken', value: 'broken', keywords: 'items, blocks' }
	];
	
	let allStatsNames = [];
	
	let selectedCategory = '';
	let selectedKey = '';
	
	let keyInput = '';
	
	let isSelection = false;
	
	let statKeyOptions = [];
	
	let unique = {};
	
	function onCategorySelection(event) {
		categoryInput = event.detail.label;
		selectedCategory = event.detail.value;
		categoryFocused = false;
		console.log(selectedCategory);
		statKeyOptions = allStatsNames.filter(item => item.category === ("minecraft:" + event.detail.value)).map(item => ({
					label: item.key.replace("minecraft:", "").split('_').map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' '),
					value: item.key.replace("minecraft:", "")
				}));
		console.log(statKeyOptions);
		unique = {};
	}
	
	function onKeySelection(event) {
		keyInput = event.detail.label;
		selectedKey = event.detail.value;
		keyFocused = false;
		console.log(event.detail.value);
		isSelection = true;
		unique = {};
	}
	
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
					total_blocks_mined: blocksMined,
					total_blocks_placed: blocksPlaced,
					used: usedStats
				};
			});
			
			const nResponse = await fetch("/api/listStats");
			if (!response.ok) throw new Error("Failed to fetch stat names");
			allStatsNames = await nResponse.json();
			
			console.log(allStats);
		} catch (err) {
			error = err.message;
			console.error(err.stack);
		} finally {
			loading = false;
		}
	});
	
	let categoryFocused = false;
	let keyFocused = false;
	
	function handleFocus(inputType, focused) {
		if (inputType === "category") categoryFocused = focused;
		if (inputType === "key") keyFocused = focused;
	}
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
	
	.input {
		width: 100%;
		max-width: 400px;
		padding: 12px;
		font-size: 16px;
		color: #333;
		background-color: #f4f4f4;
		border: 2px solid #999;
		border-radius: 8px;
		outline: none;
		transition: all 0.2s ease-in-out;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.15);
	}
	
	.input:focus {
		border-color: #007bff;
		background-color: white;
		box-shadow: 0 0 8px rgba(0, 123, 255, 0.5);
	}
	
	.input::placeholder {
		color: #777;
		font-style: italic;
		opacity: 0.8;
	}
	
	.input:not(:last-child) {
		margin-bottom: 12px;
	}
	
	.input:hover {
		background-color: #eaeaea;
	}
	
	.customLeaderboard {
		position: relative;
		width: 80%;
		max-width: 400px;
		margin: 20px auto;
		display: flex;
		flex-direction: column;
		align-items: center;
	}
	
	:global(.style-autocomplete) {
		width: 100%;
		padding: 10px;
		border: 2px solid #ccc;
		border-radius: 6px;
		font-size: 16px;
		background-color: white;
		transition: border 0.3s ease-in-out;	
	}
	
	:global(.style-autocomplete:focus) {
		border-color: #007bff;
		outline: none;
	}
	
	:global(.style-autocomplete ul) {
		width: 100%;
		border: 1px solid #ccc;
		background: white;
		box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
		border-radius: 6px;
		padding: 0;
		margin: 5px 0;
		list-style: none;
	}

	:global(.customLeaderboard li button) {
		width: 100%;
		height: 100%;
		padding: 8px;
		cursor: pointer;
		transition: all 0.2s;
		font-size: 16px;
	}

	:global(.style-autocomplete li button:hover) {
		background-color: #a0a;
		color: white;
		border: 3px solid #fa0;
	}
</style>

<svelte:head>
	<title>Leaderboards</title>
</svelte:head>

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
			{#if isSelection}
				{#key unique}
					<CustomLeaderboard {selectedCategory} {selectedKey} />
				{/key}
			{/if}
		</div>
		<center>
			<div class="customLeaderboard">
				<h2>View a custom leaderboard</h2>
				<input
					class="input"
					type="search"
					name="category"
					bind:value={categoryInput}
					placeholder="Search categories..."
					on:focus={() => handleFocus("category", true)}
					on:blur={() => setTimeout(() => { if (categoryFocused) handleFocus("category", false); }, 150)}
				/>
				{#if categoryFocused}
					<Autocomplete
						bind:input={categoryInput}
						options={statCategoryOptions}
						on:selection={onCategorySelection}
						class="style-autocomplete"
					/>
				{/if}
				{#if selectedCategory !== ''}
					<input
						class="input"
						type="search"
						name="key"
						bind:value={keyInput}
						placeholder="Search keys..."
						on:focus={() => handleFocus("key", true)}
						on:blur={() => setTimeout(() => { if (keyFocused) handleFocus("key", false); }, 150)}
					/>
					{#if keyFocused}
						<Autocomplete
							bind:input={keyInput}
							options={statKeyOptions}
							on:selection={onKeySelection}
							class="style-autocomplete"
						/>
					{/if}
				{/if}
			</div>
		</center>
	{/if}
</div>