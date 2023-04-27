package cz.tul.fm.jiri_vokrinek.stin_semestral.service;

import cz.tul.fm.jiri_vokrinek.stin_semestral.service.exception.EntityStateException;
import cz.tul.fm.jiri_vokrinek.stin_semestral.service.exception.NoEntityFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

/**
 * Common superclass for service logic of all entities supporting operations Create, Read, Update, Delete.
 *
 * @param <K> Type of (primary) key.
 * @param <E> Type of entity
 * @param <REPOSITORY> Type of repository. Must extend from JpaRepository<E, K>
 */
public abstract class AbstractCrudService<K, E, REPOSITORY extends JpaRepository<E, K>> {
    /**
     * Reference to data layer.
     */
    protected final REPOSITORY repository;

    /**
     * Protected constructor to force extending services to define their repository
     * @param repository Type of repository this service will read/write/update data from
     */
    protected AbstractCrudService(REPOSITORY repository) {
        this.repository = repository;
    }

    public abstract boolean exists(E entity);

    /**
     * Attempts to store a new entity. Throws exception if an entity with the same key is already stored.
     *
     * @param entity entity to be stored
     * @throws EntityStateException if an entity with the same key is already stored
     */
    @Transactional
    public E create(E entity) throws EntityStateException {
        if (exists(entity))
            throw new EntityStateException(entity);
        return repository.save(entity);
    }

    public Optional<E> readById(K id) {
        return repository.findById(id);
    }

    public Collection<E> readAll() {
        return repository.findAll();
    }

    /**
     * Attempts to replace an already stored entity.
     *
     * @param entity the new state of the entity to be updated; the instance must contain a key value
     * @throws EntityStateException if the entity cannot be found
     */
    @Transactional
    public E update(E entity) throws EntityStateException {
        if (exists(entity))
            return repository.save(entity);
        else
            throw new EntityStateException(entity);
    }

    @Transactional
    public void deleteById(K id) throws NoEntityFoundException {
        if (!repository.existsById(id))
            throw new NoEntityFoundException();
        repository.deleteById(id);
    }
}


