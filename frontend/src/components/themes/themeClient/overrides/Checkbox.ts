// project import
import getColors from '@utils/getColors';
import {Theme} from '@mui/material/styles';

// ==============================|| RADIO - COLORS ||============================== //

function getColorStyle({color, theme}: { color: string; theme: Theme }) {
    const colors = getColors(theme, color);
    const {lighter, main, dark} = colors;

    return {
        '&:hover': {
            backgroundColor: lighter,
            '& .icon': {
                borderColor: main
            }
        },
        '&.Mui-focusVisible': {
            outline: `2px solid ${dark}`,
            outlineOffset: -4
        }
    };
}

// ==============================|| CHECKBOX - SIZE STYLE ||============================== //

function getSizeStyle(size: string) {
    switch (size) {
        case 'small':
            return {size: 16, fontSize: 1, position: 1};
        case 'large':
            return {size: 24, fontSize: 1.6, position: 2};
        case 'medium':
        default:
            return {size: 20, fontSize: 1.35, position: 2};
    }
}

// ==============================|| CHECKBOX - STYLE ||============================== //

function checkboxStyle(size: string) {
    const sizes = getSizeStyle(size);

    return {
        '& .icon': {
            width: sizes.size,
            height: sizes.size,
            '& .filled': {
                fontSize: `${sizes.fontSize}rem`,
                top: -sizes.position,
                left: -sizes.position
            }
        }
    };
}

// ==============================|| OVERRIDES - CHECKBOX ||============================== //

export default function Checkbox(theme: Theme) {
    const {palette} = theme;

    return {
        MuiCheckbox: {
            styleOverrides: {
                root: {
                    borderRadius: 0,
                    color: palette.secondary[300],
                    '&.size-small': {
                        ...checkboxStyle('small')
                    },
                    '&.size-medium': {
                        ...checkboxStyle('medium')
                    },
                    '&.size-large': {
                        ...checkboxStyle('large')
                    }
                },
                colorPrimary: getColorStyle({color: 'primary', theme}),
                colorSecondary: getColorStyle({color: 'secondary', theme}),
                colorSuccess: getColorStyle({color: 'success', theme}),
                colorWarning: getColorStyle({color: 'warning', theme}),
                colorInfo: getColorStyle({color: 'info', theme}),
                colorError: getColorStyle({color: 'error', theme})
            }
        }
    };
}
