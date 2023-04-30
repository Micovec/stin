package cz.tul.fm.jiri_vokrinek.stin_semestral.api.controller;

import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.UserLoginInfoDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.mapper.UserMapper;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import cz.tul.fm.jiri_vokrinek.stin_semestral.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    UserService userService;

    @MockBean
    UserMapper userMapper;

    @Autowired
    MockMvc mock;

    @Test
    public void testGetUserLoginInfoInfo() throws Exception {
        User user0 = new User("test@test.test", "Test", "Test", "test");
        User user1 = new User("test1@test1.test1", "Test1", "Test1", "test1");

        Collection<User> users = new ArrayList<>();
        users.add(user0);
        users.add(user1);

        Collection<UserLoginInfoDto> userDtos = new ArrayList<>();
        userDtos.add(new UserLoginInfoDto(user0.getEmail(), user0.getPassword(), user0.getSecret()));
        userDtos.add(new UserLoginInfoDto(user1.getEmail(), user1.getPassword(), user1.getSecret()));

        Mockito.when(userService.readAll()).thenReturn(users);
        Mockito.when(userMapper.toUserLoginInfoDtos(users)).thenReturn(userDtos);

        mock.perform(get("/user/loginInfos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].email", Matchers.is(user0.getEmail())))
                .andExpect(jsonPath("$[0].password", Matchers.is(user0.getPassword())))
                .andExpect(jsonPath("$[0].secret", Matchers.is(user0.getSecret())))
                .andExpect(jsonPath("$[1].email", Matchers.is(user1.getEmail())))
                .andExpect(jsonPath("$[1].password", Matchers.is(user1.getPassword())))
                .andExpect(jsonPath("$[1].secret", Matchers.is(user1.getSecret())));
    }
}
