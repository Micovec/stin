package cz.tul.fm.jiri_vokrinek.stin_semestral.api.mapper;

import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.UserLoginInfoDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    UserMapper userMapper;

    @BeforeEach
    public void initialize() {
        userMapper = new UserMapper();
    }

    @Test
    public void testToDtos() {
        User user0 = new User("test@test.test", "Test", "Test", "test");
        User user1 = new User("test1@test1.test1", "Test1", "Test1", "test1");

        List<User> users = new ArrayList<>();
        users.add(user0);
        users.add(user1);

        List<UserLoginInfoDto> dtos = userMapper.toUserLoginInfoDtos(users).stream().toList();

        assertEquals(users.size(), dtos.size());

        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i).getEmail(), dtos.get(i).email);
            assertEquals(users.get(i).getPassword(), dtos.get(i).password);
            assertEquals(users.get(i).getSecret(), dtos.get(i).secret);
        }
    }
}
