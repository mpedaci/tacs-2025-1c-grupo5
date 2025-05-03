package utn.tacs.grupo5.controller;

import static org.mockito.Mockito.when;

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
import utn.tacs.grupo5.controller.exceptions.ConflictException;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.user.UserInputDto;
import utn.tacs.grupo5.dto.user.UserOutputDto;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.mapper.UserMapper;
import utn.tacs.grupo5.service.IUserService;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = UserController.class)
@Import(TestSecurityConfig.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    UserMapper userMapper;

    @MockitoBean
    IUserService userService;

    @Test
    void save_shouldReturnOK_whenValidInput() throws Exception {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setName("John Doe");

        UserOutputDto userOutputDto = new UserOutputDto();
        UUID userId = UUID.randomUUID();
        userOutputDto.setId(userId);
        userOutputDto.setName(userInputDto.getName());

        User user = new User();
        user.setName(userInputDto.getName());
        user.setId(userId);

        when(userService.save(userInputDto)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userOutputDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userOutputDto.getName()))
                ;
    }

    @Test
    void save_shouldReturnConflict_whenValidUserExists() throws Exception {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setName("John Doe");

        when(userService.save(userInputDto)).thenThrow(new ConflictException("User already exists"));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInputDto)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void get_shouldReturnUser_whenUserExists() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");

        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setId(userId);
        userOutputDto.setName(user.getName());

        when(userService.get(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userOutputDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John Doe"));
    }

    @Test
    void get_shouldReturnNotFound_whenUserDoesntExists() throws Exception {
        UUID userId = UUID.randomUUID();

        when(userService.get(userId)).thenReturn(Optional.empty());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void update_shouldReturnOK_whenValidInput() throws Exception {
        UUID userId = UUID.randomUUID();
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setName("Updated Name");

        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setId(userId);
        userOutputDto.setName(userInputDto.getName());

        User user = new User();
        user.setId(userId);
        user.setName(userInputDto.getName());

        when(userService.update(userId, userInputDto)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userOutputDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Updated Name"));
    }

    @Test
    void update_shouldReturnNotFound_whenUserDoesntExists() throws Exception {
        UUID userId = UUID.randomUUID();
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setName("Updated Name");

        when(userService.update(userId, userInputDto)).thenThrow(new NotFoundException("User not found"));

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInputDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteUser() throws Exception {
        UUID userId = UUID.randomUUID();

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));
    }

}
