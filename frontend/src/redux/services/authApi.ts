import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";
import {config} from "@config/config";

export const authApi = createApi({
    reducerPath: "AuthApi",
    baseQuery: fetchBaseQuery({baseUrl: config.apiUrl}),
    endpoints: (builder) => ({
        login: builder.mutation<
            { token: string },
            { username: string, password: string }
        >({
            query: ({
                        username,
                        password
                    }) => ({
                url: `auth/login`,
                method: "POST",
                body: {
                    username,
                    password
                }
            }),
        }),

        logout: builder.mutation<void, { token: string }>({
            query: ({token}) => ({
                url: `auth/logout`,
                method: "POST",
                headers: {
                    token: token
                }
            }),
        }),
    })
});

export const {
    useLoginMutation,
    useLogoutMutation,
} = authApi;