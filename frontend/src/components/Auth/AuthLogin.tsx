import React, {useState} from "react";
import {
    Button,
    Grid2 as Grid,
    InputAdornment,
    InputLabel,
    OutlinedInput,
    Stack,
} from "@mui/material";
import IconButton from "@mui/material/IconButton"
import {useNotification} from "@components/Notifications/NotificationContext";
import {useLoginMutation} from "@redux/services/authApi";
import {parseJwt} from "@utils/decode_jwt";
import {setUser, User} from "@redux/features/userSlice";
import {postsRoute} from "@routes/router";
import {useDispatch} from "react-redux";
import {useRouter} from "next/navigation";

// ============================|| JWT - LOGIN ||============================ //

const AuthLogin = () => {
    const [password, setPassword] = useState("");
    const [username, setUsername] = useState("");
    const [showPassword, setShowPassword] = useState(false);
    const {addNotification} = useNotification();
    const [
        login,
        {isLoading: loginIsLoading}
    ] = useLoginMutation();
    const dispatch = useDispatch();
    const router = useRouter();

    const handleClickShowPassword = () => {
        setShowPassword(!showPassword);
    };

    const handleLogin = async (response: {
        userExists: boolean;
        token: string;
    }) => {
        const userExists: boolean = response.userExists;
        if (userExists) {
            const tokenCookie = response.token;
            const jsonRes = parseJwt(tokenCookie);
            const user: User = {
                id: jsonRes.id ?? "",
                name: jsonRes.name ?? "",
                isAdmin: jsonRes.admin ?? false,
            }
            dispatch(setUser(user));
            router.push(postsRoute());
        } else {
            addNotification("Usuario o contraseña incorrectos", "error");
        }
    }

    const handleSubmit = async () => {
        if (!username || !password) {
            addNotification("Por favor, ingrese un usuario y contraseña", "error");
            return;
        }
        try {
            // const response = await login({username, password}).unwrap();
            // handleLogin(response);
            router.push(postsRoute());
        } catch {
            addNotification("Error al iniciar sesión", "error");
        }
    }

    return (
        <Grid container spacing={3}>
            <Grid size={12}>
                <Stack spacing={1}>
                    <InputLabel htmlFor="username-login">Usuario</InputLabel>
                    <OutlinedInput
                        id="username-login"
                        type="text"
                        value={username}
                        name="username"
                        onChange={(e) => setUsername(e.target.value)}
                        placeholder="Ingrese su usuario"
                        fullWidth
                    />
                </Stack>
            </Grid>
            <Grid size={12}>
                <Stack spacing={1}>
                    <InputLabel htmlFor="password-login">Contraseña</InputLabel>
                    <OutlinedInput
                        fullWidth
                        id="-password-login"
                        type={showPassword ? "text" : "password"}
                        value={password}
                        name="password"
                        onChange={(e) => setPassword(e.target.value)}
                        endAdornment={
                            <InputAdornment position="end">
                                <IconButton
                                    aria-label="Invertir visibilidad de la contraseña"
                                    onClick={handleClickShowPassword}
                                    edge="end"
                                    color="secondary"
                                >
                                    {showPassword ? (
                                        <i className="fa-duotone fa-solid fa-eye-slash"/>
                                    ) : (
                                        <i className="fa-duotone fa-solid fa-eye"/>
                                    )}
                                </IconButton>
                            </InputAdornment>
                        }
                        placeholder="Ingrese su contraseña"
                    />
                </Stack>
            </Grid>
            <Grid size={12}>
                <Button
                    disableElevation
                    disabled={loginIsLoading}
                    fullWidth
                    type="submit"
                    variant="contained"
                    color="primary"
                    onClick={handleSubmit}
                >
                    Ingresar
                </Button>
            </Grid>
        </Grid>
    );
};

export default AuthLogin;
