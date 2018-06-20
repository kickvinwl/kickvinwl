package persistence;

import entities.Matchday;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class MatchdayPersistenceService extends PersistenceService<Matchday> {

    private static MatchdayPersistenceService instance;

    public static MatchdayPersistenceService getInstance()
    {
        return instance = instance != null ? instance : new MatchdayPersistenceService();
    }

    private MatchdayPersistenceService() {};

    public Matchday getById(final int id) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT ma FROM Matchday ma WHERE id = :id");
            query.setParameter("id", id);
            List<Matchday> matchdays = query.getResultList();
            if(matchdays.isEmpty())
                throw new NoResultException();
            else
                return matchdays.get(0);
        });
    }

    // TODO test
    public List<Matchday> getAllMatchDaysForLeague(final String leagueID) {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT ma FROM Matchday ma WHERE fk_league = :id");
            query.setParameter("fk_league", leagueID);
            List<Matchday> matchdays = query.getResultList();
            if(matchdays.isEmpty())
                throw new NoResultException();
            else
                return matchdays;
        });
    }

    public boolean exists(final int matchDay){
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT us FROM Matchday us WHERE matchday = :md"); //TODO wenn matchday zu matchdayID gemacht wurde anpassen
            query.setParameter("md", matchDay);
            List<Matchday> matchdays = query.getResultList();
            return !matchdays.isEmpty();
        });
    }

    public Matchday getDefault()
    {
        return getById(1);
    }

    public void setDefault(Matchday matchday)
    {
        Matchday aDefault = getDefault();
        aDefault.setMatchday(matchday.getMatchday());
        update(aDefault);
    }
}
