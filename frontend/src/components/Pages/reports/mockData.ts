import {GraphItem} from "@components/Pages/reports/ReportGraph";

export const postsMockData: GraphItem[] = [
    {
        title: "Publicaciones creadas",
        data: [
            {x: "2023-01-01", y: 100},
            {x: "2023-01-02", y: 200},
            {x: "2023-01-03", y: 300},
            {x: "2023-01-04", y: 400},
            {x: "2023-01-05", y: 500},
        ],
        serie: [
            {dataKey: "y", label: "Publicaciones creadas"},
        ]
    },
    {
        title: "Publicaciones finalizadas",
        data: [
            {x: "2023-01-01", y: 50},
            {x: "2023-01-02", y: 100},
            {x: "2023-01-03", y: 150},
            {x: "2023-01-04", y: 200},
            {x: "2023-01-05", y: 250},
        ],
        serie: [
            {dataKey: "y", label: "Publicaciones finalizadas"},
        ]
    },
    {
        title: "Publicaciones en progreso",
        data: [
            {x: "2023-01-01", y: 20},
            {x: "2023-01-02", y: 40},
            {x: "2023-01-03", y: 60},
            {x: "2023-01-04", y: 80},
            {x: "2023-01-05", y: 100},
        ],
        serie: [
            {dataKey: "y", label: "Publicaciones en progreso"},
        ]
    },
    {
        title: "Publicaciones rechazadas",
        data: [
            {x: "2023-01-01", y: 10},
            {x: "2023-01-02", y: 20},
            {x: "2023-01-03", y: 30},
            {x: "2023-01-04", y: 40},
            {x: "2023-01-05", y: 50},
        ],
        serie: [
            {dataKey: "y", label: "Publicaciones rechazadas"},
        ]
    }
]

export const offersMockData: GraphItem[] = [
    {
        title: "Ofertas creadas",
        data: [
            {x: "2023-01-01", y: 100},
            {x: "2023-01-02", y: 200},
            {x: "2023-01-03", y: 300},
            {x: "2023-01-04", y: 400},
            {x: "2023-01-05", y: 500},
        ],
        serie: [
            {dataKey: "y", label: "Ofertas creadas"},
        ]
    },
    {
        title: "Ofertas aceptadas",
        data: [
            {x: "2023-01-01", y: 50},
            {x: "2023-01-02", y: 100},
            {x: "2023-01-03", y: 150},
            {x: "2023-01-04", y: 200},
            {x: "2023-01-05", y: 250},
        ],
        serie: [
            {dataKey: "y", label: "Ofertas aceptadas"},
        ]
    },
    {
        title: "Ofertas rechazadas",
        data: [
            {x: "2023-01-01", y: 20},
            {x: "2023-01-02", y: 40},
            {x: "2023-01-03", y: 60},
            {x: "2023-01-04", y: 80},
            {x: "2023-01-05", y: 100},
        ],
        serie: [
            {dataKey: "y", label: "Ofertas rechazadas"},
        ]
    },
    {
        title: "Ofertas en progreso",
        data: [
            {x: "2023-01-01", y: 10},
            {x: "2023-01-02", y: 20},
            {x: "2023-01-03", y: 30},
            {x: "2023-01-04", y: 40},
            {x: "2023-01-05", y: 50},
        ],
        serie: [
            {dataKey: "y", label: "Ofertas en progreso"},
        ]
    }
]