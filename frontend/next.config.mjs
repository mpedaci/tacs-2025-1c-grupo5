/** @type {import('next').NextConfig} */
const nextConfig = {
    transpilePackages: ['mui-file-input'],
    webpack: (config) => {
        config.resolve.alias.canvas = false;
        return config;
    },
    output: "standalone",
    async redirects() {
        return [
            {
                source: '/',
                destination: '/login',
                permanent: true,
            },
        ]
    },
};

export default nextConfig;
