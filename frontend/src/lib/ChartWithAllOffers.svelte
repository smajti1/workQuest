<script lang="ts">
	import {
		Chart,
		Colors,
		LineController,
		CategoryScale,
		LinearScale,
		LineElement,
		Legend,
		PointElement,
		Tooltip,
	} from 'chart.js';
	import { get } from 'svelte/store';
	import { fetchJobOffer, addOrRemoveDataset, mapPortalConstToString } from '$lib/ChartWithAllOffers.js';
	import Icon from '$lib/Icon.svelte';

	export let jobOfferVariantList;
	const jobOfferPromise = fetchJobOffer(get(jobOfferVariantList)[0]);
	let chart;

	Chart.register(
		Colors,
		LineController,
		LineElement,
		CategoryScale,
		LinearScale,
		Legend,
		PointElement,
		Tooltip,
	);

	function initializeChart(chartNode, jobOffer) {
		chart = new Chart(chartNode, {
			type: 'line',
			data: {
				datasets: [
					jobOffer
				],
			},
			options: {
				elements: {
					line: {
						tension: 0.4,
					},
				},
				plugins: {
					colors: {
						forceOverride: true,
					},
					tooltip: {
						mode: 'index',
						position: 'nearest',
						intersect: false
					},
				},
			},
		});
	}
</script>

<div class="relative mx-auto">
{#await jobOfferPromise}
	<p>
		fetching data
		<Icon name="spinner" class="inline animate-spin w-6 h-6"/>
	</p>
{:then jobOffer}
	<div class="scroll-px-10 snap-x scroll-px-4 snap-mandatory scroll-smooth flex gap-4 overflow-x-auto px-2 py-5 mb-2 lg:justify-center">
		{#each $jobOfferVariantList as jobOfferVariant}
			<button
				class="snap-center chip {jobOfferVariant.selected ? 'variant-filled' : 'variant-filled-surface'}"
				on:click={() => { addOrRemoveDataset(jobOfferVariantList, chart, jobOfferVariant); }}
				on:keypress
			>
				{#if jobOfferVariant.selected}
				<span>
					<Icon name="check" class="w4 h-4"/>
				</span>
				{/if}
				<span>
					{mapPortalConstToString(jobOfferVariant.jobPortal)}
					: <strong>{jobOfferVariant.category}</strong>{jobOfferVariant.city ? ` - ${jobOfferVariant.city}` : ''}
				</span>
			</button>
		{/each}
	</div>
	<div id="chartContainer" class="rounded-md">
		<canvas id="myChart" use:initializeChart={jobOffer}></canvas>
	</div>
{:catch error}
	<p style="color: red">{error.message}</p>
{/await}
</div>

<style>
	div#chartContainer {
		background-color: rgba(var(--color-surface-50) / 1);
	}
</style>