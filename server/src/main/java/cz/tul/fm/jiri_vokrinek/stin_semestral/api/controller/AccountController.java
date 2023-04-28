package cz.tul.fm.jiri_vokrinek.stin_semestral.api.controller;

import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.AccountDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.EmailDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.mapper.AccountMapper;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import cz.tul.fm.jiri_vokrinek.stin_semestral.service.AccountService;
import cz.tul.fm.jiri_vokrinek.stin_semestral.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;

    private final AccountMapper accountMapper;

    public AccountController(AccountService accountService, UserService userService, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.userService = userService;
        this.accountMapper = accountMapper;
    }

    @PostMapping
    public Collection<AccountDto> getAccounts(@RequestBody EmailDto emailDto) {
        Optional<User> userOpt = userService.readById(emailDto.email);
        if (userOpt.isEmpty()) {
            return new ArrayList<>();
        }

        return accountMapper.toDtos(accountService.getUserAccounts(userOpt.get()));
    }
}
