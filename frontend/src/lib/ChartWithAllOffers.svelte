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
				},
			},
		});
	};
</script>

<div class="relative mx-auto">
{#await jobOfferPromise}
	<p>
		fetching data
		<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
				 class="inline animate-spin w-6 h-6">
			<path stroke-linecap="round" stroke-linejoin="round"
						d="M16.023 9.348h4.992v-.001M2.985 19.644v-4.992m0 0h4.992m-4.993 0 3.181 3.183a8.25 8.25 0 0 0 13.803-3.7M4.031 9.865a8.25 8.25 0 0 1 13.803-3.7l3.181 3.182m0-4.991v4.99" />
		</svg>
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
					<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4">
						<path fill-rule="evenodd"
									d="M12.416 3.376a.75.75 0 0 1 .208 1.04l-5 7.5a.75.75 0 0 1-1.154.114l-3-3a.75.75 0 0 1 1.06-1.06l2.353 2.353 4.493-6.74a.75.75 0 0 1 1.04-.207Z"
									clip-rule="evenodd" />
					</svg>
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
		background-color: #fff;
	}
</style>