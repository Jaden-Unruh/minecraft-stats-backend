<script>
    import { onMount } from 'svelte';
    import { Autocomplete } from '@skeletonlabs/skeleton';

    let mobOptions = [];
    let loading = true;
    let error = null;

    let overview;

    const fetchMobs = async () => {
        try {
            const overviewResponse = await fetch('/api/mobOverview');
            if (!overviewResponse.ok) throw new Error(`Error: ${overviewResponse.statusText}`);
            overview = await overviewResponse.json();

            const response = await fetch('/api/allMobs');
            if (!response.ok) throw new Error(`Error: ${response.statusText}`);
            let mobs = await response.json();

            mobOptions = mobs.map(mob => ({
                label: mob.replace("minecraft:", "").split('_').map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' '),
                value: mob.replace("minecraft:", "")
            }));
        } catch (err) {
            error = err.message;
        } finally {
            loading = false;
        }
    };

    const formatNumber = n => new Intl.NumberFormat().format(n);

    onMount(fetchMobs);

    let mobInput = '';
    let mobFocused = false;

    function onMobSelection(event) {
        mobInput = event.detail.label;
        mobFocused = false;
        window.location.href = `/mobs/${event.detail.value}`;
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
        grid-template-rows: repeat(3, 1fr);
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
    <title>Mobs</title>
</svelte:head>

{#if loading}
    <p>Loading...</p>
{:else if error}
    <p>Error: {error}</p>
{:else}
    <div class="overview">
        <h2 style="grid-column: 1 / 6; grid-row: 1; text-align: center;">Together, we have...</h2>
        <h3 style="grid-column: 1 / 5; grid-row: 2; text-align: right;">Killed {formatNumber(overview.killed)} mobs</h3>
        <img src="/api/textures/diamond_sword" alt="Diamond Sword" style="grid-column: 5; grid-row: 2;"/>
        <img src="/api/textures/bow" alt="Bow" style="grid-column: 1; grid-row: 3;"/>
        <h3 style="grid-column: 2 / 6; grid-row: 3; text-align: left;">Been killed by mobs {formatNumber(overview.killed_by)} times</h3>
    </div>
    <h2 class="searchHeader">View server statistics about a particular mob</h2>
    <input
        class="input"
        type="search"
        name="mobInput"
        bind:value={mobInput}
        placeholder="Search mobs..."
        on:focus={() => (mobFocused = true)}
        on:blur={() => setTimeout(() => {mobFocused = false}, 150)}
    />
    {#if mobFocused}
        <Autocomplete
            bind:input={mobInput}
            options={mobOptions}
            on:selection={onMobSelection}
            class="style-autocomplete"
        />
    {/if}
{/if}