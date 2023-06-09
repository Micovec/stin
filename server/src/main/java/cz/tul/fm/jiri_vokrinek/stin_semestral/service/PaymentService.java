package cz.tul.fm.jiri_vokrinek.stin_semestral.service;

import cz.tul.fm.jiri_vokrinek.stin_semestral.dao.PaymentJpaRepository;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Account;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Currency;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Payment;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.supporting.PaymentRequest;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class PaymentService extends AbstractCrudService<Integer, Payment, PaymentJpaRepository> {

    private final UserService userService;
    private final AccountService accountService;
    private final CurrencyService currencyService;

    /**
     * Protected constructor to force extending services to define their repository
     *
     * @param repository Type of repository this service will read/write/update data from
     */
    protected PaymentService(PaymentJpaRepository repository, UserService userService, AccountService accountService, CurrencyService currencyService) {
        super(repository);
        this.userService = userService;
        this.accountService = accountService;
        this.currencyService = currencyService;
    }

    @Override
    public boolean exists(Payment entity) {
        return repository.existsById(entity.getId());
    }

    public Payment pay(PaymentRequest paymentRequest) {
        User user = userService.readById(paymentRequest.email()).orElseThrow();
        Currency currency = currencyService.readById(paymentRequest.currencyCode()).orElseThrow();
        Currency targetCurrency = currencyService.readById(paymentRequest.targetCurrencyCode()).orElseThrow();

        Optional<Account> accountOpt = accountService.getByUserAndCurrency(user, targetCurrency);
        if (accountOpt.isEmpty()) {
            return create(new Payment(user, paymentRequest.amount(), currency, targetCurrency, false));
        }

        double actualAmount = currency.makeExchange(paymentRequest.amount(), targetCurrency);

        Account account = accountOpt.get();

        user.addAccount(account);
        userService.update(user);

        boolean valid = account.getBalance() + paymentRequest.amount() >= 0;
        if (valid) {
            account.add(actualAmount);
            accountService.update(account);
        }

        return create(new Payment(user, paymentRequest.amount(), currency, targetCurrency, valid));
    }

    public Collection<Payment> getUserPayments(User user) {
        return repository.getUserPayments(user.getEmail());
    }
}
