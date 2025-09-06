<script>
	import ChartPieWithTotalToday from '$lib/ChartPie/ChartPieWithTotalToday.svelte';
	import { JobCategory } from '$lib/model/JobOfferCount';
	import ChartTotal from '$lib/ChartTotal/ChartTotal.svelte';
</script>

<h1>Select portal with offers</h1>

<div class="flex justify-center">
	<nav class="btn-group preset-outlined-surface-200-800 flex-col p-2 md:flex-row">
		<button type="button" class="btn preset-outlined-surface-500">
			<a href="/justJoinIt">justJoin.it</a>
		</button>
		<button type="button" class="btn preset-outlined-surface-500">
			<a class="logo-item" href="/noFluffJobs">noFluffJobs</a>
		</button>
		<button type="button" class="btn preset-outlined-surface-500">
			<a class="logo-item" href="/solidJobs">solidJobs</a>
		</button>
		<button type="button" class="btn preset-outlined-surface-500">
			<a class="logo-item" href="/bulldogJob">bulldogJob</a>
		</button>
		<button type="button" class="btn preset-outlined-surface-500">
			<a class="logo-item" href="/inHireIo">inHire.io</a>
		</button>
		<button type="button" class="btn preset-outlined-surface-500">
			<a class="logo-item" href="/pracujPl">it.pracuj.pl</a>
		</button>
		<button type="button" class="btn preset-outlined-surface-500">
		<a class="logo-item" href="/theProtocol">theProtocol<span class="hidden sm:inline-block">.it</span></a>
		</button>
		<button type="button" class="btn preset-outlined-surface-500">
		<a class="logo-item" href="/startupJobs">startupJobs<span class="hidden sm:inline-block">.com</span></a>
		</button>
		<button type="button" class="btn preset-outlined-surface-500">
			<a class="logo-item" href="/itLeaders">it-leaders.pl</a>
		</button>
	</nav>
</div>
<br />
<div>
	<div>Total offer numbers chart:</div>
	<ChartTotal />
</div>
<br />
<div class="grid grid-cols-3 gap-5">
	<ChartPieWithTotalToday jobCategory={JobCategory.Total} />
	<ChartPieWithTotalToday jobCategory={JobCategory.Kotlin} />
	<ChartPieWithTotalToday jobCategory={JobCategory.Php} />
</div>
