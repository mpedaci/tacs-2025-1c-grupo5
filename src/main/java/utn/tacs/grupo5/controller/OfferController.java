package utn.tacs.grupo5.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.tacs.grupo5.controller.response.CustomError;
import utn.tacs.grupo5.controller.response.ResponseGenerator;
import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.entity.offer.Offer;
import utn.tacs.grupo5.entity.offer.OfferStatus;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.service.IOfferService;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Offers", description = "Offer operations")
public class OfferController extends BaseController {

    private final IOfferService offerService;

    public OfferController(IOfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping(value = "/posts/{id}/offers", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new offer", description = "Create a new offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
            }),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<String> save(@RequestBody OfferInputDto offerDto, @PathVariable Long id) {
        offerDto.setPostId(id);
        offerService.save(offerDto);
        return ResponseGenerator.generateResponseOK("Offer saved successfully");
    }

    @PatchMapping(value = "/posts/{postId}/offers/{offerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Change offer status", description = "Change offer status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
            }),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<String> patch(@PathVariable Long offerId, @PathVariable Long postId,
                                        @RequestBody OfferStatus offerStatus) {
        offerService.patch(offerId, postId, offerStatus);
        return ResponseGenerator.generateResponseOK("Offer saved successfully");
    }

    @DeleteMapping(value = "/posts/{postId}/offers/{offerId}")
    @Operation(summary = "Delete an offer", description = "Delete an offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
            }),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<String> delete(@PathVariable Long offerId, @PathVariable Long postId) {
        offerService.delete(postId, offerId);
        return ResponseGenerator.generateResponseOK("Offer delete successfully");
    }

    @GetMapping(value = "/posts/{postId}/offers/{offerId}")
    @Operation(summary = "Get an offer", description = "Get an offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
            }),
    })
    public ResponseEntity<Offer> getOffer(@PathVariable Long offerId, @PathVariable Long postId) {

        Offer offer = offerService.getById(offerId, postId);

        return ResponseGenerator.generateResponseOK(offer);
    }

    @GetMapping(value = "/posts/{postId}/offers")
    @Operation(summary = "Get all offers", description = "Get all offers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Offer.class))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
            }),
    })
    public ResponseEntity<List<Offer>> getAllOffers( @PathVariable Long postId) {
        List<Offer> offers = offerService.getAllByPublicationId(postId);
        return ResponseGenerator.generateResponseOK(offers);
    }


}
