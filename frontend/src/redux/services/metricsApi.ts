import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { config } from "@config/config";
import { IMetricResponse } from "@models/responses/iMetricResponse";
import { RootState } from "@/redux/store";

export const metricsApi = createApi({
  reducerPath: "MetricsApi",
  baseQuery: fetchBaseQuery({
    baseUrl: config.apiUrl,
    prepareHeaders: (headers, { getState }) => {
      const token = (getState() as RootState).user.token;
      if (token) {
        headers.set("Authorization", `Bearer ${token}`);
      }
      return headers;
    },
  }),
  endpoints: (builder) => ({
    getPostsMetrics: builder.query<IMetricResponse[], void>({
      query: () => `metrics/posts`,
    }),
    getOffersMetrics: builder.query<IMetricResponse[], void>({
      query: () => `metrics/offers`,
    }),
  }),
});

export const { useGetPostsMetricsQuery, useGetOffersMetricsQuery } = metricsApi;
