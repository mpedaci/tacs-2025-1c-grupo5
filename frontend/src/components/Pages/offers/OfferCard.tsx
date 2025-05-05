"use client";
import {IUserResponse} from "@models/responses/iUserResponse";
import {conservationStateLabels} from "@models/enums/ConservationState";
import Grid from "@mui/material/Grid2";
import MainCard from "@/components/Cards/MainCard";
import {Button, Chip, Divider, Typography} from "@mui/material";
import {IOfferedCardResponse} from "@models/responses/IOfferedCardResponse";
import {OfferState, offerStateLabels} from "@models/enums/OfferState";
import BaseCarousel from "@components/Carousel/BaseCarousel";
import React from "react";
import ImageCarousel from "@components/Carousel/ImageCarousel";
import {useUpdateOfferStateMutation} from "@redux/services/offersApi";
import {useNotification} from "@components/Notifications/NotificationContext";

export const OfferCard = ({
                              id,
                              offeror,
                              cards,
                              money,
                              state,
                              publishedAt,
                              postOwner,
                          }: {
    id: string;
    offeror: IUserResponse;
    cards: IOfferedCardResponse[];
    money: number;
    state: OfferState;
    publishedAt: string;
    postOwner: boolean;
}) => {
    const [
        updateOfferState,
        {isLoading: isUpdatingOfferState}
    ] = useUpdateOfferStateMutation();
    const {addNotification} = useNotification();

    const handleUpdateOfferState = async (newState: OfferState) => {
        try {
            await updateOfferState({
                offerId: id,
                body: {
                    state: newState
                }
            });
            addNotification("Estado de la oferta actualizado correctamente", "success");
        } catch (error) {
            addNotification("Error al actualizar el estado de la oferta", "error");
        }
    }

    return (
        <Grid size={{xs: 12, sm: 6, md: 4}} key={id}>
            <MainCard border={false} boxShadow sx={{height: '100%'}}>
                <Grid container spacing={1} textAlign={"center"} justifyContent="center">
                    <Grid size={12}>
                        <Typography variant="h5" component="h2">
                            {offeror.name}
                        </Typography>
                        <Typography variant="subtitle1" component="h3" color="text.secondary">
                            Publicado el: {publishedAt}
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
                                            <Grid size={12} key={"card-name"}>
                                                <Typography variant="h5" component="h2">
                                                    {offer.card.name}
                                                </Typography>
                                                <Typography variant="subtitle1" component="h3" color="text.secondary">
                                                    {offer.card.game.title}
                                                </Typography>
                                            </Grid>
                                            <Grid size={12} key={"card-state"}>
                                                <Chip label={conservationStateLabels[offer.conservationStatus]}
                                                      color="secondary"/>
                                            </Grid>
                                            <Grid size={12} key={"card-images"}>
                                                <ImageCarousel images={[offer.card.imageUrl, offer.image]}/>
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
                                <Chip label={offerStateLabels[state]} color={
                                    state === OfferState.Accepted ? "success" : "error"
                                } sx={{width: "100%"}}/>
                            ) : (
                                postOwner ? (
                                    <Grid container spacing={1} justifyContent="center">
                                        <Grid size={6}>
                                            <Button variant="contained" color="error" fullWidth
                                                    onClick={() => handleUpdateOfferState(OfferState.Rejected)}
                                                    disabled={isUpdatingOfferState}>
                                                Rechazar
                                            </Button>
                                        </Grid>
                                        <Grid size={6}>
                                            <Button variant="contained" color="primary" fullWidth
                                                    onClick={() => handleUpdateOfferState(OfferState.Accepted)}
                                                    disabled={isUpdatingOfferState}>
                                                Aceptar
                                            </Button>
                                        </Grid>
                                    </Grid>
                                ) : (
                                    <Chip label={offerStateLabels[state]} color="warning" sx={{width: "100%"}}/>
                                )
                            )
                        }
                    </Grid>
                </Grid>

            </MainCard>
        </Grid>
    );
}