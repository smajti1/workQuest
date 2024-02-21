<script lang="ts">
	import {
		Chart,
		Colors,
		PieController,
		Legend,
		Tooltip,
		ArcElement
	} from 'chart.js';
	import Icon from '$lib/Icon.svelte';
	import { mapPortalConstToString } from '$lib/ChartWithAllOffers';
	import { PUBLIC_BASE_URL } from '$env/static/public';
	import { JobCategory, type JobOfferCount } from '$lib/model/JobOfferCount';

	let chart;

	export let jobCategory: JobCategory;
	const apiUrl = `/api/v1/total/today?jobCategory=${jobCategory}`;
	let label = '';
	switch (jobCategory) {
		case JobCategory.Kotlin:
			label = 'Kotlin';
			break;
		case JobCategory.Php:
			label = 'Php';
			break;
		default:
		case JobCategory.Total:
			label = '';
		break
	}

	Chart.register(
		Colors,
		PieController,
		Legend,
		Tooltip,
		ArcElement
	);

	async function initializeChart(chartNode, responseData: JobOfferCount[]) {
		let { data, countTotal } = getChartDataFromResponse(responseData);

		chart = new Chart(chartNode, {
			type: 'pie',
			data: data,
			options: {
				plugins: {
					tooltip: {
						callbacks: {
							label: function(context) {
								const count = context.dataset.data[context.dataIndex];
								return [
									' ' + count + ' offers',
									(count / countTotal * 100).toFixed(2) + '% of total'
								];
							}
						}
					}
				}
			}
		});
	}

	function getChartDataFromResponse(responseData: JobOfferCount[]) {
		let data = {
			labels: [],
			datasets: []
		};
		let datasetCount = [];
		let countTotal = 0;
		for (const key in responseData) {
			let totalOfferCount = responseData[key];
			data.labels.push(mapPortalConstToString(totalOfferCount.jobPortal));
			datasetCount.push(totalOfferCount.count);
			countTotal += totalOfferCount.count;
		}
		data.datasets.push({ data: datasetCount });
		return { data, countTotal };
	}

	function countTotalOfferNumber(responseData: JobOfferCount[]): number {
		let countTotal = 0;
		for (const key in responseData) {
			countTotal += responseData[key].count;
		}
		return countTotal;
	}

	async function getTotalOfferCount() {
		return await fetch(PUBLIC_BASE_URL + apiUrl)
			.then((response) => response.json())
			.then((data) => data);
	}
</script>

{#await getTotalOfferCount()}
	<p>
		fetching data
		<Icon name="spinner" class="inline animate-spin w-6 h-6"/>
	</p>
{:then totalOfferCount}
	<div>
		Total <strong>{label}</strong> offer number ({countTotalOfferNumber(totalOfferCount).toLocaleString()}) per job portal for today
		<div id="chartContainer" class="relative mx-auto rounded-md">
			<canvas id="myChart" use:initializeChart={totalOfferCount}></canvas>
		</div>
	</div>
{:catch error}
	<p style="color: red">{error.message}</p>
{/await}

<style>
	div#chartContainer {
		background: rgba(var(--color-surface-50) / 1);
	}
</style>