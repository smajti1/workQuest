import { PUBLIC_BASE_URL } from '$env/static/public';

export async function fetchTotalJobOffer() {
	let url = PUBLIC_BASE_URL + `/api/v1/job-offer-total`;
	try {
		const res = await fetch(url);
		if (res.ok) {
			return createChartDatasetForTotalJobOffer(await res.json());
		}
	} catch (e) {}
	throw new Error('Could not fetch job offers');
}

function createChartDatasetForTotalJobOffer(requestData) {
	let datasets = [];
	let data = [];
	let labels = [];
	for (const key in requestData) {
		datasets.push({
			type: 'line',
			label: key,
			data: requestData[key],
		});
		labels = merge(labels, Object.keys(requestData[key]));
	}
	labels.sort();

	return {
		data: {
			datasets: datasets,
			labels: labels,
		},
		pointRadius: 4,
	};
}

const merge = (a, b, predicate = (a, b) => a === b) => {
	const c = [...a];
	b.forEach((bItem) => (c.some((cItem) => predicate(bItem, cItem)) ? null : c.push(bItem)));
	return c;
};
