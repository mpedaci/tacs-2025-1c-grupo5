package utn.tacs.grupo5.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import utn.tacs.grupo5.TestSecurityConfig;
import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.service.IOfferService;

@WebMvcTest(controllers = { OfferController.class })
@Import(TestSecurityConfig.class)

public class OfferControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    IOfferService offerService;

    @Test
    public void testSaveOffer() throws Exception {
        // // Given
        // OfferInputDto offerDto = new OfferInputDto();
        // offerDto.setId(1L);
        // offerDto.setName("Test Offer");
        // offerDto.setDescription("This is a test offer");
        //
        // // When
        // mockMvc.perform(post("/api/post/id/offers")
        // .contentType(MediaType.APPLICATION_JSON)
        // .content(objectMapper.writeValueAsString(offerDto)))
        // .andExpect(status().isOk());
        //
        // // Then
        // verify(offerService, times(1)).save(offerDto);
    }

}
