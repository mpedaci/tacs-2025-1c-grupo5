import {config} from "@/config/config";
import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";
import {IUserResponse} from "@models/responses/iUserResponse";
import {IUserCreateRequest} from "@models/requests/users/iUserCreateRequest";

export const usersApi = createApi({
    reducerPath: "UsersApi",
    baseQuery: fetchBaseQuery({baseUrl: config.apiUrl}),
    tagTypes: ["Users"],
    endpoints: (builder) => ({
        createUser: builder.mutation<
            IUserResponse,
            IUserCreateRequest
        >({
            query: (body) => ({
                url: `users`,
                method: "POST",
                body,
            }),
        })
    }),
});

export const {useCreateUserMutation} = usersApi;