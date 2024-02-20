<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { jobOfferVariantList } from '$lib/store.ts';

	const IN_HIRE_IO = 'IN_HIRE_IO';
</script>

<h1>Chart with <a href="https://inhire.io" target="_blank" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">inHire.io</a> offers</h1>

<div class="container mx-auto">
	<ChartWithAllOffers jobPortal={IN_HIRE_IO} jobOfferVariantList={jobOfferVariantList} />
</div>