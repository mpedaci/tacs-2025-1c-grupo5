import {combineReducers, configureStore} from "@reduxjs/toolkit";
import {persistStore, persistReducer} from 'redux-persist';
import storageEngine from './storageEngine';

import themeSlice from "@redux/features/themeSlice";
import customizationSlice from "@redux/features/customizationSlice";
import userSlice from "@redux/features/userSlice";
import {cardsApi} from "@redux/services/cardsApi";
import {offersApi} from "@redux/services/offersApi";
import {postsApi} from "@redux/services/postsApi";
import {usersApi} from "@redux/services/usersApi";
import {authApi} from "@redux/services/authApi";

const persistConfig = {
    key: 'tacs-2025-1c-grupo5',
    storage: storageEngine,
    whitelist: ['customization', 'theme', 'user'],
    timeout: 1000,
};

const rootReducer = combineReducers({
    // Slices
    theme: themeSlice,
    customization: customizationSlice,
    user: userSlice,

    // Services
    [authApi.reducerPath]: authApi.reducer,
    [cardsApi.reducerPath]: cardsApi.reducer,
    [offersApi.reducerPath]: offersApi.reducer,
    [postsApi.reducerPath]: postsApi.reducer,
    [usersApi.reducerPath]: usersApi.reducer,
});

const persistedReducer = persistReducer(persistConfig, rootReducer);

export const store = configureStore({
    reducer: persistedReducer,
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware({
                serializableCheck: {
                    ignoredActions: ['persist/PERSIST'],
                    ignoredPaths: ['register'],
                },
            },
        )
            .concat(
                authApi.middleware,
                cardsApi.middleware,
                offersApi.middleware,
                postsApi.middleware,
                usersApi.middleware,
            ),
});

export const persistor = persistStore(store);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
