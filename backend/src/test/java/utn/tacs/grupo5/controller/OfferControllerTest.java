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
import utn.tacs.grupo5.mapper.OfferMapper;
import utn.tacs.grupo5.service.IOfferService;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import utn.tacs.grupo5.dto.offer.OfferOutputDto;
import utn.tacs.grupo5.entity.post.Offer;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = { OfferController.class })
@Import(TestSecurityConfig.class)
public class OfferControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    OfferMapper offerMapper;

    @MockitoBean
    IOfferService offerService;

    @Test
    void save_shouldReturnOK_whenValidInput() throws Exception {
        OfferInputDto inputDto = new OfferInputDto();
        Offer offer = new Offer();
        OfferOutputDto outputDto = new OfferOutputDto();

        Mockito.when(offerService.save(inputDto)).thenReturn(offer);
        Mockito.when(offerMapper.toDto(offer)).thenReturn(outputDto);

        mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void patchOfferStatus_shouldReturnOK_whenValidInput() throws Exception {
        Mockito.doNothing().when(offerService).updateStatus(eq(1L), eq(Offer.Status.ACCEPTED));

        mockMvc.perform(patch("/offers/1")
                .param("status", "ACCEPTED")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Offer state updated successfully"));
    }

    @Test
    void update_shouldReturnOK_whenValidInput() throws Exception {
        OfferInputDto inputDto = new OfferInputDto();
        Offer offer = new Offer();
        OfferOutputDto outputDto = new OfferOutputDto();

        Mockito.when(offerService.update(eq(1L), any(OfferInputDto.class))).thenReturn(offer);
        Mockito.when(offerMapper.toDto(offer)).thenReturn(outputDto);

        mockMvc.perform(put("/offers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void delete_shouldReturnOK_whenOfferExists() throws Exception {
        Mockito.doNothing().when(offerService).delete(eq(1L));

        mockMvc.perform(delete("/offers/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Offer deleted successfully"));
    }

    @Test
    void get_shouldReturnOffer_whenOfferExists() throws Exception {
        Offer offer = new Offer();
        OfferOutputDto outputDto = new OfferOutputDto();

        Mockito.when(offerService.get(eq(1L))).thenReturn(Optional.of(offer));
        Mockito.when(offerMapper.toDto(offer)).thenReturn(outputDto);

        mockMvc.perform(get("/offers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void get_shouldReturnNotFound_whenOfferDoesNotExist() throws Exception {
        Mockito.when(offerService.get(eq(1L))).thenReturn(Optional.empty());

        mockMvc.perform(get("/offers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll_shouldReturnListOfOffers_whenOffersExist() throws Exception {
        Offer offer = new Offer();
        OfferOutputDto outputDto = new OfferOutputDto();

        Mockito.when(offerService.getAllByPostId(eq(1L))).thenReturn(List.of(offer));
        Mockito.when(offerMapper.toDto(offer)).thenReturn(outputDto);

        mockMvc.perform(get("/posts/1/offers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
