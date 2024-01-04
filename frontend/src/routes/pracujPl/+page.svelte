<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { writable } from 'svelte/store';

	const PRACUJ_PL = 'PRACUJ_PL';
	export let jobOfferVariantList = writable([
		{jobPortal: PRACUJ_PL, category: 'Total', city: null, selected: true},
		{jobPortal: PRACUJ_PL, category: 'Total', city: 'Warsaw', selected: false},
		{jobPortal: PRACUJ_PL, category: 'Kotlin', city: null, selected: false},
		{jobPortal: PRACUJ_PL, category: 'Kotlin', city: 'Warsaw', selected: false},
		{jobPortal: PRACUJ_PL, category: 'Php', city: null, selected: false},
		{jobPortal: PRACUJ_PL, category: 'Php', city: 'Warsaw', selected: false},
	]);
</script>

<h1>Chart with <a href="https://it.pracuj.pl/praca" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">it.pracuj.pl</a> offers</h1>

<div class="container mx-auto">
	<ChartWithAllOffers jobOfferVariantList={jobOfferVariantList} />
</div>