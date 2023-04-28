package cz.tul.fm.jiri_vokrinek.stin_semestral.data;

import cz.tul.fm.jiri_vokrinek.stin_semestral.dto.PaymentRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentData {

    private final String backendUrl;

    public PaymentData(@Value("${backend_url}") String backedUrl) {
        this.backendUrl = backedUrl + "/payment";
    }

    public PaymentDto pay(String email, double amount, String currencyCode) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<PaymentDto> paymentDto = template.postForEntity(backendUrl, new PaymentRequestDto(email, amount, currencyCode), PaymentDto.class);
        return paymentDto.getBody();
    }
}
