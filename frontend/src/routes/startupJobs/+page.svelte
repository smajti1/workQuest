<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { jobOfferVariantList } from '$lib/store.ts';

	const STARTUP_JOBS = 'STARTUP_JOBS';
</script>

<h1>Chart with <a href="https://startupJobs.com" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">startupJobs.com</a> offers</h1>

<ChartWithAllOffers jobPortal={STARTUP_JOBS} jobOfferVariantList={jobOfferVariantList} />