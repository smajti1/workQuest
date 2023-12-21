<script>
	import '../app.pcss';
	import { AppBar, AppShell } from '@skeletonlabs/skeleton';
	import { autoModeWatcher } from '@skeletonlabs/skeleton';
	import { LightSwitch } from '@skeletonlabs/skeleton';
</script>

<svelte:head>{@html `<script>${autoModeWatcher.toString()} autoModeWatcher();</script>`}</svelte:head>

<AppShell>
	<svelte:fragment slot="pageHeader">
		<AppBar gridColumns="grid-cols-3" slotDefault="place-self-center" slotTrail="place-content-end">
			<svelte:fragment slot="lead">(icon)</svelte:fragment>
			Work in.it
			<svelte:fragment slot="trail">
				<LightSwitch />
				<div id="github-icon">
					<a href="https://github.com/smajti1/workInIt"><img src="./icon/icons8-github-48.png" alt="Github Icon" width="38px" /></a>
				</div>
				(actions)
			</svelte:fragment>
		</AppBar>
	</svelte:fragment>
	<div class="px-8">
		<slot/>
	</div>
</AppShell>

<style>
#github-icon {
    margin-right: 10px;
}
</style>