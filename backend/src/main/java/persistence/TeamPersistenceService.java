package persistence;

import entities.Team;
import util.TeamDeserializer;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Alle Datenbank-Funktionen für die zugehörige Datenklasse
 */
public class TeamPersistenceService extends PersistenceService<Team> {

    private static TeamPersistenceService instance;

    public static TeamPersistenceService getInstance()
    {
        return instance = instance != null ? instance : new TeamPersistenceService();
    }

    /**
     * private constructor so only getInstance is used
     */
    private TeamPersistenceService() {}

    /**
     *
     * @param teamId team id (API) of the searched team
     * @return Team instance
     * @throws NoResultException
     */
    public Team getByTeamId(final int teamId)throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            String qlString = "SELECT t FROM Team t where t.teamId = :tId";
            Query query = entityManager.createQuery(qlString);
            query.setParameter("tId", teamId);
            List<Team> teams = query.getResultList();
            if (teams.isEmpty())
                throw new NoResultException();
            return teams.get(0);
        });
    }

    public Team getByTeamName(final String teamName)throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            String qlString = "SELECT t FROM Team t where t.teamName = :tName";
            Query query = entityManager.createQuery(qlString);
            query.setParameter("tName", teamName);
            List<Team> teams = query.getResultList();
            if (teams.isEmpty())
                throw new NoResultException();
            return teams.get(0);
        });
    }

    public List<Team> getAllTeams() {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            String qlString = "SELECT t FROM Team t";
            Query query = entityManager.createQuery(qlString);
            List<Team> teams = query.getResultList();
            if (teams.isEmpty())
                throw new NoResultException();
            return teams;
        });
    }

}
