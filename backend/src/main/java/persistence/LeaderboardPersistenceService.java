package persistence;

import entities.UserPointsMatchday;
import resources.datamodel.UserPoints;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardPersistenceService extends PersistenceService<UserPointsMatchday> {

    private static LeaderboardPersistenceService instance;

    public static LeaderboardPersistenceService getInstance() {
        return instance = instance != null ? instance : new LeaderboardPersistenceService();
    }

    private LeaderboardPersistenceService() {
    }

    public List<UserPointsMatchday> getAlltimeLeaderboard() throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query;
            query = entityManager.createQuery("SELECT FROM UserPointsMatchday l");
            List<UserPointsMatchday> leaderboard = query.getResultList();
            if (leaderboard.isEmpty())
                throw new NoResultException();
            else
                return leaderboard;
        });
    }

    public List<UserPoints> getSeasonLeaderboard(final int leagueID) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT fk_user, SUM(points) FROM UserPointsMatchday leaderboard WHERE fk_league = :leagueID group by leaderboard.fk_user");
            query.setParameter("leagueID", leagueID);
            List<User> resultList = query.getResultList();
            if (resultList.isEmpty())
                throw new NoResultException();
            else {

                return null;
            }
        });
    }

    public List<UserPoints> getGamedayLeaderboard(final int gamedayID) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            TypedQuery<UserPointsMatchday> query = entityManager.createQuery("SELECT leaderboard FROM UserPointsMatchday leaderboard WHERE fk_matchday = :gamedayID order by points", UserPointsMatchday.class);
            query.setParameter("gamedayID", gamedayID);
            List<UserPointsMatchday> resultList = query.getResultList();
            if (resultList.isEmpty())
                throw new NoResultException();
            else {
                List<UserPoints> leaderboard = new ArrayList<>();
                for (int i=0;i<leaderboard.size();i++)
                {
                    UserPointsMatchday result = resultList.get(i);
                    leaderboard.add(new UserPoints(i+1, result.getuser().getUserName(), result.getPoints()));
                }
                return leaderboard;
            }
        });
    }
}
