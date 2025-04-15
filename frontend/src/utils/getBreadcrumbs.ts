import {
    postOffersRoute, postsRoute, reportsRoute
} from "@routes/router";

export interface IBreadcrumbItem {
    id: string;
    title: string;
    url: string;
    father: string | null;
}

const items : IBreadcrumbItem[] = [
    {
        id: "publicaciones",
        title: "Publicaciones",
        url: postsRoute(),
        father: null
    },
    {
        id: "publicaciones-ofertas",
        title: "Ofertas",
        url: postOffersRoute("#"),
        father: "publicaciones"
    },
    {
        id: "reportes",
        title: "Reportes",
        url: reportsRoute(),
        father: null
    }
]

export function GetBreadcrumbs(path: string): IBreadcrumbItem[] {
    const breadcrumbs: IBreadcrumbItem[] = [];
    let normalizedPath = path.replace(/\/[0-9a-fA-F-]{36}(?=\/|$)/, "/#");
    normalizedPath = normalizedPath.replace(/\/\d+(?=\/|$)/, "/#");

    let current: IBreadcrumbItem | undefined = items.find(item => item.url === normalizedPath);
    while(current) {
        breadcrumbs.unshift(current);
        current = items.find(item => item.id === current?.father);
    }
    return breadcrumbs;
}

export function GetBreadcrumb(path: string): IBreadcrumbItem {
    const breadcrumbs = GetBreadcrumbs(path);
    return breadcrumbs[breadcrumbs.length - 1];
}