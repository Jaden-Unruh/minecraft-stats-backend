<script>
	export let show = false;
	export let top = 0;
	export let left = 0;
	export let content = "";
</script>

<style>
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

{#if show}
	<div
		class="tooltip"
		style="top: {top}px; left: {left}px;"
		>
		{@html content}
	</div>
{/if}