import {config} from "@/config/config";
import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";
import {IOfferCreateRequest} from "@models/requests/offers/iOfferCreateRequest";
import {IOfferResponse} from "@models/responses/iOfferResponse";
import {IOfferUpdateStateRequest} from "@models/requests/offers/iOfferUpdateStateRequest";

export const offersApi = createApi({
    reducerPath: "OffersApi",
    baseQuery: fetchBaseQuery({baseUrl: config.apiUrl}),
    tagTypes: ["Offers"],
    endpoints: (builder) => ({
        getOffers: builder.query<IOfferResponse[], { postId: string }>({
            query: ({postId}) => ({
                url: `posts/${postId}/offers`,
                method: "GET",
            }),
            providesTags: ["Offers"],
        }),
        getOfferById: builder.query<IOfferResponse, { offerId: string }>({
            query: ({offerId}) => ({
                url: `/offers/${offerId}`,
                method: "GET",
            }),
        }),
        createOffer: builder.mutation<IOfferResponse, {
            body: IOfferCreateRequest;
        }>({
            query: ({body}) => ({
                url: `/offers`,
                method: "POST",
                body,
            }),
            invalidatesTags: ["Offers"],
        }),
        updateOfferState: builder.mutation<any, { postId: string; offerId: string; body: IOfferUpdateStateRequest }>({
            query: ({postId, offerId, body}) => ({
                url: `/offers/${offerId}`,
                method: "PATCH",
                body,
            }),
            invalidatesTags: ["Offers"],
        }),
    })
});

export const {
    useGetOffersQuery,
    useGetOfferByIdQuery,
    useCreateOfferMutation,
    useUpdateOfferStateMutation,
    useLazyGetOffersQuery,
    useLazyGetOfferByIdQuery,
} = offersApi;