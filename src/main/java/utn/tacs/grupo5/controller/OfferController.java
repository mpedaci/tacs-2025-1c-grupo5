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
import utn.tacs.grupo5.entity.offer.Offer;
import utn.tacs.grupo5.entity.offer.OfferState;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.service.IOfferService;

import java.util.List;

@RestController
@Tag(name = "Offers", description = "Offer operations")
public class OfferController extends BaseController {

        private final IOfferService offerService;

        public OfferController(IOfferService offerService) {
                this.offerService = offerService;
        }

        @PostMapping(value = "/offers", consumes = MediaType.APPLICATION_JSON_VALUE)
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

        @PatchMapping(value = "/offers/{offerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
        @Operation(summary = "Change offer status", description = "Change offer status")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
                        }),
                        @ApiResponse(responseCode = "200", description = "OK")
        })
        public ResponseEntity<String> patch(@PathVariable Long offerId, @PathVariable Long postId,
                        @RequestBody OfferState offerState) {
                offerService.patch(offerId, postId, offerState);
                return ResponseGenerator.generateResponseOK("Offer saved successfully");
        }

        @DeleteMapping(value = "/offers/{offerId}")
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

        @GetMapping(value = "/offers/{id}")
        @Operation(summary = "Get an offer", description = "Get an offer")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))
                        }),
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
                        }),
        })
        public ResponseEntity<Offer> getOffer(@PathVariable Long id) {
                Offer offer = offerService.get(id)
                                .orElseThrow(() -> new NotFoundException("Offer not found"));

                return ResponseGenerator.generateResponseOK(offer);
        }

        @GetMapping(value = "/posts/{postId}/offers")
        @Operation(summary = "Get all offers by post id", description = "Get all offers by post id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Offer.class)))
                        }),
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
                        }),
        })
        public ResponseEntity<List<Offer>> getAllOffersByPost(@PathVariable Long postId) {
                List<Offer> offers = offerService.getAllByPostId(postId);
                return ResponseGenerator.generateResponseOK(offers);
        }

}
