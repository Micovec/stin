package cz.tul.fm.jiri_vokrinek.stin_semestral.data;

import cz.tul.fm.jiri_vokrinek.stin_semestral.dto.CurrencyDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CurrencyData {

    private final String backendUrl;

    public CurrencyData(@Value("${backend_url}") String backedUrl) {
        this.backendUrl = backedUrl + "/currency";
    }

    public CurrencyDto[] getAll() {
        RestTemplate template = new RestTemplate();
        ResponseEntity<CurrencyDto[]> currencyDtos = template.getForEntity(backendUrl, CurrencyDto[].class);
        return currencyDtos.getBody();
    }
}
