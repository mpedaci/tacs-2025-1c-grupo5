"use client";
import {Box, Tab, Tabs} from "@mui/material";
import TabPanel from "@components/Tabs/TabPanel";
import React, {useState} from "react";
import CommunityPostsTab from "@components/Pages/posts/CommunityPostsTab";
import OwnPostsTab from "@components/Pages/posts/OwnPostsTab";

export default function PostsTabs() {
    const [value, setValue] = useState(0);
    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };

    return (
        <Box sx={{width: '100%'}}>
            <Tabs value={value} onChange={handleChange} centered>
                <Tab label="Publicaciones" icon={<i className="fa-regular fa-arrow-progress"></i>}
                     iconPosition="start"/>
                <Tab label="Mis publicaciones" icon={<i className="fa-regular fa-solid fa-paper-plane-top"></i>}
                     iconPosition="start"/>
            </Tabs>
            <TabPanel value={value} index={0}>
                <CommunityPostsTab/>
            </TabPanel>
            <TabPanel value={value} index={1}>
                <OwnPostsTab/>
            </TabPanel>
        </Box>
    )
}