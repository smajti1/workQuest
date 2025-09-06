<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { jobOfferVariantList } from '$lib/store.ts';

	const PRACUJ_PL = 'PRACUJ_PL';
</script>

<h1>Chart with <a href="https://it.pracuj.pl/praca" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">it.pracuj.pl</a> offers</h1>

<ChartWithAllOffers jobPortal={PRACUJ_PL} jobOfferVariantList={jobOfferVariantList} />