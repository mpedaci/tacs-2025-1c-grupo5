"use client";
import {IUserResponse} from "@models/responses/iUserResponse";
import {ConservationState, conservationStateLabels} from "@models/enums/ConservationState";
import {ICardResponse} from "@models/responses/iCardResponse";
import {PostState, postStateLabels} from "@models/enums/PostState";
import Grid from "@mui/material/Grid2";
import MainCard from "@/components/Cards/MainCard";
import {Button, Chip, Divider, Typography, Tooltip} from "@mui/material";
import ImageCarousel from "@components/Carousel/ImageCarousel";
import NextLink from "next/link";
import {postOffersRoute} from "@routes/router";

export const PostCard = ({
                             id,
                             user,
                             card,
                             conservationState,
                             images,
                             estimatedValue,
                             wishedCards,
                             state,
                             publishedAt,
                             endAt,
                             showState = false,
                         }: {
    id: string;
    user: IUserResponse;
    card: ICardResponse;
    conservationState: ConservationState;
    images: string[];
    estimatedValue: number;
    wishedCards: ICardResponse[];
    state: PostState;
    publishedAt: Date;
    endAt: Date;
    showState?: boolean;
}) => {
    return (
        <Grid size={{xs: 12, sm: 6, md: 4}} key={id}>
            <MainCard border={false} boxShadow sx={{height: '100%'}}>
                <Grid container spacing={1} textAlign={"center"} justifyContent="center">
                    <Grid size={12}>
                        <Typography variant="h5" component="h2">
                            {card.name}
                        </Typography>
                        <Typography variant="subtitle1" component="h3" color="text.secondary">
                            {card.game.name}
                        </Typography>
                        <Typography variant="subtitle1" component="h3" color="text.secondary">
                            Publicado el: {publishedAt.toLocaleDateString()}
                        </Typography>
                        {
                            showState && state === PostState.Finished && (
                                <Typography variant="subtitle1" component="h3" color="text.secondary">
                                    Finalizado el: {endAt.toLocaleDateString()}
                                </Typography>
                            )
                        }
                    </Grid>
                    <Grid size={12}>
                        <ImageCarousel images={[...images, card.image]}/>
                    </Grid>
                    <Grid size={6}>
                        <Chip label={conservationStateLabels[conservationState]} color="secondary"/>
                    </Grid>
                    <Grid size={6}>
                        <Chip label={`Valor: $${estimatedValue}`} color="secondary"/>
                    </Grid>
                    {
                        showState && (
                            <Grid size={12}>
                                <Chip label={postStateLabels[state]} color={
                                    state === PostState.Published ? "primary" : state === PostState.Finished ? "success" : "error"
                                } sx={{width: "100%"}}/>
                            </Grid>
                        )
                    }
                    {
                        wishedCards.length > 0 && (
                            <Grid size={12}>
                                <Tooltip title={
                                    wishedCards.map(card => (card.name)).join(", ")
                                } arrow placement="top">
                                    <Chip label={"Cartas deseadas"} color="secondary" sx={{width: "100%"}}/>
                                </Tooltip>
                            </Grid>
                        )
                    }
                </Grid>
                <Divider/>
                <Grid container justifyContent="center" sx={{padding: '10px 0'}}>
                    <Grid size={12}>
                        <Button variant="contained" color="primary" fullWidth component={NextLink}
                                href={postOffersRoute(id)}>
                            Ofertas
                        </Button>
                    </Grid>
                </Grid>
            </MainCard>
        </Grid>
    );
}