<script>
	let screenshots = [
		"/assets/screenshots/screen1.png",
		"/assets/screenshots/screen2.png",
		"/assets/screenshots/screen3.png"
	];
</script>

<style>
	.screenshots {
		display: flex;
		justify-content: center;
		gap: 1rem;
		margin: 2rem;
	}
	
	.screenshots img {
		width: 200px;
		height: auto;
		border-radius: 5px;
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	}
</style>

<div class="screenshots">
	{#each screenshots as screenshot}
		<img src={screenshot} alt="Server screenshot" />
	{/each}
</div>