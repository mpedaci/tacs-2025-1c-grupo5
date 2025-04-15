"use client";
import Grid from "@mui/material/Grid2";
import {PostCard} from "@components/Pages/posts/PostCard";
import {useState} from "react";
import {IPostResponse} from "@models/responses/iPostResponse";
import {postsMockData} from "@components/Pages/posts/postsMockData";
import {useAppSelector} from "@redux/hook";

export default function OwnPostsTab() {
    const user = useAppSelector(state => state.user);
    const [posts, setPosts] = useState<IPostResponse[]>(postsMockData.filter(p => p.user.id === user.id));

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
                        showState={true}
                    />
                ))
            }
        </Grid>
    )
}