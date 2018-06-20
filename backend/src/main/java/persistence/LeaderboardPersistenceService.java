package persistence;

import entities.UserPointsMatchday;
import org.apache.commons.lang3.reflect.Typed;
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

    public List<UserPoints> getAlltimeLeaderboard() throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            TypedQuery<UserPoints> query = entityManager.createQuery("SELECT new resources.datamodel.UserPoints('-1', leaderboard.user.username, SUM(points)) FROM UserPointsMatchday leaderboard group by leaderboard.fk_user order by points desc", UserPoints.class);
            List<UserPoints> resultList = query.getResultList();
            if (resultList.isEmpty())
                throw new NoResultException();
            else {
                for (int i=0;i<resultList.size();i++)
                {
                    resultList.get(i).setPlatzierung(i);
                }
                return resultList;
            }
        });
    }

    public List<UserPoints> getSeasonLeaderboard(final String leagueID) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            TypedQuery<UserPoints> query = entityManager.createQuery("SELECT new resources.datamodel.UserPoints('-1', leaderboard.user.username, SUM(points)) FROM UserPointsMatchday leaderboard WHERE fk_league = :leagueID group by leaderboard.fk_user order by points desc", UserPoints.class);
            query.setParameter("leagueID", leagueID);
            List<UserPoints> resultList = query.getResultList();
            if (resultList.isEmpty())
                throw new NoResultException();
            else {
                for (int i=0;i<resultList.size();i++)
                {
                    resultList.get(i).setPlatzierung(i);
                }
                return resultList;
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
