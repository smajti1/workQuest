<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { jobOfferVariantList } from '$lib/store.ts';

	const THE_PROTOCOL = 'THE_PROTOCOL';
</script>

<h1>Chart with <a href="https://theprotocol.it" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">theProtocol.it</a> offers</h1>

<ChartWithAllOffers jobPortal={THE_PROTOCOL} jobOfferVariantList={jobOfferVariantList} />