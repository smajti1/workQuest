<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { jobOfferVariantList } from '$lib/store.ts';

	const NO_FLUFF_JOBS_COM = 'NO_FLUFF_JOBS_COM';
</script>

<h1>Chart with <a href="https://noFluffJobs.com" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">noFluffJobs.com</a> offers</h1>

<ChartWithAllOffers jobPortal={NO_FLUFF_JOBS_COM} jobOfferVariantList={jobOfferVariantList} />