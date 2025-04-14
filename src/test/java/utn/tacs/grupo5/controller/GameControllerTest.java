package utn.tacs.grupo5.controller;

import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import utn.tacs.grupo5.TestSecurityConfig;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.game.GameInputDto;
import utn.tacs.grupo5.dto.game.GameOutputDto;
import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.mapper.GameMapper;
import utn.tacs.grupo5.service.IGameService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = GameController.class)
@Import(TestSecurityConfig.class)
public class GameControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    GameMapper gameMapper;

    @MockitoBean
    IGameService gameService;

    @Test
    void save_shouldReturnOK_whenValidInput() throws Exception {
        GameInputDto gameInputDto = new GameInputDto();
        gameInputDto.setName("Test Game");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameInputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Game saved successfully"));
    }

    @Test
    void get_shouldReturnGame_whenGameExists() throws Exception {
        Long gameId = 1L;
        Game game = new Game();
        game.setId(gameId);
        game.setName("Test Game");

        GameOutputDto dto = new GameOutputDto();
        dto.setId(gameId);
        dto.setName("Test Game");

        when(gameService.get(gameId)).thenReturn(Optional.of(game));
        when(gameMapper.toDto(game)).thenReturn(dto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/games/" + gameId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(gameId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Game"));
    }

    @Test
    void get_shouldReturnNotFound_whenGameDoesntExist() throws Exception {
        Long gameId = 999L;

        when(gameService.get(gameId)).thenReturn(Optional.empty());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/games/" + gameId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void update_shouldReturnOK_whenValidInput() throws Exception {
        Long gameId = 1L;
        GameInputDto gameInputDto = new GameInputDto();
        gameInputDto.setName("Updated Game");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/games/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameInputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Game updated successfully"));
    }

    @Test
    void update_shouldReturnNotFound_whenGameDoesntExist() throws Exception {
        Long gameId = 999L;
        GameInputDto gameInputDto = new GameInputDto();
        gameInputDto.setName("Updated Game");

        when(gameService.update(gameId, gameInputDto)).thenThrow(new NotFoundException("Game not found"));

        mockMvc.perform(
                MockMvcRequestBuilders.put("/games/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameInputDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void delete_shouldReturnOK_whenGameExists() throws Exception {
        Long gameId = 1L;

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/games/" + gameId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Game deleted successfully"));
    }
}
