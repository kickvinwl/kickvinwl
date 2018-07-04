package persistence;

import entities.League;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 *
 */
public class LeaguePersistenceService extends PersistenceService<League> {

    private static LeaguePersistenceService instance;

    /**
     *
     * @return
     */
    public static LeaguePersistenceService getInstance()
    {
        return instance = instance != null ? instance : new LeaguePersistenceService();
    }

    public League getCurrentLeagueByLeagueId(String leagueId) throws NoResultException{
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("select l from League l where l.leagueId = :leagueId order by season desc");
            query.setParameter("leagueId",leagueId);
            List<League> leagues = query.getResultList();
            if (leagues.isEmpty()) {
                throw new NoResultException();
            } else {
                return leagues.get(0);
            }
        });
    }
}

