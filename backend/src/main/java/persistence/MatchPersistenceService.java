package persistence;

import entities.Match;
import entities.Matchday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.persistence.TypedQuery;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Alle Datenbank-Funktionen für die zugehörige Datenklasse
 */
public class MatchPersistenceService extends PersistenceService<Match> {

    private static MatchPersistenceService instance;

    public static MatchPersistenceService getInstance() {
        return instance = instance != null ? instance : new MatchPersistenceService();
    }

    private MatchPersistenceService() {
    }

    public Match getMatchById(int matchID) {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            TypedQuery<Match> query = entityManager.createQuery("SELECT m FROM Match m WHERE id=:matchID", Match.class);
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
            Query query = entityManager.createQuery("SELECT g FROM Match g WHERE fk_matchday =: mdID", Match.class);
            query.setParameter("mdID", matchDayID);
            List<Match> matches = query.getResultList();
            if (matches.isEmpty()) {
                throw new NoResultException("kein Match mit fk_matchday(ID):" + matchDayID);
            } else {
                return matches;
            }
        });
    }

    public List<Match> getAllMatches() throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT g FROM Match g", Match.class);
            List<Match> matches = query.getResultList();
            if (matches.isEmpty()) {
                throw new NoResultException("keine Matches gefunden");
            } else {
                return matches;
            }
        });
    }

    public Match getLastMatch() {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            TypedQuery<Match> query = entityManager.createQuery("SELECT m FROM Match m WHERE matchDateTime > current_date() ORDER BY matchDateTime ASC", Match.class);
            List<Match> matches = query.getResultList();
            if (matches.isEmpty()) {
                List<Match> mts = getAllMatches();
                return mts.get(mts.size() - 1);
            }
            else {
                return matches.get(0);
            }
        });
    }

    public boolean exists(final int matchID) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT g FROM Match g WHERE id = :matchID", Match.class);
            query.setParameter("matchID", matchID);
            List<Match> matches = query.getResultList();
            return !matches.isEmpty();
        });
    }


}
