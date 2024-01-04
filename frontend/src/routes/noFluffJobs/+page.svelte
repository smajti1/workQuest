<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { writable } from 'svelte/store';

	const NO_FLUFF_JOBS_COM = 'NO_FLUFF_JOBS_COM';
	export let jobOfferVariantList = writable([
		{jobPortal: NO_FLUFF_JOBS_COM, category: 'Total', city: null, selected: true},
		{jobPortal: NO_FLUFF_JOBS_COM, category: 'Total', city: 'Warsaw', selected: false},
		{jobPortal: NO_FLUFF_JOBS_COM, category: 'Kotlin', city: null, selected: false},
		{jobPortal: NO_FLUFF_JOBS_COM, category: 'Kotlin', city: 'Warsaw', selected: false},
		{jobPortal: NO_FLUFF_JOBS_COM, category: 'Php', city: null, selected: false},
		{jobPortal: NO_FLUFF_JOBS_COM, category: 'Php', city: 'Warsaw', selected: false},
	]);
</script>

<h1>Chart with <a href="https://noFluffJobs.com" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">noFluffJobs.com</a> offers</h1>

<div class="container mx-auto">
	<ChartWithAllOffers jobOfferVariantList={jobOfferVariantList} />
</div>