package utn.tacs.grupo5.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.card.CardInputDto;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.mapper.CardMapper;
import utn.tacs.grupo5.repository.CardRepository;
import utn.tacs.grupo5.service.ICardService;

@Service
public class CardService implements ICardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public CardService(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }

    @Override
    public Optional<Card> get(Long id) {
        return cardRepository.findById(id);
    }

    @Override
    public Card save(CardInputDto dto) {
        Card card = cardMapper.toEntity(dto);
        return cardRepository.save(card);
    }

    @Override
    public Card update(Long id, CardInputDto dto) {
        cardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Card not found"));
        Card card = cardMapper.toEntity(dto);
        card.setId(id);
        return cardRepository.save(card);
    }

    @Override
    public void delete(Long id) {
        cardRepository.deleteById(id);
    }

    @Override
    public List<Card> getAllCardsByGameId(Long gameId) {
        return cardRepository.findByGameId(gameId);
    }
}
