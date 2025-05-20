"use client";
import {useAppSelector} from "@redux/hook";
import {Box, Button, CardActions, CardContent, Divider, IconButton, Stack} from "@mui/material";
import {loginRoute} from "@routes/router";
import Typography from "@mui/material/Typography";
import React from "react";
import AuthBackground from "@components/Auth/AuthBackground";
import Grid from "@mui/material/Grid2";
import Logo from "@components/Logo";
import MainCard from "@components/Cards/MainCard";
import {Controller, FormContainer, TextFieldElement} from "react-hook-form-mui";

export default function AuthValidation({
                                           children,
                                       }: {
    children: React.ReactNode;
}) {
    const user = useAppSelector(state => state.user);

    if (user.token === '') {
        return <Box sx={{minHeight: '100vh'}}>
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
                                <CardContent>
                                    <Grid container marginTop={3} spacing={3} alignItems="center" textAlign="center">
                                        <Grid size={12} key={"title"}>
                                            <Typography variant="h6" gutterBottom>
                                                You are not logged in. Please log in to access this page.
                                            </Typography>
                                        </Grid>
                                        <Grid size={12} key={"body"}>
                                            <Typography variant="body1" gutterBottom>
                                                You can log in using the button below.
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </CardContent>
                                <Divider/>
                                <CardActions
                                    sx={{
                                        display: 'flex',
                                        justifyContent: 'center',
                                        flexWrap: 'wrap'
                                    }}
                                >
                                    <Button variant="contained" color="primary" href={loginRoute()}>
                                        Go to Login
                                    </Button>
                                </CardActions>
                            </MainCard>
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Box>;
    }

    return (
        <>
            {children}
        </>
    );
}