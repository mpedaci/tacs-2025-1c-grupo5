package utn.tacs.grupo5.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.offer.*;
import utn.tacs.grupo5.dto.user.UserOutputDto;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.entity.post.*;
import utn.tacs.grupo5.service.ICardService;
import utn.tacs.grupo5.service.IPostService;
import utn.tacs.grupo5.service.IUserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OfferMapperTest {

    private OfferMapper offerMapper;
    private UserMapper userMapper;
    private CardMapper cardMapper;
    private IUserService userService;
    private ICardService cardService;
    private IPostService postService;

    @BeforeEach
    void setUp() {
        userMapper = mock(UserMapper.class);
        cardMapper = mock(CardMapper.class);
        userService = mock(IUserService.class);
        cardService = mock(ICardService.class);
        postService = mock(IPostService.class);

        offerMapper = new OfferMapper(userMapper, cardMapper, userService, cardService, postService);
    }

    @Test
    void toDto_shouldMapOfferToDtoCorrectly() {
        UUID offerId = UUID.randomUUID();
        UUID postId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();

        Card card = new Card();
        card.setId(cardId);

        OfferedCard offeredCard = new OfferedCard();
        offeredCard.setCard(card);
        offeredCard.setConservationStatus(ConservationStatus.GOOD);
        offeredCard.setImage("img.png");

        User user = new User();
        user.setId(UUID.randomUUID());

        Post post = new Post();
        post.setId(postId);

        Offer offer = new Offer();
        offer.setId(offerId);
        offer.setMoney(new BigDecimal("123.45"));
        offer.setPost(post);
        offer.setStatus(Offer.Status.ACCEPTED);
        offer.setPublishedAt(LocalDateTime.now());
        offer.setUpdatedAt(LocalDateTime.now());
        offer.setFinishedAt(LocalDateTime.now());
        offer.setOfferedCards(List.of(offeredCard));
        offer.setOfferer(user);

        OfferedCardOutputDto cardOutputDto = new OfferedCardOutputDto();
        cardOutputDto.setConservationStatus(ConservationStatus.GOOD);
        cardOutputDto.setImage("img.png");

        when(cardMapper.toDto(card)).thenReturn(null); // omitimos detalles del mapeo profundo
        when(userMapper.toDto(user)).thenReturn(new UserOutputDto());

        OfferOutputDto dto = offerMapper.toDto(offer);

        assertEquals(offerId, dto.getId());
        assertEquals(postId, dto.getPostId());
        assertEquals(new BigDecimal("123.45"), dto.getMoney());
        assertEquals(1, dto.getOfferedCards().size());
        assertEquals(ConservationStatus.GOOD, dto.getOfferedCards().getFirst().getConservationStatus());
        assertEquals("img.png", dto.getOfferedCards().getFirst().getImage());
        assertEquals(Offer.Status.ACCEPTED, dto.getState());
    }

    @Test
    void toEntity_shouldMapDtoToOfferCorrectly() {
        UUID offererId = UUID.randomUUID();
        UUID postId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();

        OfferedCardInputDto offeredCardInputDto = new OfferedCardInputDto();
        offeredCardInputDto.setCardId(cardId);
        offeredCardInputDto.setConservationStatus(ConservationStatus.EXCELLENT);
        offeredCardInputDto.setImage("img.png");

        OfferInputDto dto = new OfferInputDto();
        dto.setMoney(new BigDecimal("200.00"));
        dto.setOffererId(offererId);
        dto.setPostId(postId);
        dto.setOfferedCards(List.of(offeredCardInputDto));

        Card card = new Card();
        card.setId(cardId);

        User user = new User();
        user.setId(offererId);

        Post post = new Post();
        post.setId(postId);

        when(userService.get(offererId)).thenReturn(Optional.of(user));
        when(cardService.get(cardId)).thenReturn(Optional.of(card));
        when(postService.get(postId)).thenReturn(Optional.of(post));

        Offer offer = offerMapper.toEntity(dto);

        assertEquals(new BigDecimal("200.00"), offer.getMoney());
        assertEquals(offererId, offer.getOffererId());
        assertEquals(postId, offer.getPostId());
        assertEquals(user, offer.getOfferer());
        assertEquals(post, offer.getPost());
        assertEquals(1, offer.getOfferedCards().size());
        OfferedCard offeredCard = offer.getOfferedCards().getFirst();
        assertEquals(cardId, offeredCard.getCardId());
        assertEquals(ConservationStatus.EXCELLENT, offeredCard.getConservationStatus());
        assertEquals("img.png", offeredCard.getImage());
    }

    @Test
    void toEntity_shouldThrowWhenUserNotFound() {
        UUID userId = UUID.randomUUID();

        OfferInputDto dto = new OfferInputDto();
        dto.setOffererId(userId);
        dto.setOfferedCards(List.of());

        when(userService.get(userId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            offerMapper.toEntity(dto);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void toEntity_shouldThrowWhenPostNotFound() {
        UUID userId = UUID.randomUUID();
        UUID postId = UUID.randomUUID();

        OfferInputDto dto = new OfferInputDto();
        dto.setOffererId(userId);
        dto.setPostId(postId);
        dto.setOfferedCards(List.of());

        when(userService.get(userId)).thenReturn(Optional.of(new User()));
        when(postService.get(postId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            offerMapper.toEntity(dto);
        });

        assertEquals("Post not found", exception.getMessage());
    }

    @Test
    void toEntity_shouldThrowWhenCardNotFound() {
        UUID userId = UUID.randomUUID();
        UUID postId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();

        OfferedCardInputDto cardDto = new OfferedCardInputDto();
        cardDto.setCardId(cardId);
        cardDto.setConservationStatus(ConservationStatus.BAD);
        cardDto.setImage("img.png");

        OfferInputDto dto = new OfferInputDto();
        dto.setOffererId(userId);
        dto.setPostId(postId);
        dto.setOfferedCards(List.of(cardDto));

        when(userService.get(userId)).thenReturn(Optional.of(new User()));
        when(postService.get(postId)).thenReturn(Optional.of(new Post()));
        when(cardService.get(cardId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            offerMapper.toEntity(dto);
        });

        assertEquals("Card not found", exception.getMessage());
    }
}
