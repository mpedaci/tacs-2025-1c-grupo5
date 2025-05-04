// material-ui
import useMediaQuery from '@mui/material/useMediaQuery';
import Box from '@mui/material/Box';
import {useTheme} from "@mui/material";
import React from "react";
import Profile from "@components/Layouts/MainLayout/Header/HeaderContent/Profile";

// ==============================|| HEADER - CONTENT ||============================== //

export default function HeaderContent() {
    const theme = useTheme();
    const downLG = useMediaQuery(theme.breakpoints.down('lg'));

    return (
        <>
            {downLG && <Box sx={{ width: '100%', ml: 1 }} />}

            <Box sx={{ flexGrow: 1 }} />

            <Profile />
        </>
    );
}
