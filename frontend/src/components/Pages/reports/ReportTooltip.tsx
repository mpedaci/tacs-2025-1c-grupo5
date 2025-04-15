import {TooltipProps} from "recharts";
import {NameType, ValueType} from "recharts/types/component/DefaultTooltipContent";
import MainCard from "@components/Cards/MainCard";
import Grid from "@mui/material/Grid2";
import {Stack, Typography} from "@mui/material";
import React from "react";

export default function ReportTooltip({
                                          active,
                                          payload,
                                          label,
                                      }: TooltipProps<ValueType, NameType>) {
    if (active) {
        return (
            <MainCard title={label?.toString()}>
                <Grid container spacing={2}>
                    {payload?.map((p, index) => (
                        <Grid size={12} key={index}>
                            <Stack direction="row" spacing={1}>
                                <Typography variant="h6" color={p.color}>
                                    <i className="fa-solid fa-circle"/>
                                </Typography>
                                <Typography variant="h6">{p.name}</Typography>
                                <Typography variant="h6">{p.value}</Typography>
                            </Stack>
                        </Grid>
                    ))}
                </Grid>
            </MainCard>
        );
    }

    return null;
}