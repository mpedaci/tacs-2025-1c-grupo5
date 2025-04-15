"use client";
import {Box, Tab, Tabs} from "@mui/material";
import TabPanel from "@components/Tabs/TabPanel";
import React, {useState} from "react";
import OffersReportTab from "@components/Pages/reports/OffersReportTab";
import PostsReportTab from "@components/Pages/reports/PostsReportTab";

export default function ReportTabs() {
    const [value, setValue] = useState(0);
    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };

    return (
        <Box sx={{width: '100%'}}>
            <Tabs value={value} onChange={handleChange} centered>
                <Tab label="Publicaciones" icon={<i className="fa-regular fa-arrow-progress"></i>}
                     iconPosition="start"/>
                <Tab label="Ofertas" icon={<i className="fa-regular fa-solid fa-paper-plane-top"></i>}
                     iconPosition="start"/>
            </Tabs>
            <TabPanel value={value} index={0}>
                <PostsReportTab/>
            </TabPanel>
            <TabPanel value={value} index={1}>
                <OffersReportTab/>
            </TabPanel>
        </Box>
    )
}