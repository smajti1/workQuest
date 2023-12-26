<script>
	import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
	import { fetchJobOffer } from '$lib/ChartWithAllOffers.ts';

	const allJobOfferPromise = fetchJobOffer();
</script>

<h1>Chart with all offers</h1>
<br />
<div class="container mx-auto">
	{#await allJobOfferPromise}
		<p>fetching data</p>
	{:then allJobOfferData}
		<ChartWithAllOffers initializedData={allJobOfferData} />
	{:catch error}
		<p style="color: red">{error.message}</p>
	{/await}
</div>
