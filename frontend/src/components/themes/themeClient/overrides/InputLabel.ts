// ==============================|| OVERRIDES - INPUT LABEL ||============================== //

import {Theme} from '@mui/material/styles';

export default function InputLabel(theme: Theme) {
    return {
        MuiInputLabel: {
            styleOverrides: {
                root: {
                    color: theme.palette.grey[600],
                    '&.Mui-disabled': {
                        color: theme.palette.text.secondary
                    },
                },
                outlined: {
                    lineHeight: '0.8em',
                    '&.MuiInputLabel-sizeSmall': {
                        lineHeight: '1em'
                    },
                    '&.MuiInputLabel-shrink': {
                        background: theme.palette.background.paper,
                        padding: '0 8px',
                        marginLeft: -6,
                        lineHeight: '1.4375em'
                    }
                }
            }
        }
    };
}
