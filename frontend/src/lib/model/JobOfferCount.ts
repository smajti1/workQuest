export type JobOfferCount = {
	id: string,
	jobPortal: string,
	category: JobCategory,
	city: string|null,
	count: number,
};

export enum JobCategory {
	Total = 'Total',
	Kotlin = 'Kotlin',
	Php = 'Php',
}