<script>
	import { onMount } from "svelte";

	let name = "J3L+ Stats Webpage";
	let version = "x.x.x";

	async function fetchInfo() {
		const response = await fetch('/api/programVersion');
		if (response.ok)
			version = await response.text();
		const response2 = await fetch('/api/programName');
		if (response2.ok)
			name = await response.text();
	}

	onMount(() => {
		fetchInfo();
	});
</script>

<style>
	footer {
		text-align: center;
		padding: 1rem;
		background-color: #333;
		color: white;
		border-top: 3px solid #fa0;
	}
	
	a {
		color: white;
	}


</style>

<footer>
	{name} Version {version} | © 2025 <a href=https://linktr.ee/JadenUnruh>Jaden Unruh</a> 
	, J³L+ SMP<br>
	<a href="/sitemap">Site Map</a> | <a href="/utilities">Utilities</a>
</footer>