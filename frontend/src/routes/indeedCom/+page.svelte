<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { writable } from 'svelte/store';

	const INDEED_COM = 'INDEED_COM';
	export let jobOfferVariantList = writable([
		{jobPortal: INDEED_COM, category: 'Total', city: null, selected: true},
		{jobPortal: INDEED_COM, category: 'Total', city: 'Warsaw', selected: false},
		{jobPortal: INDEED_COM, category: 'Kotlin', city: null, selected: false},
		{jobPortal: INDEED_COM, category: 'Kotlin', city: 'Warsaw', selected: false},
		{jobPortal: INDEED_COM, category: 'Php', city: null, selected: false},
		{jobPortal: INDEED_COM, category: 'Php', city: 'Warsaw', selected: false},
	]);
</script>

<h1>Chart with <a href="https://pl.indeed.com" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">pl.indeed.com</a> offers</h1>

<div class="container mx-auto">
	<ChartWithAllOffers jobOfferVariantList={jobOfferVariantList} />
</div>