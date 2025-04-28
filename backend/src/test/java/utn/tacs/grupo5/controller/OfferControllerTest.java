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
import java.util.UUID;
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
        UUID offerId = UUID.randomUUID();
        Mockito.doNothing().when(offerService).updateStatus(eq(offerId), eq(Offer.Status.ACCEPTED));

        mockMvc.perform(patch("/offers/" + offerId)
                .param("status", "ACCEPTED")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Offer state updated successfully"));
    }

    @Test
    void update_shouldReturnOK_whenValidInput() throws Exception {
        UUID offerId = UUID.randomUUID();
        OfferInputDto inputDto = new OfferInputDto();
        Offer offer = new Offer();
        OfferOutputDto outputDto = new OfferOutputDto();

        Mockito.when(offerService.update(eq(offerId), any(OfferInputDto.class))).thenReturn(offer);
        Mockito.when(offerMapper.toDto(offer)).thenReturn(outputDto);

        mockMvc.perform(put("/offers/" + offerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void delete_shouldReturnOK_whenOfferExists() throws Exception {
        UUID offerId = UUID.randomUUID();
        Mockito.doNothing().when(offerService).delete(eq(offerId));

        mockMvc.perform(delete("/offers/" + offerId))
                .andExpect(status().isOk())
                .andExpect(content().string("Offer deleted successfully"));
    }

    @Test
    void get_shouldReturnOffer_whenOfferExists() throws Exception {
        UUID offerId = UUID.randomUUID();
        Offer offer = new Offer();
        OfferOutputDto outputDto = new OfferOutputDto();

        Mockito.when(offerService.get(eq(offerId))).thenReturn(Optional.of(offer));
        Mockito.when(offerMapper.toDto(offer)).thenReturn(outputDto);

        mockMvc.perform(get("/offers/" + offerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void get_shouldReturnNotFound_whenOfferDoesNotExist() throws Exception {
        UUID offerId = UUID.randomUUID();
        Mockito.when(offerService.get(eq(offerId))).thenReturn(Optional.empty());

        mockMvc.perform(get("/offers/" + offerId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll_shouldReturnListOfOffers_whenOffersExist() throws Exception {
        UUID postId = UUID.randomUUID();
        Offer offer = new Offer();
        OfferOutputDto outputDto = new OfferOutputDto();

        Mockito.when(offerService.getAllByPostId(eq(postId))).thenReturn(List.of(offer));
        Mockito.when(offerMapper.toDto(offer)).thenReturn(outputDto);

        mockMvc.perform(get("/posts/" + postId + "/offers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
