export let animalColors = [
    { name: "allay", color: "#7FD3FF" },         // pale sky-blue (allay's calm, airy vibe)
    { name: "armadillo", color: "#B08A63" },     // dusty tan
    { name: "axolotl", color: "#FF97C2" },       // pink axolotl
    { name: "bat", color: "#222329" },           // near-black
    { name: "camel", color: "#D7B07A" },         // sandy beige
    { name: "cat", color: "#6B5F4A" },           // warm fur-grey
    { name: "chicken", color: "#FFF7E0" },       // off-white with yellow undertone
    { name: "cod", color: "#5FAED9" },           // silvery-sea blue
    { name: "copper_golem", color: "#B56A36" },  // copper
    { name: "cow", color: "#6B4F3A" },           // brown cow hide
    { name: "donkey", color: "#8B7A66" },        // dull brown-gray
    { name: "frog", color: "#6DBE45" },          // bright pond green
    { name: "glow_squid", color: "#21FFE6" },    // neon aqua
    { name: "happy_ghast", color: "#F3EEF2" },   // pale, slightly warm white
    { name: "horse", color: "#8A5A36" },         // chestnut
    { name: "mooshroom", color: "#D63333" },     // mushroom red
    { name: "mule", color: "#776655" },          // muted brown
    { name: "ocelot", color: "#D6A84A" },        // golden spotted
    { name: "parrot", color: "#2EA84A" },        // vivid tropical green
    { name: "pig", color: "#FFB0B8" },           // pig pink
    { name: "rabbit", color: "#CDBCA8" },        // soft fur beige
    { name: "salmon", color: "#FF8B75" },        // salmon-pink
    { name: "sheep", color: "#EFEFEF" },         // wool white
    { name: "sniffer", color: "#8AA24B" },       // earthy olive-green (sniffer vibe)
    { name: "snow_golem", color: "#EAF6FF" },    // icy blue-white
    { name: "squid", color: "#213142" },         // deep inky blue
    { name: "strider", color: "#E0523C" },       // hot-pod orange-red
    { name: "tadpole", color: "#375B3A" },       // dark tadpole green
    { name: "tropical_fish", color: "#FFB54E" }, // bright orange accent
    { name: "turtle", color: "#2E8B66" },        // teal shell
    { name: "villager", color: "#8B6E4F" },      // robed-earth brown
    { name: "wandering_trader", color: "#4E6DB8" }, // blue-purple cloak
    { name: "skeleton_horse", color: "#DADADA" },// bone gray
    { name: "bee", color: "#FFCF2E" },           // honey yellow
    { name: "dolphin", color: "#6BB7D6" },       // ocean blue
    { name: "fox", color: "#D9692A" },           // fox orange
    { name: "goat", color: "#E4D7C6" },          // cream
    { name: "iron_golem", color: "#C2C6C9" },    // weathered iron
    { name: "llama", color: "#B88B63" },         // warm beige wool
    { name: "panda", color: "#1F1F1F" },         // black (panda markings)
    { name: "polar_bear", color: "#F2F8FA" },    // arctic white
    { name: "pufferfish", color: "#FFCC66" },    // yellow-orange
    { name: "trader_llama", color: "#5EB4A4" },  // decorative teal saddle tone
    { name: "wolf", color: "#7D827F" },          // gray wolf fur
    { name: "cave_spider", color: "#1E3A46" },   // dark blue-black
    { name: "drowned", color: "#3B8A86" },       // algae-green
    { name: "enderman", color: "#0F0A12" },      // very dark purplish-black
    { name: "piglin", color: "#AF6A4B" },        // hoglin/piglin reddish-brown
    { name: "spider", color: "#2E2322" },        // dark brown-black
    { name: "zombified_piglin", color: "#EA9393" }, // pinkish flesh
    { name: "blaze", color: "#FF8C1A" },         // blaze flame orange
    { name: "bogged", color: "#6B8A5B" },        // swampy olive
    { name: "breeze", color: "#9FD3E6" },        // airy pale blue
    { name: "creaking", color: "#7A6B5A" },      // aged wood brown
    { name: "creeper", color: "#5FB55F" },       // creeper green
    { name: "elder_guardian", color: "#3EA3A3" },// pale teal
    { name: "endermite", color: "#6B4F8C" },     // small purple-gray
    { name: "evoker", color: "#5A4FB6" },        // dark indigo
    { name: "ghast", color: "#F6F2F2" },         // ghost-white
    { name: "guardian", color: "#2FA6A6" },      // teal sea-guardian
    { name: "hoglin", color: "#A63D2A" },        // raw hog red
    { name: "husk", color: "#C29A6B" },          // desert-baked tan
    { name: "magma_cube", color: "#D8471A" },    // lava orange-red
    { name: "phantom", color: "#22354B" },       // night-sky navy
    { name: "piglin_brute", color: "#8E3B2F" },  // brutish deep red
    { name: "pillager", color: "#513F6B" },      // dark slate purple
    { name: "ravager", color: "#4A4A4A" },       // stone-gray
    { name: "shulker", color: "#C462FF" },       // shulker purple-magenta
    { name: "silverfish", color: "#BFC7C9" },    // silvery
    { name: "skeleton", color: "#CFCFCF" },      // bone gray
    { name: "slime", color: "#61E04B" },         // bright slime green
    { name: "stray", color: "#8FCFD6" },         // frosty blue
    { name: "vex", color: "#A3C3D9" },           // ghostly pale blue
    { name: "vindicator", color: "#3F4F4F" },    // bleak slate
    { name: "warden", color: "#274F4C" },        // deep mineral teal
    { name: "witch", color: "#6B2E6B" },         // bruise purple
    { name: "wither_skeleton", color: "#0B0B0B" },// near-black
    { name: "zoglin", color: "#6D3E4A" },        // corrupted mauve
    { name: "zombie", color: "#5CA86B" },        // sickly green
    { name: "zombie_villager", color: "#6C8A4F" },// diseased olive
    { name: "ender_dragon", color: "#2E005A" },  // deep ender purple
    { name: "wither", color: "#111111" },        // void-black
    { name: "player", color: "#ff00b6" }         // TODO: change to j3l gradient?
];

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
    { name: "FinoDinoPlays", color: ":#bb2222" },
    { name: "NewYorkMaddie", color: "-image:linear-gradient(to right, #2ef5ff, #808080, #ff4200)" },
    { name: "sweetstarfish84", color: ":#f08080" },
    { name: "Rydog12144", color: ":#00d4ff" },
    { name: "Cinnamon111", color: ":#ff6b9d" },
    { name: "superkidbox", color: ":#9d4edd" },
    { name: "tienwun", color: ":#3a86ff" },
    { name: "JustChow", color: ":#06d6a0" },
    { name: "Encorous", color: ":#fb5607" }
];

