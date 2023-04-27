package cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto;

public class UserLoginInfoDto {

    public String email;

    public String password;

    public String secret;

    public UserLoginInfoDto(String email, String password, String secret) {
        this.email = email;
        this.password = password;
        this.secret = secret;
    }
}
