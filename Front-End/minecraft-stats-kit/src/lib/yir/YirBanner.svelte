<script>
    import { goto } from "$app/navigation";
    import BannerPath from "$lib/yir/BannerPath.svelte";

    function yir() {
        goto("/yearInReview/2025");
    }
</script>

<div class="bannerDiv">
    <button class="banner" on:click={yir}>
        <BannerPath />
    </button>
</div>

<style>
    .bannerDiv {
        text-align: center;
    }

    .banner {
        background-color: #5555ff;
        border-radius: 50px;
        outline: 4px dashed #0000AA;
        width: 50%;
        padding: 20px;
        outline-offset: 0px;
        animation: dash 2.5s ease-in infinite alternate;
        border: none;
        cursor: pointer;
        margin: 25px;
    }

    @keyframes dash {
        from {
            outline-offset: 0px;
        }
        to {
            outline-offset: 25px;
        }
    }
</style>
