import {createSlice, PayloadAction} from '@reduxjs/toolkit';

export interface User {
    id: string;
    name: string;
    isAdmin: boolean;
    token: string;
}

const initialState: User = {
    id: '00000000-0000-0000-0000-000000000000',
    name: '',
    isAdmin: false,
    token: '',
};

const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        setUser: (state, action: PayloadAction<User>) => {
            state.id = action.payload.id;
            state.name = action.payload.name;
            state.isAdmin = action.payload.isAdmin;
            state.token = action.payload.token;
        },
        clearUser: (state) => {
            state.id = '';
            state.name = '';
            state.isAdmin = false;
            state.token = '';
        },
        setUserName: (state, action: PayloadAction<string>) => {
            state.name = action.payload;
        },
    },
});

export const {
    setUser, clearUser, setUserName
} = userSlice.actions;
export default userSlice.reducer;
