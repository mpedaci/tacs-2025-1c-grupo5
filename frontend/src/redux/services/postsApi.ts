import { config } from "@/config/config";
import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { RootState } from "@/redux/store";
import { IPostResponse } from "@models/responses/iPostResponse";
import { IPostCreateRequest } from "@models/requests/posts/iPostCreateRequest";
import { IPostUpdateStateRequest } from "@models/requests/posts/iPostUpdateStateRequest";
import { ConservationState } from "@models/enums/ConservationState";

export const postsApi = createApi({
  reducerPath: "PostsApi",
  baseQuery: fetchBaseQuery({
    baseUrl: config.apiUrl,
    credentials: "include",
    prepareHeaders: (headers, { getState }) => {
      const token = (getState() as RootState).user.token;
      if (token) {
        console.log("Token", token);
        console.log("Headers", headers);
        headers.set("Authorization", `Bearer ${token}`);
      }
      return headers;
    },
  }),
  tagTypes: ["Posts"],
  endpoints: (builder) => ({
    getPosts: builder.query<
      IPostResponse[],
      {
        gameId?: string;
        name?: string;
        state?: ConservationState;
      }
    >({
      query: ({ gameId, name, state }) => ({
        url: `posts`,
        method: "GET",
        params: {
          gameId: gameId,
          name: name,
          state: state,
        },
      }),
      providesTags: ["Posts"],
    }),
    getPostById: builder.query<
      IPostResponse,
      {
        id: string;
      }
    >({
      query: ({ id }) => ({
        url: `posts/${id}`,
        method: "GET",
      }),
    }),
    createPost: builder.mutation<IPostResponse, IPostCreateRequest>({
      query: (body) => ({
        url: `posts`,
        method: "POST",
        body,
      }),
      invalidatesTags: ["Posts"],
    }),
    updatePost: builder.mutation<
      IPostResponse,
      {
        id: string;
        body: IPostCreateRequest;
      }
    >({
      query: ({ id, body }) => ({
        url: `posts/${id}`,
        method: "PUT",
        body,
      }),
      invalidatesTags: ["Posts"],
    }),
    updatePostState: builder.mutation<
      IPostResponse,
      {
        id: string;
        body: IPostUpdateStateRequest;
      }
    >({
      query: ({ id, body }) => ({
        url: `posts/${id}`,
        method: "PATCH",
        body,
      }),
      invalidatesTags: ["Posts"],
    }),
    deletePost: builder.mutation<void, { id: string }>({
      query: ({ id }) => ({
        url: `posts/${id}`,
        method: "DELETE",
      }),
      invalidatesTags: ["Posts"],
    }),
  }),
});

export const {
  useGetPostsQuery,
  useGetPostByIdQuery,
  useCreatePostMutation,
  useUpdatePostMutation,
  useUpdatePostStateMutation,
  useDeletePostMutation,
  useLazyGetPostByIdQuery,
  useLazyGetPostsQuery,
} = postsApi;
