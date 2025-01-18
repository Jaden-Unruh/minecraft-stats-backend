<script>
	import Navbar from './components/Navbar.svelte';
	import Footer from './components/Footer.svelte';
	import Home from './routes/Home.svelte';
	import PlayerList from './routes/PlayerList.svelte';
	import Backups from './routes/Backups.svelte';
	import Map from './routes/Map.svelte';
	import Stats from './routes/Stats.svelte';
	import LBoards from './routes/LBoards.svelte';
	import Inventory from './routes/Inventory.svelte';
	
	let currentRoute = "home";
	let playerName = null;
	
	const path = window.location.pathname;
	
	if (path === "/") {
		currentRoute = "home";
	} else if (path === "/players") {
		currentRoute = "players";
	} else if (path === "/backups") {
		currentRoute = "backups";
	} else if (path === "/map") {
		currentRoute = "map";
	} else if (path.startsWith("/stats/")) {
		currentRoute = "stats";
		playerName = path.split("/stats/")[1];
	} else if (path === "/leaderboards") {
		currentRoute = "boards";
	} else if (path.startsWith("/inventory/")) {
		currentRoute = "inventory";
		playerName = path.split("/inventory/")[1];
	}
</script>

<style>
	main {
		display: flex;
		flex-direction: column;
		min-height: 100vh;
	}
	
	.content {
		flex: 1;
	}
</style>

<main>
	<Navbar bind:currentRoute />
	<div class="content">
		{#if currentRoute === "home"}
			<Home />
		{:else if currentRoute === "players"}
			<PlayerList />
		{:else if currentRoute === "backups"}
			<Backups />
		{:else if currentRoute === "map"}
			<Map />
		{:else if currentRoute === "stats"}
			<Stats {playerName} />
		{:else if currentRoute === "boards"}
			<LBoards />
		{:else if currentRoute === "inventory"}
			<Inventory {playerName} />
		{/if}
	</div>
	<Footer />
</main>