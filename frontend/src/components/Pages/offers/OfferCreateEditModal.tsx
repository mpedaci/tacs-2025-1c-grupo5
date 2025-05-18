"use client";
import React, {useState} from "react";
import {Controller, SelectElement, TextFieldElement, useFieldArray, useForm} from "react-hook-form-mui";
import {useNotification} from "@components/Notifications/NotificationContext";
import {FormFieldValue} from "@components/Forms/Form";
import {Box, Button, Typography} from "@mui/material";
import AnimatedModal from "@/components/Modals/AnimatedModal";
import FormCard from "@/components/Forms/FormCard";
import Grid from "@mui/material/Grid2";
import {IOfferCreateRequest, IOfferedCardRequest} from "@models/requests/offers/iOfferCreateRequest";
import {useAppSelector} from "@redux/hook";
import {useGetGamesQuery} from "@redux/services/gamesApi";
import {useCreateOfferMutation} from "@redux/services/offersApi";
import CardSelectElement from "@components/Forms/CardSelectElement";
import {ConservationState, conservationStateLabels} from "@models/enums/ConservationState";
import {MuiFileInput} from "mui-file-input";

export default function OfferCreateEditModal({
                                                 postId,
                                                 offerId,
                                                 editMode,
                                                 onClose,
                                             }: {
    postId: string;
    offerId?: string;
    editMode: boolean;
    onClose?: () => void;
}) {
    const [openModal, setOpenModal] = useState(false);
    const formContext = useForm();
    const {addNotification} = useNotification();
    const user = useAppSelector((state) => state.user);
    const {fields: offeredCards, append: appendOfferedCard, remove: removeOfferedCard} = useFieldArray({
        name: "offeredCards",
        control: formContext.control
    });

    const {data: games} = useGetGamesQuery();

    const [
        createOffer,
        {isLoading: isCreatingOffer},
    ] = useCreateOfferMutation();

    const handleClose = () => {
        if (onClose) onClose();
        formContext.reset();
        setOpenModal(false);
    }

    const handleCreate = async (data: FormFieldValue) => {
        const req : IOfferCreateRequest = {
            money: Number(data.money),
            postId: postId,
            offererId: user.id,
            offeredCards: ((data.offeredCards as unknown) as {
                cardId: { id: string };
                imageBase64: string;
                conservationStatus: string;
            }[]).map((card) => ({
                cardId: card.cardId.id,
                image: card.imageBase64,
                conservationStatus: card.conservationStatus,
            })) as IOfferedCardRequest[],
        }
        try {
            await createOffer({body: req}).unwrap();
            addNotification("Oferta creada con éxito", "success");
            handleClose();
        } catch (error) {
            addNotification("Error al crear la oferta", "error");
        }
    }

    const handleUpdate = async (data: FormFieldValue) => {

    }

    return (
        <>
            {
                !editMode && (
                    <Button
                        variant="contained"
                        onClick={() => setOpenModal(true)}
                        endIcon={<i className="fa-regular fa-plus"/>}
                        size="small"
                    >
                        Crear
                    </Button>
                )
            }
            <AnimatedModal
                open={editMode ? offerId !== undefined : openModal}
                onClose={handleClose}
            >
                <FormCard
                    title={editMode ? "Editar oferta" : "Crear oferta"}
                    formContext={formContext}
                    handleSuccess={editMode ? handleUpdate : handleCreate}
                    handleClose={handleClose}
                    isLoading={isCreatingOffer || false}
                    submitText={editMode ? "Editar" : "Crear"}
                >
                    <Grid container spacing={3} alignItems="center">
                        <Grid size={12} key={"money"}>
                            <TextFieldElement
                                name={"money"}
                                label={"Dinero ofrecido"}
                                placeholder={"Ingrese el dinero ofrecido"}
                                required={offeredCards.length === 0}
                                fullWidth
                                rules={
                                    {
                                        required: offeredCards.length === 0 ? "Por favor ingrese el dinero ofrecido" : undefined,
                                        min: {
                                            value: 0,
                                            message: "El dinero ofrecido debe ser mayor a 0",
                                        },
                                    }
                                }
                                type={"number"}
                                InputProps={{
                                    startAdornment: <Box sx={{ml: 1}}>$</Box>,
                                }}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                inputProps={{
                                    min: 0,
                                }}
                            />
                        </Grid>
                        <Grid size={10} key={"offeredCards-title"} alignContent="center">
                            <Typography variant="h6">
                                Cartas ofrecidas
                            </Typography>
                        </Grid>
                        <Grid size={2} key={"offeredCards-btn"}>
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={() => appendOfferedCard({
                                    gameId: "",
                                    cardId: "",
                                    image: "",
                                    imageBase64: "",
                                    conservationStatus: "",
                                })}
                                sx={{width: "100%"}}
                                size="small"
                            >
                                Agregar
                            </Button>
                        </Grid>
                        {
                            offeredCards.map((card, index) => (
                                <Grid size={12} key={index} container spacing={3} columns={13}>
                                    <Grid size={3} key={"game"}>
                                        <SelectElement
                                            name={`offeredCards.${index}.gameId`}
                                            label={"Juego"}
                                            placeholder={"Seleccione el juego"}
                                            required={true}
                                            fullWidth
                                            rules={
                                                {
                                                    required: "Por favor seleccione el juego"
                                                }
                                            }
                                            options={games?.map((game) => ({
                                                id: game.id,
                                                label: game.title
                                            })) ?? []}
                                        />
                                    </Grid>
                                    <Grid size={3} key={"cardId"}>
                                        <CardSelectElement
                                            gameId={formContext.watch(`offeredCards.${index}.gameId`) as string}
                                            name={`offeredCards.${index}.cardId`}
                                            label={"Carta"}
                                            required={true}
                                            control={formContext.control}
                                        />
                                    </Grid>
                                    <Grid size={3} key={"conservationState"}>
                                        <SelectElement
                                            name={`offeredCards.${index}.conservationStatus`}
                                            label={"Estado de conservación"}
                                            placeholder={"Seleccione el estado de conservación"}
                                            required={true}
                                            fullWidth
                                            rules={
                                                {
                                                    required: "Por favor seleccione el estado de conservación"
                                                }
                                            }
                                            options={[
                                                {id: ConservationState.Bad, label: conservationStateLabels[ConservationState.Bad]},
                                                {
                                                    id: ConservationState.Regular,
                                                    label: conservationStateLabels[ConservationState.Regular]
                                                },
                                                {id: ConservationState.Good, label: conservationStateLabels[ConservationState.Good]},
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
                                        />
                                    </Grid>
                                    <Grid size={3} key={"image"}>
                                        <Controller
                                            name={`offeredCards.${index}.image`}
                                            control={formContext.control}
                                            rules={{
                                                required: "Por favor seleccione una imagen"
                                            }}
                                            render={({ field, fieldState }) => (
                                                <MuiFileInput
                                                    onChange={(file) => {
                                                        if (!file) {
                                                            field.onChange(null);
                                                            formContext.setValue(`offeredCards.${index}.imageBase64`, null);
                                                            return;
                                                        }

                                                        field.onChange(file);

                                                        const reader = new FileReader();
                                                        reader.onloadend = () => {
                                                            const base64String = reader.result?.toString() || '';
                                                            formContext.setValue(`offeredCards.${index}.imageBase64`, base64String);
                                                        };
                                                        reader.readAsDataURL(file);
                                                    }}
                                                    value={field.value || null}
                                                    helperText={fieldState.invalid ? "Por favor seleccione una imagen" : ""}
                                                    error={fieldState.invalid}
                                                    label={"Imagen"}
                                                    placeholder={"Seleccione la imagen"}
                                                    InputProps={{
                                                        inputProps: {
                                                            accept: 'image/*'
                                                        },
                                                        startAdornment: <i className="fa-solid fa-image"/>,
                                                    }}
                                                    clearIconButtonProps={{
                                                        title: "Limpiar",
                                                        children: <i className="fa-solid fa-xmark"/>,
                                                    }}
                                                />
                                            )}
                                        />
                                    </Grid>

                                    <Grid size={1} key={"remove-offeredCard-btn"}>
                                        <Button
                                            variant="contained"
                                            color="error"
                                            onClick={() => removeOfferedCard(index)}
                                            sx={{width: "100%"}}
                                        >
                                            Eliminar
                                        </Button>
                                    </Grid>
                                </Grid>
                            ))
                        }
                    </Grid>
                </FormCard>
            </AnimatedModal>
        </>
    )
}