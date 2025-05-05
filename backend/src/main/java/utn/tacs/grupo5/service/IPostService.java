package utn.tacs.grupo5.service;

import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.entity.post.Post;

import java.util.List;
import java.util.UUID;

public interface IPostService extends ICRUDService<Post, UUID, PostInputDto> {

    List<Post> getAll();

    void updateStatus(UUID postId, Post.Status newStatus);

    List<Post> getAllWithFilters(String cardName, UUID gameId, String cardStatus);
}
