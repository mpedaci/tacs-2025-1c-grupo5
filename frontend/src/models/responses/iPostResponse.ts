import {IUserResponse} from "@models/responses/iUserResponse";
import {ConservationState} from "@models/enums/ConservationState";
import {ICardResponse} from "@models/responses/iCardResponse";
import {PostState} from "@models/enums/PostState";

export interface IPostResponse {
    id: string;
    user: IUserResponse;
    card: ICardResponse;
    conservationState: ConservationState;
    images: string[];
    estimatedValue: number;
    wishedCards: ICardResponse[];
    state: PostState;
    publishedAt: Date;
    endAt: Date;
}