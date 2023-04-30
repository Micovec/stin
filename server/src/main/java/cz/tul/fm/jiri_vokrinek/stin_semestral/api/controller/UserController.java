package cz.tul.fm.jiri_vokrinek.stin_semestral.api.controller;

import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.UserLoginInfoDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.mapper.UserMapper;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import cz.tul.fm.jiri_vokrinek.stin_semestral.service.UserService;

import dev.samstevens.totp.code.CodeVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/loginInfos")
    public Collection<UserLoginInfoDto> getUserLoginInfoInfo() {
        return userMapper.toUserLoginInfoDtos(userService.readAll());
    }
}
