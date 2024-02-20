import { writable } from 'svelte/store';

export const jobOfferVariantList = writable([
	{ category: 'Total', city: null, selected: true },
	{ category: 'Total', city: 'Warsaw', selected: false },
	{ category: 'Kotlin', city: null, selected: false },
	{ category: 'Kotlin', city: 'Warsaw', selected: false },
	{ category: 'Php', city: null, selected: false },
	{ category: 'Php', city: 'Warsaw', selected: false },
]);