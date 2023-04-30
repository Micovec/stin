package cz.tul.fm.jiri_vokrinek.stin_semestral.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.AccountDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.EmailDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.mapper.AccountMapper;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Account;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Currency;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import cz.tul.fm.jiri_vokrinek.stin_semestral.service.AccountService;
import cz.tul.fm.jiri_vokrinek.stin_semestral.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @MockBean
    AccountService accountService;

    @MockBean
    AccountMapper accountMapper;

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mock;

    @Test
    public void testGetAccounts() throws Exception {
        Optional<User> user = Optional.of(new User("test@test.test", "Test", "Test", "test"));

        Currency currency0 = new Currency("CZK", "Česká republika", 1);
        Currency currency1 = new Currency("EUR", "EUM", 20);

        Account account0 = new Account(user.get(), currency0);
        Account account1 = new Account(user.get(), currency1);

        Collection<Account> accounts = new ArrayList<>();
        accounts.add(account0);
        accounts.add(account1);

        Collection<AccountDto> accountDtos = new ArrayList<>();
        accountDtos.add(new AccountDto(account0.getCurrency().getCode(), account0.getBalance()));
        accountDtos.add(new AccountDto(account1.getCurrency().getCode(), account1.getBalance()));

        Mockito.when(userService.readById(user.get().getEmail())).thenReturn(user);
        Mockito.when(accountMapper.toDtos(accounts)).thenReturn(accountDtos);
        Mockito.when(accountService.getUserAccounts(user.get())).thenReturn(accounts);

        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mock.perform(post("/account")
                        .header("Content-Type", "application/json")
                        .content(writer.writeValueAsString(new EmailDto(user.get().getEmail()))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].currencyCode", Matchers.is(currency0.getCode())))
                .andExpect(jsonPath("$[0].balance", Matchers.is(account0.getBalance())))
                .andExpect(jsonPath("$[1].currencyCode", Matchers.is(currency1.getCode())))
                .andExpect(jsonPath("$[1].balance", Matchers.is(account1.getBalance())));
    }
}
