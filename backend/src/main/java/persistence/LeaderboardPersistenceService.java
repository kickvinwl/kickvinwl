package persistence;

import entities.UserPointsLeague;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.ws.rs.DefaultValue;
import java.util.List;

public class LeaderboardPersistenceService extends PersistenceService<UserPointsLeague> {

    private static LeaderboardPersistenceService instance;

    public static LeaderboardPersistenceService getInstance() {
        return instance = instance != null ? instance : new LeaderboardPersistenceService();
    }

    private LeaderboardPersistenceService() {
    }

    public List<UserPointsLeague> getLeaderboard(@DefaultValue("-1") final int leagueID) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query;
            if (leagueID != -1) {
                query = entityManager.createQuery("SELECT l FROM Leaderboard l WHERE fk_league = :leagueID");
                query.setParameter("leagueID", leagueID);
            }
            else {
                query = entityManager.createQuery("SELECT l FROM Leaderboard tip");
            }
            List<UserPointsLeague> leaderboard = query.getResultList();
            if (leaderboard.isEmpty())
                throw new NoResultException();
            else
                return leaderboard;
        });
    }
}
