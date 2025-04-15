"use client";
import Grid from "@mui/material/Grid2";
import {postsMockData} from "@components/Pages/posts/postsMockData";
import {useState} from "react";
import {PostCard} from "@components/Pages/posts/PostCard";
import {IPostResponse} from "@models/responses/iPostResponse";
import {useAppSelector} from "@redux/hook";
import {PostState} from "@models/enums/PostState";

export default function CommunityPostsTab() {
    const user = useAppSelector(state => state.user);
    const [posts, setPosts] = useState<IPostResponse[]>(postsMockData.filter(p => p.user.id !== user.id && p.state === PostState.Published));

    return (
        <Grid container spacing={3}>
            {
                posts.map((p, i) => (
                    <PostCard
                        id={p.id}
                        user={p.user}
                        card={p.card}
                        conservationState={p.conservationState}
                        images={p.images}
                        estimatedValue={p.estimatedValue}
                        wishedCards={p.wishedCards}
                        state={p.state}
                        publishedAt={p.publishedAt}
                        endAt={p.endAt}
                        key={i}
                    />
                ))
            }
        </Grid>
    )
}