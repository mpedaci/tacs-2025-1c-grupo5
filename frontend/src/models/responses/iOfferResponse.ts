import {IOfferedCardResponse} from "@models/responses/IOfferedCardResponse";
import {OfferState} from "@models/enums/OfferState";
import {IUserResponse} from "@models/responses/iUserResponse";

export interface IOfferResponse {
    id: string;
    offerer: IUserResponse;
    offeredCards: IOfferedCardResponse[];
    money: number;
    state: OfferState;
    publishedAt: string;
    finishedAt: string;
}