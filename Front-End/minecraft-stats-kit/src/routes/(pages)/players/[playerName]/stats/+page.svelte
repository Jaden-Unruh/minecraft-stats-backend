<script>
	import { onMount } from "svelte";
	import { page } from "$app/stores";
	
	let playerName;
	let selectedCategory = "general";
	let stats = {};
	let loading = true;
	let error = null;
	
	let generalStats = [];
	let itemStats = [];
	let mobStats = [];
	
	const fetchStats = async () => {
		$: playerName = $page.params.playerName;
		console.log(playerName);
	
		try{
			const response = await fetch(`/api/stats/${playerName}`);
			if (!response.ok) throw new Error("Failed to fetch stats");
			const data = await response.json();
			
			processGeneralStats(data["minecraft:custom"] || {});
			processItemStats(data);
			processMobStats(data);
		} catch (err) {
			error = err.message;
		} finally {
			loading = false;
		}
	};
	
	const processGeneralStats = (data) => {
			//Extract and format `minecraft:custom` stats
			
			generalStats = Object.entries(data)
				.sort((a, b) => b[1] - a[1])
				.map(([key, value]) => ({
					name: convertStatName(key.replace("minecraft:", "")),
					value: convertStatValue(key.replace("minecraft:", ""), value),
				}));
	}
	
	const processItemStats = (data) => {
		const categories = ["minecraft:broken", "minecraft:used", "minecraft:dropped", "minecraft:crafted", "minecraft:mined", "minecraft:picked_up"];
		const items = {};
		
		for (const category of categories) {
			const itemCategory = data[category] || {};
			for (const [key, value] of Object.entries(itemCategory)) {
				const itemName = key.replace("minecraft:", "").replace(/_/g, " ");
				if (!items[itemName]) {
					items[itemName] = { broken: 0, used: 0, dropped: 0, crafted: 0, mined: 0, picked_up: 0};
				}
				items[itemName][category.replace("minecraft:", "")] = value;
			}
		}
		
		itemStats = Object.entries(items)
			.map(([name, counts]) => ({
				name,
				...counts,
				total: Object.values(counts).reduce((a, b) => a + b, 0),
			}))
			.sort((a, b) => b.total - a.total);
	};
	
	const processMobStats = (data) => {
		const categories = ["minecraft:killed", "minecraft:killed_by"];
		const mobs = {};
		
		for (const category of categories) {
			const mobCategory = data[category] || {};
			for (const [key, value] of Object.entries(mobCategory)) {
				const mobName = key.replace("minecraft:", "").replace(/_/g, " ");
				if (!mobs[mobName]) {
					mobs[mobName] = { killed: 0, killed_by: 0};
				}
				mobs[mobName][category.replace("minecraft:", "")] = value;
			}
		}
		
		mobStats = Object.entries(mobs)
			.map(([name, counts]) => ({
				name,
				...counts,
				total: Object.values(counts).reduce((a, b) => a + b, 0),
			}))
			.sort((a, b) => b.total - a.total);
	};
	
	const convertStatName = (key) => {
		if (key === "play_time") return "Hours played";
		if (key === "time_since_death") return "Minutes since death";
		if (key === "time_since_rest") return "Minutes since sleep";
		if (key === "sneak_time") return "Hours sneaked";
		if (key === "total_world_time") return "Hours logged in";
		if (key === "swim_one_cm") return "Kilometers swam";
		if (key === "fly_one_cm") return "Kilometers traveled forward and up more than 1 block above the ground";
		if (key === "fall_one_cm") return "Kilometers fallen";
		if (key === "sprint_one_cm") return "Kilometers sprinted";
		if (key === "walk_on_water_one_cm") return "Kilometers bobbed on water";
		if (key === "crouch_one_cm") return "Kilometers crouched";
		if (key === "climb_one_cm") return "Kilometers climbed";
		if (key === "walk_one_cm") return "Kilometers walked";
		if (key === "walk_under_water_one_cm") return "Kilometers walked underwater";
		if (key === "boat_one_cm") return "Kilometers boated";
		if (key === "minecart_one_cm") return "Kilometers carted";
		if (key === "aviate_one_cm") return "Kilometers by elytra";
		if (key === "strider_one_cm") return "Kilometers Strided";
		if (key === "pig_one_cm") return "Kilometers hogged";
		return key.replace(/_/g, " ");
	}
	
	const convertStatValue = (key, value) => {
		if (key === "play_time") return `${(value / 72000).toFixed(1)}`;
		if (key === "time_since_death") return `${(value / 1200).toFixed(1)}`;
		if (key === "time_since_death") return `${(value / 1200).toFixed(1)}`;
		if (key === "sneak_time") return `${(value / 72000).toFixed(1)}`;
		if (key === "total_world_time") return `${(value / 72000).toFixed(1)}`;
		if (key === "swim_one_cm") return `${(value / 100000).toFixed(2)}`;
		if (key === "fly_one_cm") return `${(value / 100000).toFixed(2)}`;
		if (key === "fall_one_cm") return `${(value / 100000).toFixed(2)}`;
		if (key === "sprint_one_cm") return `${(value / 100000).toFixed(2)}`;
		if (key === "walk_on_water_one_cm") return `${(value / 100000).toFixed(2)}`;
		if (key === "crouch_one_cm") return `${(value / 100000).toFixed(2)}`;
		if (key === "climb_one_cm") return `${(value / 100000).toFixed(2)}`;
		if (key === "walk_one_cm") return `${(value / 100000).toFixed(2)}`;
		if (key === "walk_under_water_one_cm") return `${(value / 100000).toFixed(2)}`;
		if (key === "boat_one_cm") return `${(value / 100000).toFixed(2)}`;
		if (key === "minecart_one_cm") return `${(value / 100000).toFixed(2)}`;
		if (key === "aviate_one_cm") return `${(value / 100000).toFixed(2)}`;
		if (key === "strider_one_cm") return `${(value / 100000).toFixed(2)}`;
		if (key === "pig_one_cm") return `${(value / 100000).toFixed(2)}`;
		return value;
	}
	
	onMount(fetchStats);
