import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";
import {config} from "@config/config";
import {ICardResponse} from "@models/responses/iCardResponse";

export const cardsApi = createApi({
    reducerPath: "CardsApi",
    baseQuery: fetchBaseQuery({baseUrl: config.apiUrl}),
    tagTypes: ["Cards"],
    endpoints: (builder) => ({
        getCards: builder.query<ICardResponse[], {
            gameId?: string;
            name?: string;
        }>({
            query: ({gameId, name}) => ({
                url: `cards`,
                method: "GET",
                params: {gameId, name}
            }),
            providesTags: ["Cards"],
        }),
        getCard: builder.query<ICardResponse, {
            id: string;
        }>({
            query: ({id}) => ({
                url: `cards/${id}`,
                method: "GET",
            }),
        }),
    }),
});

export const {
    useGetCardsQuery,
    useGetCardQuery,
    useLazyGetCardsQuery,
    useLazyGetCardQuery,
} = cardsApi;