package cz.tul.fm.jiri_vokrinek.stin_semestral.data;

import cz.tul.fm.jiri_vokrinek.stin_semestral.dto.AccountDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.dto.EmailDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AccountData {

    private final String backendUrl;

    public AccountData(@Value("${backend_url}") String backedUrl) {
        this.backendUrl = backedUrl + "/account";
    }

    public AccountDto[] getUserAccounts(String email) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<AccountDto[]> accountDtos = template.postForEntity(backendUrl, new EmailDto(email), AccountDto[].class);
        return accountDtos.getBody();
    }
}
