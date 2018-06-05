package persistence;

import entities.EntityGeneratedKey;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceService<E extends EntityGeneratedKey> {

    private final EntityManagerFactory emf;

    public PersistenceService() {
        emf = Persistence.createEntityManagerFactory("kickvinwl");
    }

    EntityManagerFactory entityManagerFactory() {
        return emf;
    }

    public void save(final E entity) {
        JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            entityManager.persist(entity);
        });
    }

    public void delete(final E entity) {
        JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            E merged = entityManager.merge(entity);
            entityManager.remove(merged);
        });
    }

    public void update(final E entity) {
        JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            E merged = entityManager.merge(entity);
            entityManager.persist(merged);
        });
    }
}