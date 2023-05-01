package cz.tul.fm.jiri_vokrinek.stin_semestral.service;

import cz.tul.fm.jiri_vokrinek.stin_semestral.dao.UserJpaRepository;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserJpaRepository userRepository;

    @Test
    public void testExists() {
        User user = new User("test@test.test", "Test", "Test", "test");

        Mockito.when(userRepository.existsById(user.getEmail())).thenReturn(true);

        assertTrue(userService.exists(user));
    }
}
