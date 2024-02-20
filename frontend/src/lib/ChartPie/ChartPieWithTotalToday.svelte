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

	let chart;
	const totalOfferCountPromise = fetch(PUBLIC_BASE_URL + `/api/v1/total/today`);
	type JobOfferCountType = {
		id: string,
		jobPortal: string,
		category: string,
		city: string|null,
		count: number,
	};

	Chart.register(
		Colors,
		PieController,
		Legend,
		Tooltip,
		ArcElement
	);

	async function initializeChart(chartNode, totalOfferCountResponse: Response) {
		const responseData = await totalOfferCountResponse.json();
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

	function getChartDataFromResponse(responseData: JobOfferCountType[]) {
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
</script>

{#await totalOfferCountPromise}
	<p>
		fetching data
		<Icon name="spinner" class="inline animate-spin w-6 h-6"/>
	</p>
{:then totalOfferCount}
	<div>
		Total offer number per job portal for today
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