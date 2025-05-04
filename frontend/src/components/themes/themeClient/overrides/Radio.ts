// project import
import getColors from '@utils/getColors';
import {Theme} from '@mui/material/styles';

// ==============================|| RADIO - COLORS ||============================== //

function getColorStyle({color, theme}: { color: string, theme: Theme }) {
    const colors = getColors(theme, color);
    const {lighter, main, dark} = colors;

    return {
        '& .dot': {
            backgroundColor: main
        },
        '&:hover': {
            backgroundColor: lighter
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
            return {size: 16, dotSize: 8, position: 3};
        case 'large':
            return {size: 24, dotSize: 12, position: 5};
        case 'medium':
        default:
            return {size: 20, dotSize: 10, position: 4};
    }
}

// ==============================|| CHECKBOX - STYLE ||============================== //

function radioStyle(size: string) {
    const sizes = getSizeStyle(size);

    return {
        '& .icon': {
            width: sizes.size,
            height: sizes.size,
            '& .dot': {
                width: sizes.dotSize,
                height: sizes.dotSize,
                top: sizes.position,
                left: sizes.position
            }
        }
    };
}

// ==============================|| OVERRIDES - CHECKBOX ||============================== //

export default function Radio(theme: Theme) {
    const {palette} = theme;

    return {
        MuiRadio: {
            defaultProps: {
                className: 'size-small',
            },
            styleOverrides: {
                root: {
                    color: palette.secondary[300],
                    '&.size-small': {
                        ...radioStyle('small')
                    },
                    '&.size-medium': {
                        ...radioStyle('medium')
                    },
                    '&.size-large': {
                        ...radioStyle('large')
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
