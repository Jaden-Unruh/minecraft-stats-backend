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
	import Advancements from './routes/Advancements.svelte';
	
	let currentRoute = "home";
	let playerName = null;
	
	const path = window.location.pathname;
	
	if (path === "/") {
		currentRoute = "Home";
	} else if (path === "/players") {
		currentRoute = "Players";
	} else if (path === "/backups") {
		currentRoute = "Backups";
	} else if (path === "/map") {
		currentRoute = "Map";
	} else if (path.startsWith("/stats/")) {
		currentRoute = "Stats";
		playerName = path.split("/stats/")[1];
	} else if (path === "/leaderboards") {
		currentRoute = "Leaderboards";
	} else if (path.startsWith("/inventory/")) {
		currentRoute = "Inventory";
		playerName = path.split("/inventory/")[1];
	} else if (path.startsWith("/advancements/")) {
		currentRoute = "Advancements";
		playerName = path.split("/advancements/")[1];
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

<svelte:head>
	<title>J³L+ SMP: {currentRoute}</title>
</svelte:head>

<main>
	<Navbar bind:currentRoute />
	<div class="content">
		{#if currentRoute === "Home"}
			<Home />
		{:else if currentRoute === "Players"}
			<PlayerList />
		{:else if currentRoute === "Backups"}
			<Backups />
		{:else if currentRoute === "Map"}
			<Map />
		{:else if currentRoute === "Stats"}
			<Stats {playerName} />
		{:else if currentRoute === "Leaderboards"}
			<LBoards />
		{:else if currentRoute === "Inventory"}
			<Inventory {playerName} />
		{:else if currentRoute === "Advancements"}
			<Advancements {playerName} />
		{/if}
	</div>
	<Footer />
</main>