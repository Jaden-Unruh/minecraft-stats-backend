<script>
	import { onMount } from "svelte";

	let serverInfo = "Loading...";
	const serverIP = "j3lsmp.org";

	async function fetchPlayerCount() {
		try {
			const response = await fetch(`https://api.mcstatus.io/v2/status/java/${serverIP}`);
			const data = await response.json();
			if (!response.ok)
				throw new Error("Failed to fetch server info");

			if (!data.online) {
				serverInfo = "Server is offline";
			} else {
				serverInfo = `${data.players.online}/${data.players.max} players online`;
			}
		} catch (err) {
			serverInfo = "Error fetching status";
			console.error("Failed to fetch player count: ", err);
		}
	}

	let menuResponsive = false;

	function toggleMenu() {
		menuResponsive = !menuResponsive;
	}

	onMount(() => {
		fetchPlayerCount();
		const interval = setInterval(fetchPlayerCount, 10000);

		function handleResize() {
			if (window.innerWidth > 1035 && menuResponsive) {
				menuResponsive = false;
			}
		}
		window.addEventListener('resize', handleResize);

		return () => {
			clearInterval(interval);
			window.removeEventListener('resize', handleResize);
		};
	});
</script>

<style>
	nav {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 1rem 2rem;
		background-color: #333;
		color: white;
		border-bottom: 3px solid #a0a;
	}
	
	.menu {
		overflow: hidden;
	}
	
	.menu a {
		float: left;
		display: block;
		color: white;
		text-align: center;
		padding: 14px 16px;
		text-decoration: none;
		cursor: pointer;
	}
	
	.menu a:hover {
		background-color: #fa0;
		color: black;
	}

	.menu .icon {
		display: none;
	}

	@media screen and (max-width: 1035px) {
		.menu {
			position: static;
		}
		.menu a:not(:first-child) {display: none;}
		.menu a.icon {
			float: right;
			display: block;
		}

    	.menu.responsive {
        	position: absolute;
        	left: 0;
        	right: 0;
        	top: 60px;
        	background: #333;
        	border-bottom: 3px solid #a0a;
        	box-shadow: 0 4px 16px rgba(0,0,0,0.2);
        	z-index: 100;
    	}
		.menu.responsive a {
        	float: none;
        	display: block;
        	text-align: left;
    	}
    	.menu.responsive a.icon {
        	position: absolute;
        	right: 0;
        	top: 0;
    	}
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
	<!--<p>{serverInfo}</p>-->
	<div id="menu" class="menu" class:responsive={menuResponsive}>
		<a href="/">Home</a>
		<a href="/players">Players</a>
		<a href="/leaderboards">Leaderboards</a>
		<a href="/items">Items</a>
		<a href="/mobs">Mobs</a>
		<a href="/backups">Backups</a>
		<a href="/map">World Map</a>
		<a href="https://github.com/Jaden-Unruh/minecraft-stats-backend" target="_blank">GitHub</a>
		<a class="icon" on:click={toggleMenu}>&#9776;</a>
	</div>
</nav>