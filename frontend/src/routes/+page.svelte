<script>
	import ChartPieWithTotalToday from '$lib/ChartPie/ChartPieWithTotalToday.svelte';
	import { JobCategory } from '$lib/model/JobOfferCount';
	import ChartTotal from '$lib/ChartTotal/ChartTotal.svelte';
</script>

<h1>Select portal with offers</h1>

<div class="logo-cloud grid-cols-1 grid-cols-3 gap-1 md:!grid-cols-5">
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
	<a class="logo-item" href="/theProtocol">
		<span>theProtocol<span class="hidden sm:inline-block">.it</span></span>
	</a>
	<a class="logo-item" href="/startupJobs">
		<span>startupJobs<span class="hidden sm:inline-block">.com</span></span>
	</a>
	<a class="logo-item" href="/itLeaders">
		<span>it-leaders.pl</span>
	</a>
</div>
<br />
<div class="container mx-auto">
	<div>Total offer numbers chart:</div>
	<ChartTotal />
</div>
<div class="grid grid-cols-3 gap-5">
	<ChartPieWithTotalToday jobCategory={JobCategory.Total} />
	<ChartPieWithTotalToday jobCategory={JobCategory.Kotlin} />
	<ChartPieWithTotalToday jobCategory={JobCategory.Php} />
</div>
