package persistence;

import entities.BundesligaTable;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 */
public class BundesligaTablePersistenceService extends PersistenceService<BundesligaTable> {

    private static BundesligaTablePersistenceService instance;

    /**
     *
     * @return instance of the persistence service
     */
    public static BundesligaTablePersistenceService getInstance()
    {
        return instance = instance != null ? instance : new BundesligaTablePersistenceService();
    }

    /**
     *
     * @param leagueId
     * @return Sorted list of all table entries for a corresponding league in order
     * @throws NoResultException
     */
    public List<BundesligaTable> getAllEntriesByLeagueId(final int leagueId) throws NoResultException{
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            String qlString = "SELECT b FROM BundesligaTable b WHERE league_id = :leagueId";
            Query query = entityManager.createQuery(qlString);
            query.setParameter("leagueId", leagueId);
            List<BundesligaTable> bundesligaTableEntries = query.getResultList();
            if(bundesligaTableEntries.isEmpty())
                throw new NoResultException();
            else {
                bundesligaTableEntries.forEach((BundesligaTable::setGoalDifference));
                Collections.sort(bundesligaTableEntries, new Comparator<BundesligaTable>(){
                    public int compare(BundesligaTable bl1, BundesligaTable bl2){
                        if (bl1.getLeaguePosition() > bl2.getLeaguePosition()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });
                return bundesligaTableEntries;
            }
        });
    }

    /**
     *
     * @param leagueId
     * @param pos
     * @return
     * @throws NoResultException
     */
    public BundesligaTable getEntryByPos(final int leagueId, final int pos) throws NoResultException{
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            String qlString = "SELECT b FROM bundesligatable b " +
                              "WHERE leagueId = :leagueId AND leaguePosition = :leaguePos";
            Query query = entityManager.createQuery(qlString);
            query.setParameter("leagueId", leagueId);
            query.setParameter("leaguePos", pos);
            List<BundesligaTable> bundesligaTableEntries = query.getResultList();
            if(bundesligaTableEntries.isEmpty())
                throw new NoResultException();
            else {
                 bundesligaTableEntries.get(0).setGoalDifference();
                return bundesligaTableEntries.get(0);
            }
        });
    }
}
