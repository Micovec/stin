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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    @Test
    public void testGetUserAccounts() {
        User user = new User("test@test.test", "Test", "Test", "test");

        Currency currency0 = new Currency("CZK", "Česká republika", 1);
        Currency currency1 = new Currency("EUR", "EUM", 20);
        Currency currency2 = new Currency("FOR", "Bambania", 25.6);

        Account account0 = new Account(user, currency0);
        Account account1 = new Account(user, currency1);
        Account account2 = new Account(user, currency2);

        List<Account> accounts = new ArrayList<>();
        accounts.add(account0);
        accounts.add(account1);
        accounts.add(account2);

        Mockito.when(accountRepository.getAccountsByUser(user)).thenReturn(accounts);

        Collection<Account> returnedAccounts = accountService.getUserAccounts(user);

        assertEquals(returnedAccounts, accounts);
    }

    @Test
    public void testExists() {
        User user = new User("test@test.test", "Test", "Test", "test");
        Currency currency = new Currency("CZK", "Česká republika", 1);

        Account account = new Account(user, currency);
        Mockito.when(accountRepository.existsById(account.getId())).thenReturn(true);

        assertTrue(accountService.exists(account));
    }
}
