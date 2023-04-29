package cz.tul.fm.jiri_vokrinek.stin_semestral.service;


import cz.tul.fm.jiri_vokrinek.stin_semestral.dao.AccountJpaRepository;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Account;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Currency;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    AccountJpaRepository accountRepository;

    @Test
    public void testGetByUserAndCurrency() {
        User user = new User("test@test.test", "Test", "Test", "test");
        Currency currency = new Currency("CZK", "Česká republika", 1);

        Optional<Account> account = Optional.of(new Account(user, currency));
        Mockito.when(accountRepository.getAccountByUserAndCurrency(user, currency)).thenReturn(account);

        Optional<Account> returnedAccount = accountService.getByUserAndCurrency(user, currency);

        assertTrue(returnedAccount.isPresent());
        assertEquals(returnedAccount.get().getCurrency(), currency);
        assertEquals(returnedAccount.get().getUser(), user);
        assertEquals(returnedAccount.get().getBalance(), 0);
        verify(accountRepository, times(1)).getAccountByUserAndCurrency(user, currency);
    }
}
