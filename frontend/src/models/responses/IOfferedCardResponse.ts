import {ICardResponse} from "@models/responses/iCardResponse";
import {ConservationState} from "@models/enums/ConservationState";

export interface IOfferedCardResponse {
    id: string;
    card: ICardResponse;
    image: string;
    conservationStatus: ConservationState;
}