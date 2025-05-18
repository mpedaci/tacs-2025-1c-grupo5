export enum PostState {
    Published = "PUBLISHED",
    Finished = "FINISHED",
    Cancelled = "CANCELLED",
}

export const postStateLabels = {
    [PostState.Published]: "Publicado",
    [PostState.Finished]: "Finalizado",
    [PostState.Cancelled]: "Cancelado",
};