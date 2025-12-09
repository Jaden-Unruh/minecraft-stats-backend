<script>
    import BannerPath from "$lib/yir/BannerPath.svelte";
    import { goto } from "$app/navigation";

    let isBanner = false;

    const players = [
        { name: "Select...", color: ":#333333" },
        { name: "J_dg", color: ":#ff00b6" },
        { name: "lunar_dusting", color: ":#078b0f" },
        { name: "Y3s_N0_Maybe", color: ":#7d00ff" },
        { name: "WolfNumberTen", color: ":#333cd0" },
        { name: "ja_cc", color: ":#ec5800" },
        { name: "Phoenospace", color: ":#263583" },
        { name: "leelapm", color: ":#16e5e5" },
        { name: "genericname0002", color: ":#c4c4c4" },
        { name: "XanderGee", color: ":#efbf04" },
        { name: "FinoDinoPlays" , color: ":#bb2222" },
        { name: "NewYorkMaddie" , color: "-image:linear-gradient(to right, #2ef5ff, #808080, #ff4200)"},
        { name: "sweetstarfish84", color: ":#f08080" },
        { name: "Rydog12144", color: ":#00d4ff" },
        { name: "Cinnamon111", color: ":#ff6b9d" },
        { name: "superkidbox", color: ":#9d4edd" },
        { name: "tienwun", color: ":#3a86ff" },
        { name: "JustChow", color: ":#06d6a0" },
        { name: "Encorous", color: ":#fb5607" }
    ];

    let open = false;
    let selected = players[0];

    let flyClone = null;
    let selectedBox;

    function choose(item, event) {
        open = false;

        const el = event.currentTarget;
        const rect = el.getBoundingClientRect();

        flyClone = {
            text: item.name,
            color: item.color,
            x: rect.left,
            y: rect.top,
            width: rect.width,
        };

        const targetRect = selectedBox.getBoundingClientRect();

        requestAnimationFrame(() => {
            flyClone.x = targetRect.left;
            flyClone.y = targetRect.top;
            flyClone.width = targetRect.width;
        });

        setTimeout(() => {
            selected = item;
            flyClone = null;
        }, 300);
    }

    let animating = false;
    let switched = false;

    function buttonAnimate() {
        animating = true;

        setTimeout(() => {
            switched = true;
        }, 625);
        setTimeout(() => {
            animating = false;
        }, 1250);
    }

    function leaveButton() {
        if (animating) {
            setTimeout(() => {
                switched = false;
            }, 1250);
        } else {
            switched = false;
        }
    }
</script>

<div class="startDiv">
    <BannerPath {isBanner} />
    <h2>Select a player to view their Review!</h2>
    <h3 class="mobile-only">The Review is best experienced on desktop in fullscreen!</h3>
    <div class="dropdown">
        <div
            bind:this={selectedBox}
            class="selected"
            style="background{selected.color}"
            on:click={() => (open = !open)}
        >
            {selected.name}
        </div>

        {#if open}
            <div class="menu">
                {#each players as player}
                    <div
                        class="option"
                        style={(player.color.includes("image")) ? "--hover-image: " + player.color.substring(7) : "--hover-color: " + player.color.substring(1)}
                        on:click={(e) => {
                            choose(player, e)
                        }}
                    >
                        {player.name}
                    </div>
                {/each}
            </div>
        {/if}

        {#if flyClone}
            <div
                class="clone"
                style="
                    left:{flyClone.x}px;
                    top:{flyClone.y}px;
                    background{flyClone.color};
                    width:{flyClone.width}px;
                "
            >
                {flyClone.text}
            </div>
        {/if}

        {#if selected.name != "Select..."}
            <button class:animating={animating} class:switched={switched} on:mouseenter={buttonAnimate} on:mouseleave={leaveButton} on:click={goto("/yearInReview/2025/"+selected.name)}>Go!</button>
        {/if}
    </div>
</div>

<style>
    .mobile-only {
        display: none;
    }

    button.animating {
        box-shadow: 5px 5px 10px #fff;
    }

    button {
        margin: 10px;
        width: 50%;
        height: 50px;

        background: #999;
        position: relative;
        overflow: hidden;

        border-radius: 10px;
    }

    button::after {
        content: "";
        width: 500%;
        position: absolute;
        inset: 0;

        background: linear-gradient(90deg, #999, #ff00b6, #078b0f, #ec5800, #16e5e5, #000055);

        opacity: 0;
        pointer-events: none;
    }

    button.animating::after {
        opacity: 1;
        animation: slide 1.25s linear forwards;
    }

    button.switched {
        background: #000055;
        color: #fff;
        box-shadow: 5px 5px 10px #fff;
    }

    @keyframes slide {
        0% {
            transform: translateX(20%);
        } 100% {
            transform: translateX(-100%);
        }
    }

    .startDiv {
        text-align: center;
        justify-items: center;
    }

    .dropdown {
        width: 40%;
        position: relative;
        user-select: none;
    }

    .selected {
        padding: 8px;
        cursor: pointer;
        color: #000;
        text-shadow: -1px -1px 3px #fff;
        border: 1px solid #aaa;
        border-radius: 4px;
    }

    .menu {
        position: absolute;
        width: 150%;
        transform: translateX(-17.5%);
        top: 100%;
        left: 0;
        right: 0;
        border: 1px solid #aaa;
        background: #222;
        border-radius: 4px;
        z-index: 10;
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 4px;
        padding: 4px;
    }

    .option {
        padding: 8px;
        color: #fff;
        cursor: pointer;
    }

    .option:hover {
        background: var(--hover-color);
        background-image: var(--hover-image);
        color: #000;
        text-shadow: -1px -1px 3px #fff;
    }

    .clone {
        position: fixed;
        padding: 8px;
        border-radius: 4px;
        color: #000;
        text-shadow: -1px -1px 3px #fff;
        pointer-events: none;
        transition: left 0.3s ease-in-out, top 0.3s ease-in-out, width 0.3s ease-in-out;
        z-index: 10;
        border: 1px solid #999;
        background: #fff;
    }

    @media (max-width: 900px) {
        .mobile-only {
            display: block;
        }
    }
</style>
