"use client";
import Grid from "@mui/material/Grid2";
import {PostCard} from "@components/Pages/posts/PostCard";
import {useAppSelector} from "@redux/hook";
import {useGetPostsQuery} from "@redux/services/postsApi";

export default function CommunityPostsTab() {
    const user = useAppSelector(state => state.user);
    const {data: postsData} = useGetPostsQuery();

    return (
        <Grid container spacing={3}>
            {
                (postsData || [])
                    .filter((p) => p.user.id !== user.id)
                    .map((p, i) => (
                    <PostCard
                        id={p.id}
                        user={p.user}
                        card={p.card}
                        conservationState={p.conservationStatus}
                        images={p.images}
                        estimatedValue={p.estimatedValue}
                        wishedCards={p.wantedCards}
                        state={p.status}
                        publishedAt={p.publishedAt}
                        endAt={p.finishedAt}
                        key={i}
                    />
                ))
            }
        </Grid>
    )
}