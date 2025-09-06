<script lang="ts">
	import {
		Chart,
		LineController,
		CategoryScale,
		LinearScale,
		LineElement,
		Legend,
		PointElement,
		Tooltip,
	} from 'chart.js';
	import { get } from 'svelte/store';
	import {
		fetchJobOffer,
		addOrRemoveDataset,
		mapPortalConstToString,
		fetchRestSelectedJobOffer,
	} from '$lib/ChartWithAllOffers.js';
	import Icon from '$lib/Icon.svelte';

	let { jobPortal, jobOfferVariantList } = $props();
	const jobOfferSelectedList = get(jobOfferVariantList).filter((obj) => obj.selected);
	const jobOfferPromise = fetchJobOffer(jobPortal, jobOfferSelectedList[0], jobOfferVariantList);
	let chart;

	Chart.register(
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
		fetchRestSelectedJobOffer(jobPortal, chart, jobOfferVariantList, jobOfferSelectedList);
	}
</script>

<div class="relative mx-auto">
{#await jobOfferPromise}
	<p>
		fetching data
		<Icon name="spinner" class="inline animate-spin w-6 h-6"/>
	</p>
{:then jobOffer}
	<div class="scroll-px-10 snap-x scroll-px-4 snap-mandatory scroll-smooth flex gap-4 overflow-x-auto px-2 py-5 mb-2 lg:justify-center items-center">
		{#each $jobOfferVariantList as jobOfferVariant}
			<button
				type="button"
				class="snap-center chip {jobOfferVariant.selected ? 'preset-filled-surface-500' : 'preset-outlined-surface-500'}"
				onclick={() => { addOrRemoveDataset(jobPortal, jobOfferVariantList, chart, jobOfferVariant); }}
				onkeypress={() => {}}
			>
				{#if jobOfferVariant.selected}
					<Icon name="check" class="h-3"/>
				{/if}
				<span>
					{mapPortalConstToString(jobPortal)}
					: <strong>{jobOfferVariant.category}</strong>{jobOfferVariant.city ? ` - ${jobOfferVariant.city}` : ''}
				</span>
			</button>
		{/each}
	</div>
	<div id="chartContainer" class="rounded-md dark:preset-filled">
		<canvas id="myChart" use:initializeChart={jobOffer} class="w-full"></canvas>
	</div>
{:catch error}
	<p style="color: red">{error.message}</p>
{/await}
</div>