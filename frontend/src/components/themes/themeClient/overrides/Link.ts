// ==============================|| OVERRIDES - LINK ||============================== //

export default function Link() {
    return {
        MuiLink: {
            defaultProps: {
                underline: "hover" as "none" | "hover" | "always" | undefined
            }
        }
    };
}
