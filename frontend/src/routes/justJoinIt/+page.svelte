<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { jobOfferVariantList } from '$lib/store.ts';

	const JUST_JOIN_IT = 'JUST_JOIN_IT';
</script>

<h1>Chart with <a href="https://justJoin.it" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">justJoin.it</a> offers</h1>

<ChartWithAllOffers jobPortal={JUST_JOIN_IT} jobOfferVariantList={jobOfferVariantList} />