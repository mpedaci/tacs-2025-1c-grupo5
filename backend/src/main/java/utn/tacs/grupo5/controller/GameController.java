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
import utn.tacs.grupo5.dto.game.GameInputDto;
import utn.tacs.grupo5.dto.game.GameOutputDto;
import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.mapper.GameMapper;
import utn.tacs.grupo5.service.IGameService;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Games", description = "Game operations")
public class GameController extends BaseController {

        private final IGameService gameService;
        private final GameMapper gameMapper;

        public GameController(IGameService gameService, GameMapper gameMapper) {
                this.gameService = gameService;
                this.gameMapper = gameMapper;
        }

        @PostMapping(value = "/games", consumes = MediaType.APPLICATION_JSON_VALUE)
        @Operation(summary = "Create new game", description = "Create new game")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = GameOutputDto.class))
                        })
        })
        public ResponseEntity<GameOutputDto> save(@RequestBody GameInputDto gameDto) {
                Game game = gameService.save(gameDto);
                return ResponseGenerator.generateResponseOK(gameMapper.toDto(game));
        }

        @GetMapping(value = "/games/{id}")
        @Operation(summary = "Get game by id", description = "Get game by id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
                        }),
                        @ApiResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = GameOutputDto.class))
                        })
        })
        public ResponseEntity<GameOutputDto> get(@PathVariable UUID id) {
                Game game = gameService.get(id)
                                .orElseThrow(() -> new NotFoundException("Game not found"));
                return ResponseGenerator.generateResponseOK(gameMapper.toDto(game));
        }

        @PutMapping(value = "/games/{id}")
        @Operation(summary = "Update game by id", description = "Update game by id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
                        }),
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = GameOutputDto.class))
                        })
        })
        public ResponseEntity<GameOutputDto> update(@RequestBody GameInputDto gameDto, @PathVariable UUID id) {
                Game game = gameService.update(id, gameDto);
                return ResponseGenerator.generateResponseOK(gameMapper.toDto(game));
        }

        @DeleteMapping(value = "/games/{id}")
        @Operation(summary = "Delete game by id", description = "Delete game by id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
                        }),
                        @ApiResponse(responseCode = "200", description = "OK")
        })
        public ResponseEntity<String> delete(@PathVariable UUID id) {
                gameService.delete(id);
                return ResponseGenerator.generateResponseOK("Game deleted successfully");
        }

        @GetMapping(value = "/games")
        @Operation(summary = "Get all games", description = "Get all games")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = GameOutputDto.class))
                        })
        })
        public ResponseEntity<List<GameOutputDto>> getAll() {
                List<Game> games = gameService.getAll();
                return ResponseGenerator.generateResponseOK(games.stream().map(gameMapper::toDto).toList());
        }
}
