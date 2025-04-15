package utn.tacs.grupo5.mapper;

import org.springframework.stereotype.Component;

import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.dto.post.PostOutputDto;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.service.ICardService;
import utn.tacs.grupo5.service.IUserService;

@Component
public class PostMapper implements IMapper<Post, PostInputDto, PostOutputDto> {

    private final IUserService userService;
    private final UserMapper userMapper;
    private final ICardService cardService;
    private final CardMapper cardMapper;

    public PostMapper(
            IUserService userService,
            UserMapper userMapper,
            ICardService cardService,
            CardMapper cardMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.cardService = cardService;
        this.cardMapper = cardMapper;
    }

    @Override
    public PostOutputDto toDto(Post post) {
        PostOutputDto dto = new PostOutputDto();
        dto.setId(post.getId());
        dto.setUser(userMapper.toDto(post.getUser()));
        dto.setImages(post.getImages());
        dto.setCard(cardMapper.toDto(post.getCard()));
        dto.setConservationStatus(post.getConservationStatus());
        dto.setEstimatedValue(post.getEstimatedValue());
        dto.setWantedCards(
                post.getWantedCards().stream()
                        .map(cardMapper::toDto)
                        .toList());
        dto.setDescription(post.getDescription());
        dto.setStatus(post.getStatus());
        dto.setPublishedAt(post.getPublishedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setFinishedAt(post.getFinishedAt());
        return dto;
    }

    @Override
    public Post toEntity(PostInputDto dto) {
        Post post = new Post();
        post.setUser(userService.get(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found")));
        post.setImages(dto.getImages());
        post.setCard(cardService.get(dto.getCardId())
                .orElseThrow(() -> new NotFoundException("Card not found")));
        post.setConservationStatus(dto.getConservationStatus());
        post.setEstimatedValue(dto.getEstimatedValue());
        post.setWantedCards(
                dto.getWantedCardsIds().stream()
                        .map(cardId -> cardService.get(cardId)
                                .orElseThrow(() -> new NotFoundException("Card not found")))
                        .toList());
        post.setDescription(dto.getDescription());
        return post;
    }

}
