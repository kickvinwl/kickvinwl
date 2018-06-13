package persistence;

import entities.Team;
import util.TeamDeserializer;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

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

    public List<Team> getAllTeams() {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            String qlString = "SELECT * FROM User";
            Query query = entityManager.createQuery(qlString);
            List<Team> teams = query.getResultList();
            if (teams.isEmpty())
                throw new NoResultException();
            return teams;
        });
    }

    public static void persistTeams() {
        TeamDeserializer td = new TeamDeserializer();
        try{
            System.out.println("Starting to persist Teams ...");
            System.out.println();
            List<Team> teamList = td.deserializeTeam("https://www.openligadb.de/api/getavailableteams/bl1/2017");
            TeamPersistenceService tps = TeamPersistenceService.getInstance();
            for (Team team : teamList) {
                try {
                    tps.getByTeamId(team.getTeamId());
                } catch (NoResultException e) {
                    tps.save(team);
                }
            }
            System.out.println();
            System.out.println("Finished to persist Teams !!!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
