<style>
    h2 {
        margin-top: 1rem;
    }

    a {
        text-decoration: none;
    }

    a:hover {
        text-decoration: underline;
    }

    li {
        border-top: 1px solid #ccc;
    }

    ul {
        padding-left: 1rem;
        margin: 0;
    }

    span {
        display: block;
        padding: 0.5rem 0;
    }
</style>

<svelte:head>
    <title>Site Map - J3L+ SMP</title>
    <meta name="description" content="A comprehensive site map for J3L+ SMP, detailing all available pages and their purposes." />
    <meta name="keywords" content="Minecraft, server, site map, J_dg, J3L, J3L+, SMP, player statistics, leaderboards, items, mobs, backups" />
    <link rel="canonical" href="https://j3lsmp.org/sitemap" />
</svelte:head>

<h2>Site Map</h2>
<span><a href="/">/: Home</a> - The home page of the site, with screenshots and a description.</span>
<ul>
    <li><span><a href="/backups">/backups: Backups</a> - A list of backups, updated manually when backups are made. Not currently maintained.</span></li>
    <li><span><a href="/items">/items: Items Overview</a> - A summary of statistics about block and item interations</span>
        <ul>
            <li><span><a href="/items/stone">/items/&lbrace;itemId&rbrace;: Item</a> - Statistics and leaderboards about interactions with a specific block or item (~1246 pages)</span></li>
        </ul>
    </li>
    <li><span><a href="/leaderboards">/leaderboards: Leaderboards</a> - Various leaderboards of specific statistics</span></li>
    <li><span><a href="/mobs">/mobs: Mobs Overview</a> - A summary of statistics about mob kills and deaths</span>
        <ul>
            <li><span><a href="/mobs/skeleton">/mobs/&lbrace;mobId&rbrace;: Mob</a> - Statistics and leaderboards about kills and deaths for a specific mob (~75 pages)</span></li>
        </ul>
    </li>
    <li><span><a href="/map">/map: Map</a> - A map of each dimension of the world, updated manually after large projects</span>
        <ul>
            <li><span><a href="/map/overworld">/map/overworld: Overworld</a> - The Overworld map</span></li>
            <li><span><a href="/map/nether">/map/nether: Nether</a> - The Nether map</span></li>
            <li><span><a href="/map/netherroof">/map/netherroof: Nether Roof</a> - The Nether Roof map</span></li>
            <li><span><a href="/map/end">/map/end: End</a> - The End map</span></li>
        </ul>
    </li>
    <li><span><a href="/players">/players: Player list</a> - A list of players on the server linking to their specific pages</span>
        <ul>
            <li><span><a href="/players/J_dg">/players/&lbrace;playerName&rbrace;: Player</a> - An overview of player stats and links to various player pages (~14 pages, each with 3 sub-pages)</span>
                <ul>
                    <li><span><a href="/players/J_dg/advancements">/players/&lbrace;playerName&rbrace;/advancements: Advancements</a> - A list of advancements earned by the player</span></li>
                    <li><span><a href="/players/J_dg/inventory">/players/&lbrace;playerName&rbrace;/inventory: Inventory</a> - A capture of the player's inventory. Manually and rarely updated</span></li>
                    <li><span><a href="/players/J_dg/stats">/players/&lbrace;playerName&rbrace;/stats: Statistics</a> - Lists of all of the player statistics, automatically updated every hour on the hour</span></li>
                </ul>
            </li>
        </ul>
    </li>
    <li><span><a href="/sitemap">/sitemap: Site Map</a> - This page</span></li>
    <li><span><a href="/utilities">/utilities: Server Utilities</a> - Tools and utilities for live server and web management</span></li>
</ul>