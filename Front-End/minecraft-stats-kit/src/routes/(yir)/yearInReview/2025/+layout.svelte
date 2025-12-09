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
    <meta name="description" content="Year-In-Review annual summary statistics for 2025 for the J3L+ SMP Season 2. A spotify-wrapped style presentation of interesting statistics for each player." />
    <link rel="icon" href="/favicon.ico" />
</svelte:head>

<style>
    main {
        background-color: #555555;
        flex: 1;
        overflow: visible;
    }

    .main-container {
        display: flex;
        flex-direction: column;
        height: 100vh;
    }
</style>