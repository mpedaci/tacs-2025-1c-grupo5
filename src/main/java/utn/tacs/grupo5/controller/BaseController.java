package utn.tacs.grupo5.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import utn.tacs.grupo5.controller.response.CustomError;

@ApiResponses(value = {
        @ApiResponse(responseCode = "400", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
        }),
        @ApiResponse(responseCode = "500", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = CustomError.class))
        })
})
public class BaseController {

}
