import {ConservationState} from "@models/enums/ConservationState";

export interface IOfferCreateRequest {
    money: number;
    postId: string;
    offererId: string;
    offeredCards: IOfferedCardRequest[];
}

export interface IOfferedCardRequest {
    cardId: string;
    image: string;
    conservationStatus: ConservationState;
}