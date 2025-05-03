export enum OfferState {
    Pending = "PENDING",
    Accepted = "ACCEPTED",
    Rejected = "REJECTED",
}

export const offerStateLabels = {
    [OfferState.Pending]: "Pendiente",
    [OfferState.Accepted]: "Aceptada",
    [OfferState.Rejected]: "Rechazada",
};