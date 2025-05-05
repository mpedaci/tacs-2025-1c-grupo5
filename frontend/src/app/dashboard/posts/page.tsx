import MainCard from "@/components/Cards/MainCard";
import PostsTabs from "@components/Pages/posts/PostsTabs";
import PostCreateEditModal from "@components/Pages/posts/PostCreateEditModal";

export default function DashboardPostsPage() {
    return (
        <MainCard
            content={false}
            title="Lista de publicaciones"
            secondary={
                <PostCreateEditModal
                    editMode={false}
                />
            }
        >
            <PostsTabs/>
        </MainCard>
    );
}