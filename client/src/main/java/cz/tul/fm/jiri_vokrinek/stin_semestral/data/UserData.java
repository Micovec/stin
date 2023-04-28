package cz.tul.fm.jiri_vokrinek.stin_semestral.data;

import cz.tul.fm.jiri_vokrinek.stin_semestral.dto.UserLoginInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UserData {

    private final Map<String, UserLoginInfoDto> userLoginInfoDtoMap;

    public UserData() {
        userLoginInfoDtoMap = new HashMap<>();

        fillUserLogins();
    }

    public Optional<UserLoginInfoDto> findUserLoginInfo(String email) {
        UserLoginInfoDto dto = userLoginInfoDtoMap.get(email);
        if (dto == null) {
            return Optional.empty();
        }

        return Optional.of(dto);
    }

    private void fillUserLogins() {
        userLoginInfoDtoMap.put("test@test.test", new UserLoginInfoDto("test@test.test", "test", "PNTLRNRGY6D63VYNWOXPSUEPTCQVAJRF"));
        userLoginInfoDtoMap.put("jan.novak@email.cz", new UserLoginInfoDto("jan.novak@email.cz", "heslo", "TCBKBM2T7CVAOJ4FID653XAX4KFX7XCO"));
    }
}
