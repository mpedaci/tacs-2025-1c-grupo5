package utn.tacs.grupo5.service;

import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.entity.post.Post;

import java.util.List;

public interface IPostService extends ICRUDService<Post, Long, PostInputDto> {

    List<Post> getAll();

    void updateStatus(Long postId, Post.Status newStatus);

}
