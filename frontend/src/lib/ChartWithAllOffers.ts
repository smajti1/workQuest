const JUST_JOIN_IT = 'JUST_JOIN_IT';

export async function fetchAllJobOffer(jobCategory = 'Total') {
	const url = `http://localhost:8080/api/v1/job-offer/${JUST_JOIN_IT}?jobCategory=${jobCategory}`;
	const res = await fetch(url);

	if (res.ok) {
		return mapDataToChartJsFormat(await res.json());
	} else {
		throw new Error('Could not fetch job offers');
	}
}

function mapDataToChartJsFormat(requestData) {
	let data = [];
	for (const key in requestData) {
		data.push({ x: key, y: requestData[key] })
	}
	return {
		datasets: [{
			label: mapPortalConstToString(JUST_JOIN_IT),
			data: data,
		}],
	};
}

function mapPortalConstToString(portalConst) {
	switch(portalConst) {
		case JUST_JOIN_IT:
			return 'JustJoin.it';
	}
	return 'Undefined';
}