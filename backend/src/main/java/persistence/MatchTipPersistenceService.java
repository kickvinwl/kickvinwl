package persistence;

import entities.MatchTip;
import entities.User;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class MatchTipPersistenceService extends PersistenceService<MatchTip> {

    private static MatchTipPersistenceService instance;

    public static MatchTipPersistenceService getInstance()
    {
        return instance = instance != null ? instance : new MatchTipPersistenceService();
    }

    private MatchTipPersistenceService() {}

    public  List<MatchTip> getByUserId(final int userID) throws NoResultException, SecurityException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT tip FROM MatchTip tip WHERE fk_user = :uID");
            query.setParameter("uID", userID);
            List<MatchTip> matchTips = query.getResultList();
            if(matchTips.isEmpty())
                throw new NoResultException();
            else
                return matchTips;
        });
    }

}
