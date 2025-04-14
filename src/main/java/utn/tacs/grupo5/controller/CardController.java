package utn.tacs.grupo5.controller;

import java.util.List;

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
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.controller.response.CustomError;
import utn.tacs.grupo5.controller.response.ResponseGenerator;
import utn.tacs.grupo5.dto.card.CardInputDto;
import utn.tacs.grupo5.dto.card.CardOutputDto;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.mapper.CardMapper;
import utn.tacs.grupo5.service.ICardService;

@RestController
@Tag(name = "Cards", description = "Card operations")
public class CardController {

        private final ICardService cardService;
        private final CardMapper cardMapper;

        public CardController(ICardService cardService, CardMapper cardMapper) {
                this.cardService = cardService;
                this.cardMapper = cardMapper;
        }

        @PostMapping(value = "/cards", consumes = MediaType.APPLICATION_JSON_VALUE)
        @Operation(summary = "Create new card", description = "Create new card")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK")
        })
        public ResponseEntity<String> save(@RequestBody CardInputDto cardDto) {
                cardService.save(cardDto);
                return ResponseGenerator.generateResponseOK("Card saved successfully");
        }

        @GetMapping(value = "/cards/{id}")
        @Operation(summary = "Get card by id", description = "Get card by id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
                        }),
                        @ApiResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CardOutputDto.class))
                        })
        })
        public ResponseEntity<CardOutputDto> get(@PathVariable Long id) {
                Card card = cardService.get(id)
                                .orElseThrow(() -> new NotFoundException("Card not found"));
                return ResponseGenerator.generateResponseOK(cardMapper.toDto(card));
        }

        @PutMapping(value = "/cards/{id}")
        @Operation(summary = "Update card by id", description = "Update card by id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
                        }),
                        @ApiResponse(responseCode = "200", description = "OK")
        })
        public ResponseEntity<String> update(@RequestBody CardInputDto cardDto, @PathVariable Long id) {
                cardService.update(id, cardDto);
                return ResponseGenerator.generateResponseOK("Card updated successfully");
        }

        @DeleteMapping(value = "/cards/{id}")
        @Operation(summary = "Delete card by id", description = "Delete card by id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
                        }),
                        @ApiResponse(responseCode = "200", description = "OK")
        })
        public ResponseEntity<String> delete(@PathVariable Long id) {
                cardService.delete(id);
                return ResponseGenerator.generateResponseOK("Card deleted successfully");
        }

        @GetMapping(value = "/games/{id}/cards") // TODO hacer pageable en un futuro
        @Operation(summary = "Get all card by game id", description = "Get all cards by game id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
                        }),
                        @ApiResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CardOutputDto.class)))
                        })
        })
        public ResponseEntity<List<CardOutputDto>> getAllCardsByGame(@PathVariable Long id) {
                List<CardOutputDto> cards = cardService.getAllByGameId(id)
                                .stream()
                                .map(cardMapper::toDto)
                                .toList();
                return ResponseGenerator.generateResponseOK(cards);
        }
}
