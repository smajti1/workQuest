import { get } from 'svelte/store';
import type { Chart } from 'chart.js';
import { PUBLIC_BASE_URL } from '$env/static/public';

export async function fetchJobOffer(jobPortal: string, jobOfferVariant: JobOfferVariantType, jobOfferVariantList) {
	let url = PUBLIC_BASE_URL + `/api/v1/job-offer/${jobPortal}?jobCategory=${jobOfferVariant.category}`;
	if (jobOfferVariant.city) {
		url += `&city=${jobOfferVariant.city}`;
	}
	try {
		const res = await fetch(url);
		if (res.ok) {
			return createChartDataset(await res.json(), jobPortal, jobOfferVariant, jobOfferVariantList);
		}
	} catch (e) {
	}
	throw new Error('Could not fetch job offers');
}

function createChartDataset(requestData, jobPortal: string, jobOfferVariant: JobOfferVariantType, jobOfferVariantList) {
	let data = [];
	for (const key in requestData) {
		data.push({x: key, y: requestData[key]});
	}
	return {
		label: mapPortalConstToString(jobPortal) + ' ' + jobOfferVariant.category + (jobOfferVariant.city ? ` - ${jobOfferVariant.city}` : ''),
		data: data,
		pointRadius: 4,
		jobOfferVariantIndex: getJobOfferVariantIndex(jobOfferVariantList, jobOfferVariant),
		backgroundColor: jobOfferVariant.lineColor.replace('rgb(', 'rgba(').replace(')', ', 0.5)'),
		borderColor: jobOfferVariant.lineColor,
	};
}

export function mapPortalConstToString(portalConst: string) {
	switch (portalConst) {
		case 'JUST_JOIN_IT':
			return 'justJoin.it';
		case 'NO_FLUFF_JOBS_COM':
			return 'noFluffJobs.com';
		case 'SOLID_JOBS':
			return 'solid.jobs';
		case 'BULLDOG_JOB':
			return 'bulldogJob.pl';
		case 'IN_HIRE_IO':
			return 'inHire.io';
		case 'PRACUJ_PL':
			return 'it.pracuj.pl';
		case 'INDEED_COM':
			return 'indeed.com';
		case 'THE_PROTOCOL':
			return 'theProtocol.it';
		case 'STARTUP_JOBS':
			return 'startupJobs.com';
		case 'IT_LEADERS':
			return 'it-leaders.pl';
	}
	return 'Undefined';
}

type JobOfferVariantType = {category: string, city: string|null, selected: boolean, lineColor: string};

function getJobOfferVariantIndex(jobOfferVariantList, jobOfferVariant: JobOfferVariantType) {
	return get(jobOfferVariantList).findIndex((obj) => obj.category === jobOfferVariant.category && obj.city === jobOfferVariant.city)
}

export function addOrRemoveDataset(jobPortal: string, jobOfferVariantList, chart: Chart, jobOfferVariant: JobOfferVariantType) {
	const jobOfferVariantIndex = getJobOfferVariantIndex(jobOfferVariantList, jobOfferVariant);
	if (jobOfferVariantIndex < 0) {
		throw new Error('Could not find job offer variant!');
	}
	let isLastElementToExclude = get(jobOfferVariantList).filter((obj) => obj.selected).length === 1
		&& get(jobOfferVariantList)[jobOfferVariantIndex].selected;
	if (isLastElementToExclude) {
		return;
	}
	get(jobOfferVariantList)[jobOfferVariantIndex].selected = !get(jobOfferVariantList)[jobOfferVariantIndex].selected;
	jobOfferVariantList.set(get(jobOfferVariantList));

	if (get(jobOfferVariantList)[jobOfferVariantIndex].selected) {
		fetchJobOffer(jobPortal, jobOfferVariant, jobOfferVariantList).then(result => {
			result.jobOfferVariantIndex = jobOfferVariantIndex
			chart.data.datasets.push(result);
			chart.update();
		});
	} else {
		const datasetIndex = chart.data.datasets.findIndex(
			(obj) => obj.jobOfferVariantIndex === jobOfferVariantIndex
		);
		chart.data.datasets.splice(datasetIndex, 1);
		chart.update();
	}
}

export function fetchRestSelectedJobOffer(jobPortal: string, chart: Chart, jobOfferVariantList, jobOfferSelectedList: JobOfferVariantType[]) {
	for (const index in jobOfferSelectedList) {
		if (index == 0) {
			continue;
		}
		const jobOfferVariant = jobOfferSelectedList[index];
		const jobOfferVariantIndex = getJobOfferVariantIndex(jobOfferVariantList, jobOfferVariant);
		fetchJobOffer(jobPortal, jobOfferVariant, jobOfferVariantList).then(result => {
			result.jobOfferVariantIndex = jobOfferVariantIndex
			chart.data.datasets.push(result);
			chart.update();
		});
	}
}