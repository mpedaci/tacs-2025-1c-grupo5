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
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.service.IOfferService;

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
        User offerUser = new User(); // ver si se usa el dto o el entity
        Post publication = new Post();

        offerUser.setFirstName("John Doe");
        offerUser.setEmail("john.doe@example.com");

        offerInputDto.setPostId(1L);
        //offerInputDto.setOfferUser(offerUser);
        //offerInputDto.setPublication(publication);
        offerInputDto.setMoney(100.0F);
        //offerInputDto.set(OfferState.PENDING);
//        offerInputDto.setPublicationDate();
//        offerInputDto.setOfferEndDate();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/posts/1/offers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(offerInputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string("Offer saved successfully"));

    }


}
