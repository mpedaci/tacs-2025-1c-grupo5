import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";
import {config} from "@config/config";
import {IGameResponse} from "@models/responses/iGameResponse";

export const gamesApi = createApi({
    reducerPath: "GamesApi",
    baseQuery: fetchBaseQuery({baseUrl: config.apiUrl}),
    endpoints: (builder) => ({
        getGames: builder.query<IGameResponse[], void>({
            query: () => ({
                url: `games`,
                method: "GET",
            })
        }),
    }),
});

export const {
    useGetGamesQuery,
    useLazyGetGamesQuery,
} = gamesApi;