"use client";
import Grid from "@mui/material/Grid2";
import {OfferCard} from "@components/Pages/offers/OfferCard";
import {useGetOffersQuery} from "@redux/services/offersApi";

export default function OffersTab({postId, postOwner}: { postId: string, postOwner: boolean }) {
    const {data: offers} = useGetOffersQuery({postId});

    return (
        <Grid container spacing={3}>
            {
                (offers || []).map((p, i) => (
                    <OfferCard
                        id={p.id}
                        offeror={p.offerer}
                        cards={p.offeredCards}
                        money={p.money}
                        state={p.state}
                        publishedAt={p.publishedAt}
                        key={i}
                        postOwner={postOwner}
                    />
                ))
            }
        </Grid>
    )
}