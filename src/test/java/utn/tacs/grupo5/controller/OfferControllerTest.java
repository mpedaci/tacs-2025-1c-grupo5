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
import utn.tacs.grupo5.dto.offeredCard.OfferedCardInputDto;
import utn.tacs.grupo5.entity.offer.Offer;
import utn.tacs.grupo5.entity.offer.OfferStatus;
import utn.tacs.grupo5.service.IOfferService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(controllers = OfferController.class)
@Import(TestSecurityConfig.class)
public class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IOfferService offerService;

    @Test
    void saveShouldReturnOK() throws Exception {
        OfferInputDto dto = new OfferInputDto();
        dto.setOffererId(1L);
        dto.setMoney(100.0F);
        dto.setOfferedCards(List.of(new OfferedCardInputDto()));

        when(offerService.save(any())).thenReturn(new Offer());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/posts/1/offers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void patchShouldReturnOK() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/offers/posts/1/offers/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(OfferStatus.ACCEPTED)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(offerService).patch(2L, 1L, OfferStatus.ACCEPTED);
    }

    @Test
    void deleteShouldReturnOK() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/offers/posts/1/offers/2"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(offerService).delete(1L, 2L);
    }

    @Test
    void getOfferShouldReturnOK() throws Exception {
        Offer offer = new Offer();
        offer.setId(1L);

        when(offerService.getById(1L, 2L)).thenReturn(offer);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/offers/posts/2/offers/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    void getAllOffersShouldReturnOK() throws Exception {
        when(offerService.getAllByPublicationId(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/offers/posts/1/offers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
