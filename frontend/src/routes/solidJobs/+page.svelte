<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { jobOfferVariantList } from '$lib/store.ts';

	const SOLID_JOBS = 'SOLID_JOBS';
</script>

<h1>Chart with <a href="https://solid.jobs" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">solid.jobs</a> offers</h1>

<div class="container mx-auto">
	<ChartWithAllOffers jobPortal={SOLID_JOBS} jobOfferVariantList={jobOfferVariantList} />
</div>