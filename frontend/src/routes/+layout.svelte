<script lang="ts">
	import '../app.css';
	import { AppBar, Navigation } from '@skeletonlabs/skeleton-svelte';
	import { page } from '$app/state';
	import { onMount } from 'svelte';
	import Icon from '$lib/Icon.svelte';
	import LightSwitch  from '$lib/LightSwitch/LightSwitch.svelte';

	interface Props {
		children?: import('svelte').Snippet;
	}

	let { children }: Props = $props();

	let leftSidebarVisible = $state(false);

	onMount(() => {
		let localVisible = localStorage.getItem('leftSidebarVisible');
		leftSidebarVisible = localVisible !== null ? localVisible === 'true' : true;
		const smallWindow = window.innerWidth > 600;
		if (smallWindow && localVisible === null) {
			leftSidebarVisible = false;
		}
	});

	const onChangeLeftSidebar = (): void => {
		leftSidebarVisible = !leftSidebarVisible;
		localStorage.setItem('leftSidebarVisible', String(leftSidebarVisible));
	}
</script>

<div class="grid h-screen grid-rows-[auto_1fr_auto]">
	<header>
		<AppBar gridColumns="grid-cols-3" slotDefault="place-self-center" slotTrail="place-content-end" padding="px-8 py-3">
			{#snippet lead()}
				<button onclick={onChangeLeftSidebar}>
					<Icon name="burger3" class="w-5 h-5 opacity-50 hover:opacity-100" />
				</button>
			{/snippet}
			WorkQuest.it offer report
			{#snippet trail()}
				<LightSwitch />
				<a href="https://github.com/smajti1/workQuest" target="_blank" title="Github smajti1/workQuest project">
					<img src="./icon/icons8-github-48.png" alt="Github Icon" class="h-6"/>
				</a>
			{/snippet}
		</AppBar>
	</header>

	<div class="grid grid-cols-1 md:grid-cols-[auto_1fr]">
			{#if leftSidebarVisible}
				<Navigation.Rail width="w-22" >
					{#snippet header()}
						<Navigation.Tile href="/" selected={page.url.pathname === '/'}>
							<Icon name="home" class="w-6 h-6 inline-block" />
						</Navigation.Tile>
						<Navigation.Tile href="/justJoinIt" selected={page.url.pathname === '/justJoinIt'} label="justJoin.it"/>
						<Navigation.Tile href="/noFluffJobs" selected={page.url.pathname === '/noFluffJobs'} label="noFluffJobs"/>
						<Navigation.Tile href="/solidJobs" selected={page.url.pathname === '/solidJobs'} label="solid.jobs"/>
						<Navigation.Tile href="/bulldogJob" selected={page.url.pathname === '/bulldogJob'} label="bulldogJob"/>
						<Navigation.Tile href="/inHireIo" selected={page.url.pathname === '/inHireIo'} label="inHire.io"/>
						<Navigation.Tile href="/pracujPl" selected={page.url.pathname === '/pracujPl'} label="it.pracuj.pl"/>
						<Navigation.Tile href="/theProtocol" selected={page.url.pathname === '/theProtocol'} label="theProtocol"/>
						<Navigation.Tile href="/startupJobs" selected={page.url.pathname === '/startupJobs'} label="startupJobs"/>
						<Navigation.Tile href="/itLeaders" selected={page.url.pathname === '/itLeaders'} label="it-leaders"/>
					{/snippet}
				</Navigation.Rail>
			{:else}
				<aside></aside>
			{/if}

		<main class="container mx-auto">
				{@render children?.()}
		</main>
	</div>
</div>