import MainCard from "@components/Cards/MainCard";
import OffersTab from "@components/Pages/offers/OffersTab";
import OfferCreateEditModal from "@components/Pages/offers/OfferCreateEditModal";

export default function DashboardPostOffersPage({
                                                    params,
                                                }: {
    params: {
        postId: string;
    }
}) {
    return (
        <MainCard
            content={false}
            title="Lista de ofertas"
            secondary={
                <OfferCreateEditModal
                    postId={params.postId}
                    editMode={false}
                />
            }
        >
            <OffersTab
                postId={params.postId}
            />
        </MainCard>
    );
}