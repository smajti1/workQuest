<script lang="ts">
	import {
		Chart,
		Colors,
		PieController,
		Tooltip,
		ArcElement
	} from 'chart.js';
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
		Tooltip,
		ArcElement
	);

	async function initializeChart(chartNode, totalOfferCountResponse: Response) {
		const responseData = await totalOfferCountResponse.json();
		let { data, countTotal } = getCHartDataFromResponse(responseData);

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

	function getCHartDataFromResponse(responseData: JobOfferCountType[]) {
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
		<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
				 class="inline animate-spin w-6 h-6">
			<path stroke-linecap="round" stroke-linejoin="round"
						d="M16.023 9.348h4.992v-.001M2.985 19.644v-4.992m0 0h4.992m-4.993 0 3.181 3.183a8.25 8.25 0 0 0 13.803-3.7M4.031 9.865a8.25 8.25 0 0 1 13.803-3.7l3.181 3.182m0-4.991v4.99" />
		</svg>
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