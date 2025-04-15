// material-ui
// ==============================|| OVERRIDES - TOOLTIP ||============================== //

import {Theme} from '@mui/material/styles';

export default function Tooltip(theme:Theme) {
  return {
    MuiTooltip: {
      styleOverrides: {
        tooltip: {
          color: theme.palette.background.paper
        }
      }
    }
  };
}
