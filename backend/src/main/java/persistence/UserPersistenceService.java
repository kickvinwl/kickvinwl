package persistence;

import entities.User;

import javax.persistence.Query;
import java.util.List;

public class UserPersistenceService extends PersistenceService {

    private static UserPersistenceService instance;

    public User get(final String userName) {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("select us from User us where userName = :userName");
            query.setParameter("userName", userName);
            List<User> user = query.getResultList();
            return user.get(0);
        });
    }

    public static UserPersistenceService getInstance()
    {
        return instance = instance != null ? instance : new UserPersistenceService();
    }
}
