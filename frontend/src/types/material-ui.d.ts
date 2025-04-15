// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {ThemeOptions} from "@mui/system/createTheme/createTheme";

declare module '@mui/material/styles' {
    interface ExtendedPalette {
        primary: {
            lighter: string;
            100: string;
            200: string;
            light: string;
            400: string;
            main: string;
            dark: string;
            700: string;
            darker: string;
            900: string;
            contrastText: string;
        },
        secondary: {
            lighter: string;
            100: string;
            200: string;
            light: string;
            400: string;
            main: string;
            600: string;
            dark: string;
            800: string;
            darker: string;
            A100: string;
            A200: string;
            A300: string;
            contrastText: string;
        },
        error: {
            lighter: string;
            light: string;
            main: string;
            dark: string;
            darker: string;
            contrastText: string;
        },
        warning: {
            lighter: string;
            light: string;
            main: string;
            dark: string;
            darker: string;
            contrastText: string;
        },
        info: {
            lighter: string;
            light: string;
            main: string;
            dark: string;
            darker: string;
            contrastText: string;
        },
        success: {
            lighter: string;
            light: string;
            main: string;
            dark: string;
            darker: string;
            contrastText: string;
        },
        grey: {
            0: string;
            50: string;
            100: string;
            200: string;
            300: string;
            400: string;
            500: string;
            600: string;
            700: string;
            800: string;
            900: string;
            A50: string;
            A100: string;
            A200: string;
            A400: string;
            A700: string;
            A800: string;
        }
    }

    interface ExtendedPaletteOptions {
        primary: {
            lighter?: string;
            100?: string;
            200?: string;
            light?: string;
            400?: string;
            main?: string;
            dark?: string;
            700?: string;
            darker?: string;
            900?: string;
            contrastText?: string;
        },
        secondary: {
            lighter?: string;
            100?: string;
            200?: string;
            light?: string;
            400?: string;
            main?: string;
            600?: string;
            dark?: string;
            800?: string;
            darker?: string;
            A100?: string;
            A200?: string;
            A300?: string;
            contrastText?: string;
        },
        error: {
            lighter?: string;
            light?: string;
            main?: string;
            dark?: string;
            darker?: string;
            contrastText?: string;
        },
        warning: {
            lighter?: string;
            light?: string;
            main?: string;
            dark?: string;
            darker?: string;
            contrastText?: string;
        },
        info: {
            lighter?: string;
            light?: string;
            main?: string;
            dark?: string;
            darker?: string;
            contrastText?: string;
        },
        success: {
            lighter?: string;
            light?: string;
            main?: string;
            dark?: string;
            darker?: string;
            contrastText?: string;
        },
        grey: {
            0?: string;
            50?: string;
            100?: string;
            200?: string;
            300?: string;
            400?: string;
            500?: string;
            600?: string;
            700?: string;
            800?: string;
            900?: string;
            A50?: string;
            A100?: string;
            A200?: string;
            A400?: string;
            A700?: string;
            A800?: string;
        }
    }

    interface Theme {
        palette: ExtendedPalette & Palette;
        customShadows: {
            button: string;
            text: string;
            z1: string;
            primary: string;
            secondary: string;
            error: string;
            warning: string;
            info: string;
            success: string;
            grey: string;
            primaryButton: string;
            secondaryButton: string;
            errorButton: string;
            warningButton: string;
            infoButton: string;
            successButton: string;
            greyButton: string;
        }
    }

    interface ThemeOptions {
        palette?: ExtendedPaletteOptions & PaletteOptions;
        customShadows?: {
            button?: string;
            text?: string;
            z1?: string;
            primary?: string;
            secondary?: string;
            error?: string;
            warning?: string;
            info?: string;
            success?: string;
            grey?: string;
            primaryButton?: string;
            secondaryButton?: string;
            errorButton?: string;
            warningButton?: string;
            infoButton?: string;
            successButton?: string;
            greyButton?: string;
        }
    }
}