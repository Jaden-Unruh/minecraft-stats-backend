<script>
	import MapNavbar from "$lib/MapNavbar.svelte";
	import Footer from "$lib/Footer.svelte";
</script>

<head>
	<title>J3L+S2 World Map</title>
</head>

<main>
	<MapNavbar />
	<slot />
	<Footer />
</main>