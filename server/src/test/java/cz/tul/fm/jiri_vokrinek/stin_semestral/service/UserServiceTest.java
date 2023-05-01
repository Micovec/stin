package cz.tul.fm.jiri_vokrinek.stin_semestral.service;

import cz.tul.fm.jiri_vokrinek.stin_semestral.dao.UserJpaRepository;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import cz.tul.fm.jiri_vokrinek.stin_semestral.service.exception.EntityStateException;
import cz.tul.fm.jiri_vokrinek.stin_semestral.service.exception.NoEntityFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testReadAll() {
        User user0 = new User("test@test.test", "Test", "Test", "test");
        User user1 = new User("test1@test1.test1", "Test1", "Test1", "test1");

        List<User> userList = new ArrayList<>();
        userList.add(user0);
        userList.add(user1);

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        Collection<User> returnedUsers = userService.readAll();

        assertEquals(userList, returnedUsers);
    }

    @Test
    public void testReadById() {
        User user = new User("test@test.test", "Test", "Test", "test");

        Mockito.when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));

        Optional<User> returnedUser = userService.readById(user.getEmail());

        assertTrue(returnedUser.isPresent());

        assertEquals(user, returnedUser.get());
    }

    @Test
    public void testCreateDoesExists() {
        User user = new User("test@test.test", "Test", "Test", "test");

        Mockito.when(userRepository.existsById(user.getEmail())).thenReturn(true);

        assertThrows(EntityStateException.class, () -> userService.create(user));
    }

    @Test
    public void testUpdateExists() {
        User user = new User("test@test.test", "Test", "Test", "test");

        Mockito.when(userRepository.existsById(user.getEmail())).thenReturn(true);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        User returnedUser = userService.update(user);

        assertEquals(user, returnedUser);
    }

    @Test
    public void testUpdateDoesNotExist() {
        User user = new User("test@test.test", "Test", "Test", "test");

        Mockito.when(userRepository.existsById(user.getEmail())).thenReturn(false);

        assertThrows(EntityStateException.class, () -> userService.update(user));
    }

    @Test
    public void testDeleteByIdExists() {
        User user = new User("test@test.test", "Test", "Test", "test");

        Mockito.when(userRepository.existsById(user.getEmail())).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteById(user.getEmail()));
    }

    @Test
    public void testDeleteByIdDoesNotExist() {
        User user = new User("test@test.test", "Test", "Test", "test");

        Mockito.when(userRepository.existsById(user.getEmail())).thenReturn(false);

        assertThrows(NoEntityFoundException.class, () -> userService.deleteById(user.getEmail()));
    }
}
