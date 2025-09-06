<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { jobOfferVariantList } from '$lib/store.ts';

	const IT_LEADERS = 'IT_LEADERS';
</script>

<h1>Chart with <a href="https://it-leaders.pl" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">it-leaders.pl</a> offers</h1>

<ChartWithAllOffers jobPortal={IT_LEADERS} jobOfferVariantList={jobOfferVariantList} />