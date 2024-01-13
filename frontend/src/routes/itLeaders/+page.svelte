<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { writable } from 'svelte/store';

	const IT_LEADERS = 'IT_LEADERS';
	export let jobOfferVariantList = writable([
		{jobPortal: IT_LEADERS, category: 'Total', city: null, selected: true},
		{jobPortal: IT_LEADERS, category: 'Total', city: 'Warsaw', selected: false},
		{jobPortal: IT_LEADERS, category: 'Kotlin', city: null, selected: false},
		{jobPortal: IT_LEADERS, category: 'Kotlin', city: 'Warsaw', selected: false},
		{jobPortal: IT_LEADERS, category: 'Php', city: null, selected: false},
		{jobPortal: IT_LEADERS, category: 'Php', city: 'Warsaw', selected: false},
	]);
</script>

<h1>Chart with <a href="https://it-leaders.pl" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">it-leaders.pl</a> offers</h1>

<div class="container mx-auto">
	<ChartWithAllOffers jobOfferVariantList={jobOfferVariantList} />
</div>