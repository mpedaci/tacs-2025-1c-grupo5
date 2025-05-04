package utn.tacs.grupo5.mapper;

import org.springframework.stereotype.Component;

import utn.tacs.grupo5.dto.card.CardInputDto;
import utn.tacs.grupo5.dto.card.CardOutputDto;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.service.IGameService;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;

@Component
public class CardMapper implements IMapper<Card, CardInputDto, CardOutputDto> {

    private final IGameService gameService;
    private final GameMapper gameMapper;

    public CardMapper(IGameService gameService, GameMapper gameMapper) {
        this.gameService = gameService;
        this.gameMapper = gameMapper;
    }

    @Override
    public CardOutputDto toDto(Card card) {
        CardOutputDto dto = new CardOutputDto();
        dto.setId(card.getId());
        dto.setName(card.getName());
        dto.setImageUrl(card.getImageUrl());
        dto.setExternalId(card.getExternalId());
        dto.setGame(gameMapper.toDto(card.getGame()));
        return dto;
    }

    @Override
    public Card toEntity(CardInputDto dto) {
        Card card = new Card();
        Game game = gameService.get(dto.getGameId())
                .orElseThrow(() -> new NotFoundException("Game not found"));
        card.setGame(game);
        card.setName(dto.getName());
        card.setImageUrl(dto.getImageUrl());
        card.setExternalId(dto.getExternalId());
        return card;
    }

}
