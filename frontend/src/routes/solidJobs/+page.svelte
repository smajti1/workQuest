<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { writable } from 'svelte/store';

	const SOLID_JOBS = 'SOLID_JOBS';
	export let jobOfferVariantList = writable([
		{jobPortal: SOLID_JOBS, category: 'Total', city: null, selected: true},
		{jobPortal: SOLID_JOBS, category: 'Total', city: 'Warsaw', selected: false},
		{jobPortal: SOLID_JOBS, category: 'Kotlin', city: null, selected: false},
		{jobPortal: SOLID_JOBS, category: 'Kotlin', city: 'Warsaw', selected: false},
		{jobPortal: SOLID_JOBS, category: 'Php', city: null, selected: false},
		{jobPortal: SOLID_JOBS, category: 'Php', city: 'Warsaw', selected: false},
	]);
</script>

<h1>Chart with <a href="https://solid.jobs" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">solid.jobs</a> offers</h1>

<div class="container mx-auto">
	<ChartWithAllOffers jobOfferVariantList={jobOfferVariantList} />
</div>