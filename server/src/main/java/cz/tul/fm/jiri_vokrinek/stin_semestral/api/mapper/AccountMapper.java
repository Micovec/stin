package cz.tul.fm.jiri_vokrinek.stin_semestral.api.mapper;

import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.AccountDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Account;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AccountMapper {

    public AccountDto toDto(Account account) {
        return new AccountDto(account.getCurrency().getCode(), account.getBalance());
    }

    public Collection<AccountDto> toDtos(Collection<Account> accounts) {
        return accounts.stream().map(this::toDto).toList();
    }
}
