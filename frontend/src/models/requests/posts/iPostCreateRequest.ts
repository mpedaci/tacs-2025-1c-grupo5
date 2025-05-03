import {ConservationState} from "@models/enums/ConservationState";

export interface IPostCreateRequest {
    userId: string;
    cardId: string;
    conservationStatus: ConservationState;
    images: string[];
    estimatedValue: number;
    wantedCardsIds: string[];
    description: string;
}