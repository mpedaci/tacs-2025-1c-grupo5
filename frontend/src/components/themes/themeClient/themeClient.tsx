"use client";
import {useAppSelector} from "@/redux/hook";
import {createTheme, ThemeProvider} from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import React, {useMemo} from "react";
import {StyledEngineProvider} from "@mui/material";

import Palette from './palette';
import Typography from './typography';
import CustomShadows from './shadows';
import componentsOverride from './overrides';

const ThemeClient: React.FC<{ children: React.ReactNode }> = ({children}) => {
    const themeMode = useAppSelector((state) => state.theme.mode);
    const fontFamily = useAppSelector((state) => state.theme.fontFamily);

    const font = useMemo(() => {
        switch (fontFamily) {
            case 'Inter':
                return "var(--font-inter)";
            case 'Poppins':
                return "var(--font-poppins)";
            case 'Public Sans':
                return "var(--font-public-sans)";
            case 'Roboto':
                return "var(--font-roboto)";
            default:
                return "var(--font-inter)";
        }
    }, [fontFamily]);
    const theme = useMemo(() => Palette(themeMode), [themeMode]);
    const themeTypography = useMemo(() => Typography(font), [font]);
    const themeCustomShadows = useMemo(() => CustomShadows(theme), [theme]);

    const themeOptions = useMemo(
        () => ({
            breakpoints: {
                values: {
                    xs: 0,
                    sm: 768,
                    md: 1024,
                    lg: 1266,
                    xl: 1440
                }
            },
            mixins: {
                toolbar: {
                    minHeight: 60,
                    paddingTop: 8,
                    paddingBottom: 8
                }
            },
            palette: theme.palette,
            customShadows: themeCustomShadows,
            typography: themeTypography
        }),
        [theme, themeTypography, themeCustomShadows]
    );

    const themes = createTheme(themeOptions);
    
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-expect-error
    themes.components = componentsOverride(themes);

    return (
        <StyledEngineProvider injectFirst>
            <ThemeProvider theme={themes}>
                <CssBaseline/>
                {children}
            </ThemeProvider>
        </StyledEngineProvider>
    );
};

export default ThemeClient;
