<script>
	import ChartPieWithTotalToday from '$lib/ChartPie/ChartPieWithTotalToday.svelte';
</script>

<h1>Select portal with offers</h1>

<div class="logo-cloud grid-cols-1 lg:!grid-cols-5 gap-1">
	<a class="logo-item" href="/justJoinIt">
		<span>justJoin.it</span>
	</a>
	<a class="logo-item" href="/noFluffJobs">
		<span>noFluffJobs</span>
	</a>
	<a class="logo-item" href="/solidJobs">
		<span>solidJobs</span>
	</a>
	<a class="logo-item" href="/bulldogJob">
		<span>bulldogJob</span>
	</a>
	<a class="logo-item" href="/inHireIo">
		<span>inHire.io</span>
	</a>
	<a class="logo-item" href="/pracujPl">
		<span>it.pracuj.pl</span>
	</a>
	<a class="logo-item" href="/indeedCom">
		<span>indeed.com</span>
	</a>
	<a class="logo-item" href="/theProtocol">
		<span>theProtocol.it</span>
	</a>
	<a class="logo-item" href="/startupJobs">
		<span>startupJobs.com</span>
	</a>
	<a class="logo-item" href="/itLeaders">
		<span>it-leaders.pl</span>
	</a>
</div>
<br>
<div id="chartPieContainer">
	<ChartPieWithTotalToday />
</div>

<style>
	div#chartPieContainer {
		max-width: 650px;
	}
</style>