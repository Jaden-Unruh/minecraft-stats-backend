<script>
	import { onMount } from "svelte";
	
	let images = [];
	let currentIndex = 0;
	let interval;
	
	let description = "The J3L SMP is a Minecraft server originally created by a group of four roommates and very close friends. In its first season, it was hosted locally by me, Jaden, one of the four. We reset for season two in October of 2024, and decided to invite some more friends. I switched to hosting the server through Azure for better stability and uptime, and our world has far exceeded my expectations.";
	
	async function fetchImageList() {
		const response = await fetch('api/imageList');
		const data = await response.json();
		images = data;
		prefetchImages();
		startSlideshow();
		console.log(images);
	}
	
	function startSlideshow() {
		interval = setInterval(() => {
			nextImage();
		}, 15000);
	}
	
	function stopSlideShow() {
		clearInterval(interval);
	}
	
	function nextImage() {
		currentIndex = (currentIndex + 1) % images.length;
		stopSlideShow();
		startSlideshow();
		prefetchImages();
	}
	
	function prevImage() {
		currentIndex = (currentIndex - 1 + images.length) % images.length;
		stopSlideShow();
		startSlideshow();
		prefetchImages();
	}
	
	function prefetchImages() {
		const nextIndex = (currentIndex + 1) % images.length;
		const prevIndex = (currentIndex - 1 + images.length) % images.length;
		
		if (images[nextIndex])
			new Image().src = "api/image/" + images[nextIndex];
			
		if (images[prevIndex])
			new Image().src = "api/image/" + images[prevIndex];
	}
	
	onMount(() => {
		fetchImageList();
	});
</script>

<style>
	.slideshow-container {
		position: relative;
		width: 100%;
		max-width: 100%;
		margin: auto;
		overflow: hidden;
	}
	
	.image {
		width: 100%;
		height: auto;
		animation: fade 1s ease-in-out;
	}
	
	@keyframes fade {
		from { opacity: 0.4; }
		to { opacity: 1; }
	}
	
	.arrow {
		position: absolute;
		top: 50%;
		transform: translateY(-50%);
		background-color: rgba(0, 0, 0, 0.5);
		color: white;
		padding: 10px;
		cursor: pointer;
	}
	
	.prev {
		left: 10px;
	}
	
	.next {
		right: 10px;
	}
	
	.description {
		text-align: center;
		font-size: 14px;
		margin-top: 25px;
		padding: 0 20px;
		line-height: 1.5;
		max-width: 500px;
	}
</style>

<svelte:head>
	<title>J3L+ SMP S2</title>
	<meta name="description" content="The J3L+ SMP is a Minecraft server originally created by a group of four roommates and very close friends. In its first season, it was hosted locally by me, Jaden, one of the four. We reset for season two in October of 2024, and decided to invite some more friends. I switched to hosting the server through Azure for better stability and uptime, and our world has far exceeded my expectations." />
	<meta name="keywords" content="J3L+, SMP, Minecraft, server, friends, Azure, season two, gaming, J_dg, Jaden, Unruh, Jaden Unruh" />
	<link rel="canonical" href="https://j3lsmp.org/" />
</svelte:head>

<div class="slideshow-container">
	{#if images.length > 0}
		<img src={"api/image/" + images[currentIndex]} alt="Slideshow" class="image" />
		<div class="arrow prev" on:click="{prevImage}">❮</div>
		<div class="arrow next" on:click="{nextImage}">❯</div>
	{/if}
</div>
<center>
	<div class="description">
		{description}
	</div>
</center>