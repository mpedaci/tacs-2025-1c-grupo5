package utn.tacs.grupo5.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import utn.tacs.grupo5.TestSecurityConfig;
import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.entity.offer.Offer;
import utn.tacs.grupo5.service.IOfferService;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = {OfferController.class})
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
        OfferInputDto offerInputDto = new OfferInputDto();

        when(offerService.save(offerInputDto)).thenReturn(new Offer());


        mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/posts/1/offers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(offerInputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string("Offer saved successfully"));

    }


}
