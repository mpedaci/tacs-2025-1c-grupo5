import {IPostResponse} from "@models/responses/iPostResponse";
import {ConservationState} from "@models/enums/ConservationState";
import {PostState} from "@models/enums/PostState";

export const postsMockData: IPostResponse[] = [
    {
        id: "00000000-0000-0000-0000-000000000001",
        card: {
            id: "00000000-0000-0000-0000-000000000001",
            game: {
                id: "00000000-0000-0000-0000-000000000001",
                name: "Magic: The Gathering 1",
            },
            name: "Ancestor's Chosen",
            externalId: "card1",
            image: "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=130550&type=card"
        },
        user: {
            id: "00000000-0000-0000-0000-000000000000",
            name: "John Doe",
            username: "johndoe",
        },
        conservationState: ConservationState.Excellent,
        images: [
            "https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=150317&type=card",
            "https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=148028&type=card"
        ],
        estimatedValue: 100,
        wishedCards: [
            {
                id: "00000000-0000-0000-0000-000000000001",
                game: {
                    id: "00000000-0000-0000-0000-000000000001",
                    name: "Magic: The Gathering",
                },
                name: "Ancestor's Chosen",
                externalId: "card1",
                image: "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=130550&type=card"
            }
        ],
        state: PostState.Published,
        publishedAt: new Date("2023-10-01T00:00:00Z"),
        endAt: new Date("2023-10-31T00:00:00Z")
    },
    {
        id: "00000000-0000-0000-0000-000000000001",
        user: {
            id: "00000000-0000-0000-0000-000000000001",
            name: "John Doe",
            username: "johndoe",
        },
        card: {
            id: "00000000-0000-0000-0000-000000000001",
            game: {
                id: "00000000-0000-0000-0000-000000000001",
                name: "Magic: The Gathering",
            },
            name: "Ancestor's Chosen 2",
            externalId: "card1",
            image: "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=130550&type=card"
        },
        conservationState: ConservationState.Excellent,
        images: [
            "https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=150317&type=card",
            "https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=148028&type=card"
        ],
        estimatedValue: 100,
        wishedCards: [
            {
                id: "00000000-0000-0000-0000-000000000001",
                game: {
                    id: "00000000-0000-0000-0000-000000000001",
                    name: "Magic: The Gathering",
                },
                name: "Ancestor's Chosen 2",
                externalId: "card1",
                image: "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=130550&type=card"
            }
        ],
        state: PostState.Published,
        publishedAt: new Date("2023-10-01T00:00:00Z"),
        endAt: new Date("2023-10-31T00:00:00Z")
    },{
        id: "00000000-0000-0000-0000-000000000001",
        card: {
            id: "00000000-0000-0000-0000-000000000001",
            game: {
                id: "00000000-0000-0000-0000-000000000001",
                name: "Magic: The Gathering 1",
            },
            name: "Ancestor's Chosen",
            externalId: "card1",
            image: "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=130550&type=card"
        },
        user: {
            id: "00000000-0000-0000-0000-000000000000",
            name: "John Doe",
            username: "johndoe",
        },
        conservationState: ConservationState.Excellent,
        images: [
            "https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=150317&type=card",
            "https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=148028&type=card"
        ],
        estimatedValue: 100,
        wishedCards: [
            {
                id: "00000000-0000-0000-0000-000000000001",
                game: {
                    id: "00000000-0000-0000-0000-000000000001",
                    name: "Magic: The Gathering",
                },
                name: "Ancestor's Chosen",
                externalId: "card1",
                image: "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=130550&type=card"
            }
        ],
        state: PostState.Finished,
        publishedAt: new Date("2023-10-01T00:00:00Z"),
        endAt: new Date("2023-10-31T00:00:00Z")
    },
]