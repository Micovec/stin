package cz.tul.fm.jiri_vokrinek.stin_semestral.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CurrencyDtoTest {

    @Test
    public void testCtor() {
        Assertions.assertDoesNotThrow(() -> new CurrencyDto("EUR", "EUM", 5));
    }

    @Test
    public void testEmptyCtor() {
        Assertions.assertDoesNotThrow(() -> new CurrencyDto());
    }
}
