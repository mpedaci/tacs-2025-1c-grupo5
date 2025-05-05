"use client";
import ReportGraph from "@components/Pages/reports/ReportGraph";
import Grid from "@mui/material/Grid2";
import MainCard from "@components/Cards/MainCard";
import {useGetOffersMetricsQuery} from "@redux/services/metricsApi";


export default function OffersReportTab() {
    const {data: graphData} = useGetOffersMetricsQuery();

    return (
        <Grid container spacing={3}>
            {(graphData || [])
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