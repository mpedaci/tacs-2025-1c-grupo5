export const loginRoute = () => {
    return '/login';
}

export const registerRoute = () => {
    return '/login/register';
}

export const reportsRoute = () => {
    return '/dashboard/reports';
}

export const postsRoute = () => {
    return '/dashboard/posts';
}

export const postOffersRoute = (id: string) => {
    return `/dashboard/posts/${id}/offers`;
}