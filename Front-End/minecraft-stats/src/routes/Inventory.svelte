<script>
	import { writable } from "svelte/store";
	import { onMount } from "svelte";
	import ItemTooltip from '../components/ItemTooltip.svelte';
	
	export let playerName;
	
	let loading = true;
	let error = null;
	
	let inventory = [];
	let enderChest = [];
	let armor = [];
	
	let selectedShulker = null;
	
	let showTooltip = writable(false);
	let tooltipTop = writable(0);
	let tooltipLeft = writable(0);
	let tooltipAbove = writable(true);
	let tooltipContent = writable("");
	
	onMount(async () => {
		try {
			const response = await fetch(`/api/inventories/${playerName}`);
			const data = await response.json();
			
			const inventoryData = data.Inventory.filter(item => item.slot < 100).sort((a, b) => a.slot - b.slot);
			armor = data.Inventory.filter(item => (item.slot >= 100 && item.slot <= 103) || item.slot === -106).sort((a, b) => b.slot - a.slot);
			const enderChestData = data.EnderChest.sort((a, b) => a.slot - b.slot);
			
			inventory = Array(36).fill(null).map((_, slot) => {
				const item = inventoryData.find(item => item.slot === slot);
				return item || { id: "empty", count: 0, slot }; //Placeholder for empty slots
			});
			
			enderChest = Array(27).fill(null).map((_, slot) => {
				const item = enderChestData.find(item => item.slot === slot);
				return item || { id: "empty", count: 0, slot }; //Placeholder for empty slots
			});
		} catch (err) {
			error = err.message;
		} finally {
			loading = false;
		}
	});
	
	function handleHover(item, event) {
		const tileRect = event.target.getBoundingClientRect();
		
		const content = generateTooltipContent(item);
		const tooltip = document.createElement("div");
		tooltip.className = "tooltip";
		tooltip.innerText = content;
		
		document.body.appendChild(tooltip);
		const tooltipRect = tooltip.getBoundingClientRect();
		document.body.removeChild(tooltip);
		
		const fitsAbove = (tileRect.top - tooltipRect.height - 8) >= 0;
		
		showTooltip.set(true);
		tooltipContent.set(content);
		
		if (fitsAbove) {
			tooltipTop.set(tileRect.top - tooltipRect.height - 8);
		} else {
			tooltipTop.set(tileRect.bottom + 8);
		}
		
		tooltipLeft.set(tileRect.left + (tileRect.width / 2));
	}
	
	function handleLeave() {
		showTooltip.set(false);
	}
	
	function generateTooltipContent(item) {
		let content = (item.components && item.components.customName) ? "<b>" + item.components.customName + "</b>\n" : "";
		content += item.id;
		
		if (item.count > 1)
			content += " x" + item.count;
		
		if (item.components && item.components.Enchantments) {
			content += "\n<b>Enchantments:</b>";
			for (let enchantment of item.components.Enchantments)
				content += "\n" + enchantment.name + " " + enchantment.level;
		}
		
		if (item.components && item.components.Trim) {
			content += "\n<b>Trim:</b>\nPattern: " + item.components.Trim.pattern + "\nMaterial: " + item.components.Trim.material;	
		}
		
		return content;
	}
</script>

<style>
	.centered {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		margin-top: 20px;
	}
	
	.inventory-grid {
		display: grid;
		grid-template-columns: repeat(9, 50px);
		gap: 5 px;
		margin-top: 10px;
		justify-content: center;
	}
	
	.armor-grid {
		display: flex;
		justify-content: center;
		margin-bottom: 10px;
		gap: 10px;
	}
	
	.item-slot {
		width: 50px;
		height: 50px;
		background-color: #ccc; /* Placeholder until we have icons */
		border: 1px solid #888;
		position: relative;
		text-align: center;
		line-height: 50px;
		cursor: pointer;
		border-radius: 5px;
		transition: background-color 0.2s;
	}
	
	.item-slot:hover {
		background-color: #aaa;
	}
	
	.item-slot.empty-slot {
		background-color: #f2f2f2;
		border: 1px dashed #ccc;
		cursor: default;
	}
	
	.item-slot.empty-slot:hover {
		background-color: #f2f2f2;
	}
	
	.shulker-tooltip {
		position: absolute;
		top: 60px;
		left: 0;
		transform: translateX(-50%);
		background-color: white;
		border: 1px solid #888;
		padding: 10px;
		z-index: 10;
		border-radius: 5px;
	}
	
	.shulker-grid {
		display: grid;
		grid-template-columns: repeat(9, 40px);
		gap: 5px;
	}
	
	.tooltip {
		position: fixed;
		background-color: rgba(0, 0, 0, 0.8);
		color: white;
		padding: 5px;
		border-radius: 5px;
		font-size: 12px;
		white-space: pre-wrap;
		z-index: 1000;
		line-height: 20px;
		box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.3);
		transform: translateX(-50%);
	}