export function getAnimalColor(name) {
    const entry = animalColors.find(m => m.name === name);
    return entry ? entry.color : null;
}

export function getPlayerColor(name) {
    const entry = players.find(m => m.name === name);
    return entry ? entry.color : null;
}

export function lighten(hex, amount = 0.25) {
    const { r, g, b } = hexToRgb(hex);
    let { h, s, l } = rgbToHsl(r, g, b);

    l = Math.min(1, l + amount);
    l = Math.max(0, l);

    const { r: r2, g: g2, b: b2 } = hslToRgb(h, s, l);
    return rgbToHex(r2, g2, b2);
}

function hexToRgb(hex) {
    hex = hex.replace("#", "");
    const bigInt = parseInt(hex, 16);
    return {
        r: (bigInt >> 16) & 255,
        g: (bigInt >> 8) & 255,
        b: bigInt & 255
    };
}

export function hexToRgba(hex, a) {
    hex = hex.replace("#", "");
    const bigInt = parseInt(hex, 16);
    return ((bigInt >> 16) & 255) + ", " + ((bigInt >> 8) & 255) + ", " + (bigInt & 255) + ", " + a;
}

function rgbToHex(r, g, b) {
    return "#" + [r, g, b]
        .map(x => x.toString(16).padStart(2, "0"))
        .join("");
}

function rgbToHsl(r, g, b) {
    r /= 255; g /= 255; b /= 255;
    const max = Math.max(r, g, b), min = Math.min(r, g, b);
    let h, s, l = (max + min) / 2;

    if (max === min) {
        h = s = 0;
    } else {
        const d = max - min;
        s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
        switch (max) {
            case r: h = (g - b) / d + (g < b ? 6 : 0); break;
            case g: h = (b - r) / d + 2; break;
            case b: h = (r - g) / d + 4; break;
        }
        h /= 6;
    }
    return { h, s, l };
}

function hslToRgb(h, s, l) {
    let r, g, b;

    if (s === 0) {
        r = g = b = l;
    } else {
        const hue2rgb = (p, q, t) => {
            if (t < 0) t += 1;
            if (t > 1) t -= 1;
            if (t < 1 / 6) return p + (q - p) * 6 * t;
            if (t < 1 / 2) return q;
            if (t < 2 / 3) return p + (q - p) * (2 / 3 - t) * 6;
            return p;
        };
        const q = l < 0.5 ? l * (1 + s) : l + s - l * s;
        const p = 2 * l - q;

        r = hue2rgb(p, q, h + 1 / 3);
        g = hue2rgb(p, q, h);
        b = hue2rgb(p, q, h - 1 / 3);
    }

    return {
        r: Math.round(r * 255),
        g: Math.round(g * 255),
        b: Math.round(b * 255)
    };
}

export function invertHex(hex) {
    return "#" + (0xffffff ^ parseInt(hex.replace("#", ""), 16)).toString(16).padStart(6, '0');
}