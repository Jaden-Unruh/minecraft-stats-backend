<script>
    import {
        Chart,
        DoughnutController,
        ArcElement,
        Legend,
        Tooltip,
    } from "chart.js";
    import { getPlayerColor } from "$lib/yir/colors.js";
    import { onMount } from "svelte";

    export let selectedCategory;
    export let selectedKey;
    export let entries;

    Chart.register(DoughnutController, ArcElement, Legend, Tooltip);

    onMount(async () => {
        const data = {
            labels: entries.map((value) => value.name),
            datasets: [
                {
                    label: selectedKey + "/" + selectedCategory,
                    data: entries.map((value) => value.value),
                    backgroundColor: entries.map((value) =>
                        value.name == "NewYorkMaddie"
                            ? "#2ef5ff"
                            : getPlayerColor(value.name).substring(1),
                    ),
                    hoverOffset: 4,
                },
            ],
        };

        const ctx = document.getElementById(
            selectedKey + "/" + selectedCategory + " pie chart",
        );

        new Chart(ctx, {
            type: "doughnut",
            data: data,
        });

        console.log("Created Chart");
    });
</script>

<div>
    <canvas id={selectedKey + "/" + selectedCategory + " pie chart"}></canvas>
</div>
