import {
    Backdrop,
    Fade,
    Modal
} from "@mui/material";
import React from "react";

export default function AnimatedModal(
    {
        open,
        onClose,
        children,
    }: {
        open: boolean,
        onClose: () => void,
        children: React.ReactNode,
    }
) {
    return (
        <Modal
            open={open}
            onClose={onClose}
            closeAfterTransition
            slots={{
                backdrop: Backdrop
            }}
            slotProps={{
                backdrop: {
                    timeout: 500
                }
            }}
        >
            <Fade in={open}>
                <div>
                    {children}
                </div>
            </Fade>
        </Modal>
    )
}