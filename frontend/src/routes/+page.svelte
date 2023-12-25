<script>
    import ChartWithAllOffers from '$lib/ChartWithAllOffers.svelte';
		import { fetchAllJobOffer } from '$lib/ChartWithAllOffers.ts'
		const allJobOfferPromise = fetchAllJobOffer();
</script>

<h1>Chart with all offers</h1>
<div>
	{#await allJobOfferPromise}
		<p>fetching data</p>
	{:then allJobOfferData}
		<ChartWithAllOffers data={allJobOfferData} />
	{:catch error}
		<p style="color: red">{error.message}</p>
	{/await}
</div>
