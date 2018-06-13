package persistence;

import entities.Match;


import javax.persistence.TypedQuery;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;


public class MatchPersistenceService extends PersistenceService<Match> {

    private static MatchPersistenceService instance;

    public static MatchPersistenceService getInstance() {
        return instance = instance != null ? instance : new MatchPersistenceService();
    }

    private MatchPersistenceService() {
    }

    public Match getMatchById(int matchID) {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            TypedQuery<Match> query = entityManager.createQuery("SELECT m FROM Match m WHERE matchID = :matchID", Match.class);
            query.setParameter("matchID", matchID);

            Match result = query.getSingleResult();
            if (result == null) {
                throw new NoResultException();
            } else {
                return result;
            }
        });
    }

    public List<Match> getAllMatchesForMatchDay(final int matchDayID) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT g FROM game g WHERE matchdayId = :mdID", Match.class);
            query.setParameter("mdID", matchDayID);
            List<Match> matches = query.getResultList();
            if (matches.isEmpty()) {
                throw new NoResultException();
            } else {
                return matches;
            }
        });
    }

    public boolean exists(final int matchID) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT g FROM game g WHERE id = :matchID", Match.class);
            query.setParameter("matchID", matchID);
            List<Match> matches = query.getResultList();
            return !matches.isEmpty();
        });
    }


}
