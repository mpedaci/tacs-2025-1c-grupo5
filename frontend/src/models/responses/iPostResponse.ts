import {IUserResponse} from "@models/responses/iUserResponse";
import {ConservationState} from "@models/enums/ConservationState";
import {ICardResponse} from "@models/responses/iCardResponse";
import {PostState} from "@models/enums/PostState";

export interface IPostResponse {
    id: string;
    user: IUserResponse;
    card: ICardResponse;
    conservationStatus: ConservationState;
    images: string[];
    estimatedValue: number;
    wantedCards: ICardResponse[];
    status: PostState;
    publishedAt: string;
    finishedAt: string;
}