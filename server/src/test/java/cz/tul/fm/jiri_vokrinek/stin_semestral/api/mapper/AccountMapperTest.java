package cz.tul.fm.jiri_vokrinek.stin_semestral.api.mapper;

import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.AccountDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Account;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Currency;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountMapperTest {

    AccountMapper accountMapper;

    @BeforeEach
    public void initialize() {
        accountMapper = new AccountMapper();
    }

    @Test
    public void testToDtos() {
        User user = new User("test@test.test", "Test", "Test", "test");
        Currency currency0 = new Currency("CZK", "Česká republika", 1);
        Currency currency1 = new Currency("EUR", "Polsko", 5);

        Account account0 = new Account(user, currency0);
        Account account1 = new Account(user, currency1);

        List<Account> accountList = new ArrayList<>();
        accountList.add(account0);
        accountList.add(account1);

        List<AccountDto> accountDtos = accountMapper.toDtos(accountList).stream().toList();

        assertEquals(accountList.size(), accountDtos.size());

        for (int i = 0; i < accountList.size(); i++) {
            assertEquals(accountList.get(i).getBalance(), accountDtos.get(i).balance);
            assertEquals(accountList.get(i).getCurrency().getCode(), accountDtos.get(i).currencyCode);
        }
    }
}
