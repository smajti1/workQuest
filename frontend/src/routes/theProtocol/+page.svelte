<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { writable } from 'svelte/store';

	const THE_PROTOCOL = 'THE_PROTOCOL';
	export let jobOfferVariantList = writable([
		{jobPortal: THE_PROTOCOL, category: 'Total', city: null, selected: true},
		{jobPortal: THE_PROTOCOL, category: 'Total', city: 'Warsaw', selected: false},
		{jobPortal: THE_PROTOCOL, category: 'Kotlin', city: null, selected: false},
		{jobPortal: THE_PROTOCOL, category: 'Kotlin', city: 'Warsaw', selected: false},
		{jobPortal: THE_PROTOCOL, category: 'Php', city: null, selected: false},
		{jobPortal: THE_PROTOCOL, category: 'Php', city: 'Warsaw', selected: false},
	]);
</script>

<h1>Chart with <a href="https://theprotocol.it" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">theProtocol.it</a> offers</h1>

<div class="container mx-auto">
	<ChartWithAllOffers jobOfferVariantList={jobOfferVariantList} />
</div>