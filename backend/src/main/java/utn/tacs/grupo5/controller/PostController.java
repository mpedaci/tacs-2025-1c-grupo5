package utn.tacs.grupo5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.controller.response.CustomError;
import utn.tacs.grupo5.controller.response.ResponseGenerator;
import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.dto.post.PostOutputDto;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.mapper.PostMapper;
import utn.tacs.grupo5.service.impl.PostService;

import java.util.List;

@RestController
@Tag(name = "Posts", description = "Posts operations")
public class PostController extends BaseController {

        private final PostService postService;
        private final PostMapper postMapper;

        public PostController(PostService postService, PostMapper postMapper) {
                this.postService = postService;
                this.postMapper = postMapper;
        }

        @PostMapping(value = "/posts", consumes = MediaType.APPLICATION_JSON_VALUE)
        @Operation(summary = "Create a new post", description = "Create a new post")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PostOutputDto.class))
                        })
        })
        public ResponseEntity<PostOutputDto> post(@RequestBody PostInputDto postInputDto) {
                // TODO: implementar validaciones de inputs en próximas entregas
                Post post = postService.save(postInputDto);
                return ResponseGenerator.generateResponseOK(postMapper.toDto(post));
        }

        @PutMapping("/posts/{id}")
        @Operation(summary = "Update a post data", description = "Update a post data")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PostOutputDto.class))
                        }),
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
                        }),
        })
        public ResponseEntity<PostOutputDto> update(@PathVariable Long id, @RequestBody PostInputDto postInputDto) {
                // TODO: implementar validaciones de inputs en próximas entregas
                Post post = postService.update(id, postInputDto);
                return ResponseGenerator.generateResponseOK(postMapper.toDto(post));
        }

        @DeleteMapping("/posts/{id}")
        @Operation(summary = "Delete a post", description = "Delete a post")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
                        }),
        })
        public ResponseEntity<String> delete(@PathVariable Long id) {
                // TODO: deberia cancelar el post
                postService.delete(id);
                return ResponseGenerator.generateResponseOK("Post deleted successfully");
        }

        @GetMapping(value = "/posts")
        @Operation(summary = "Get all the posts", description = "Get all the posts")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PostOutputDto.class)))
                        }),
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
                        }),
        })
        public ResponseEntity<List<PostOutputDto>> getAll() {
                List<PostOutputDto> posts = postService.getAll()
                                .stream()
                                .map(postMapper::toDto)
                                .toList();
                return ResponseGenerator.generateResponseOK(posts);
        }

        @GetMapping("/posts/{id}")
        @Operation(summary = "Get a post by id", description = "Get a post by id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PostOutputDto.class))
                        }),
        })
        public ResponseEntity<PostOutputDto> get(@PathVariable Long id) {
                Post post = postService.get(id).orElseThrow(() -> new NotFoundException("Post not found"));
                return ResponseGenerator.generateResponseOK(postMapper.toDto(post));
        }
}
