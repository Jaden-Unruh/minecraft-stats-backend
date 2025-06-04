<script>
	import { onMount } from "svelte";
	import { page } from "$app/stores";
	
	let playerName;
	let loading = true;
	let error = null;
	
	let playerAdvancements = [];
	
	const fetchAdvancements = async () => {
		$: playerName = $page.params.playerName;
		console.log(playerName);
	
		try {
			const playerResponse = await fetch(`/api/advancements/${playerName}`);
			if (!playerResponse.ok) throw new Error("Failed to fetch advancements");
			const playerData = await playerResponse.json();
			
			const advancementsResponse = await fetch("/api/allAdvancementData");
			if (!advancementsResponse.ok) throw new Error("Failed to fetch advancements");
			const advancementsData = await advancementsResponse.json();
			
			playerAdvancements = Object.entries(playerData)
				.map(([advancementId, timestamp]) => {
					const advancementInfo = advancementsData[advancementId] || {
						title: "Unknown",
						description: "None",
					};
					
					return {
						id: advancementId,
						title: advancementInfo.title,
						description: advancementInfo.description,
						earnedAt: timestamp,
					};
				})
				.sort((a, b) => new Date(a.earnedAt) - new Date(b.earnedAt));
		} catch (err) {
			console.error(err);
			error = err.message;
		} finally {
			loading = false;
		}
	}
	
	onMount(fetchAdvancements);
</script>

<style>
	.advancements-div {
		margin: 2rem auto;
		max-width: 600px;
		padding: 1rem;
		border: 3px solid #fa0;
		border-radius: 8px;
	}

	.advancement-list {
		list-style-type: none;
		padding: 0;
		margin: 0;
	}
	
	.advancement-item {
		margin-bottom: 1rem;
		padding: 1rem;
		border: 1px solid #a0a;
		border-radius: 5px;
		background-color: #ddd;
	}
	
	.advancement-title {
		font-weight: bold;
		font-size: 1.2rem;
		margin-bottom: 0.5rem;
	}
	
	.advancement-description {
		margin-bottom: 0.5rem;
		font-style: italic;
	}
	
	.advancement-timestamp {
		color: #666;
		font-size: 0.9rem;
	}
</style>

<svelte:head>
	<title>{playerName}'s advancements</title>
</svelte:head>

<div class="advancements-div">
	{#if loading}
		<p>Loading advancements for {playerName}...</p>
	{:else if error}
		<p>Error: {error}</p>
	{:else}
		<h2>{playerName}'s Advancements</h2>
		<ul class="advancement-list">
			{#each playerAdvancements as advancement}
				<li class="advancement-item">
					<div class="advancement-title">{advancement.title}</div>
					<div class="advancement-description">{advancement.description}</div>
					<div class="advancement-timestamp">{advancement.earnedAt}</div>
				</li>
			{/each}
		</ul>
	{/if}
</div>