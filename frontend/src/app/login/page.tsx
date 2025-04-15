"use client";
import {useRouter} from "next/navigation";
import Grid from "@mui/material/Grid2";
import {Button, Stack} from "@mui/material";
import Typography from "@mui/material/Typography";
import AuthLogin from "@components/Auth/AuthLogin";
import AuthWrapper from "@components/Auth/AuthWrapper";
import React from "react";
import {registerRoute} from "@routes/router";

export default function LoginPage() {
    const router = useRouter();

    return (
        <AuthWrapper>
            <Grid container spacing={3} direction={"column"}>
                <Grid size={12}>
                    <Stack direction="row" justifyContent="space-between" alignItems="baseline"
                           sx={{mb: {xs: -0.5, sm: 0.5}}}>
                        <Typography variant="h3">Login</Typography>
                    </Stack>
                </Grid>
                <Grid size={12}>
                    <AuthLogin/>
                </Grid>
                <Grid size={12} sx={{display: "flex", justifyContent: "center"}}>
                    <Button
                        variant="contained"
                        color="secondary"
                        onClick={() => {
                            router.push(registerRoute());
                        }}
                        fullWidth
                    >
                        Registrarse
                    </Button>
                </Grid>
            </Grid>
        </AuthWrapper>
    )
}