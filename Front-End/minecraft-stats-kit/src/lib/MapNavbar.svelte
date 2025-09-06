<script>
    import { onMount } from "svelte";
    
	let showLabels = false;
    let menuResponsive = false;
	let mounted = false;

	const qs = () => showLabels ? '?labels=true' : '';

    function toggleMenu() {
        menuResponsive = !menuResponsive;
    }
    
    onMount(() => {
		const params = new URLSearchParams(window.location.search);
		showLabels = params.get('labels') === 'true';
		mounted = true;

        function handleResize() {
            if (window.innerWidth > 1035 && menuResponsive) {
                menuResponsive = false;
            }
        }
        window.addEventListener('resize', handleResize);

        return () => {
            window.removeEventListener('resize', handleResize);
        };
    });

	function updateUrl()  {
		if (!mounted) return;

		const url = new URL(window.location);
		if (showLabels) url.searchParams.set('labels', 'true');
		else url.searchParams.delete('labels');
		window.history.replaceState({}, '', url);
		window.location.reload();
	}
</script>

<style>
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
		.menu a {display: none;}
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
</style>

<nav>
    <a href="/"><div class="logo"><img src="/assets/logo.png" alt="J3L+ Logo"> J3L+ SMP S2</div></a>
    <div>
		<input type="checkbox" bind:checked={showLabels} on:change={() => updateUrl()} name="labels" id="showLabels" />
		<label for="showLabels" title="Toggle Labels" style="cursor: pointer; color: white; margin-right: 1rem;">Show Labels</label>
	</div>
	<div id="menu" class="menu" class:responsive={menuResponsive}>
        <a href="/map/overworld{qs()}">Overworld</a>
        <a href="/map/nether{qs()}">Nether</a>
		<a href="/map/netherroof{qs()}">Nether Roof</a>
        <a href="/map/end{qs()}">End</a>
		<a class="icon" on:click={toggleMenu}>&#9776;</a>
    </div>
</nav>