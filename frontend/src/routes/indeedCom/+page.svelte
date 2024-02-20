<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { jobOfferVariantList } from '$lib/store.ts';

	const INDEED_COM = 'INDEED_COM';
</script>

<h1>Chart with <a href="https://pl.indeed.com" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">pl.indeed.com</a> offers</h1>

<div class="container mx-auto">
	<ChartWithAllOffers jobPortal={INDEED_COM} jobOfferVariantList={jobOfferVariantList} />
</div>