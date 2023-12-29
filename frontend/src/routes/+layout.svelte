<script>
	import '../app.pcss';
	import { AppBar, AppShell, AppRail, AppRailAnchor } from '@skeletonlabs/skeleton';
	import { autoModeWatcher } from '@skeletonlabs/skeleton';
	import { LightSwitch } from '@skeletonlabs/skeleton';
	import { page } from '$app/stores';
	import { onMount } from 'svelte';

	let leftSidebarVisible = true;
	onMount(() => {
		const smallWindow = window.innerWidth > 600;
		leftSidebarVisible = smallWindow ?? true;
	});
</script>

<svelte:head>{@html `<script>${autoModeWatcher.toString()} autoModeWatcher();</script>`}</svelte:head>

<AppShell>
	<svelte:fragment slot="header">
		<AppBar gridColumns="grid-cols-3" slotDefault="place-self-center" slotTrail="place-content-end" padding="px-8 py-3">
			<svelte:fragment slot="lead">
				<button on:click={() => leftSidebarVisible = !leftSidebarVisible}>
					<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="w-5 h-5 opacity-50 hover:opacity-100">
						<path fill-rule="evenodd"
									d="M2 4.75A.75.75 0 0 1 2.75 4h14.5a.75.75 0 0 1 0 1.5H2.75A.75.75 0 0 1 2 4.75ZM2 10a.75.75 0 0 1 .75-.75h14.5a.75.75 0 0 1 0 1.5H2.75A.75.75 0 0 1 2 10Zm0 5.25a.75.75 0 0 1 .75-.75h14.5a.75.75 0 0 1 0 1.5H2.75a.75.75 0 0 1-.75-.75Z"
									clip-rule="evenodd" />
					</svg>
				</button>
			</svelte:fragment>
			Work in.it
			<svelte:fragment slot="trail">
				<LightSwitch />
			</svelte:fragment>
		</AppBar>
	</svelte:fragment>
	<svelte:fragment slot="sidebarLeft">
		{#if leftSidebarVisible}
			<AppRail>
				<AppRailAnchor href="/" selected={$page.url.pathname === '/'}>home</AppRailAnchor>
				<AppRailAnchor href="/justJoinIt" selected={$page.url.pathname === '/justJoinIt'}>justJoinIt</AppRailAnchor>
				<AppRailAnchor href="/noFluffJobs" selected={$page.url.pathname === '/noFluffJobs'}>noFluffJobs</AppRailAnchor>
				<svelte:fragment slot="trail">
					<AppRailAnchor href="https://github.com/smajti1/workInIt" target="_blank" title="Github smajti1/workInIt project">
						<svelte:fragment slot="lead">
							<img src="./icon/icons8-github-48.png" alt="Github Icon" width="38px" />
						</svelte:fragment>
					</AppRailAnchor>
				</svelte:fragment>
			</AppRail>
		{/if}
	</svelte:fragment>
	<div class="px-8">
		<slot/>
	</div>
</AppShell>