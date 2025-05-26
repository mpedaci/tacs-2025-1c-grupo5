import React from "react";
import MainLayout from "@components/Layouts/MainLayout"
import AuthValidation from "@components/Validations/AuthValidation";

export default function Layout({
                                   children,
                               }: {
    children: React.ReactNode;
}) {
    return (
        <AuthValidation>
            <MainLayout>
                {children}
            </MainLayout>
        </AuthValidation>
    );
}