import {ConservationState} from "@models/enums/ConservationState";

export interface IPostUpdateRequest {
    conservationStatus: ConservationState;
    images: string[];
    estimatedValue: number;
    wantedCardsIds: string[];
}