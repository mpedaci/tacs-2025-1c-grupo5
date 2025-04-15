import {IOfferResponse} from "@models/responses/iOfferResponse";
import {ConservationState} from "@models/enums/ConservationState";
import {OfferState} from "@models/enums/OfferState";

export const offersMockData: IOfferResponse[] = [
    {
        id: "00000000-0000-0000-0000-000000000001",
        offeror: {
            id: "00000000-0000-0000-0000-000000000001",
            name: "Juan Perez",
            username: "juanperez"
        },
        cards: [
            {
                id: "00000000-0000-0000-0000-000000000001",
                card: {
                    id: "00000000-0000-0000-0000-000000000001",
                    game: {
                        id: "00000000-0000-0000-0000-000000000001",
                        name: "Magic: The Gathering",
                    },
                    name: "Ancestor's Chosen",
                    externalId: "card1",
                    image: "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=130550&type=card"
                },
                image: "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=130550&type=card",
                state: ConservationState.Regular
            }
        ],
        money: 100,
        state: OfferState.Pending,
        publishedAt: new Date("2023-10-01T00:00:00Z"),
        finishedAt: new Date("2023-10-31T00:00:00Z")
    },
    {
        id: "00000000-0000-0000-0000-000000000001",
        offeror: {
            id: "00000000-0000-0000-0000-000000000001",
            name: "Juan Perez",
            username: "juanperez"
        },
        cards: [
            {
                id: "00000000-0000-0000-0000-000000000001",
                card: {
                    id: "00000000-0000-0000-0000-000000000001",
                    game: {
                        id: "00000000-0000-0000-0000-000000000001",
                        name: "Magic: The Gathering",
                    },
                    name: "Ancestor's Chosen",
                    externalId: "card1",
                    image: "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=130550&type=card"
                },
                image: "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=130550&type=card",
                state: ConservationState.Regular
            }
        ],
        money: 100,
        state: OfferState.Rejected,
        publishedAt: new Date("2023-10-01T00:00:00Z"),
        finishedAt: new Date("2023-10-31T00:00:00Z")
    },
    {
        id: "00000000-0000-0000-0000-000000000001",
        offeror: {
            id: "00000000-0000-0000-0000-000000000001",
            name: "Juan Perez",
            username: "juanperez"
        },
        cards: [
            {
                id: "00000000-0000-0000-0000-000000000001",
                card: {
                    id: "00000000-0000-0000-0000-000000000001",
                    game: {
                        id: "00000000-0000-0000-0000-000000000001",
                        name: "Magic: The Gathering",
                    },
                    name: "Ancestor's Chosen",
                    externalId: "card1",
                    image: "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=130550&type=card"
                },
                image: "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=130550&type=card",
                state: ConservationState.Regular
            }
        ],
        money: 100,
        state: OfferState.Accepted,
        publishedAt: new Date("2023-10-01T00:00:00Z"),
        finishedAt: new Date("2023-10-31T00:00:00Z")
    }
]