<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { writable } from 'svelte/store';

	const BULLDOG_JOB = 'BULLDOG_JOB';
	export let jobOfferVariantList = writable([
		{jobPortal: BULLDOG_JOB, category: 'Total', city: null, selected: true},
		{jobPortal: BULLDOG_JOB, category: 'Total', city: 'Warsaw', selected: false},
		{jobPortal: BULLDOG_JOB, category: 'Kotlin', city: null, selected: false},
		{jobPortal: BULLDOG_JOB, category: 'Kotlin', city: 'Warsaw', selected: false},
		{jobPortal: BULLDOG_JOB, category: 'Php', city: null, selected: false},
		{jobPortal: BULLDOG_JOB, category: 'Php', city: 'Warsaw', selected: false},
	]);
</script>

<h1>Chart with <a href="https://bulldogJob.pl" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">bulldogJob.pl</a> offers</h1>

<div class="container mx-auto">
	<ChartWithAllOffers jobOfferVariantList={jobOfferVariantList} />
</div>