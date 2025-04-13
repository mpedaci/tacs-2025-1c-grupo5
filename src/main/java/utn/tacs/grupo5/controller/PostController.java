package utn.tacs.grupo5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.controller.response.CustomError;
import utn.tacs.grupo5.controller.response.ResponseGenerator;
import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.dto.post.PostOutputDto;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.service.impl.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController extends BaseController {
    @Autowired
    private PostService postService;

    @PostMapping
    @Operation(summary = "Create a new post", description = "Create a new post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostOutputDto.class))
            })
    })
    public ResponseEntity<PostOutputDto> createPost(@RequestBody PostInputDto postInputDto) {
        //TODO: implementar validaciones de inputs en próximas entregas
        Post post = postService.save(postInputDto);
        return ResponseGenerator.generateResponseOK(new PostOutputDto(post.getId()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a post data", description = "Update a post data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostOutputDto.class))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
            }),
    })
    public ResponseEntity<PostOutputDto> updatePost(@PathVariable Long id, @RequestBody PostInputDto postInputDto) {
        //TODO: implementar validaciones de inputs en próximas entregas
        Post post = postService.update(id, postInputDto);
        return ResponseGenerator.generateResponseOK(new PostOutputDto(post.getId()));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update the post status", description = "Update the post status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostOutputDto.class))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
            }),
    })
    public ResponseEntity<PostOutputDto> updatePostStatus(@PathVariable Long id,
                                                          @RequestBody PostInputDto postInputDto) {
        //TODO: implementar validaciones de inputs en próximas entregas (solo debería quedar el status)
        Post post = postService.update(id, postInputDto);
        return ResponseGenerator.generateResponseOK(new PostOutputDto(post.getId()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a post", description = "Delete a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
            }),
    })
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.delete(id);
        return ResponseGenerator.generateResponseOK("Post deleted successfully");
    }

    @GetMapping()
    @Operation(summary = "Get all the posts", description = "Get all the posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
            }),
    })
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = postService.getAll();
        return ResponseGenerator.generateResponseOK(posts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a post by id", description = "Get a post by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))
            }),
    })
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        Post post = postService.get(id).orElseThrow(() -> new NotFoundException("Post not found"));
        return ResponseGenerator.generateResponseOK(post);
    }
}
