<script>
	import { onMount } from "svelte";
	import { page } from "$app/stores";
	let loading = true;
	let error = null;
	
	let playerName;
	
	let playerOverview = [];
	let online = false;
	
	const fetchOverview = async () => {
		$: playerName = $page.params.playerName;
		console.log(playerName);
	
		try {
			const response = await fetch(`/api/playerOverview/${playerName}`);
			if (!response.ok) throw new Error("Failed to fetch player overview");
			playerOverview = await response.json();
			// online = playerOverview.Online == "true";
		} catch (err) {
			console.error(err);
			error = err.message;
		} finally {
			loading = false;
		}
	};
	
	onMount(fetchOverview);
</script>

<style>
	.player-div {
		margin: 2rem auto;
		max-width: 800px;
		padding: 1rem;
		border: 3px solid #fa0;
		border-radius: 8px;
		display: grid;
		grid-template-columns: repeat(3, 1fr);
		gap: 10px;
		grid-template-rows: 2fr 1fr;
	}
	
	.playerModel {
		grid-column: 1;
		grid-row: 1;
	}
	
	.playerInfo {
		grid-column: 2 / 4;
		grid-row: 1;
	}
	
	.button {
		display: inline-block;
		border-radius: 4px;
		background-color: #ccc;
		border: 3px solid #a0a;
		color: #000;
		text-align: center;
		font-size: 16px;
		padding: 10px;
		width: 100%;
		transition: all 0.5s;
		cursor: pointer;
		margin: 5px;
		overflow: hidden;
	}
	
	.button:hover {
		background-color: #a0a;
		color: #fff;
	}
	
	.button span {
		cursor: pointer;
		display: inline-block;
		position: relative;
		transition: 0.5s;
	}
	
	.button span:after {
		content: '\00bb';
		position: absolute;
		opacity: 0;
		top: 0;
		right: -10px;
		transition: 0.5s;
		color: #fa0;
	}
	
	.button:hover span {
		padding-right: 10px;
	}
	
	.button:hover span:after {
		opacity: 1;
		right: 0;
	}
	
	.statsButton {
		grid-column: 1;
		grid-row: 2;
	}
	
	.advButton {
		grid-column: 2;
		grid-row: 2;
	}
	
	.invButton {
		grid-column: 3;
		grid-row: 2;
	}
</style>

<svelte:head>
	<title>{playerName}</title>
</svelte:head>

<div class="player-div">
	<iframe class="playerModel" src={`https://minerender.org/embed/skin?skin=${playerName}&shadow=true`} frameborder="0"></iframe>
	<div class="playerInfo">
		<h2>{playerName}</h2>
		<p>Play time: {(playerOverview.Playtime / 72000).toFixed(2)} hours</p>
		<p>Advancements: {playerOverview.Advancements}</p>
		<p>{playerName} is first on {playerOverview.Leaderboards} leaderboards. A list can be found on the fourth table in the player stats page below.</p>
		{#if online}
			<p>{playerName} is currently online</p>
			<p>Location: {playerOverview.Position}</p>
			<p>Health: {playerOverview.Health}</p>
		{:else}
			<p>Live player information from the server will be added in a future update. I am still bug-testing the server-side mod that would allow this.</p>
		{/if}
	</div>
	<button type="button" class="button statsButton" on:click={() => {location.href='/players/' + playerName + '/stats';}}><span>View {playerName}'s statistics</span></button>
	<button type="button" class="button advButton" on:click={() => {location.href='/players/' + playerName + '/advancements';}}><span>View {playerName}'s advancements</span></button>
	<button type="button" class="button invButton" on:click={() => {location.href='/players/' + playerName + '/inventory';}}><span>View {playerName}'s inventory</span></button>
</div>