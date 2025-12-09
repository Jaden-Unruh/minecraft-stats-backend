import { cubicOut } from "svelte/easing";

export function growX(node, {
    duration = 300,
    easing = cubicOut
} = {}) {
    return {
        duration,
        easing,
        css: t => `
        transform-origin: left;
        transform: scaleX(${t})
        `
    };
}

export function growY(node, {
    duration = 300,
    easing = cubicOut
} = {}) {
    return {
        duration,
        easing,
        css: t => `
        transform-origin: bottom;
        transform: scaleY(${t})
        `
    };
}