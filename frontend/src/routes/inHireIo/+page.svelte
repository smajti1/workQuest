<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { writable } from 'svelte/store';

	const IN_HIRE_IO = 'IN_HIRE_IO';
	export let jobOfferVariantList = writable([
		{jobPortal: IN_HIRE_IO, category: 'Total', city: null, selected: true},
		{jobPortal: IN_HIRE_IO, category: 'Total', city: 'Warszawa', selected: false},
		{jobPortal: IN_HIRE_IO, category: 'Kotlin', city: null, selected: false},
		{jobPortal: IN_HIRE_IO, category: 'Kotlin', city: 'Warszawa', selected: false},
		{jobPortal: IN_HIRE_IO, category: 'Php', city: null, selected: false},
		{jobPortal: IN_HIRE_IO, category: 'Php', city: 'Warszawa', selected: false},
	]);
</script>

<h1>Chart with <a href="https://InHire.io" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">InHire.io</a> offers</h1>

<div class="container mx-auto">
	<ChartWithAllOffers jobOfferVariantList={jobOfferVariantList} />
</div>