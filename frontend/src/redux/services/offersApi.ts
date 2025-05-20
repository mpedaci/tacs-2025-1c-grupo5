import { config } from "@/config/config";
import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { RootState } from "@/redux/store";
import { IOfferCreateRequest } from "@models/requests/offers/iOfferCreateRequest";
import { IOfferResponse } from "@models/responses/iOfferResponse";
import { IOfferUpdateStateRequest } from "@models/requests/offers/iOfferUpdateStateRequest";

export const offersApi = createApi({
  reducerPath: "OffersApi",
  baseQuery: fetchBaseQuery({
    baseUrl: config.apiUrl,
    credentials: "include",
    prepareHeaders: (headers, { getState }) => {
      const token = (getState() as RootState).user.token;
      if (token) {
        headers.set("Authorization", `Bearer ${token}`);
      }
      return headers;
    },
  }),
  tagTypes: ["Offers"],
  endpoints: (builder) => ({
    getOffers: builder.query<IOfferResponse[], { postId: string }>({
      query: ({ postId }) => ({
        url: `posts/${postId}/offers`,
        method: "GET",
      }),
      providesTags: ["Offers"],
    }),
    getOfferById: builder.query<IOfferResponse, { offerId: string }>({
      query: ({ offerId }) => ({
        url: `/offers/${offerId}`,
        method: "GET",
      }),
    }),
    createOffer: builder.mutation<
      IOfferResponse,
      {
        body: IOfferCreateRequest;
      }
    >({
      query: ({ body }) => ({
        url: `/offers`,
        method: "POST",
        body,
      }),
      invalidatesTags: ["Offers"],
    }),
    updateOfferState: builder.mutation<
      void,
      { offerId: string; body: IOfferUpdateStateRequest }
    >({
      query: ({ offerId, body }) => ({
        url: `/offers/${offerId}`,
        method: "PATCH",
        body: {
          status: body.state,
        },
      }),
      invalidatesTags: ["Offers"],
    }),
  }),
});

export const {
  useGetOffersQuery,
  useGetOfferByIdQuery,
  useCreateOfferMutation,
  useUpdateOfferStateMutation,
  useLazyGetOffersQuery,
  useLazyGetOfferByIdQuery,
} = offersApi;
