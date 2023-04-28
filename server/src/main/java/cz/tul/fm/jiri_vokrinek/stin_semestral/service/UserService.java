package cz.tul.fm.jiri_vokrinek.stin_semestral.service;

import cz.tul.fm.jiri_vokrinek.stin_semestral.dao.UserJpaRepository;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserService extends AbstractCrudService<String, User, UserJpaRepository> {
    /**
     * Protected constructor to force extending services to define their repository
     *
     * @param repository Type of repository this service will read/write/update data from
     */
    protected UserService(UserJpaRepository repository) {
        super(repository);
    }

    @Override
    public boolean exists(User entity) {
        return repository.existsById(entity.getEmail());
    }
}

