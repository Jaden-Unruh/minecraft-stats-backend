<script>
	import { onMount } from 'svelte';
	
	let serverInfo = null;
	let loading = true;
	let error = null;
	
	const serverAddress = "j3lsmp.org";
	
	let lastStatsUpdate = null;
	let secondsSinceStatsUpdate = 0;
	let canStatsUpdate = false;
	let interval;
	
	async function fetchStatus() {
		try {
			loading = true;
			error = null;
		
			const response = await fetch(`https://api.mcstatus.io/v2/status/java/${serverAddress}`);
			if (!response.ok) throw new Error("Failed to fetch server status");
			
			serverInfo = await response.json();
		} catch (err) {
			error = err.message;
			console.error(err);
		} finally {
			loading = false;
		}
	}
	
	async function fetchLastStatsUpdate() {
		try {
			const res = await fetch("/api/lastStatsUpdate");
			if (!res.ok) throw new Error("Failed to fetch last stats update");
			const data = await res.json();
			lastStatsUpdate = data.lastStatsUpdate;
			updateTimer();
			startTimer();
		} catch (err) {
			error = err.message;
			console.error(err);
		}
	}
	
	function updateTimer() {
		if (lastStatsUpdate != null) {
			const diff = Math.floor((Date.now() - new Date(lastStatsUpdate).getTime()) / 1000);
			secondsSinceStatsUpdate = diff;
			canStatsUpdate = diff >= 10800;
		} else {
			console.log("No last stats update time found, allowing update");
			canStatsUpdate = true; // Allow update if we don't have a last update time
		}
	}
	
	function startTimer() {
		clearInterval(interval);
		interval = setInterval(updateTimer, 1000);
	}
	
	async function triggerStatsUpdate() {
		try {
			const res = await fetch("/api/updateStats", { method: "POST" });
			if (!res.ok) throw new Error("Failed to trigger update");
			lastStatsUpdate = new Date();
			updateTimer();
		} catch (err) {
			error = err.message;
			console.error(err);
		}
	}
	
	onMount(() => {
		console.log("Fetching status");
		fetchStatus();
		console.log("Fetching last stats update");
		fetchLastStatsUpdate();
		return () => clearInterval(interval);
	});
	
	function formatTime(seconds) {
		const h = Math.floor(seconds / 3600);
		const m = Math.floor((seconds % 3600) / 60);
		const s = seconds % 60;
		return `${h > 0 ? h + "h " : ""}${m}m ${s}s`;
	}
</script>

<style>
	.utilities {
		max-width: 800px;
		margin: 2rem auto;
		padding: 1rem;
		border: 1px solid #ccc;
		border-radius: 8px;
		background: #f9f9f9;
		box-shadow: 0px 2px 6px rgba(0,0,0,0.1);
	}
	h1 {
		text-align: center;
	}
	.status {
		margin-top: 1rem;
	}
	.error {
		color: red;
		font-weight: bold;
	}
	.motd, .player-name {
		font-family: monospace;
		white-space: pre-wrap;
	}
	.motd {
		background: #333;
	}
	button {
		display: block;
		margin: 1rem auto;
		padding: 0.5rem 1rem;
		font-size: 1rem;
		cursor: pointer;
		border: 1px solid #ccc;
		border-radius 5px;
		background: #eee;
		transition: background 0.2s;
	}
	button:hover {
		background: #ddd;
	}
	button:disabled {
		background: #aaa;
		cursor: not-allowed;
	}
	.favicon {
		width: 32px;
		height: 32px;
		margin-right: 8px;
		vertical-align: middle;
	}
	
	.update-section {
		margin-top: 2rem;
		text-align: center;
	}
</style>

<svelte:head>
	<title>Server Utilities - J3L+ SMP</title>
	<meta name="description" content="Utilities associated with the server and webpage, administration, and information" />
	<meta name="keywords" content="Minecraft, server, utilities, J_dg, J3L, J3L+, SMP, player statistics, leaderboards, items, mobs, backups" />
	<link rel="canonical" href="https://j3lsmp.org/utilities" />
</svelte:head>

<div class="utilities">
	<h1>Server Utilities</h1>
	
	{#if loading}
		<p>Loading server status...</p>
	{:else if error}
		<p class="error">Error: {error}</p>
	{:else}
		<div>
			{#if serverInfo.icon}
				<img class="favicon" src={serverInfo.icon} alt="Server Icon" />
			{/if}
		</div>
		<div class="status">
			<p><strong>MOTD:</strong></p>
			<div class="motd">{@html serverInfo.motd?.html}</div>
			
			<p><strong>Version:</strong> {serverInfo.version?.name_raw || "N/A"}</p>
			<p><strong>Players Online</strong> {serverInfo.players?.online} / {serverInfo.players?.max}</p>
			
			{#if serverInfo.players?.list?.length > 0}
				<p><strong>Online Players:</strong></p>
				<ul>
					{#each serverInfo.players.list as player}
						<li class="player-name">{@html player.name_html}</li>
					{/each}
				</ul>
			{/if}
		</div>
		<button on:click={fetchStatus}>Refresh server status</button>
	{/if}
	<div class="update-section">
		<h2>Stats Update</h2>
		<p>Updates are allowed once every three hours.</p>
		<p>This feature is disabled for now, I want to do more testing to make sure it's safe.</p>
		<p>Last update: {lastStatsUpdate != null ? formatTime(secondsSinceStatsUpdate) + " ago" : "unknown"}</p>
		<button on:click={triggerStatsUpdate} disabled={!canStatsUpdate}>Update Stats</button>
	</div>
</div>