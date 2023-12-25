<script>
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
	export let data;

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

		new Chart(ctx, {
			type: 'line',
			data: data,
			options: {
				scales: {
					y: {
						beginAtZero: true,
					},
				},
			},
		});


	});
</script>

<div style="width: 800px;">
	<canvas id="myChart"></canvas>
</div>

<style>
	div {
		background-color: #fff;
	}
</style>