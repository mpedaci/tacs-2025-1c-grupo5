export interface IMetricResponse {
    title: string;
    data: { [p: string]: string | number }[];
    serie: {
        dataKey: string;
        label: string;
    }[];
}