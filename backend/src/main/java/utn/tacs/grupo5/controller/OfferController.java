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
import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.dto.offer.OfferOutputDto;
import utn.tacs.grupo5.dto.offer.OfferStatusInputDto;
import utn.tacs.grupo5.entity.post.Offer;
import utn.tacs.grupo5.mapper.OfferMapper;
import utn.tacs.grupo5.service.IOfferService;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Offers", description = "Offer operations")
public class OfferController extends BaseController {

    private final IOfferService offerService;
    private final OfferMapper offerMapper;

    public OfferController(IOfferService offerService, OfferMapper offerMapper) {
        this.offerService = offerService;
        this.offerMapper = offerMapper;
    }

    @PostMapping(value = "/offers", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new offer", description = "Create a new offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
            }),
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = OfferOutputDto.class))
            })
    })
    public ResponseEntity<OfferOutputDto> save(@RequestBody OfferInputDto offerDto) {
        Offer offer = offerService.save(offerDto);
        return ResponseGenerator.generateResponseOK(offerMapper.toDto(offer));
    }

    @PatchMapping(value = "/offers/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Change offer status", description = "Change offer status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
            }),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<String> patch(@PathVariable UUID id, @RequestBody OfferStatusInputDto status) {
        offerService.updateStatus(id, status);
        return ResponseGenerator.generateResponseOK("Offer status updated successfully");
    }

    @PutMapping(value = "/offers/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update offer", description = "Update offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
            }),
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = OfferOutputDto.class))
            })
    })
    public ResponseEntity<OfferOutputDto> put(@PathVariable UUID id, @RequestBody OfferInputDto offerDto) {
        Offer offer = offerService.update(id, offerDto);
        return ResponseGenerator.generateResponseOK(offerMapper.toDto(offer));
    }

    @DeleteMapping(value = "/offers/{id}")
    @Operation(summary = "Delete an offer", description = "Delete an offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
            }),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        // TODO: deberia cancelar la oferta
        offerService.delete(id);
        return ResponseGenerator.generateResponseOK("Offer deleted successfully");
    }

    @GetMapping(value = "/offers/{id}")
    @Operation(summary = "Get an offer", description = "Get an offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = OfferOutputDto.class))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
            }),
    })
    public ResponseEntity<OfferOutputDto> get(@PathVariable UUID id) {
        Offer offer = offerService.get(id)
                .orElseThrow(() -> new NotFoundException("Offer not found"));
        return ResponseGenerator.generateResponseOK(offerMapper.toDto(offer));
    }

    @GetMapping(value = "/posts/{id}/offers")
    @Operation(summary = "Get all offers by post id", description = "Get all offers by post id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OfferOutputDto.class)))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
            }),
    })
    public ResponseEntity<List<OfferOutputDto>> getAllOffersByPost(@PathVariable UUID id) {
        List<Offer> offers = offerService.getAllByPostId(id);
        return ResponseGenerator.generateResponseOK(offers.stream().map(offerMapper::toDto).toList());
    }

}
