package cz.tul.fm.jiri_vokrinek.stin_semestral.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MfaAuthenticationTest {

    Authentication authentication = Mockito.mock(AnonymousAuthenticationToken.class);

    @InjectMocks
    MfaAuthentication mfaAuthentication;

    @Test
    public void testGetCredentials() {
        Object credentialsObject = "";

        Mockito.when(authentication.getCredentials()).thenReturn(credentialsObject);

        assertEquals(mfaAuthentication.getCredentials(), credentialsObject);
    }

    @Test
    public void testIsAuthenticated() {
        assertFalse(mfaAuthentication.isAuthenticated());
    }

    @Test
    public void testGetFirst() {
        assertEquals(mfaAuthentication.getFirst(), authentication);
    }

    @Test
    public void testEraseCredentials() {
        assertDoesNotThrow(() -> mfaAuthentication.eraseCredentials());
    }
}
