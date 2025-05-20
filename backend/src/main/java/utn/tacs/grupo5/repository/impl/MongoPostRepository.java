package utn.tacs.grupo5.repository.impl;

import org.springframework.data.mongodb.repository.MongoRepository;
import utn.tacs.grupo5.entity.post.Post;

import java.util.UUID;

public interface MongoPostRepository extends MongoRepository<Post, UUID> {
}
