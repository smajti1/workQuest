import { writable, get } from 'svelte/store';
import type { Chart } from 'chart.js';

const JUST_JOIN_IT = 'JUST_JOIN_IT';

export async function fetchJobOffer(jobCategory = 'Total', city = null) {
	let url = `http://localhost:8080/api/v1/job-offer/${JUST_JOIN_IT}?jobCategory=${jobCategory}`;
	if (city) {
		url += `&city=${city}`;
	}
	try {
		const res = await fetch(url);
		if (res.ok) {
			return createChartDataset(await res.json(), jobCategory, city);
		}
	} catch (e) {
	}
	throw new Error('Could not fetch job offers');
}

function createChartDataset(requestData, jobCategory: string, city: string|null) {
	let data = [];
	for (const key in requestData) {
		data.push({x: key, y: requestData[key]});
	}
	return {
		label: mapPortalConstToString(JUST_JOIN_IT) + ' ' + jobCategory + (city ? ` - ${city}` : ''),
		data: data,
	};
}

export function mapPortalConstToString(portalConst) {
	switch (portalConst) {
		case JUST_JOIN_IT:
			return 'JustJoin.it';
	}
	return 'Undefined';
}

type JobOfferVariantType = {jobPortal: string, category: string, city: string|null, selected: boolean};
export let jobOfferVariantList = writable([
	{jobPortal: JUST_JOIN_IT, category: 'Total', city: null, selected: true},
	{jobPortal: JUST_JOIN_IT, category: 'Total', city: 'Warszawa', selected: false},
	{jobPortal: JUST_JOIN_IT, category: 'Kotlin', city: null, selected: false},
	{jobPortal: JUST_JOIN_IT, category: 'Kotlin', city: 'Warszawa', selected: false},
	{jobPortal: JUST_JOIN_IT, category: 'Php', city: null, selected: false},
	{jobPortal: JUST_JOIN_IT, category: 'Php', city: 'Warszawa', selected: false},
]);

function finJobOfferVariantType(jobOfferVariant: JobOfferVariantType) {
	return (obj) =>
		obj.jobPortal === jobOfferVariant.jobPortal
		&& obj.category === jobOfferVariant.category
		&& obj.city === jobOfferVariant.city;
}

export function addOrRemoveDataset(chart: Chart, jobOfferVariant: JobOfferVariantType) {
	const jobOfferVariantIndex = get(jobOfferVariantList).findIndex(finJobOfferVariantType(jobOfferVariant));
	if (jobOfferVariantIndex < 0) {
		throw new Error('Could not find job offer variant!');
	}
	get(jobOfferVariantList)[jobOfferVariantIndex].selected = !get(jobOfferVariantList)[jobOfferVariantIndex].selected;
	jobOfferVariantList.set(get(jobOfferVariantList));

	if (get(jobOfferVariantList)[jobOfferVariantIndex].selected) {
		fetchJobOffer(jobOfferVariant.category, jobOfferVariant.city).then(result => {
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