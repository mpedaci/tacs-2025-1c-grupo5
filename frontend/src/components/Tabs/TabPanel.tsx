import React from "react";
import {Box} from "@mui/material";

export default function TabPanel({children, value, index}: {
    children: React.ReactNode,
    value: number,
    index: number
}) {
    return (
        <div role="tabpanel" hidden={value !== index} id={`tabpanel-${index}`} aria-labelledby={`tab-${index}`}>
            {value === index && <Box sx={{pt: 2}}>{children}</Box>}
        </div>
    );
}