export enum ConservationState {
    Bad,
    Regular,
    Good,
    Excellent,
    AlmostPerfect,
    Perfect,
}

export const conservationStateLabels = {
    [ConservationState.Bad]: "Mala",
    [ConservationState.Regular]: "Regular",
    [ConservationState.Good]: "Buena",
    [ConservationState.Excellent]: "Excelente",
    [ConservationState.AlmostPerfect]: "Casi Perfecta",
    [ConservationState.Perfect]: "Perfecta",
};