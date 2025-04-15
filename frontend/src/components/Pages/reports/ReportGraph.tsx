import {Area, AreaChart, CartesianGrid, Legend, ResponsiveContainer, Tooltip, XAxis, YAxis} from "recharts";
import ReportTooltip from "./ReportTooltip";
import React from "react";

export interface GraphItem {
    title: string;
    data: { [p: string]: string | number }[];
    serie: { dataKey: string; label: string }[];
}

export default function ReportGraph({
                                        data,
                                        serie,
                                    }: {
    data: { [p: string]: string | number }[];
    serie: {
        dataKey: string;
        label: string;
    }[]
}) {
    return (
        <ResponsiveContainer width="100%" height={400}>
            <AreaChart data={data} margin={{top: 10, right: 30, left: 0, bottom: 0}}>
                <CartesianGrid strokeDasharray="3 3"/>
                <XAxis dataKey="x"/>
                <YAxis/>
                <Tooltip content={ReportTooltip}/>
                <Legend/>
                {serie.map((s) => {
                    return (
                        <Area
                            type="monotone"
                            dataKey={s.dataKey}
                            name={s.label}
                            key={s.dataKey}
                            strokeWidth={3}
                            stroke="#8884d8"
                            fill="#8884d8"
                            dot={{r: 6}}
                            activeDot={{r: 8}}
                        />
                    )
                })}
            </AreaChart>
        </ResponsiveContainer>
    )
}