"use client";
import MainCard from "@components/Cards/MainCard";
import OffersTab from "@components/Pages/offers/OffersTab";
import OfferCreateEditModal from "@components/Pages/offers/OfferCreateEditModal";
import {useGetPostByIdQuery} from "@redux/services/postsApi";
import {useAppSelector} from "@redux/hook";
import {PostState} from "@models/enums/PostState";

export default function DashboardPostOffersPage({
                                                    params,
                                                }: {
    params: {
        postId: string;
    }
}) {
    const user = useAppSelector((state) => state.user);
    const {data: postData} = useGetPostByIdQuery({id: params.postId});

    const secondary = (postData && postData.status === PostState.Published && postData.user.id !== user.id) ? (
        <OfferCreateEditModal
            postId={params.postId}
            editMode={false}
        />
    ) : null;

    return (
        <MainCard
            content={false}
            title="Lista de ofertas"
            secondary={secondary}
        >
            <OffersTab
                postId={params.postId}
                postOwner={(postData && postData.status === PostState.Published && postData.user.id === user.id) ?? false}
            />
        </MainCard>
    );
}