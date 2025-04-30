package utn.tacs.grupo5.repository;

import utn.tacs.grupo5.entity.post.Post;

import java.util.UUID;

public interface PostRepository extends ICRUDRepository<Post, UUID> {

    Long getCountByStatus(Post.Status status);

    Long getCount();

}
