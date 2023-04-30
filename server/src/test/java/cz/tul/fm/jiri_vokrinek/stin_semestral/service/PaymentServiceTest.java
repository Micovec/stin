package cz.tul.fm.jiri_vokrinek.stin_semestral.service;

import cz.tul.fm.jiri_vokrinek.stin_semestral.dao.PaymentJpaRepository;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Account;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Currency;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Payment;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.supporting.PaymentRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    PaymentService paymentService;

    @Mock
    PaymentJpaRepository paymentRepository;

    @Mock
    UserService userService;

    @Mock
    CurrencyService currencyService;

    @Mock
    AccountService accountService;

    @Test
    public void testExists() {
        User user = new User("test@test.test", "Test", "Test", "test");

        Currency sourceCurrency = new Currency("CZK", "Česká republika", 1);
        Currency targetCurrency = new Currency("EUR", "EUM", 20);

        Payment payment = new Payment(user, 10, sourceCurrency, targetCurrency, true);
        Mockito.when(paymentRepository.existsById(payment.getId())).thenReturn(true);

        assertTrue(paymentRepository.existsById(payment.getId()));
    }

    @Test
    public void testPayPositiveBalance() {
        PaymentRequest request = new PaymentRequest("test@test.test", "CZK", "EUR", 20);
        Optional<User> user = Optional.of(new User("test@test.test", "Test", "Test", "test"));
        Optional<Currency> sourceCurrency = Optional.of(new Currency("CZK", "Česká republika", 1));
        Optional<Currency> targetCurrency = Optional.of(new Currency("EUR", "EUM", 20));
        Optional<Account> account = Optional.of(new Account(user.get(), targetCurrency.get()));

        Mockito.when(userService.readById(user.get().getEmail())).thenReturn(user);
        Mockito.when(currencyService.readById(sourceCurrency.get().getCode())).thenReturn(sourceCurrency);
        Mockito.when(currencyService.readById(targetCurrency.get().getCode())).thenReturn(targetCurrency);
        Mockito.when(accountService.getByUserAndCurrency(user.get(), targetCurrency.get())).thenReturn(account);

        Payment payment = new Payment(user.get(), sourceCurrency.get().makeExchange(request.amount(), targetCurrency.get()), sourceCurrency.get(), targetCurrency.get(), true);
        Mockito.when(paymentRepository.existsById(payment.getId())).thenReturn(false);

        paymentService.pay(request);

        assertTrue(payment.isValid());
    }

    @Test
    public void testGetUserPayments() {
        PaymentRequest request = new PaymentRequest("test@test.test", "CZK", "EUR", 20);
        Optional<User> user = Optional.of(new User(request.email(), "Test", "Test", "test"));
        Optional<Currency> currency0 = Optional.of(new Currency("CZK", "Česká republika", 1));
        Optional<Currency> currency1 = Optional.of(new Currency("EUR", "EUM", 20));

        Payment payment0 = new Payment(user.get(), currency0.get().makeExchange(request.amount(), currency1.get()), currency0.get(), currency1.get(), true);
        Payment payment1 = new Payment(user.get(), currency1.get().makeExchange(request.amount(), currency0.get()), currency1.get(), currency0.get(), true);

        List<Payment> payments = new ArrayList<>();
        payments.add(payment0);
        payments.add(payment1);

        Mockito.when(paymentRepository.getUserPayments(user.get().getEmail())).thenReturn(payments);

        Collection<Payment> returnedPayments = paymentService.getUserPayments(user.get());

        assertEquals(payments, returnedPayments);
    }
}
