package utn.tacs.grupo5.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import utn.tacs.grupo5.entity.post.Post;

import java.util.UUID;

public interface PostRepository extends MongoRepository<Post, UUID> {
}
