import {ConservationState} from "@models/enums/ConservationState";

export interface IPostUpdateStateRequest {
    conservationState: ConservationState;
}