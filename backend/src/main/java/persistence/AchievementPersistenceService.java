package persistence;

import entities.Achievement;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 *
 */
public class AchievementPersistenceService extends PersistenceService{

    private static AchievementPersistenceService instance;

    /**
     * @param achievementName
     * @return
     * @throws NoResultException
     */
    public Achievement getByAchievementName(final String achievementName)throws NoResultException{
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            String qlString = "SELECT a FROM achievement a where achievementName = :aName";
            Query query = entityManager.createQuery(qlString);
            query.setParameter("aName", achievementName);
            List<Achievement> achievement = query.getResultList();
            if (achievement.isEmpty())
                throw new NoResultException();
            return achievement.get(0);
        });
    }

    /**
     * @param userName
     * @return
     * @throws NoResultException
     */
    public List<Achievement> getAllByUserName(final String userName)throws NoResultException{
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            String qlString = "SELECT achievement FROM achievement as a " +
                              "INNER JOIN a.user as u " +
                              "WHERE u.userName = :uName";
            Query query = entityManager.createQuery(qlString);
            query.setParameter("uName", userName);
            List<Achievement> achievement = query.getResultList();
            if (achievement.isEmpty())
                throw new NoResultException();
            return achievement;
        });
    }

    /**
     * @return
     */
    public static AchievementPersistenceService getInstance() {
        return instance = instance != null ? new AchievementPersistenceService() : instance;
    }
}
