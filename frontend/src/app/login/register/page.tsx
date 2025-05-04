"use client";
import {useCreateUserMutation} from "@redux/services/usersApi";
import {IUserCreateRequest} from "@models/requests/users/iUserCreateRequest";
import {loginRoute} from "@routes/router";
import { useNotification } from "@/components/Notifications/NotificationContext";
import {Controller, FormContainer, TextFieldElement, useForm} from "react-hook-form-mui";
import {useTheme} from "@mui/material/styles";
import {useRouter} from 'next/navigation';
import { FormFieldValue } from "@/components/Forms/Form";
import {Box, Button, CardActions, Divider, Stack, Typography, CardContent, IconButton} from "@mui/material";
import AuthBackground from "@/components/Auth/AuthBackground";
import Grid from "@mui/material/Grid2";
import Logo from "@/components/Logo";
import MainCard from "@/components/Cards/MainCard";
import React from "react";

export default function RegisterPage() {
    const theme = useTheme();
    const formContext = useForm();
    const {addNotification} = useNotification();
    const router = useRouter();
    const [
        createUser,
        {isLoading: createUserIsLoading}
    ] = useCreateUserMutation();

    const handleSave = async (data: FormFieldValue) => {
        const req: IUserCreateRequest = {
            name: data.name,
            username: data.username,
            password: data.password,
            admin: false
        };
        try {
            await createUser(req).unwrap();
            addNotification("Usuario creado correctamente", "success");
            router.push(loginRoute());
        } catch (error) {
            addNotification("Error al crear el usuario", "error");
        }
    }

    return (
        <Box sx={{minHeight: '100vh'}}>
            <AuthBackground/>
            <Grid
                container
                direction="column"
                justifyContent="flex-end"
                sx={{
                    minHeight: '100vh'
                }}
            >
                <Grid sx={{ml: 3, mt: 3}}>
                    <Logo isIcon={false} to="/"/>
                </Grid>
                <Grid size={12}>
                    <Grid
                        size={12}
                        container
                        justifyContent="center"
                        alignItems="center"
                        sx={{
                            minHeight: {
                                xs: 'calc(100vh - 210px)',
                                sm: 'calc(100vh - 134px)',
                                md: 'calc(100vh - 112px)'
                            }
                        }}
                    >
                        <Grid size={{
                            xs: 12,
                            sm: 10,
                            md: 8,
                            lg: 6,
                        }}>
                            <MainCard content={false} sx={{overflow: 'visible'}}>
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
                                            <IconButton
                                                onClick={() => {
                                                    router.replace(loginRoute());
                                                }}
                                                sx={{mr: 0.5}}
                                            >
                                                <i className="fa-solid fa-arrow-turn-down-left"></i>
                                            </IconButton>
                                            <Box component="div" sx={{flexGrow: 1, m: 0, pl: 1.5}}>
                                                <Typography variant="h5" sx={{flexGrow: 1}}>
                                                    Registro de Usuario
                                                </Typography>
                                                <Typography variant="subtitle1" sx={{flexGrow: 1}}>
                                                    Registrese en el sistema, completando el formulario
                                                </Typography>
                                            </Box>
                                        </Stack>
                                    </CardActions>
                                    <CardContent>
                                        <Grid container marginTop={3} spacing={3} alignItems="center">
                                            <Grid size={121} key={"nombre"}>
                                                <TextFieldElement
                                                    name={"nombre"}
                                                    label={"Nombre"}
                                                    placeholder={"Ingrese su nombre"}
                                                    required={true}
                                                    fullWidth
                                                    rules={
                                                        {
                                                            required: "Por favor ingrese su nombre"
                                                        }
                                                    }
                                                />
                                            </Grid>
                                            <Grid size={12} key={"username"}>
                                                <TextFieldElement
                                                    name={"username"}
                                                    label={"Nombre de Usuario"}
                                                    placeholder={"Ingrese su nombre de usuario"}
                                                    required={true}
                                                    fullWidth
                                                    rules={
                                                        {
                                                            required: "Por favor ingrese su nombre de usuario"
                                                        }
                                                    }
                                                />
                                            </Grid>
                                            <Grid size={12} key={"password"}>
                                                <Controller
                                                    name="password"
                                                    rules={{
                                                        required: 'La contraseña es obligatoria',
                                                        validate: async (value) => {
                                                            const resp = value.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&#-]{8,}$/);
                                                            if (!resp) {
                                                                return "La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una minúscula, un número y un caracter especial";
                                                            } else {
                                                                return true;
                                                            }
                                                        },
                                                    }}
                                                    render={({field, fieldState}) => (
                                                        <TextFieldElement
                                                            {...field}
                                                            label="Contraseña"
                                                            type="password"
                                                            error={!!fieldState.error}
                                                            helperText={fieldState.error?.message}
                                                            fullWidth
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
                                                    disabled={createUserIsLoading}>
                                                Registrar
                                            </Button>
                                        </Stack>
                                    </CardActions>
                                </FormContainer>
                            </MainCard>
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Box>
    )
}