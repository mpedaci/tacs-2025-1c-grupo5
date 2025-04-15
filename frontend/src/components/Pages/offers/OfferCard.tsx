"use client";
import {IUserResponse} from "@models/responses/iUserResponse";
import {conservationStateLabels} from "@models/enums/ConservationState";
import {postStateLabels} from "@models/enums/PostState";
import Grid from "@mui/material/Grid2";
import MainCard from "@/components/Cards/MainCard";
import {Button, Chip, Divider, Typography} from "@mui/material";
import {IOfferedCardResponse} from "@models/responses/IOfferedCardResponse";
import {OfferState} from "@models/enums/OfferState";
import BaseCarousel from "@components/Carousel/BaseCarousel";
import React from "react";

export const OfferCard = ({
                              id,
                              offeror,
                              cards,
                              money,
                              state,
                              publishedAt
                          }: {
    id: string;
    offeror: IUserResponse;
    cards: IOfferedCardResponse[];
    money: number;
    state: OfferState;
    publishedAt: Date;
}) => {
    return (
        <Grid size={{xs: 12, sm: 6, md: 4}} key={id}>
            <MainCard border={false} boxShadow sx={{height: '100%'}}>
                <Grid container spacing={1} textAlign={"center"} justifyContent="center">
                    <Grid size={12}>
                        <Typography variant="h5" component="h2">
                            {offeror.name}
                        </Typography>
                        <Typography variant="subtitle1" component="h3" color="text.secondary">
                            Publicado el: {publishedAt.toLocaleDateString()}
                        </Typography>
                    </Grid>
                    <Grid size={12}>
                        <Chip label={`Dinero: $${money}`} color="secondary"/>
                    </Grid>
                </Grid>
                <Divider/>
                <Grid container spacing={1} textAlign={"center"} justifyContent="center" sx={{padding: '10px 0'}}>
                    <Grid size={12}>
                        <Typography variant="h5" component="h2">
                            Cartas ofrecidas
                        </Typography>
                    </Grid>
                    <Grid size={12}>
                        <BaseCarousel
                            items={
                                cards.map((offer: IOfferedCardResponse, i) => (
                                        <Grid container justifyContent="center" key={offer.id}>
                                            <Grid size={12}>
                                                <Typography variant="h5" component="h2">
                                                    {offer.card.name}
                                                </Typography>
                                                <Typography variant="subtitle1" component="h3" color="text.secondary">
                                                    {offer.card.game.name}
                                                </Typography>
                                            </Grid>
                                            <Grid size={12}>
                                                <Chip label={conservationStateLabels[offer.state]} color="secondary"/>
                                            </Grid>
                                            <Grid size={12}>
                                                <img
                                                    src={offer.card.image}
                                                    style={{
                                                        width: '100%',
                                                        height: '400px',
                                                        objectFit: 'cover',
                                                        borderRadius: '10px'
                                                    }}
                                                />
                                            </Grid>
                                        </Grid>
                                    )
                                )
                            }
                        />
                    </Grid>
                </Grid>
                <Divider/>
                <Grid container justifyContent="center" sx={{padding: '10px 0'}}>
                    <Grid size={12}>
                        {
                            (state === OfferState.Rejected || state === OfferState.Accepted) ? (
                                <Chip label={postStateLabels[state]} color={
                                    state === OfferState.Accepted ? "success" : "error"
                                } sx={{width: "100%"}}/>
                            ) : (
                                <Grid container spacing={1} justifyContent="center">
                                    <Grid size={6}>
                                        <Button variant="contained" color="error" fullWidth onClick={() => {}}>
                                            Rechazar
                                        </Button>
                                    </Grid>
                                    <Grid size={6}>
                                        <Button variant="contained" color="primary" fullWidth onClick={() => {}}>
                                            Aceptar
                                        </Button>
                                    </Grid>
                                </Grid>
                            )
                        }
                    </Grid>
                </Grid>

            </MainCard>
        </Grid>
    );
}