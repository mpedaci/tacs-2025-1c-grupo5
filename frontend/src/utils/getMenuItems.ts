import {
    postsRoute, reportsRoute
} from "@routes/router";

const items: IMenuItem[] = [
    {
        id: "publicaciones",
        title: "Publicaciones",
        type: "item",
        url: postsRoute(),
        icon: "fa-duotone fa-solid fa-file-lines",
        public: true,
        disabled: false
    },
    {
        id: "reportes",
        title: "Reportes",
        type: "item",
        url: reportsRoute(),
        icon: "fa-duotone fa-solid fa-chart-simple",
        public: false,
        disabled: false
    }
]

export interface IMenuItem {
    id: string,
    title: string,
    type: string,
    url?: string,
    icon?: string,
    children?: IMenuItem[],
    caption?: string,
    disabled?: boolean,
    chip?: {
        color?: 'default' | 'primary' | 'secondary' | 'error' | 'info' | 'success' | 'warning',
        variant?: 'filled' | 'outlined',
        size?: 'small' | 'medium',
        label?: string,
    },
    public: boolean,
}

export function GetMenuItems() {
    const menuItem: IMenuItem = {
        id: "menu-id",
        title: "Menu",
        type: "group",
        children: items,
        public: true,
    }

    return [menuItem];
}