</style>

<div class="centered">
	{#if loading}
		<p>Loading stats for {playerName}...</p>
	{:else if error}
		<p>Error: {error}</p>
	{:else}
		<h2>{playerName}'s Inventory and Ender Chest</h2>
		
		<div class="armor-grid">
			{#each armor as item}
				<div
					class="item-slot"
					on:mouseenter={(event) => handleHover(item, event)}
					on:mouseleave={() => handleLeave()}
				>
					{#if item.id !== 'empty'}
						<img
							src={`/api/textures/${item.id}`}
							alt={item.id}
							class="item-icon"
						/>
					{/if}
				</div>
			{/each}
		</div>
		
		<div class="inventory-grid">
			{#each inventory as item}
				<div
					class="item-slot {item.id === 'empty' ? 'empty-slot' : ''}"
					on:mouseenter={(event) => handleHover(item, event)}
					on:mouseleave={() => handleLeave()}
					on:click={() => {
						if (item.id !== "empty" && item.components && item.components.contents) {
							selectedShulker = selectedShulker === item.components.contents ? null : item.components.contents;
						}
					}}
				>
					{#if item.id !== 'empty'}
						<img
							src={`/api/textures/${item.id}`}
							alt={item.id}
							class="item-icon"
						/>
					{/if}
					
					
					{#if item.components && selectedShulker === item.components.contents}
						<div class="shulker-tooltip">
							<div class="shulker-grid">
								{#each selectedShulker as shulkerItem}
									<div 
										class="item-slot"
										on:mouseenter={(event) => handleHover(shulkerItem, event)}
										on:mouseleave={() => handleLeave()}
									>
										{#if item.id !== 'empty'}
											<img
												src={`/api/textures/${shulkerItem.id}`}
												alt={shulkerItem.id}
												class="item-icon"
											/>
										{/if}
									</div>
								{/each}
							</div>
						</div>
					{/if}
				</div>
			{/each}
		</div>
		
		<div class="inventory-grid">
			{#each enderChest as item}
				<div
					class="item-slot {item.id === 'empty' ? 'empty-slot' : ''}"
					on:mouseenter={(event) => handleHover(item, event)}
					on:mouseleave={() => handleLeave()}
					on:click={() => {
						if (item.id !== "empty" && item.components && item.components.contents) {
							selectedShulker = selectedShulker === item.components.contents ? null : item.components.contents;
						}
					}}
				>
					{#if item.id !== 'empty'}
						<img
							src={`/api/textures/${item.id}`}
							alt={item.id}
							class="item-icon"
						/>
					{/if}
					
					
					{#if item.components && selectedShulker === item.components.contents}
						<div class="shulker-tooltip">
							<div class="shulker-grid">
								{#each selectedShulker as shulkerItem}
									<div 
										class="item-slot"
										on:mouseenter={(event) => handleHover(shulkerItem, event)}
										on:mouseleave={() => handleLeave()}
									>
										{#if item.id !== 'empty'}
											<img
												src={`/api/textures/${shulkerItem.id}`}
												alt={shulkerItem.id}
												class="item-icon"
											/>
										{/if}
									</div>
								{/each}
							</div>
						</div>
					{/if}
				</div>
			{/each}
		</div>
		<p>Some items are difficult to render as they don't have their own textures - e.g. stairs and slabs. Others, I simply don't have the textures for - e.g. custom mob heads.<br>If there's a specific vanilla item that you think is important enough that I should go get the item texture for it, let me know.</p>
	{/if}
</div>

<ItemTooltip show={$showTooltip} top={$tooltipTop} left={$tooltipLeft} content={$tooltipContent} />