package utn.tacs.grupo5.telegrambot.service;

import utn.tacs.grupo5.telegrambot.dto.post.PostInputDto;
import utn.tacs.grupo5.telegrambot.dto.post.PostOutputDto;

import java.util.List;

public interface IPostService {
    PostOutputDto createPost(String token, PostInputDto postInputDto);

    List<PostOutputDto> getPosts(String token, String gameId, String cardName);
}
