import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { config } from "@config/config";
import { IGameResponse } from "@models/responses/iGameResponse";
import { RootState } from "@/redux/store";

export const gamesApi = createApi({
  reducerPath: "GamesApi",
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
    getGames: builder.query<IGameResponse[], void>({
      query: () => ({
        url: `games`,
        method: "GET",
      }),
    }),
  }),
});

export const { useGetGamesQuery, useLazyGetGamesQuery } = gamesApi;
