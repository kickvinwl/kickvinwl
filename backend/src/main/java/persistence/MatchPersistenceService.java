package persistence;

import entities.Match;

import javax.persistence.TypedQuery;

public class MatchPersistenceService extends PersistenceService<Match> {

    private static MatchPersistenceService instance;

    public static MatchPersistenceService getInstance()
    {
        return instance = instance != null ? instance : new MatchPersistenceService();
    }
    private MatchPersistenceService() {};

    public Match getMatchById(int matchID) {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            TypedQuery<Match> query = entityManager.createQuery("SELECT m FROM Match m WHERE matchID = :matchID", Match.class);
            query.setParameter("matchID", matchID);

            return query.getSingleResult();
        });
    }
}
