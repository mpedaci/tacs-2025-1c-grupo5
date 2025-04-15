export enum PostState {
    Published,
    Finished,
    Cancelled,
}

export const postStateLabels = {
    [PostState.Published]: "Publicado",
    [PostState.Finished]: "Finalizado",
    [PostState.Cancelled]: "Cancelado",
};