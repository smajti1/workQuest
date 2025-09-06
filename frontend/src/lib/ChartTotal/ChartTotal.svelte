<script lang="ts">
	import { Chart, LineController, CategoryScale, LinearScale, LineElement, Legend, PointElement, Tooltip } from 'chart.js';
	import { fetchTotalJobOffer } from '$lib/ChartTotal/ChartTotal.js';
	import Icon from '$lib/Icon.svelte';

	const jobOfferTotalPromise = fetchTotalJobOffer();
	let chart;

	Chart.register(LineController, LineElement, CategoryScale, LinearScale, Legend, PointElement, Tooltip);

	function initializeChart(chartNode, jobOfferTotalData) {
		chart = new Chart(chartNode, {
			type: 'line',
			...jobOfferTotalData,
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
						intersect: false,
					},
				},
			},
		});
	}
</script>

<div class="relative mx-auto">
	{#await jobOfferTotalPromise}
		<p>
			fetching data
			<Icon name="spinner" class="inline h-6 w-6 animate-spin" />
		</p>
	{:then jobOfferTotalData}
		<div id="chartContainer" class="rounded-md dark:preset-filled">
			<canvas id="myChart" use:initializeChart={jobOfferTotalData} class="w-full"></canvas>
		</div>
	{:catch error}
		<p style="color: red">{error.message}</p>
	{/await}
</div>

<style>
</style>
