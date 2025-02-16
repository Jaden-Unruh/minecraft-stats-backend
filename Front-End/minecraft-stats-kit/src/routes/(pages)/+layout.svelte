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

<style>
	main {
		min-height: 80vh;
		padding: 20px;
	}
</style>