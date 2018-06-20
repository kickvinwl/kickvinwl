package persistence;

import entities.MatchTip;
import entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resources.datamodel.Tip;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class MatchTipPersistenceService extends PersistenceService<MatchTip> {

    private static MatchTipPersistenceService instance;

    public static MatchTipPersistenceService getInstance() {
        if (instance == null) {
            instance = new MatchTipPersistenceService();
        }
        return instance;
    }

    private MatchTipPersistenceService() {
    }

    public List<MatchTip> getByUserId(final int userID) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT tip FROM MatchTip tip WHERE fk_user = :uID", MatchTip.class);
            query.setParameter("uID", userID);
            List<MatchTip> matchTips = query.getResultList();
            return matchTips;
        });
    }

    public MatchTip getByUserIdAndMatchId(final int userID, final int matchID) throws NoResultException {
        return JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            Query query = entityManager.createQuery("SELECT tip FROM MatchTip tip WHERE fk_user = :uID AND fk_match = :mID");
            query.setParameter("uID", userID);
            query.setParameter("mID", matchID);
            List<MatchTip> matchTips = query.getResultList();
            if (matchTips.isEmpty())
                throw new NoResultException("kein MatchTip gefunden zu userID:" + userID + " und matchID:" + matchID);
            else
                return matchTips.get(0);
        });
    }

    public void createOrUpdateMatchTip(User user, Tip tip) {
        JPAOperations.doInJPA(this::entityManagerFactory, entityManager -> {
            TypedQuery<MatchTip> query = entityManager.createQuery("SELECT t FROM MatchTip t WHERE fk_user = :uID AND fk_match = :matchID", MatchTip.class);
            query.setParameter("uID", user.getId());
            query.setParameter("matchID", tip.getmatchId());

            if (query.getResultList().isEmpty()) {
                Logger slf4jLogger = LoggerFactory.getLogger("MatchTipPers");
                MatchTip matchTip = new MatchTip();
                matchTip.setGoalsTeam1(tip.gethomeTip());
                matchTip.setGoalsTeam2(tip.getawayTip());
                matchTip.setOwner(user);
                slf4jLogger.info("empty matchtip");
                matchTip.setTippedMatch(MatchPersistenceService.getInstance().getMatchById(tip.getmatchId()));
                slf4jLogger.info("empty matchtip2");
                this.save(matchTip);
            } else {
                MatchTip matchTip = query.getSingleResult();
                matchTip.setGoalsTeam1(tip.gethomeTip());
                matchTip.setGoalsTeam2(tip.getawayTip());
                this.update(matchTip);
            }
        });
    }
}
