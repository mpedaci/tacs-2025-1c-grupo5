"use client";
import ReportGraph, {GraphItem} from "@components/Pages/reports/ReportGraph";
import {useState} from "react";
import {postsMockData} from "@components/Pages/reports/mockData";
import Grid from "@mui/material/Grid2";
import MainCard from "@components/Cards/MainCard";


export default function PostsReportTab() {
    const [graphData, setGraphData] = useState<GraphItem[]>(postsMockData);

    return (
        <Grid container spacing={3}>
            {graphData
                .map(({title, data, serie}, index) => (
                    <Grid size={{
                        xs: 12,
                        xl: 6,
                    }} key={index}>
                        <MainCard title={title} content={false} darkTitle>
                            <ReportGraph data={data} serie={serie}/>
                        </MainCard>
                    </Grid>
                ))
            }
        </Grid>
    );
}
