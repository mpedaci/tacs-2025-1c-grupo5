import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { config } from "@config/config";
import { ICardResponse } from "@models/responses/iCardResponse";
import { RootState } from "@/redux/store";

export const cardsApi = createApi({
  reducerPath: "CardsApi",
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
  endpoints: (builder) => ({
    getCards: builder.query<
      ICardResponse[],
      {
        gameId: string;
        name?: string;
      }
    >({
      query: ({ gameId, name }) => ({
        url: `games/${gameId}/cards`,
        method: "GET",
        params: { gameId, name },
      }),
    }),
    getCard: builder.query<
      ICardResponse,
      {
        id: string;
      }
    >({
      query: ({ id }) => ({
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
