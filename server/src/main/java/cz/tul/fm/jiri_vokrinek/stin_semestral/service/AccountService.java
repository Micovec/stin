package cz.tul.fm.jiri_vokrinek.stin_semestral.service;

import cz.tul.fm.jiri_vokrinek.stin_semestral.dao.AccountJpaRepository;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Account;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Currency;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountService extends AbstractCrudService<Integer, Account, AccountJpaRepository> {
    /**
     * Protected constructor to force extending services to define their repository
     *
     * @param repository Type of repository this service will read/write/update data from
     */
    protected AccountService(AccountJpaRepository repository) {
        super(repository);
    }

    @Override
    public boolean exists(Account entity) {
        return repository.existsById(entity.getId());
    }

    public Optional<Account> getByUserAndCurrency(User user, Currency currency) {
        return repository.getAccountByUserAndCurrency(user, currency);
    }
}
