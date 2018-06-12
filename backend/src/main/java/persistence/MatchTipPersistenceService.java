package persistence;

import entities.MatchTip;
import entities.User;
import resources.datamodel.Tip;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class MatchTipPersistenceService extends PersistenceService<MatchTip> {

    private static MatchTipPersistenceService instance;

    public static MatchTipPersistenceService getInstance() {
        return instance = instance != null ? instance : new MatchTipPersistenceService();
    }

    private MatchTipPersistenceService() {
    }

    public List<MatchTip> getByUserId(final int userID) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT tip FROM MatchTip tip WHERE fk_user = :uID");
            query.setParameter("uID", userID);
            List<MatchTip> matchTips = query.getResultList();
            if (matchTips.isEmpty())
                throw new NoResultException();
            else
                return matchTips;
        });
    }

    public void createOrUpdateMatchTip(User user, Tip tip) {
        JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            TypedQuery<MatchTip> query = entityManager.createQuery("SELECT t FROM MatchTip t WHERE fk_user = :uID AND fk_match = :matchID",MatchTip.class);
            query.setParameter("uID", user.getId());
            query.setParameter("matchID", tip.getMatchID());

            if (query.getResultList().isEmpty()){
                MatchTip matchTip = new MatchTip();
                matchTip.setGoalsTeam1(tip.getTeam1Tip());
                matchTip.setGoalsTeam2(tip.getTeam2Tip());
                matchTip.setOwner(user);
                matchTip.setTippedMatch(MatchPersistenceService.getInstance().getMatchById(tip.getMatchID()));
                this.save(matchTip);
            }
            else{
                MatchTip matchTip = query.getSingleResult();
                matchTip.setGoalsTeam1(tip.getTeam1Tip());
                matchTip.setGoalsTeam2(tip.getTeam2Tip());
                this.update(matchTip);
            }
        });
    }
}
