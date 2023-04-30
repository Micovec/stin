package cz.tul.fm.jiri_vokrinek.stin_semestral.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmailDtoTest {

    @Test
    public void testCtor() {
        Assertions.assertDoesNotThrow(() -> new EmailDto("test@test.test"));
    }
}
