import {ConservationState} from "@models/enums/ConservationState";

export interface IOfferCreateRequest {
    cards: IOfferedCardRequest[];
    money: number;
}

export interface IOfferedCardRequest {
    cardId: string;
    image: string;
    state: ConservationState;
}