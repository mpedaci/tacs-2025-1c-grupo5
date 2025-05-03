import {styled, Theme} from "@mui/material";

const DropzoneWrapper = styled('div')(({theme}: { theme: Theme }) => ({
    outline: 'none',
    overflow: 'hidden',
    position: 'relative',
    padding: theme.spacing(5, 1),
    borderRadius: theme.shape.borderRadius,
    transition: theme.transitions.create('padding'),
    backgroundColor: theme.palette.background.paper,
    border: `1px dashed ${theme.palette.secondary.main}`,
    '&:hover': {opacity: 0.72, cursor: 'pointer'}
}));

export default DropzoneWrapper;