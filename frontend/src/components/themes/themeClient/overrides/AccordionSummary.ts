// assets
import {Theme} from '@mui/material/styles';

// ==============================|| OVERRIDES - ALERT TITLE ||============================== //

export default function AccordionSummary(theme: Theme) {
  const { spacing } = theme;

  return {
    MuiAccordionSummary: {
      styleOverrides: {
        expandIconWrapper: {
          '&.Mui-expanded': {
            transform: 'rotate(90deg)'
          }
        },
        content: {
          marginTop: spacing(1.25),
          marginBottom: spacing(1.25),
          marginLeft: spacing(1)
        }
      }
    }
  };
}
