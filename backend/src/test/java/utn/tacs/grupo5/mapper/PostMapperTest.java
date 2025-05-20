package utn.tacs.grupo5.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.card.CardOutputDto;
import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.dto.post.PostOutputDto;
import utn.tacs.grupo5.dto.user.UserOutputDto;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.entity.post.ConservationStatus;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.service.ICardService;
import utn.tacs.grupo5.service.IUserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostMapperTest {

    private PostMapper postMapper;
    private IUserService userService;
    private ICardService cardService;
    private UserMapper userMapper;
    private CardMapper cardMapper;

    @BeforeEach
    void setUp() {
        userService = mock(IUserService.class);
        cardService = mock(ICardService.class);
        userMapper = mock(UserMapper.class);
        cardMapper = mock(CardMapper.class);

        postMapper = new PostMapper(userService, userMapper, cardService, cardMapper);
    }

    @Test
    void toDto_shouldMapPostToDto() {
        UUID postId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        User user = new User();
        user.setId(userId);

        Card card = new Card();
        card.setId(cardId);

        Post post = new Post();
        post.setId(postId);
        post.setUser(user);
        post.setCard(card);
        post.setImages(List.of("img1.png", "img2.png"));
        post.setConservationStatus(ConservationStatus.EXCELLENT);
        post.setEstimatedValue(BigDecimal.valueOf(150.50));
        post.setWantedCards(List.of(card));
        post.setDescription("Test description");
        post.setStatus(Post.Status.PUBLISHED);
        post.setPublishedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setFinishedAt(LocalDateTime.now());

        when(userMapper.toDto(user)).thenReturn(new UserOutputDto());
        when(cardMapper.toDto(card)).thenReturn(new CardOutputDto());

        PostOutputDto dto = postMapper.toDto(post);

        assertEquals(postId, dto.getId());
        assertEquals(2, dto.getImages().size());
        assertEquals(ConservationStatus.EXCELLENT, dto.getConservationStatus());
        assertEquals("Test description", dto.getDescription());
        assertEquals(Post.Status.PUBLISHED, dto.getStatus());
        assertNotNull(dto.getUser());
        assertNotNull(dto.getCard());
        assertEquals(1, dto.getWantedCards().size());
    }

    @Test
    void toEntity_shouldMapDtoToPost() {
        UUID userId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();
        UUID wantedCardId = UUID.randomUUID();

        PostInputDto dto = new PostInputDto();
        dto.setUserId(userId);
        dto.setCardId(cardId);
        dto.setImages(List.of("imgA.png"));
        dto.setConservationStatus(ConservationStatus.PERFECT);
        dto.setEstimatedValue(BigDecimal.TEN);
        dto.setWantedCardsIds(List.of(wantedCardId));
        dto.setDescription("Rare card");

        User user = new User();
        user.setId(userId);

        Card card = new Card();
        card.setId(cardId);

        Card wantedCard = new Card();
        wantedCard.setId(wantedCardId);

        when(userService.get(userId)).thenReturn(Optional.of(user));
        when(cardService.get(cardId)).thenReturn(Optional.of(card));
        when(cardService.get(wantedCardId)).thenReturn(Optional.of(wantedCard));

        Post post = postMapper.toEntity(dto);

        assertEquals(userId, post.getUserId());
        assertEquals(cardId, post.getCardId());
        assertEquals(1, post.getImages().size());
        assertEquals(ConservationStatus.PERFECT, post.getConservationStatus());
        assertEquals(BigDecimal.TEN, post.getEstimatedValue());
        assertEquals(1, post.getWantedCards().size());
        assertEquals("Rare card", post.getDescription());
    }

    @Test
    void toEntity_shouldThrowWhenUserNotFound() {
        UUID userId = UUID.randomUUID();

        PostInputDto dto = new PostInputDto();
        dto.setUserId(userId);
        dto.setWantedCardsIds(List.of());
        dto.setImages(List.of());

        when(userService.get(userId)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            postMapper.toEntity(dto);
        });

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void toEntity_shouldThrowWhenCardNotFound() {
        UUID userId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();

        PostInputDto dto = new PostInputDto();
        dto.setUserId(userId);
        dto.setCardId(cardId);
        dto.setWantedCardsIds(List.of());
        dto.setImages(List.of());

        when(userService.get(userId)).thenReturn(Optional.of(new User()));
        when(cardService.get(cardId)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            postMapper.toEntity(dto);
        });

        assertEquals("Card not found", ex.getMessage());
    }

    @Test
    void toEntity_shouldThrowWhenWantedCardNotFound() {
        UUID userId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();
        UUID wantedCardId = UUID.randomUUID();

        PostInputDto dto = new PostInputDto();
        dto.setUserId(userId);
        dto.setCardId(cardId);
        dto.setWantedCardsIds(List.of(wantedCardId));
        dto.setImages(List.of());

        when(userService.get(userId)).thenReturn(Optional.of(new User()));
        when(cardService.get(cardId)).thenReturn(Optional.of(new Card()));
        when(cardService.get(wantedCardId)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            postMapper.toEntity(dto);
        });

        assertEquals("Card not found", ex.getMessage());
    }
}
