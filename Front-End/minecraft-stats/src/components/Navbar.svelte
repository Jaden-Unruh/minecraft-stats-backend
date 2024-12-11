<style>
	nav {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 1rem 2rem;
		background-color: #333;
		color: white;
		border-bottom: 3px solid #aa00aa;
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
		color: white;
	}
	
	.logo img {
		width: 35px;
		height: 35px;
		border-radius: 6px;
		display: inline;
		vertical-align: middle;
	}
</style>

<nav>
	<a href="/"><div class="logo"><img src="/assets/logo.png" alt="J3L+ Logo"> J3L+ SMP S2</div></a>
	<div class="menu">
		<a href="/">Home</a>
		<a href="/players">Players</a>
		<a href="/leaderboards">Leaderboards</a>
		<a href="/backups">Backups</a>
		<a href="/world-map">World Map</a>
		<a href="https://github.com/Jaden-Unruh/minecraft-stats-backend" target="_blank">GitHub</a>
	</div>
</nav>