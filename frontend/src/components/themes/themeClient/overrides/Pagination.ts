// ==============================|| OVERRIDES - PAGINATION ||============================== //

export default function Pagination() {
  return {
    MuiPagination: {
      defaultProps: {
        shape: 'rounded' as "rounded" | "circular" | undefined
      }
    }
  };
}
