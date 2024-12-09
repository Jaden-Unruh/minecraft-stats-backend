<script>
	export let currentRoute;
</script>

<style>
	nav {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 1rem 2rem;
		background-color: #333;
		color: white;
	}
	
	.menu {
		display: flex;
		gap: 1rem;
	}
	
	.menu a {
		color: white;
		text-decoration: none;
		cursor: pointer;
	}
	
	.menu a:hover {
		text-decoration: underline;
	}
	
	.logo {
		font-weight: bold;
		font-size: 1.2rem;
	}
</style>

<nav>
	<div class="logo">J3L+ SMP S2</div>
	<div class="menu">
		<a on:click={() => (currentRoute = "home")}>Home</a>
		<a on:click={() => (currentRoute = "players")}>Players</a>
		<a on:click={() => (currentRoute = "backups")}>Backups</a>
		<a on:click={() => (currentRoute = "world-map")}>World Map</a>
		<a href="https://github.com/Jaden-Unruh/minecraft-stats-backend" target="_blank">GitHub</a>
	</div>
</nav>