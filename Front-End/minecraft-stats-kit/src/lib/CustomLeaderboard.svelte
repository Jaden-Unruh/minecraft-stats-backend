<script>
	import PieChart from "./PieChart.svelte";

	export let selectedCategory;
	export let selectedKey;
	export let showChart = false;
	export let showBoard = true;
	
	let entries = [];

	let loaded = false;
	
	$: fetchData();
	
	async function fetchData() {
		if ((!(selectedCategory === '')) && (!(selectedKey === ''))) {
			const response = await fetch("/api/leaderboard/" + selectedCategory + "/" + selectedKey);
			if (!response.ok) throw new Error("Failed to fetch custom leaderboard");
			let json = await response.json();
			entries = Object.entries(json)
					.map(([name, value]) => ({ name, value }))
					.sort((a, b) => b.value - a.value);
		}

		loaded = true;
	}
</script>

<style>
	.leaderboard {
		border: 1px solid #ddd;
		border-radius: 8px;
		padding: 1rem;
		background: #f9f9f9;
		box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	}
	
	h2 {
		margin-bottom: 0.5rem;
		font-size: 1.2rem;
		text-align: center;
	}
	
	ul {
		list-style: none;
		padding: 0;
		margin: 0;
	}
	
	li {
		display: flex;
		justify-content: space-between;
		padding: 0.5rem 0;
		border-bottom: 1px solid #eee;
	}
	
	li:last-child {
		border-bottom: none;
	}
	
	a {
		color: black;
		text-decoration: none;
	}
</style>

<div class="leaderboard">
	<h2>{selectedCategory.replaceAll("_", " ").split(' ').map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ')}: {selectedKey.replaceAll("_", " ").split(' ').map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ')}</h2>
	{#if showBoard}
		<ul>
			{#each entries as { name, value }}
				<li>
					<span><a href={'/players/' + name}>{name}</a></span>
					<span>{value.toLocaleString()}</span>
				</li>
			{/each}
		</ul>
	{/if}
	{#if loaded}
		{#if showChart}
			<PieChart selectedCategory={selectedCategory} selectedKey={selectedKey} entries={entries} />
		{/if}
	{/if}
</div>