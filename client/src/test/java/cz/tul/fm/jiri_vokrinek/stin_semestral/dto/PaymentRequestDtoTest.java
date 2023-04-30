package cz.tul.fm.jiri_vokrinek.stin_semestral.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaymentRequestDtoTest {

    @Test
    public void testCtor() {
        Assertions.assertDoesNotThrow(() -> new PaymentRequestDto("test@test.test", 20, "CZK", "EUR"));
    }
}
