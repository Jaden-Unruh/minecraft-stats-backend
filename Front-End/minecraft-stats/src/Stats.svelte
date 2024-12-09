<script>
	let stats = [];
	let error = null;
	
	async function fetchStats() {
		try {
			const res = await fetch("http://localhost:8080/api/stats");
			if (!res.ok) throw new Error("Failed to fetch stats");
			stats = await res.json();
		} catch (err) {
			error = err.message;
		}
	}
	
	fetchStats();
</script>

<style>
	.error {
		color: red;
	}
</style>

<main>
	{#if error}
		<p class="error">{error}</p>
	{:else if stats.length === 0}
		<p>Loading...</p>
	{:else}
		<ul>
			{#each stats as player}
				<li>{player.name}: {player.score}</li>
			{/each}
		</ul>
	{/if}
</main>