<script>
	import { onMount } from "svelte";
	
	let backups = [];
	let loading = true;
	let error = null;
	
	const fetchBackups = async () => {
		try {
			const response = await fetch("/api/backups");
			if (!response.ok) throw new Error(`Error: ${response.statusText}`);
			backups = await response.json();
		} catch (err) {
			error = err.message;
		} finally {
			loading = false;
		}
	};
	
	onMount(fetchBackups);
</script>

<style>
	.backup-list {
		margin: 2rem auto;
		max-width: 600px;
		padding: 1rem;
		border: 1px solid #ddd;
		border-radius: 8px;
		background: #f9f9f9;
	}
	
	.backup-item {
		display: flex;
		justify-content: space-between;
		padding: 0.5rem 0;
		border-bottom: 1px solid #ddd;
	}
	
	.backup-item:last-child {
		border-bottom: none;
	}
</style>

<p>Backups are currently run manually, and this list will be updated each time I make a new one.<br>In the future, this may be automated.</p>
<div class="backup-list">
	{#if loading}
		<p>Loading backups...</p>
	{:else if error}
		<p>Error loading backups: {error}</p>
	{:else}
		{#each backups as backup}
			<div class = "backup-item">
				<span>On {backup.Date} at {backup.Time}: <a href={backup.Link}>Google Drive🔗</a>; {backup.Size}</span>
			</div>
		{/each}
	{/if}
</div>