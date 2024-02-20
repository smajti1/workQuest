<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { jobOfferVariantList } from '$lib/store.ts';

	const BULLDOG_JOB = 'BULLDOG_JOB';
</script>

<h1>Chart with <a href="https://bulldogJob.pl" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">bulldogJob.pl</a> offers</h1>

<div class="container mx-auto">
	<ChartWithAllOffers jobPortal={BULLDOG_JOB} jobOfferVariantList={jobOfferVariantList} />
</div>