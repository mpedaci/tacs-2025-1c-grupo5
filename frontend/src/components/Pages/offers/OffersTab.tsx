"use client";
import {useState} from "react";
import {offersMockData} from "@components/Pages/offers/offersMockData";
import Grid from "@mui/material/Grid2";
import {OfferCard} from "@components/Pages/offers/OfferCard";

export default function OffersTab() {
    const [offers, setOffers] = useState(offersMockData);

    return (
        <Grid container spacing={3}>
            {
                offers.map((p, i) => (
                    <OfferCard
                        id={p.id}
                        offeror={p.offeror}
                        cards={p.cards}
                        money={p.money}
                        state={p.state}
                        publishedAt={p.publishedAt}
                        key={i}
                    />
                ))
            }
        </Grid>
    )
}