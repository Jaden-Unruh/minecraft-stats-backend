<script>
	import Navbar from './components/Navbar.svelte';
	import Footer from './components/Footer.svelte';
	import Home from './components/Home.svelte';
	import PlayerList from './components/PlayerList.svelte';
	
	let currentRoute = "home";
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
		{/if}
	</div>
	<Footer />
</main>