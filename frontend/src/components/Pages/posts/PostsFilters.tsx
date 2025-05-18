"use client";
import Grid from "@mui/material/Grid2";
import {AutocompleteElement, FormContainer, TextFieldElement, useForm} from "react-hook-form-mui";
import {ConservationState, conservationStateLabels} from "@models/enums/ConservationState";
import React, {useEffect} from "react";
import {useGetGamesQuery} from "@redux/services/gamesApi";

export default function PostsFilters(
    {
        getPosts,
    }: {
        getPosts: (params: {
            gameId?: string;
            name?: string;
            state?: ConservationState;
        }) => void;
    }
) {
    const formContext = useForm();
    const {data: games} = useGetGamesQuery();

    useEffect(() => {
        getPosts({
            gameId: formContext.watch(`gameId`) as string || undefined,
            name: formContext.watch(`cardName`) as string || undefined,
            state: formContext.watch(`conservationStatus`) as ConservationState || undefined,
        });
    }, [formContext.watch(`gameId`), formContext.watch(`cardName`), formContext.watch(`conservationStatus`), getPosts]);


    return (
        <FormContainer
            formContext={formContext}
            onSuccess={() => {
            }}
        >
            <Grid size={12} key={"filter"} container spacing={3}>
                <Grid size={4} key={"gameId"}>
                    <AutocompleteElement
                        name="gameId"
                        control={formContext.control}
                        label="Juego"
                        options={games ?? []}
                        matchId
                        autocompleteProps={{
                            getOptionLabel: (option) => option.title || "",
                        }}
                    />
                </Grid>
                <Grid size={4} key={"cardName"}>
                    <TextFieldElement
                        name="cardName"
                        label="Nombre de la carta"
                        variant="outlined"
                        fullWidth
                    />
                </Grid>
                <Grid size={4} key={"conservationState"}>
                    <AutocompleteElement
                        name="conservationStatus"
                        label="Estado de conservación"
                        options={[
                            {id: ConservationState.Bad, label: conservationStateLabels[ConservationState.Bad]},
                            {
                                id: ConservationState.Regular,
                                label: conservationStateLabels[ConservationState.Regular]
                            },
                            {
                                id: ConservationState.Good,
                                label: conservationStateLabels[ConservationState.Good]
                            },
                            {
                                id: ConservationState.Excellent,
                                label: conservationStateLabels[ConservationState.Excellent]
                            },
                            {
                                id: ConservationState.AlmostPerfect,
                                label: conservationStateLabels[ConservationState.AlmostPerfect]
                            },
                            {
                                id: ConservationState.Perfect,
                                label: conservationStateLabels[ConservationState.Perfect]
                            },
                        ]}
                        matchId
                        autocompleteProps={{
                            getOptionLabel: (option) => option.label ?? "",
                        }}
                    />
                </Grid>
            </Grid>
        </FormContainer>
    )
}