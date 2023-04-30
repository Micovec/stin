package cz.tul.fm.jiri_vokrinek.stin_semestral.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserLoginInfoDtoTest {

    @Test
    public void testCtor() {
        Assertions.assertDoesNotThrow(() -> new UserLoginInfoDto("test@test.test", "test", "secret"));
    }
}
