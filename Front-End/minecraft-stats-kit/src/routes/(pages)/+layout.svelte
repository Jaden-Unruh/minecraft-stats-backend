<script>
	import Navbar from "$lib/Navbar.svelte";
	import Footer from "$lib/Footer.svelte";
</script>

<header>
	<Navbar />
</header>

<main>
	<slot />
</main>

<footer>
	<Footer />
</footer>

<svelte:head>
	<meta name="description" content="Home page for the J3L+ SMP Season 2, with player stats, leaderboards, and more." />
	<link rel="icon" href="/favicon.ico" />
</svelte:head>

<style>
	main {
		min-height: 80vh;
		padding: 20px;
	}
</style>