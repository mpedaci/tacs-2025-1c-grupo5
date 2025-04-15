// material-ui
import {List, ListItemButton, ListItemIcon, ListItemText} from '@mui/material';
import NextLink from "next/link";
import {loginRoute} from "@routes/router";
import {useRouter} from "next/navigation";
import {useLogoutMutation} from "@redux/services/authApi";
import {useNotification} from "@components/Notifications/NotificationContext";
import {useAppDispatch} from "@redux/hook";
import {clearUser} from "@redux/features/userSlice";

// ==============================|| HEADER PROFILE - PROFILE TAB ||============================== //

const ProfileTab = () => {
    const router = useRouter();
    const {addNotification} = useNotification();
    const [
        logout,
        {isLoading: isLogoutLoading}
    ] = useLogoutMutation();
    const dispatch = useAppDispatch();

    const handleLogout = async () => {
        try {
            await logout().unwrap();
            addNotification("Sesión cerrada correctamente", "success");
            dispatch(clearUser());
            router.push(loginRoute());
        } catch (error) {
            addNotification("Error al cerrar sesión", "error");

        }
    }

    return (
        <List component="nav" sx={{p: 0, '& .MuiListItemIcon-root': {minWidth: 32}}}>
            {/*<ListItemButton component={NextLink} href={profileRoute()}>
                <ListItemIcon>
                    <i className="fa-duotone fa-solid fa-user"></i>
                </ListItemIcon>
                <ListItemText primary="Ver Perfil"/>
            </ListItemButton>*/}
            <ListItemButton onClick={handleLogout} disabled={isLogoutLoading}>
                <ListItemIcon>
                    <i className="fa-duotone fa-solid fa-right-from-bracket"></i>
                </ListItemIcon>
                <ListItemText primary="Cerrar Sesión"/>
            </ListItemButton>
        </List>
    );
};

export default ProfileTab;
