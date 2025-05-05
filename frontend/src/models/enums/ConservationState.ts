export enum ConservationState {
    Bad = "BAD",
    Regular = "REGULAR",
    Good = "GOOD",
    Excellent = "EXCELLENT",
    AlmostPerfect = "ALMOST_PERFECT",
    Perfect = "PERFECT",
}

export const conservationStateLabels = {
    [ConservationState.Bad]: "Mala",
    [ConservationState.Regular]: "Regular",
    [ConservationState.Good]: "Buena",
    [ConservationState.Excellent]: "Excelente",
    [ConservationState.AlmostPerfect]: "Casi Perfecta",
    [ConservationState.Perfect]: "Perfecta",
};