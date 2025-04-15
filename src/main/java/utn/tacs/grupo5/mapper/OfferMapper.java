package utn.tacs.grupo5.mapper;

import org.springframework.stereotype.Component;

import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.dto.offer.OfferOutputDto;
import utn.tacs.grupo5.dto.offer.OfferedCardInputDto;
import utn.tacs.grupo5.dto.offer.OfferedCardOutputDto;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.entity.post.Offer;
import utn.tacs.grupo5.entity.post.OfferedCard;
import utn.tacs.grupo5.service.ICardService;
import utn.tacs.grupo5.service.IPostService;
import utn.tacs.grupo5.service.IUserService;

@Component
public class OfferMapper implements IMapper<Offer, OfferInputDto, OfferOutputDto> {

    private final UserMapper userMapper;
    private final CardMapper cardMapper;
    private final IUserService userService;
    private final ICardService cardService;
    private final IPostService postService;

    public OfferMapper(UserMapper userMapper,
            CardMapper cardMapper,
            IUserService userService,
            ICardService cardService,
            IPostService postService) {
        this.userMapper = userMapper;
        this.cardMapper = cardMapper;
        this.userService = userService;
        this.cardService = cardService;
        this.postService = postService;
    }

    @Override
    public OfferOutputDto toDto(Offer offer) {
        OfferOutputDto dto = new OfferOutputDto();
        dto.setId(offer.getId());
        dto.setMoney(offer.getMoney());
        dto.setPostId(offer.getPost().getId());
        dto.setState(offer.getStatus());
        dto.setPublishedAt(offer.getPublishedAt());
        dto.setUpdatedAt(offer.getUpdatedAt());
        dto.setFinishedAt(offer.getFinishedAt());
        dto.setOfferedCards(offer.getOfferedCards().stream()
                .map(this::toOfferedCardDto)
                .toList());
        dto.setOfferer(userMapper.toDto(offer.getOfferer()));
        return dto;
    }

    @Override
    public Offer toEntity(OfferInputDto dto) {
        Offer offer = new Offer();
        offer.setMoney(dto.getMoney());
        offer.setOfferedCards(dto.getOfferedCards().stream()
                .map(this::toOfferedCard)
                .toList());
        offer.setOfferer(userService.get(dto.getOffererId())
                .orElseThrow(() -> new NotFoundException("User not found")));
        offer.setPost(postService.get(dto.getPostId())
                .orElseThrow(() -> new NotFoundException("Post not found")));
        return offer;
    }

    private OfferedCardOutputDto toOfferedCardDto(OfferedCard offeredCard) {
        OfferedCardOutputDto dto = new OfferedCardOutputDto();
        dto.setCard(cardMapper.toDto(offeredCard.getCard()));
        dto.setConservationStatus(offeredCard.getConservationStatus());
        return dto;
    }

    private OfferedCard toOfferedCard(OfferedCardInputDto dto) {
        OfferedCard offeredCard = new OfferedCard();
        Card card = cardService.get(dto.getCardId())
                .orElseThrow(() -> new NotFoundException("Card not found"));
        offeredCard.setCard(card);
        offeredCard.setConservationStatus(dto.getConservationStatus());
        return offeredCard;
    }

}
