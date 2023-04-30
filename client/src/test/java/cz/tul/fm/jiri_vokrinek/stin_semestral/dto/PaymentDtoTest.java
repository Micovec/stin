package cz.tul.fm.jiri_vokrinek.stin_semestral.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class PaymentDtoTest {

    @Test
    public void testEmptyCtor() {
        Assertions.assertDoesNotThrow(() -> new PaymentDto());
    }

    @Test
    public void testCtor() {
        Assertions.assertDoesNotThrow(() -> new PaymentDto(20, "CZK", "EUR", LocalDate.now(), true));
    }
}
