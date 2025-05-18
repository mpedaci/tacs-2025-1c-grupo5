import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";
import {config} from "@config/config";
import {IMetricResponse} from "@models/responses/iMetricResponse";

export const metricsApi = createApi({
    reducerPath: "MetricsApi",
    baseQuery: fetchBaseQuery({baseUrl: config.apiUrl}),
    endpoints: (builder) => ({
        getPostsMetrics: builder.query<IMetricResponse[], void>({
            query: () => `metrics/posts`,
        }),
        getOffersMetrics: builder.query<IMetricResponse[], void>({
            query: () => `metrics/offers`,
        }),
    })
});

export const {
    useGetPostsMetricsQuery,
    useGetOffersMetricsQuery,
} = metricsApi;