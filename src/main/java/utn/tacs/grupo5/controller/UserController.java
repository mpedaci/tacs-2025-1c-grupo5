package utn.tacs.grupo5.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.controller.response.CustomError;
import utn.tacs.grupo5.controller.response.ResponseGenerator;
import utn.tacs.grupo5.dto.user.UserInputDto;
import utn.tacs.grupo5.dto.user.UserOutputDto;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.mapper.UserMapper;
import utn.tacs.grupo5.service.IUserService;

@RestController
@Tag(name = "Users", description = "User operations")
public class UserController extends BaseController {

        private final IUserService userService;
        private final UserMapper userMapper;

        public UserController(IUserService userService, UserMapper userMapper) {
                this.userService = userService;
                this.userMapper = userMapper;
        }

        @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
        @Operation(summary = "Register a new user", description = "Register a new user")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "409", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
                        }),
                        @ApiResponse(responseCode = "200", description = "OK")
        })
        public ResponseEntity<String> save(@RequestBody UserInputDto userDto) {
                userService.save(userDto);
                return ResponseGenerator.generateResponseOK("User saved successfully");
        }

        @GetMapping(value = "/users/{id}")
        @Operation(summary = "Get user by id", description = "Get user by id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
                        }),
                        @ApiResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UserOutputDto.class))
                        })
        })
        public ResponseEntity<UserOutputDto> get(@PathVariable Long id) {
                User user = userService.get(id)
                                .orElseThrow(() -> new NotFoundException("User not found"));
                return ResponseGenerator.generateResponseOK(userMapper.toDto(user));
        }

        @PutMapping(value = "/users/{id}")
        @Operation(summary = "Update user by id", description = "Update user by id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
                        }),
                        @ApiResponse(responseCode = "200", description = "OK")
        })
        public ResponseEntity<String> update(@RequestBody UserInputDto userDto, @PathVariable Long id) {
                userService.update(id, userDto);
                return ResponseGenerator.generateResponseOK("User updated successfully");
        }

        // TODO: patch

        @DeleteMapping(value = "/users/{id}")
        @Operation(summary = "Delete user by id", description = "Delete user by id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
                        }),
                        @ApiResponse(responseCode = "200", description = "OK")
        })
        public ResponseEntity<String> delete(@PathVariable Long id) {
                userService.delete(id);
                return ResponseGenerator.generateResponseOK("User deleted successfully");
        }

}
