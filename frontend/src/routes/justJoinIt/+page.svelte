<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { writable } from 'svelte/store';

	const JUST_JOIN_IT = 'JUST_JOIN_IT';
	export let jobOfferVariantList = writable([
		{jobPortal: JUST_JOIN_IT, category: 'Total', city: null, selected: true},
		{jobPortal: JUST_JOIN_IT, category: 'Total', city: 'Warsaw', selected: false},
		{jobPortal: JUST_JOIN_IT, category: 'Kotlin', city: null, selected: false},
		{jobPortal: JUST_JOIN_IT, category: 'Kotlin', city: 'Warsaw', selected: false},
		{jobPortal: JUST_JOIN_IT, category: 'Php', city: null, selected: false},
		{jobPortal: JUST_JOIN_IT, category: 'Php', city: 'Warsaw', selected: false},
	]);
</script>

<h1>Chart with <a href="https://justJoin.it" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">justJoin.it</a> offers</h1>

<div class="container mx-auto">
	<ChartWithAllOffers jobOfferVariantList={jobOfferVariantList} />
</div>