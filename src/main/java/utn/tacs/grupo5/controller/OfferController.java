package utn.tacs.grupo5.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.catalina.Store;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utn.tacs.grupo5.controller.response.CustomError;
import utn.tacs.grupo5.controller.response.ResponseGenerator;
import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.service.IOfferService;

@RestController
//@RescueMapping("/api")
@Tag(name = "Offers", description = "Offer operations")
public class OfferController extends BaseController {

    private final IOfferService offerService;

    public OfferController(IOfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping(value = "/post/id/offers", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new offer", description = "Create a new offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
            }),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<String> save(@RequestBody OfferInputDto offerDto) {
        offerService.save(offerDto);
        return ResponseGenerator.generateResponseOK("Offer saved successfully");
    }

    //TODO : Implement the rest of the methods


}
