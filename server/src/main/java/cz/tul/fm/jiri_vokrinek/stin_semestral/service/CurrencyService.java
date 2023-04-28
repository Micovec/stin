package cz.tul.fm.jiri_vokrinek.stin_semestral.service;

import cz.tul.fm.jiri_vokrinek.stin_semestral.dao.CurrencyJpaRepository;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Currency;
import org.springframework.stereotype.Component;

@Component
public class CurrencyService extends AbstractCrudService<String, Currency, CurrencyJpaRepository> {
    /**
     * Protected constructor to force extending services to define their repository
     *
     * @param repository Type of repository this service will read/write/update data from
     */
    protected CurrencyService(CurrencyJpaRepository repository) {
        super(repository);
    }

    @Override
    public boolean exists(Currency entity) {
        return repository.existsById(entity.getCode());
    }
}
