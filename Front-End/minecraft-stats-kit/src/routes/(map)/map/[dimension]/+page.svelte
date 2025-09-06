<script>
    import { page } from '$app/stores';

    let dimension;
    $: dimension = $page.params.dimension;
</script>

<style>
	.iframe-container {
		width: 100%;
		height: calc(100vh - 152px);
		border: none;
		flex: 1;
	}
</style>

<iframe
	src="/api/map/{dimension}/index.html"
	class="iframe-container"
	title="J3L+S2 World Map">
</iframe>