<script>
    export let name;
    export let key1;
    export let location1;
    export let key2;
    export let location2;
    export let multiplier;

    export let allStats = [];

    let topPlayers = [];

    $: computeLeaderboard();

    function computeLeaderboard() {
        topPlayers = allStats
            .map((player) => {
                let value1 = getValue(player, key1, location1);
                let value2 = getValue(player, key2, location2);
                let ratio = value1 / value2;
				return {
                    name: player.name,
                    ratio: ratio * multiplier,
                };
            })
			.filter((player) => !Number.isNaN(player.ratio))
			.sort((a, b) => b.ratio - a.ratio);
    }

    function getValue(player, key, location) {
        if (location === "computed") {
            return Number(player[key] || 0);
        } else {
            return Number(player[location]?.[key] || 0);
        }
    }
</script>

<style>
	.leaderboard {
		border: 1px solid #ddd;
		border-radius: 8px;
		padding: 1rem;
		background: #f9f9f9;
		box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	}
	
	h2 {
		margin-bottom: 0.5rem;
		font-size: 1.2rem;
		text-align: center;
	}
	
	ul {
		list-style: none;
		padding: 0;
		margin: 0;
	}
	
	li {
		display: flex;
		justify-content: space-between;
		padding: 0.5rem 0;
		border-bottom: 1px solid #eee;
	}
	
	li:last-child {
		border-bottom: none;
	}
	
	a {
		color: black;
		text-decoration: none;
	}
</style>

<div class="leaderboard">
	<h2>{name}</h2>
	<ul>
		{#each topPlayers as { name, ratio }}
			{#if name}
				<li>
					<span><a href={'/players/' + name}>{name}</a></span>
					<span>{ratio}</span>
				</li>
			{/if}
		{/each}
	</ul>
</div>