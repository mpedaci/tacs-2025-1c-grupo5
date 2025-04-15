// ==============================|| OVERRIDES - TABLE CELL ||============================== //

import {Theme} from '@mui/material/styles';

export default function TableFooter(theme:Theme) {
  return {
    MuiTableFooter: {
      styleOverrides: {
        root: {
          backgroundColor: theme.palette.grey[50],
          borderTop: `2px solid ${theme.palette.divider}`,
          borderBottom: `1px solid ${theme.palette.divider}`
        }
      }
    }
  };
}
