// ==============================|| OVERRIDES - INPUT BASE ||============================== //

import {Theme} from '@mui/material/styles';

export default function InputBase(theme: Theme) {
  return {
    MuiInputBase: {
      styleOverrides: {
        sizeSmall: {
          fontSize: '0.75rem'
        },
        input: {
          '&.Mui-disabled': {
            WebkitTextFillColor: theme.palette.text.primary
          }
        }
      }
    }
  };
}
