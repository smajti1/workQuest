<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { writable } from 'svelte/store';

	const STARTUP_JOBS = 'STARTUP_JOBS';
	export let jobOfferVariantList = writable([
		{jobPortal: STARTUP_JOBS, category: 'Total', city: null, selected: true},
		{jobPortal: STARTUP_JOBS, category: 'Total', city: 'Warsaw', selected: false},
		{jobPortal: STARTUP_JOBS, category: 'Kotlin', city: null, selected: false},
		{jobPortal: STARTUP_JOBS, category: 'Kotlin', city: 'Warsaw', selected: false},
		{jobPortal: STARTUP_JOBS, category: 'Php', city: null, selected: false},
		{jobPortal: STARTUP_JOBS, category: 'Php', city: 'Warsaw', selected: false},
	]);
</script>

<h1>Chart with <a href="https://startupJobs.com" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">startupJobs.com</a> offers</h1>

<div class="container mx-auto">
	<ChartWithAllOffers jobOfferVariantList={jobOfferVariantList} />
</div>