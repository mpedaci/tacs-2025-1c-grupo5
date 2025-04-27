import {ConservationState} from "@models/enums/ConservationState";

export interface IPostCreateRequest {
    userId: string;
    cardId: string;
    conservationState: ConservationState;
    images: string[];
    estimatedValue: number;
    wantedCardsIds: string[];
}