package cz.tul.fm.jiri_vokrinek.stin_semestral.api.mapper;

import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.UserLoginInfoDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserMapper {

    public UserLoginInfoDto toUserLoginInfoDto(User user) {
        return new UserLoginInfoDto(user.getEmail(), user.getPassword(), user.getSecret());
    }

    public Collection<UserLoginInfoDto> toUserLoginInfoDtos(Collection<User> users) {
        Collection<UserLoginInfoDto> dtos = new ArrayList<>();

        for (User user : users) {
            dtos.add(toUserLoginInfoDto(user));
        }

        return dtos;
    }
}
