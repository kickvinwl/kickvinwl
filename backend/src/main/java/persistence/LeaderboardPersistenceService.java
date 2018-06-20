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
            TypedQuery<UserPoints> query = entityManager.createQuery("SELECT new resources.datamodel.UserPoints(-1, leaderboard.user.userName, SUM(points)) FROM UserPointsMatchday leaderboard group by leaderboard.user order by points desc", UserPoints.class);
            List<UserPoints> resultList = query.getResultList();
            if (resultList.isEmpty())
                throw new NoResultException();
            else {
                for (int i = 0; i < resultList.size(); i++) {
                    resultList.get(i).setPlatzierung(i);
                }
                return resultList;
            }
        });
    }

    public List<UserPoints> getSeasonLeaderboard(final String leagueID) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            TypedQuery<UserPoints> query = entityManager.createQuery("SELECT new resources.datamodel.UserPoints(-1, leaderboard.user.userName, SUM(leaderboard.points)) FROM UserPointsMatchday leaderboard WHERE leaderboard.matchday.id = :leagueID group by leaderboard.user order by points desc", UserPoints.class);
            query.setParameter("leagueID", leagueID);
            List<UserPoints> resultList = query.getResultList();
            if (resultList.isEmpty())
                throw new NoResultException();
            else {
                for (int i = 0; i < resultList.size(); i++) {
                    resultList.get(i).setPlatzierung(i);
                }
                return resultList;
            }
        });
    }

    public List<UserPoints> getGamedayLeaderboard(final int gamedayID) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            TypedQuery<UserPointsMatchday> query = entityManager.createQuery("SELECT leaderboard FROM UserPointsMatchday leaderboard WHERE leaderboard.matchday.id = :gamedayID order by points", UserPointsMatchday.class);
            query.setParameter("gamedayID", gamedayID);
            List<UserPointsMatchday> resultList = query.getResultList();
            if (resultList.isEmpty())
                throw new NoResultException();
            else {
                List<UserPoints> leaderboard = new ArrayList<>();
                for (int i = 0; i < resultList.size(); i++) {
                    UserPointsMatchday result = resultList.get(i);
                    leaderboard.add(new UserPoints(i + 1, result.getuser().getUserName(), result.getPoints()));
                }
                return leaderboard;
            }
        });
    }

    public void saveOrUpdatePoints(final UserPointsMatchday userPointsMatchday) {
        JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            TypedQuery<UserPointsMatchday> query = entityManager.createQuery("SELECT up FROM UserPointsMatchday up WHERE fk_user = :uID", UserPointsMatchday.class);
            query.setParameter("uID", userPointsMatchday.getuser().getId());

            if (query.getResultList().isEmpty()) {
                this.save(userPointsMatchday);
            } else {
                UserPointsMatchday userPointsMatchdayNew = query.getSingleResult();
                userPointsMatchday.setPoints(userPointsMatchday.getPoints());
                this.update(userPointsMatchdayNew);
            }
        });
    }
}
