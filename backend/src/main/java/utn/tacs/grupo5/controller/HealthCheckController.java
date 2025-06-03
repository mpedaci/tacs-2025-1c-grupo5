package utn.tacs.grupo5.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import utn.tacs.grupo5.controller.response.ResponseGenerator;

@RestController
@Hidden
public class HealthCheckController {

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseGenerator.generateResponseOK("OK");
    }

}