</script>

<style>
	.stats-container {
		max-width: 800px;
		margin: 2rem auto;
		padding: 1rem;
		background: #f9f9f9;
		border-radius: 8px;
		box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
	}
	
	.dropdown {
		margin-bottom: 1rem;
	}
	
	.stats-table {
		width: 100%;
		border-collapse: collapse;
	}
	
	.stats-table th,
	.stats-table td {
		padding: 0.5rem;
		border: 1px solid #333;
		text-align: left;
	}
	
	.stats-table th {
		background-color: #f0f0f0;
	}
	
	.stat-row {
		display: flex;
		justify-content: space-between;
		padding: 0.5rem 0;
		border-bottom: 1px solid #333;
	}
	
	.stat-row:last-child {
		border-bottom: none;
	}
	
	.stat-name {
		text-transform: capitalize;
		font-weight: bold;
	}
	
	.stat-value {
		color: #555;
	}
</style>

<svelte:head>
	<title>{playerName}'s stats</title>
</svelte:head>

<div class="stats-container">
	{#if loading}
		<p>Loading stats for {playerName}...</p>
	{:else if error}
		<p>Error: {error}</p>
	{:else}
		<h2>{playerName}'s Stats</h2>
		<div class="dropdown">
			<select bind:value={selectedCategory}>
				<option value="general">General Stats</option>
				<option value="items">Item Stats</option>
				<option value="mobs">Mob Stats</option>
			</select>
		</div>
		
		{#if selectedCategory === "general"}
			<h2>General Statistics</h2>
			{#each generalStats as { name, value }}
				<div class="stat-row">
					<span class="stat-name">{name}</span>
					<span class="stat-value">{value}</span>
				</div>
			{/each}
		{:else if selectedCategory === "items"}
			<h2>Item Statistics</h2>
			<table class="stats-table">
				<thead>
					<tr>
						<th>Item</th>
						<th>Broken</th>
						<th>Used</th>
						<th>Dropped</th>
						<th>Crafted</th>
						<th>Mined</th>
						<th>Picked up</th>
						<th>Total</th>
					</tr>
				</thead>
				<tbody>
					{#each itemStats as { name, broken, used, dropped, crafted, mined, picked_up, total }}
						<tr>
							<td>{name}</td>
							<td>{broken}</td>
							<td>{used}</td>
							<td>{dropped}</td>
							<td>{crafted}</td>
							<td>{mined}</td>
							<td>{picked_up}</td>
							<td>{total}</td>
						</tr>
					{/each}
				</tbody>
			</table>
		{:else if selectedCategory === "mobs"}
			<h2>Mob Statistics</h2>
			<table class="stats-table">
				<thead>
					<tr>
						<th>Mob</th>
						<th>Killed</th>
						<th>Killed By</th>
						<th>Total</th>
					</tr>
				</thead>
				<tbody>
					{#each mobStats as { name, killed, killed_by, total }}
						<tr>
							<td>{name}</td>
							<td>{killed}</td>
							<td>{killed_by}</td>
							<td>{total}</td>
						</tr>
					{/each}
				</tbody>
			</table>
		{/if}
	{/if}
</div>