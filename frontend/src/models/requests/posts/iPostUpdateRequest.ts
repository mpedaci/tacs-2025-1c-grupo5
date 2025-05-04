import {ConservationState} from "@models/enums/ConservationState";

export interface IPostUpdateRequest {
    conservationState: ConservationState;
    images: string[];
    estimatedValue: number;
    wishedCards: string[];
}