<script>
    import { onMount } from 'svelte';
	import { Autocomplete } from '@skeletonlabs/skeleton';

    let itemOptions = [];
    let loading = true;
    let error = null;

    let total = "Loading...";

    let overview;

    const fetchItems = async () => {
        try {
            const overviewResponse = await fetch('/api/itemOverview');
            if (!overviewResponse.ok) throw new Error(`Error: ${overviewResponse.statusText}`);
            overview = await overviewResponse.json();

            const response = await fetch('/api/allItems');
            if (!response.ok) throw new Error(`Error: ${response.statusText}`);
            let items = await response.json();

            itemOptions = items.map(item => ({
                label: item.replace("minecraft:", "").split('_').map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' '),
                value: item.replace("minecraft:", "")
            }))

            total = formatNumber(overview.mined + overview.broken + overview.picked_up + overview.crafted + overview.dropped + overview.used);
        } catch (err) {
            error = err.message;
        } finally {
            loading = false;
        }
    };

    const formatNumber = n => new Intl.NumberFormat().format(n);

    onMount(fetchItems);

    let itemInput = '';
    let itemFocused = false;

    function onItemSelection(event) {
        itemInput = event.detail.label;
        itemFocused = false;
        window.location.href = `/items/${event.detail.value}`;
    }
</script>

<style>
    h1 {
        text-align: center;
        margin-bottom: 1rem;
    }
	
	.input {
		width: 100%;
		max-width: 400px;
		padding: 12px;
		font-size: 16px;
		color: #333;
		background-color: #f4f4f4;
		border: 2px solid #999;
		border-radius: 8px;
		outline: none;
		transition: all 0.2s ease-in-out;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.15);
	}
	
	.input:focus {
		border-color: #007bff;
		background-color: white;
		box-shadow: 0 0 8px rgba(0, 123, 255, 0.5);
	}
	
	.input::placeholder {
		color: #777;
		font-style: italic;
		opacity: 0.8;
	}
	
	.input:not(:last-child) {
		margin-bottom: 12px;
	}
	
	.input:hover {
		background-color: #eaeaea;
	}
	
	:global(.style-autocomplete) {
		width: 100%;
		padding: 10px;
		border: 2px solid #ccc;
		border-radius: 6px;
		font-size: 16px;
		background-color: white;
		transition: border 0.3s ease-in-out;	
	}
	
	:global(.style-autocomplete:focus) {
		border-color: #007bff;
		outline: none;
	}
	
	:global(.style-autocomplete ul) {
		width: 100%;
		border: 1px solid #ccc;
		background: white;
		box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
		border-radius: 6px;
		padding: 0;
		margin: 5px 0;
		list-style: none;
	}

	:global(.style-autocomplete li button:hover) {
		background-color: #a0a;
		color: white;
		border: 3px solid #fa0;
	}

    .overview {
        display: grid;
        grid-template-columns: repeat(5, 1fr);
        gap: 10px;
        grid-template-rows: repeat(8, 1fr);
        margin: 2rem auto;
        max-width: 800px;
        padding: 1rem;
        border: 3px solid #fa0;
        border-radius: 8px;
    }

    .overview img {
        width: 64px;
        height: 64px;
        image-rendering: pixelated;
        justify-self: center;
    }

    .searchHeader {
        text-align: center;
        margin-top: 1rem;
        font-size: 1.5rem;
    }

    .input {
        width: 100%;
        max-width: 400px;
        padding: 12px;
        font-size: 16px;
        color: #333;
        background-color: #f4f4f4;
        border: 2px solid #999;
        border-radius: 8px;
        outline: none;
        transition: all 0.2s ease-in-out;
        box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.15);
        display: block;
        margin: 0 auto;
    }
</style>

<svelte:head>
	<title>Items</title>
</svelte:head>

{#if loading}
    <p>Loading...</p>
{:else if error}
    <p>Error: {error}</p>
{:else}
    <div class="overview">
        <h2 style="grid-column: 1 / 6; grid-row: 1; text-align: center;">Together, we have...</h2>
        <img src="/api/textures/nether_star" alt="Nether Star" style="grid-column: 1; grid-row: 2;"/>
        <h3 style="grid-column: 2 / 6; grid-row: 2; text-align: left;">Interacted with blocks and items {total} times</h3>
        <h3 style="grid-column: 1 / 5; grid-row: 3; text-align: right;">Mined {formatNumber(overview.mined)} blocks</h3>
        <img src="/api/textures/diamond_pickaxe" alt="Diamond Pickaxe" style="grid-column: 5; grid-row: 3;"/>
        <img src="/api/textures/shears" alt="Shears" style="grid-column: 1; grid-row: 4;"/>
        <h3 style="grid-column: 2 / 6; grid-row: 4; text-align: left;">Broken {formatNumber(overview.broken)} tools</h3>
        <h3 style="grid-column: 1 / 5; grid-row: 5; text-align: right;">Picked up {formatNumber(overview.picked_up)} items</h3>
        <img src="/api/textures/diamond" alt="Diamond" style="grid-column: 5; grid-row: 5;"/>
        <img src="/api/textures/crafting_table" alt="Crafting Table" style="grid-column: 1; grid-row: 6;"/>
        <h3 style="grid-column: 2 / 6; grid-row: 6; text-align: left;">Crafted {formatNumber(overview.crafted)} items</h3>
        <h3 style="grid-column: 1 / 5; grid-row: 7; text-align: right;">Dropped {formatNumber(overview.dropped)} items</h3>
        <img src="/api/textures/dropper" alt="Dropper" style="grid-column: 5; grid-row: 7;"/>
        <img src="/api/textures/stone_bricks" alt="Stone Bricks" style="grid-column: 1; grid-row: 8;"/>
        <h3 style="grid-column: 2 / 6; grid-row: 8; text-align: left;">Placed/used {formatNumber(overview.used)} blocks/items</h3>
    </div>
    <h2 class="searchHeader">View server statistics about a particular item</h2>
    <input
        class="input"
        type="search"
        name="itemInput"
        bind:value={itemInput}
        placeholder="Search items..."
        on:focus={() => (itemFocused = true)}
        on:blur={() => setTimeout(() => {itemFocused = false}, 150)}
    />
    {#if itemFocused}
        <Autocomplete
            bind:input={itemInput}
            options={itemOptions}
            on:selection={onItemSelection}
            class="style-autocomplete"
        />
    {/if}
{/if}