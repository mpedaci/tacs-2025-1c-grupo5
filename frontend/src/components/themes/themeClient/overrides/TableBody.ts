// ==============================|| OVERRIDES - TABLE ROW ||============================== //

import {Theme} from '@mui/material/styles';

export default function TableBody(theme:Theme) {
  const hoverStyle = {
    '&:hover': {
      backgroundColor: theme.palette.secondary.lighter
    }
  };

  return {
    MuiTableBody: {
      styleOverrides: {
        root: {
          '&.striped .MuiTableRow-root': {
            '&:nth-of-type(even)': {
              backgroundColor: theme.palette.grey[50]
            },
            ...hoverStyle
          },
          '& .MuiTableRow-root': {
            ...hoverStyle
          }
        }
      }
    }
  };
}
