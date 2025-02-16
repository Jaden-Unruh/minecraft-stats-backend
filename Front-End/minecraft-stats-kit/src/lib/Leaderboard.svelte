<script>
	export let name;
	export let key;
	export let location;
	export let formatter;
	export let allStats = [];
	
	let topPlayers = [];
	
	$: computeLeaderboard();
	
	function computeLeaderboard() {
		topPlayers = allStats
			.map((player) => {
				let value = 0;
				if (location === "computed") {
					value = player[key] || 0;
				} else {
					value = player[location]?.[key] || 0;
				}
				return {
					name: player.name,
					value,
				};
			})
			.sort((a, b) => b.value - a.value)
			.slice(0, 5);
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
</style>

<div class="leaderboard">
	<h2>{name}</h2>
	<ul>
		{#each topPlayers as { name, value }}
			<li>
				<span>{name}</span>
				<span>{formatter(value)}</span>
			</li>
		{/each}
	</ul>
</div>