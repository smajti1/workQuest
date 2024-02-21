import { writable } from 'svelte/store';

export const jobOfferVariantList = writable([
	{ category: 'Total', city: null, selected: true, lineColor: 'rgb(54, 162, 235)' },
	{ category: 'Total', city: 'Warsaw', selected: false, lineColor: 'rgb(255, 99, 132)' },
	{ category: 'Kotlin', city: null, selected: false, lineColor: 'rgb(255, 159, 64)' },
	{ category: 'Kotlin', city: 'Warsaw', selected: false, lineColor: 'rgb(255, 205, 86)' },
	{ category: 'Php', city: null, selected: false, lineColor: 'rgb(75, 192, 192)' },
	{ category: 'Php', city: 'Warsaw', selected: false, lineColor: 'rgb(153, 102, 255)' },
]);