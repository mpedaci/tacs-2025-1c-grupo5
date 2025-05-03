"use client";
import Grid from "@mui/material/Grid2";
import {
    FormContainer,
    SelectElement,
    TextFieldElement,
    useForm,
    AutocompleteElement,
    Controller
} from "react-hook-form-mui";
import {
    Box,
    Button,
    CardActions,
    CardContent,
    Divider,
    Stack,
    Typography
} from "@mui/material";
import React from "react";
import {useTheme} from "@mui/material/styles";
import {useNotification} from "@components/Notifications/NotificationContext";
import {FormFieldValue} from "@components/Forms/Form";
import {useGetGamesQuery} from "@redux/services/gamesApi";
import {ConservationState, conservationStateLabels} from "@models/enums/ConservationState";
import BasicDropzone from "@components/Dropzones/BasicDropzone";
import CardSelectElement, {CardOption} from "@components/Forms/CardSelectElement";
import {useCreatePostMutation} from "@redux/services/postsApi";
import {IPostCreateRequest} from "@models/requests/posts/iPostCreateRequest";
import {useAppSelector} from "@redux/hook";

export default function CreatePostTab() {
    const theme = useTheme();
    const formContext = useForm();
    const {data: games} = useGetGamesQuery();
    const {addNotification} = useNotification();
    const [
        createPost,
        {isLoading: isCreatingPost}
    ] = useCreatePostMutation();
    const user = useAppSelector(state => state.user);



    const handleSave = async (data: FormFieldValue) => {
        const req: IPostCreateRequest = {
            userId: user.id,
            cardId: ((data.card as unknown) as CardOption).id,
            conservationStatus: data.conservationState as unknown as ConservationState,
            images: [data.images],
            estimatedValue: Number(data.estimatedValue),
            description: data.description,
            wantedCardsIds: (data.wantedCards as unknown as CardOption[]).map((card) => card.id),
        };
        try {
            await createPost(req).unwrap();
            addNotification("Publicación creada correctamente", "success");
            formContext.reset();
        } catch (error) {
            addNotification("Error al crear la publicación", "error");
        }
    }

    return (
        <FormContainer
            formContext={formContext}
            onSuccess={handleSave}
        >
            <CardActions
                sx={{
                    position: 'sticky',
                    top: 0,
                    bgcolor: theme.palette.background.default,
                    zIndex: 2,
                    borderBottom: `1px solid ${theme.palette.divider}`
                }}
            >
                <Stack direction="row" alignItems="center" justifyContent="space-between"
                       sx={{width: 1}}>
                    <Box component="div" sx={{flexGrow: 1, m: 0, pl: 1.5}}>
                        <Typography variant="h5" sx={{flexGrow: 1}}>
                            Crear publicación
                        </Typography>
                        <Typography variant="subtitle1" sx={{flexGrow: 1}}>
                            Complete el formulario para crear una nueva publicación.
                        </Typography>
                    </Box>
                </Stack>
            </CardActions>
            <CardContent>
                <Grid container marginTop={3} spacing={3} alignItems="center">
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
                    <Grid size={12} key={"estimatedValue"}>
                        <TextFieldElement
                            name={"estimatedValue"}
                            label={"Valor estimado"}
                            placeholder={"Ingrese el valor estimado"}
                            required={true}
                            fullWidth
                            rules={
                                {
                                    required: "Por favor ingrese el valor estimado"
                                }
                            }
                            type={"number"}
                            InputProps={{
                                startAdornment: <Box sx={{ml: 1}}>USD</Box>,
                            }}
                            InputLabelProps={{
                                shrink: true,
                            }}
                            inputProps={{
                                min: 0,
                            }}
                        />
                    </Grid>
                    <Grid size={12} key={"description"}>
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
                    </Grid>
                    <Grid size={12} key={"wantedCards"}>
                        <CardSelectElement
                            gameId={formContext.watch("game") as string}
                            name={"wantedCards"}
                            label={"Cartas buscadas"}
                            required={true}
                            multiple={true}
                            control={formContext.control}
                        />
                    </Grid>
                    <Grid size={12} key={"images"}>
                        <Controller
                            name="images"
                            control={formContext.control}
                            rules={{
                                required: "Por favor seleccione una imagen",
                            }}
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
                        />
                    </Grid>
                </Grid>
            </CardContent>
            <Divider/>
            <CardActions sx={{
                bgcolor: theme.palette.background.default,
            }}>
                <Stack direction="row" spacing={1} justifyContent="center"
                       sx={{width: 1, px: 1.5, py: 0.75}}>
                    <Button color="primary" variant="contained" type={"submit"}
                            disabled={isCreatingPost}>
                        Registrar
                    </Button>
                </Stack>
            </CardActions>

        </FormContainer>
    );
}