import {jwtDecode} from 'jwt-decode'

export interface DecodedUser {
    aud?: string;

    id?: string;
    name?: string;
    admin?: boolean;
}

export const parseJwt = (token: string): DecodedUser => {
    try {
        return jwtDecode(token);
    } catch {
        return {} as DecodedUser;
    }
}