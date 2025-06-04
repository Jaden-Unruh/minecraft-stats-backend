<script>
    import { onMount } from 'svelte';
    import { page } from '$app/stores';
    import CustomLeaderboard from '$lib/CustomLeaderboard.svelte';

    let loading = true;
    let error = null;

    let itemId;
    let itemName = '';
    $: itemId = $page.params.itemId;

    let stats = [];
    
    const fetchOverview = async () => {

        itemName = itemId.split('_').map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ');

        try {
            const response = await fetch(`/api/itemOverview/${itemId}`);
    
            if (!response.ok) throw new Error(`Failed to fetch overview for item ${itemId}`);
    
            const overview = await response.json();
    
            console.log(overview);

            stats = Object.entries(overview).map(([key, value]) => ({ key, value })).sort((a, b) => b.value - a.value)

            console.log(stats);
        } catch (err) {
            console.error(err);
            error = err.message;
        } finally {
            loading = false;
        }
    }

    onMount(fetchOverview);
</script>

<style>
    .item-div {
        margin: 2rem auto;
		max-width: 800px;
		padding: 1rem;
		border: 3px solid #fa0;
		border-radius: 8px;
		display: grid;
		grid-template-columns: repeat(3, 1fr);
		gap: 10px;
    }

    .itemModel {
        grid-column: 1;
        grid-row: 1;
        min-width: 200px;
        image-rendering: pixelated;
    }

    .itemInfo {
        grid-column: 2 / 4;
        grid-row: 1;
    }

    .leaderboards {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
        gap: 1rem;
        margin-top: 1rem;
    }
</style>

<svelte:head>
	<title>{itemName}</title>
</svelte:head>

<div class="item-div">
    <img class="itemModel" src={`/api/textures/${itemId}`} alt="Item Texture" />
    <div class="itemInfo">
        <h2>{itemName}</h2>
        <h3>Together, we have:</h3>
        {#if loading}
            <p>Loading...</p>
        {:else if error}
            <p>Server error: {error}</p>
        {:else}
            <ul>
                {#each stats as item}
                    {#if item.value > 0}
                        <li>
                            {item.key.split('z').map(word => word.charAt(0).toUpperCase() + word.slice(1)).join('').replaceAll('_', ' ')} {item.value} {itemName.toLowerCase()}
                        </li>
                    {/if}
                {/each}
            </ul>
        {/if}
    </div>
</div>
<div class="leaderboards">
    {#if loading}
        <p>Loading leaderboard...</p>
    {:else if error}
        <p>Server error: {error}</p>
    {:else}
        {#each stats as item}
            {#if item.value > 0}
                <CustomLeaderboard selectedCategory={item.key} selectedKey={itemId} />
            {/if}
        {/each}
    {/if}
</div>