<script>
	import Navbar from "$lib/Navbar.svelte";
	import Footer from "$lib/Footer.svelte";
</script>

<div class="main-container">
    <header>
        <Navbar />
    </header>

    <main>
        <slot />
    </main>

    <footer>
        <Footer />
    </footer>
</div>

<svelte:head>
	<meta name="description" content="Home page for the J3L+ SMP Season 2, with player stats, leaderboards, and more." />
	<link rel="icon" href="/favicon.ico" />
</svelte:head>

<style>
	main {
		min-height: 80vh;
		padding: 20px;
        flex: 1;
        overflow: auto;
    }

    .main-container {
        display: flex;
        flex-direction: column;
        height: 100vh;
    }
</style>