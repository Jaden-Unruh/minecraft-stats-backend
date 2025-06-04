<script>
	import Navbar from "$lib/Navbar.svelte";
	import Footer from "$lib/Footer.svelte";
</script>

<style>
	main {
		height: 100vh;
	}
</style>

<svelte:head>
	<title>J3L+S2 World Map</title>
	<meta name="description" content="Explore the J3L+S2 world map." />
	<link rel="icon" href="/favicon.ico" />
</svelte:head>

<main>
	<slot />
</main>