"use client";
// project-import
import {useAppSelector} from "@redux/hook";

const LogoMain = () => {
    const customization = useAppSelector((state) => state.customization);
    return (
        <img src={customization.logo} alt="TACS" height="85"/>
    );
};

export default LogoMain;
