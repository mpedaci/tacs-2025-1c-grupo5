import {IGameResponse} from "@models/responses/iGameResponse";

export interface ICardResponse {
    id: string;
    game: IGameResponse;
    name: string;
    externalId: string;
    imageUrl: string;
}