<script>
	import L from "leaflet";
	import { onMount } from "svelte";
	
	let map;
	let currentLayer;
	
	const maps = {
		overworld: "/assets/maps/overworld.png",
		nether: "/assets/maps/nether.png",
		end: "/assets/maps/end.png",
	};
	
	let selectedMap = "overworld";
	
	function switchMap(mapName) {
		selectedMap = mapName;
		if (currentLayer) {
			map.removeLayer(currentLayer);
		}
		currentLayer = L.imageOverlay(maps[selectedMap], [[-5000, -5000], [5000, 5000]]).addTo(map);
		map.fitBounds([[-5000, -5000], [5000, 5000]]);
	}
	
	onMount(() => {
		map = L.map("map", {
			minZoom: -5,
			maxZoom: 10,
			center: [0, 0],
			zoom: 0,
			crs: L.CRS.Simple,
		});
		switchMap("overworld");
	});
</script>

<style>
	#map {
		height: 600px;
		width: 100%;
	}
	
	.dimension-selector {
		top: 10px;
		left: 10px;
		z-index: 1000;
		background: white;
		padding: 10px;
		border-radius: 5px;
	}
</style>

<div class="dimension-selector">
	<button on:click={() => switchMap("overworld")}>Overworld</button>
	<button on:click={() => switchMap("nether")}>Nether</button>
	<button on:click={() => switchMap("end")}>End</button>
</div>

<div id="map"></div>
	