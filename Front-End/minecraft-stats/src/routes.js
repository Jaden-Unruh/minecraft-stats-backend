import Home from "./components/Home.svelte";
import PlayerList from "./components/PlayerList.svelte";
import Stats from "./components/Stats.svelte";
import Map from "./components/Map.svelte";
import Backups from "./components/Backup.svelte";
import NotFound from "./components/NotFound.svelte";

export const routes [
	{ path: "/", component: Home },
	{ path: "/players", component: PlayerList}
];