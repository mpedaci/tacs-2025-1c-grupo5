import {IOfferedCardResponse} from "@models/responses/IOfferedCardResponse";
import {OfferState} from "@models/enums/OfferState";
import {IUserResponse} from "@models/responses/iUserResponse";

export interface IOfferResponse {
    id: string;
    offeror: IUserResponse;
    cards: IOfferedCardResponse[];
    money: number;
    state: OfferState;
    publishedAt: Date;
    finishedAt: Date;
}