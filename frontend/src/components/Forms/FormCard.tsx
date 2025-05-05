import React from "react";
import MainCard from "@components/Cards/MainCard";
import {FieldValues, FormContainer, UseFormReturn} from "react-hook-form-mui";
import {Button, CardContent, Divider, Stack} from "@mui/material";

export default function FormCard({
                                     title,
                                     formContext,
                                     handleSuccess,
                                     handleClose,
                                     isLoading,
                                     submitText,
                                     children,
                                     width = "80%",
                                     modal = true,
                                     disableActions = false
                                 }: {
    title: string,
    formContext: UseFormReturn<FieldValues>,
    handleSuccess: (data: FieldValues) => void,
    handleClose: () => void,
    isLoading: boolean,
    submitText: string,
    children: React.ReactNode,
    width?: string,
    modal?: boolean,
    disableActions?: boolean
}) {
    return (
        <MainCard modal={modal} darkTitle content={false} title={title} sx={{width}}>
            <FormContainer
                formContext={formContext}
                onSuccess={handleSuccess}
            >
                <CardContent>
                    {children}
                </CardContent>
                {
                    !disableActions &&
                    <>
                        <Divider/>
                        <Stack direction="row" spacing={1} justifyContent="flex-end" sx={{px: 2.5, py: 2}}>
                            <Button color="error" size="small" onClick={handleClose}>
                                Cancelar
                            </Button>
                            <Button
                                variant="contained"
                                size="small"
                                type="submit"
                                disabled={isLoading}
                            >
                                {submitText}
                            </Button>
                        </Stack>
                    </>
                }
            </FormContainer>
        </MainCard>
    );
}