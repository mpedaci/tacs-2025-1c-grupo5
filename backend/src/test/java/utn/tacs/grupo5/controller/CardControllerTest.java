package utn.tacs.grupo5.controller;

import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import utn.tacs.grupo5.TestSecurityConfig;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.card.CardInputDto;
import utn.tacs.grupo5.dto.card.CardOutputDto;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.mapper.CardMapper;
import utn.tacs.grupo5.service.ICardService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = CardController.class)
@Import(TestSecurityConfig.class)
public class CardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    CardMapper cardMapper;

    @MockitoBean
    ICardService cardService;

    @Test
    void save_shouldReturnOK_whenValidInput() throws Exception {
        CardInputDto cardInputDto = new CardInputDto();
        cardInputDto.setName("Test Card");

        CardOutputDto cardOutputDto = new CardOutputDto();
        cardOutputDto.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        cardOutputDto.setName(cardInputDto.getName());

        Card card = new Card();
        card.setName(cardInputDto.getName());
        card.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));

        when(cardService.save(cardInputDto)).thenReturn(card);
        when(cardMapper.toDto(card)).thenReturn(cardOutputDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardInputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value("123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Card"));
    }

    @Test
    void get_shouldReturnCard_whenCardExists() throws Exception {
        UUID cardId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        Card card = new Card();
        card.setId(cardId);
        card.setName("Test Card");

        CardOutputDto dto = new CardOutputDto();
        dto.setId(cardId);
        dto.setName("Test Card");

        when(cardService.get(cardId)).thenReturn(Optional.of(card));
        when(cardMapper.toDto(card)).thenReturn(dto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/cards/" + cardId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(cardId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Card"));
    }

    @Test
    void get_shouldReturnNotFound_whenCardDoesntExist() throws Exception {
        UUID cardId = UUID.fromString("123e4567-e89b-12d3-a456-426614174999");

        when(cardService.get(cardId)).thenReturn(Optional.empty());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/cards/" + cardId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void update_shouldReturnOK_whenValidInput() throws Exception {
        UUID cardId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        CardInputDto cardInputDto = new CardInputDto();
        cardInputDto.setName("Updated Card");

        CardOutputDto cardOutputDto = new CardOutputDto();
        cardOutputDto.setId(cardId);
        cardOutputDto.setName(cardInputDto.getName());

        Card card = new Card();
        card.setId(cardId);
        card.setName(cardInputDto.getName());

        when(cardService.update(cardId, cardInputDto)).thenReturn(card);
        when(cardMapper.toDto(card)).thenReturn(cardOutputDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/cards/" + cardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardInputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(cardId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Card"));
    }

    @Test
    void update_shouldReturnNotFound_whenCardDoesntExist() throws Exception {
        UUID cardId = UUID.fromString("123e4567-e89b-12d3-a456-426614174999");
        CardInputDto cardInputDto = new CardInputDto();
        cardInputDto.setName("Updated Card");

        when(cardService.update(cardId, cardInputDto)).thenThrow(new NotFoundException("Card not found"));

        mockMvc.perform(
                MockMvcRequestBuilders.put("/cards/" + cardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardInputDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void delete_shouldReturnOK_whenCardExists() throws Exception {
        UUID cardId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/cards/" + cardId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Card deleted successfully"));
    }

    @Test
    void getAllCardsByGame_shouldReturnCards_whenGameExists() throws Exception {
        UUID gameId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        Card card1 = new Card();
        card1.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"));
        card1.setName("Card 1");

        Card card2 = new Card();
        card2.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174002"));
        card2.setName("Card 2");

        CardOutputDto dto1 = new CardOutputDto();
        dto1.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"));
        dto1.setName("Card 1");

        CardOutputDto dto2 = new CardOutputDto();
        dto2.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174002"));
        dto2.setName("Card 2");

        when(cardService.getAllByGameId(gameId, null)).thenReturn(List.of(card1, card2));
        when(cardMapper.toDto(card1)).thenReturn(dto1);
        when(cardMapper.toDto(card2)).thenReturn(dto2);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/games/" + gameId + "/cards"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Card 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Card 2"));
    }
}
