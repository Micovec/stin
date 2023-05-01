package cz.tul.fm.jiri_vokrinek.stin_semestral.security;

import cz.tul.fm.jiri_vokrinek.stin_semestral.data.UserData;
import cz.tul.fm.jiri_vokrinek.stin_semestral.dto.UserLoginInfoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Mock
    UserData userData;

    @Test
    public void testLoadUserByUsername() {
        UserLoginInfoDto userLoginInfoDto = new UserLoginInfoDto("test@test.test", "test", "secret");

        Mockito.when(userData.findUserLoginInfo(userLoginInfoDto.getEmail())).thenReturn(Optional.of(userLoginInfoDto));

        UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginInfoDto.getEmail());

        assertEquals(userDetails.getUsername(), userLoginInfoDto.getEmail());
        assertEquals(userDetails.getPassword(), userLoginInfoDto.getPassword());    
    }
}
