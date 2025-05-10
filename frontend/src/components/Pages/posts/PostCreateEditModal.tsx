"use client";
import React, {useState} from "react";
import {Controller, SelectElement, TextFieldElement, useForm} from "react-hook-form-mui";
import {useNotification} from "@components/Notifications/NotificationContext";
import {FormFieldValue} from "@components/Forms/Form";
import {Box, Button} from "@mui/material";
import AnimatedModal from "@/components/Modals/AnimatedModal";
import FormCard from "@/components/Forms/FormCard";
import Grid from "@mui/material/Grid2";
import {useCreatePostMutation} from "@redux/services/postsApi";
import {useAppSelector} from "@redux/hook";
import {useGetGamesQuery} from "@redux/services/gamesApi";
import {IPostCreateRequest} from "@models/requests/posts/iPostCreateRequest";
import CardSelectElement, {CardOption} from "@components/Forms/CardSelectElement";
import {ConservationState, conservationStateLabels} from "@models/enums/ConservationState";
import {MuiFileInput} from "mui-file-input";

export default function PostCreateEditModal({
                                                postId,
                                                editMode,
                                                onClose,
                                            }: {
    postId?: string;
    editMode: boolean;
    onClose?: () => void;
}) {
    const [openModal, setOpenModal] = useState(false);
    const formContext = useForm();
    const {addNotification} = useNotification();

    const {data: games} = useGetGamesQuery();

    const [
        createPost,
        {isLoading: isCreatingPost}
    ] = useCreatePostMutation();
    const user = useAppSelector(state => state.user);

    const handleClose = () => {
        if (onClose) onClose();
        formContext.reset();
        setOpenModal(false);
    }

    const handleCreate = async (data: FormFieldValue) => {
        const req: IPostCreateRequest = {
            userId: user.id,
            cardId: ((data.card as unknown) as CardOption).id,
            conservationStatus: data.conservationState as unknown as ConservationState,
            images: (data.images as unknown as { file: File; base64: string }[] || []).map((image) => (image.base64)),
            estimatedValue: Number(data.estimatedValue),
            // description: data.description,
            description: "",
            wantedCardsIds: (data.wantedCards as unknown as CardOption[] || []).map((card) => card.id),
        };
        try {
            await createPost(req).unwrap();
            addNotification("Publicación creada correctamente", "success");
            handleClose();
        } catch (error) {
            addNotification("Error al crear la publicación", "error");
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
                open={editMode ? postId !== undefined : openModal}
                onClose={handleClose}
            >
                <FormCard
                    title={editMode ? "Editar publicacion" : "Crear publicacion"}
                    formContext={formContext}
                    handleSuccess={editMode ? handleUpdate : handleCreate}
                    handleClose={handleClose}
                    isLoading={isCreatingPost || false}
                    submitText={editMode ? "Editar" : "Crear"}
                >
                    <Grid container spacing={3} alignItems="center">
                        <Grid size={12} key={"game"}>
                            <SelectElement
                                name={"game"}
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
                        <Grid size={12} key={"card"}>
                            <CardSelectElement
                                gameId={formContext.watch("game") as string}
                                name={"card"}
                                label={"Carta"}
                                required={true}
                                control={formContext.control}
                            />
                        </Grid>
                        <Grid size={12} key={"conservationState"}>
                            <SelectElement
                                name={"conservationState"}
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
                            />
                        </Grid>
                        <Grid size={12} key={"estimatedValue"}>
                            <TextFieldElement
                                name={"estimatedValue"}
                                label={"Valor estimado"}
                                placeholder={"Ingrese el valor estimado"}
                                fullWidth
                                rules={
                                    {
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
                        {/*<Grid size={12} key={"description"}>
                            <TextFieldElement
                                name={"description"}
                                label={"Descripción"}
                                placeholder={"Ingrese una descripción"}
                                fullWidth
                                multiline
                                rows={4}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                            />
                        </Grid>*/}
                        <Grid size={12} key={"wantedCards"}>
                            <CardSelectElement
                                gameId={formContext.watch("game") as string}
                                name={"wantedCards"}
                                label={"Cartas buscadas"}
                                multiple={true}
                                control={formContext.control}
                            />
                        </Grid>
                        <Grid size={12} key={"images"}>
                            <Controller
                                name="images"
                                control={formContext.control}
                                render={({ field, fieldState }) => (
                                    <MuiFileInput
                                        fullWidth
                                        onChange={(files: File[] | null) => {
                                            if (!files || files.length === 0) {
                                                field.onChange([]);
                                                return;
                                            }

                                            const fileArray = Array.isArray(files) ? files : [files];

                                            const readers = fileArray.map((file) => {
                                                return new Promise<{ file: File; base64: string }>((resolve) => {
                                                    const reader = new FileReader();
                                                    reader.onloadend = () => {
                                                        resolve({ file, base64: reader.result?.toString() || '' });
                                                    };
                                                    reader.readAsDataURL(file);
                                                });
                                            });

                                            Promise.all(readers).then((fileObjects) => {
                                                field.onChange(fileObjects);
                                            });
                                        }}
                                        multiple
                                        value={((field.value as { file: File }[]) || []).map(f => f.file) || null}
                                        helperText={fieldState.invalid ? "Por favor seleccione una imagen" : ""}
                                        error={fieldState.invalid}
                                        label={"Imagen"}
                                        placeholder={"Seleccione la imagen"}
                                        InputProps={{
                                            inputProps: {
                                                accept: 'image/*',
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
                            {/*<Controller
                                name="images"
                                control={formContext.control}
                                render={() => (
                                    <BasicDropzone
                                        setFile={(file) => formContext.setValue("images", file)}
                                        file={formContext.watch("images") as string}
                                        accept="image/*"
                                        isLoading={false}
                                        previewImage={true}
                                        primaryHelpText={"Arrastre y suelte o seleccione una imagen"}
                                        secondaryHelpText={"Suelte la imagen aquí o haga clic para navegar en su computadora"}
                                        error={!!formContext.formState.errors["images"]}
                                        helperText={formContext.formState.errors["images"]?.message as string}
                                    />
                                )}
                            />*/}
                        </Grid>
                    </Grid>
                </FormCard>
            </AnimatedModal>
        </>
    )
}