package cz.tul.fm.jiri_vokrinek.stin_semestral.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountDtoTest {

    @Test
    public void testCtor() {
        Assertions.assertDoesNotThrow(() -> new AccountDto("CZK", 20));
    }

    @Test
    public void testEmptyCtor() {
        Assertions.assertDoesNotThrow(() -> new AccountDto());
    }
}
