package cz.tul.fm.jiri_vokrinek.stin_semestral.dto;

public class UserLoginInfoDto {

    private String email;

    private String password;

    private String secret;

    public UserLoginInfoDto(String email, String password, String secret) {
        this.email = email;
        this.password = password;
        this.secret = secret;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSecret() {
        return secret;
    }
}