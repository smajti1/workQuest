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
	import { onMount } from 'svelte';
	import { jobOfferVariantList, addOrRemoveDataset, mapPortalConstToString } from '$lib/ChartWithAllOffers.js';

	export let initializedData;
	let chart;
	onMount(() => {
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
		const ctx = document.getElementById('myChart');

		initializedData.jobOfferVariantIndex = 0;
		chart = new Chart(ctx, {
			type: 'line',
			data: {
				datasets: [
					initializedData,
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
	});
</script>

<div class="container mx-auto">
	<div class="flex justify-center space-x-2">
		{#each $jobOfferVariantList as jobOfferVariant}
			<button
				class="chip {jobOfferVariant.selected ? 'variant-filled' : 'variant-filled-surface'}"
				on:click={() => { addOrRemoveDataset(chart, jobOfferVariant); }}
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
				<span class="capitalize">
					{mapPortalConstToString(jobOfferVariant.jobPortal)}
					: <strong>{jobOfferVariant.category}</strong>{jobOfferVariant.city ? ` - ${jobOfferVariant.city}` : ''}
				</span>
			</button>
		{/each}
	</div>
	<br />
	<div id="chartContainer" class="rounded-md">
		<canvas id="myChart"></canvas>
	</div>
</div>

<style>
	div#chartContainer {
		background-color: #fff;
	}
</style>