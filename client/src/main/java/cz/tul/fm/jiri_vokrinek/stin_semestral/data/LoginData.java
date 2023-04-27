package cz.tul.fm.jiri_vokrinek.stin_semestral.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class LoginData {

    private final WebClient webClient;

    public LoginData(@Value("${backend_url}") String backedUrl) {
        webClient = WebClient.create(backedUrl);
    }
}

