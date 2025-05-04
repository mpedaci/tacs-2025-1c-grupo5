"use client";
import React, { useState, useCallback, useEffect } from "react";
import { Controller, Control } from "react-hook-form";
import {
    Autocomplete,
    Box,
    TextField,
    Popover,
    Typography,
    CircularProgress,
    AutocompleteRenderInputParams
} from "@mui/material";
import { ICardResponse } from "@models/responses/iCardResponse";
import { useLazyGetCardsQuery } from "@redux/services/cardsApi";

export type CardOption = {
    id: string;
    label: string;
    image: string;
};

interface CardSelectElementProps {
    gameId: string;
    name: string;
    label: string;
    control: Control<any>;
    multiple?: boolean;
    required?: boolean;
    rules?: Record<string, any>;
}

export default function CardSelectElementController({
                                                        gameId,
                                                        name,
                                                        label,
                                                        control,
                                                        multiple = false,
                                                        required = false,
                                                        rules = {},
                                                    }: CardSelectElementProps) {
    const [getCards, { isLoading: loadingCards, isFetching }] = useLazyGetCardsQuery();
    const [allCards, setAllCards] = useState<ICardResponse[]>([]);
    const [visibleOptions, setVisibleOptions] = useState<CardOption[]>([]);
    const [searchQuery, setSearchQuery] = useState('');
    const BATCH_SIZE = 15;

    const [previewCard, setPreviewCard] = useState<CardOption | null>(null);

    const mapToOptions = useCallback((cards: ICardResponse[]): CardOption[] => {
        return cards.map((card) => ({
            id: card.id,
            label: card.name,
            image: card.imageUrl,
        }));
    }, []);

    const loadInitialCards = useCallback(async (query: string = '') => {
        if (!query) {
            setAllCards([]);
            setVisibleOptions([]);
            return;
        }
        try {
            const fetched = await getCards({ gameId: gameId, name: query }).unwrap();
            setAllCards(fetched);
            const options = mapToOptions(fetched);
            setVisibleOptions(options.slice(0, BATCH_SIZE));
            setSearchQuery(query);
        } catch {
        }
    }, [gameId, getCards, mapToOptions]);

    useEffect(() => {
    }, [loadInitialCards]);

    const loadMore = useCallback(() => {
        const currentVisibleCount = visibleOptions.length;
        const totalAvailableCount = allCards.length;

        if (currentVisibleCount < totalAvailableCount) {
            const nextBatchSize = Math.min(BATCH_SIZE, totalAvailableCount - currentVisibleCount);
            const nextOptions = mapToOptions(allCards.slice(0, currentVisibleCount + nextBatchSize));
            setVisibleOptions(nextOptions);
        }
    }, [visibleOptions.length, allCards, mapToOptions, BATCH_SIZE]);

    return (
        <>
            <Controller
                name={name}
                control={control}
                rules={{
                    required: required ? "Este campo es obligatorio" : false,
                    validate: (value) => {
                        if (multiple && required && (!value || value.length === 0)) {
                            return "Por favor seleccione al menos una carta";
                        }
                        return true;
                    },
                    ...rules
                }}
                render={({ field, fieldState }) => (
                    <Autocomplete
                        multiple={multiple}
                        options={visibleOptions}
                        loading={loadingCards || isFetching}
                        getOptionLabel={(option) => (typeof option === 'string' ? option : option.label)}
                        isOptionEqualToValue={(option, value) => option.id === value.id}
                        filterOptions={(x) => x}
                        autoComplete
                        includeInputInList
                        value={field.value ?? (multiple ? [] : null)}
                        onChange={(event, newValue) => {
                            field.onChange(newValue);
                            setPreviewCard(null);
                        }}
                        onBlur={field.onBlur}
                        onInputChange={(event, newInputValue, reason) => {
                            if (reason === 'input') {
                                loadInitialCards(newInputValue);
                            } else if (reason === 'clear') {
                                loadInitialCards('');
                            }
                        }}
                        renderOption={(props, option: CardOption) => {
                            return (
                                <Box
                                    component="li"
                                    sx={{
                                        display: "flex",
                                        alignItems: "center",
                                        justifyContent: "space-between",
                                        width: "100%",
                                    }}
                                    {...props}
                                    onMouseEnter={(e) => {
                                        e.stopPropagation();
                                        setTimeout(() => setPreviewCard(option), 100);
                                    }}
                                    onMouseLeave={(e) => {
                                        e.stopPropagation();
                                        setPreviewCard(null);
                                    }}
                                    key={option.id}
                                >
                                    <Box sx={{ display: "flex", alignItems: "center" }}>
                                        <Box
                                            component="img"
                                            src={option.image}
                                            alt={option.label}
                                            sx={{
                                                width: "auto",
                                                height: 40,
                                                objectFit: "contain",
                                                borderRadius: 1,
                                                marginRight: 1.5,
                                            }}
                                        />
                                        <Typography variant="body2">{option.label}</Typography>
                                    </Box>
                                </Box>
                            );
                        }}
                        renderTags={multiple ? (value: readonly CardOption[], getTagProps) =>
                                value.map((option: CardOption, index: number) => (
                                    <Box
                                        key={`${option.id}-${index}`}
                                        sx={{ display: 'flex', alignItems: 'center', mr: 0.5, mb: 0.5, p: 0.5, border: '1px solid lightgray', borderRadius: '4px' }}
                                    >
                                        <Box
                                            component="img"
                                            src={option.image}
                                            alt={option.label}
                                            sx={{ width: "auto", height: 20, objectFit: "contain", mr: 0.5 }}
                                        />
                                        <Typography variant="caption">{option.label}</Typography>
                                    </Box>
                                ))
                            : undefined}
                        renderInput={(params: AutocompleteRenderInputParams) => (
                            <TextField
                                {...params}
                                label={label}
                                required={required}
                                error={!!fieldState.error}
                                helperText={fieldState.error?.message}
                                inputRef={field.ref}
                                InputProps={{
                                    ...params.InputProps,
                                    endAdornment: (
                                        <>
                                            {loadingCards || isFetching ? <CircularProgress color="inherit" size={20} /> : null}
                                            {params.InputProps.endAdornment}
                                        </>
                                    ),
                                }}
                            />
                        )}
                        ListboxProps={{
                            onScroll: (event: React.SyntheticEvent) => {
                                const listboxNode = event.currentTarget;
                                const isNearBottom = listboxNode.scrollHeight - listboxNode.scrollTop <= listboxNode.clientHeight + 100; // Umbral ajustable

                                if (isNearBottom && !loadingCards && !isFetching && visibleOptions.length < allCards.length) {
                                    loadMore();
                                }
                            },
                        }}
                        fullWidth
                    />
                )}
            />

            <Popover
                open={Boolean(previewCard)}
                anchorReference="anchorPosition"
                anchorPosition={previewCard ? { top: window.innerHeight / 2, left: window.innerWidth / 2 } : undefined}
                onClose={() => setPreviewCard(null)}
                anchorOrigin={{
                    vertical: "center",
                    horizontal: "center",
                }}
                transformOrigin={{
                    vertical: "center",
                    horizontal: "center",
                }}
                disableRestoreFocus
                sx={{
                    pointerEvents: "none",
                    zIndex: (theme) => theme.zIndex.tooltip + 1,
                }}
                disableAutoFocus
                disableEnforceFocus
            >
                {previewCard && (
                    <Box sx={{ padding: 1, border: '1px solid #ccc', background: 'white' }}>
                        <Typography variant="subtitle2" sx={{ mb: 1 }}>{previewCard.label}</Typography>
                        <Box
                            component="img"
                            src={previewCard.image}
                            alt={previewCard.label}
                            sx={{
                                display: 'block',
                                width: 200,
                                height: "auto",
                                objectFit: "contain",
                            }}
                        />
                    </Box>
                )}
            </Popover>
        </>
    );
}