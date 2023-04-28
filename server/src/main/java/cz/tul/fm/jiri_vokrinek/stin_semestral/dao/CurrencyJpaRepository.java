package cz.tul.fm.jiri_vokrinek.stin_semestral.dao;

import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyJpaRepository extends JpaRepository<Currency, String> {
}
