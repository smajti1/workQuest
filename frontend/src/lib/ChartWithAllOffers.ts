import { get } from 'svelte/store';
import type { Chart } from 'chart.js';

export async function fetchJobOffer(jobOfferVariant: JobOfferVariantType) {
	let url = `http://localhost:8080/api/v1/job-offer/${jobOfferVariant.jobPortal}?jobCategory=${jobOfferVariant.category}`;
	if (jobOfferVariant.city) {
		url += `&city=${jobOfferVariant.city}`;
	}
	try {
		const res = await fetch(url);
		if (res.ok) {
			return createChartDataset(await res.json(), jobOfferVariant);
		}
	} catch (e) {
	}
	throw new Error('Could not fetch job offers');
}

function createChartDataset(requestData, jobOfferVariant: JobOfferVariantType) {
	let data = [];
	for (const key in requestData) {
		data.push({x: key, y: requestData[key]});
	}
	return {
		label: mapPortalConstToString(jobOfferVariant.jobPortal) + ' ' + jobOfferVariant.category + (jobOfferVariant.city ? ` - ${jobOfferVariant.city}` : ''),
		data: data,
		pointRadius: 4,
		jobOfferVariantIndex: 0,
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
	}
	return 'Undefined';
}

type JobOfferVariantType = {jobPortal: string, category: string, city: string|null, selected: boolean};

function finJobOfferVariantType(jobOfferVariant: JobOfferVariantType) {
	return (obj) =>
		obj.jobPortal === jobOfferVariant.jobPortal
		&& obj.category === jobOfferVariant.category
		&& obj.city === jobOfferVariant.city;
}

export function addOrRemoveDataset(jobOfferVariantList, chart: Chart, jobOfferVariant: JobOfferVariantType) {
	const jobOfferVariantIndex = get(jobOfferVariantList).findIndex(finJobOfferVariantType(jobOfferVariant));
	if (jobOfferVariantIndex < 0) {
		throw new Error('Could not find job offer variant!');
	}
	get(jobOfferVariantList)[jobOfferVariantIndex].selected = !get(jobOfferVariantList)[jobOfferVariantIndex].selected;
	jobOfferVariantList.set(get(jobOfferVariantList));

	if (get(jobOfferVariantList)[jobOfferVariantIndex].selected) {
		fetchJobOffer(jobOfferVariant).then(result => {
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