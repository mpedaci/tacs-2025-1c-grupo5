import {useDropzone} from "react-dropzone";
import {Box, Button, Icon, Stack, Typography} from "@mui/material";
import DropzoneWrapper from "@components/Dropzones/DropzoneWrapper";
import React from "react";

export default function BasicDropzone({
                                          setFile,
                                          file,
                                          fileName = "",
                                          accept,
                                          isLoading,
                                          previewImage = false,
                                          primaryHelpText = "Arrastrar y soltar o seleccionar archivo",
                                          secondaryHelpText = "Suelte el archivo aquí o haga clic para navegar en su computadora",
                                          error,
                                          helperText,
                                      }: {
    setFile: (file: string) => void,
    file: string,
    fileName?: string,
    accept?: string,
    isLoading: boolean,
    previewImage?: boolean,
    primaryHelpText?: string,
    secondaryHelpText?: string,
    error?: boolean,
    helperText?: string,
}) {
    const [fileNameLocal, setFileNameLocal] = React.useState(fileName);
    const {getRootProps, getInputProps, isDragActive, isDragReject} = useDropzone({
        accept: accept ? {
            [accept]: []
        } : undefined,
        multiple: false,
        onDrop: (acceptedFiles) => {
            const file = acceptedFiles[0];
            const reader = new FileReader();
            reader.onload = () => {
                setFile(reader.result as string);
            };
            reader.readAsDataURL(file);
            if (fileName == "" || fileName == undefined) {
                setFileNameLocal(file.name);
            }
        }
    });

    if (file && !previewImage) {
        return (
            <Stack
                spacing={2}
                alignItems="center"
                justifyContent="center"
                direction={'column'}
                sx={{width: 1}}
            >
                <Stack
                    spacing={2}
                    alignItems="center"
                    justifyContent="center"
                    direction={{xs: 'column', md: 'row'}}
                    sx={{width: 1, textAlign: {xs: 'center', md: 'left'}}}
                >
                    <Icon sx={{width: "auto"}} className={
                        accept == "application/pdf" ?
                            "fa-regular fa-file-pdf" :
                            "fa-regular fa-file"
                    }
                          fontSize={"large"}/>
                    <Stack sx={{p: 2}} spacing={1}>
                        <Typography variant="h5">Archivo seleccionado</Typography>
                        <Typography color="secondary">
                            {fileNameLocal}
                        </Typography>
                    </Stack>
                </Stack>
                <Button
                    variant="contained"
                    size="small"
                    onClick={() => {
                        setFile("");
                    }}
                    endIcon={<i className="fa-regular fa-trash"></i>}
                    color="error"
                    disabled={isLoading}
                    sx={{
                        mt: 2,
                    }}
                >
                    Cambiar archivo
                </Button>
            </Stack>
        )
    }
    if (file && previewImage) {
        return (
            <Stack
                spacing={2}
                alignItems="center"
                justifyContent="center"
                direction={'column'}
                sx={{width: 1}}
            >
                <Stack
                    spacing={2}
                    alignItems="center"
                    justifyContent="center"
                    direction={{xs: 'column', md: 'row'}}
                    sx={{width: 1, textAlign: {xs: 'center', md: 'left'}}}
                >
                    <img src={file} alt={fileNameLocal} style={{maxWidth: "100%", maxHeight: "300px"}}/>
                </Stack>
                <Button
                    variant="contained"
                    size="small"
                    onClick={() => {
                        setFile("");
                    }}
                    endIcon={<i className="fa-regular fa-trash"></i>}
                    color="error"
                    disabled={isLoading}
                    sx={{
                        mt: 2,
                    }}
                >
                    Cambiar archivo
                </Button>
            </Stack>
        )
    }

    return (
        <DropzoneWrapper
            {...getRootProps()}
            sx={{
                ...(isDragActive && {opacity: 0.72}),
                ...((isDragReject) && {
                    color: 'error.main',
                    borderColor: 'error.light',
                    bgcolor: 'error.lighter'
                }),
                ...(error && {
                    color: 'error.main',
                    borderColor: 'error.light',
                    bgcolor: 'error.lighter'
                }),
            }}
        >
            <input {...getInputProps()}/>
            <Stack
                spacing={2}
                alignItems="center"
                justifyContent="center"
                direction={{xs: 'column', md: 'row'}}
                sx={{width: 1, textAlign: {xs: 'center', md: 'left'}}}
            >
                <Icon sx={{width: "auto"}} className="fa-regular fa-cloud-arrow-up"
                      fontSize={"large"}/>
                <Stack sx={{p: 3}} spacing={1}>
                    <Typography variant="h5">{primaryHelpText}</Typography>
                    <Typography color="secondary">{secondaryHelpText}</Typography>
                </Stack>
            </Stack>
            {error && (
                <Box sx={{
                    color: 'error.main',
                    textAlign: 'center',
                    width: 1,
                }}>
                    <Typography variant="h5" color="error" sx={{mt: 1}}>
                        {helperText}
                    </Typography>
                </Box>
            )}
        </DropzoneWrapper>
    );
}