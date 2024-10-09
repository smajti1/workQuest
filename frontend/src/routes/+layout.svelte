<script>
	import '../app.pcss';
	import { AppBar, AppShell, AppRail, AppRailAnchor } from '@skeletonlabs/skeleton';
	import { autoModeWatcher } from '@skeletonlabs/skeleton';
	import { LightSwitch } from '@skeletonlabs/skeleton';
	import { page } from '$app/stores';
	import { onMount } from 'svelte';
	import Icon from '$lib/Icon.svelte';

	let leftSidebarVisible = true;
	onMount(() => {
		const smallWindow = window.innerWidth > 600;
		leftSidebarVisible = smallWindow ?? true;
	});
</script>

<svelte:head>{@html '<script>(' + autoModeWatcher.toString() + ')();</script>'}</svelte:head>

<AppShell>
	<svelte:fragment slot="header">
		<AppBar gridColumns="grid-cols-3" slotDefault="place-self-center" slotTrail="place-content-end" padding="px-8 py-3">
			<svelte:fragment slot="lead">
				<button on:click={() => leftSidebarVisible = !leftSidebarVisible}>
					<Icon name="burger3" class="w-5 h-5 opacity-50 hover:opacity-100"/>
				</button>
			</svelte:fragment>
			WorkQuest.it offer report
			<svelte:fragment slot="trail">
				<LightSwitch />
			</svelte:fragment>
		</AppBar>
	</svelte:fragment>
	<svelte:fragment slot="sidebarLeft">
		{#if leftSidebarVisible}
			<AppRail aspectRatio="aspect-[10/9]">
				<AppRailAnchor href="/" selected={$page.url.pathname === '/'}>
					<Icon name="home" class="w-7 h-7 inline-block"/>
				</AppRailAnchor>
				<AppRailAnchor href="/justJoinIt" selected={$page.url.pathname === '/justJoinIt'}>justJoin.it</AppRailAnchor>
				<AppRailAnchor href="/noFluffJobs" selected={$page.url.pathname === '/noFluffJobs'}>noFluffJobs</AppRailAnchor>
				<AppRailAnchor href="/solidJobs" selected={$page.url.pathname === '/solidJobs'}>solid.jobs</AppRailAnchor>
				<AppRailAnchor href="/bulldogJob" selected={$page.url.pathname === '/bulldogJob'}>bulldogJob</AppRailAnchor>
				<AppRailAnchor href="/inHireIo" selected={$page.url.pathname === '/inHireIo'}>inHire.io</AppRailAnchor>
				<AppRailAnchor href="/pracujPl" selected={$page.url.pathname === '/pracujPl'}>it.pracuj.pl</AppRailAnchor>
				<AppRailAnchor href="/indeedCom" selected={$page.url.pathname === '/indeedCom'}>indeed.com</AppRailAnchor>
				<AppRailAnchor href="/theProtocol" selected={$page.url.pathname === '/theProtocol'}>theProtocol</AppRailAnchor>
				<AppRailAnchor href="/startupJobs" selected={$page.url.pathname === '/startupJobs'}>startupJobs</AppRailAnchor>
				<AppRailAnchor href="/itLeaders" selected={$page.url.pathname === '/itLeaders'}>it-leaders</AppRailAnchor>
				<svelte:fragment slot="trail">
					<AppRailAnchor href="https://github.com/smajti1/workQuest" target="_blank" title="Github smajti1/workQuest project">
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