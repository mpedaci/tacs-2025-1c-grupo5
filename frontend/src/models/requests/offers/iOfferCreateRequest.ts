import {ConservationState} from "@models/enums/ConservationState";

export interface IOfferCreateRequest {
    offeredCards: IOfferedCardRequest[];
    money: number;
    postId: string;
    offererId: string;
}

export interface IOfferedCardRequest {
    cardId: string;
    image: string;
    state: ConservationState;
}