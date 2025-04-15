// ==============================|| OVERRIDES - LIST ITEM ICON ||============================== //

import {Theme} from '@mui/material/styles';

export default function ListItemButton(theme:Theme) {
  return {
    MuiListItemButton: {
      styleOverrides: {
        root: {
          '&.Mui-selected': {
            color: theme.palette.primary.main,
            '& .MuiListItemIcon-root': {
              color: theme.palette.primary.main
            }
          },
        }
      }
    }
  };
}
