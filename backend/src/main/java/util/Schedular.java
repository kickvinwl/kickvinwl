package util;

import entities.Match;
import persistence.MatchPersistenceService;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Schedular {

    private final long SPIELZEIT = 120 * 60 * 1000;

    private ArrayList<Runnable> matchEndSchudls;
    private ScheduledExecutorService scheduledExecutorService;
    private Match nextEndingMatch;
    private Runnable nextEndingMatchRunnable;

    /**
     * Verwaltet die verschiedenen Schedular aufgaben
     */
    public Schedular() {
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        initMatchEndSchedul();
    }

    private void initMatchEndSchedul()
    {
        matchEndSchudls = new ArrayList<>();
        nextEndingMatch = MatchPersistenceService.getInstance().getNextMatch();
        nextEndingMatchRunnable = new Runnable() {
            @Override
            public void run() {
                nextEndingMatch = MatchPersistenceService.getInstance().getNextMatch();
                try {
                    matchEndSchudls.forEach(Runnable::run);
                }catch (Exception e)
                {
                    e.printStackTrace();
                    fireNextAfterMatchSchedul();
                }

                fireNextAfterMatchSchedul();
            }
        };
        fireNextAfterMatchSchedul();
    }

    private void fireNextAfterMatchSchedul()
    {
        if(nextEndingMatch.getMatchDateTime().after(new Date())) {
            scheduledExecutorService.schedule(nextEndingMatchRunnable, Math.max(nextEndingMatch.getMatchDateTime().getTime() - System.currentTimeMillis(), 1000) + SPIELZEIT, TimeUnit.MILLISECONDS);
        }
        else
        {
            System.out.println("keine Spiele in der Zukunft vorhanden!");
            scheduledExecutorService.schedule(nextEndingMatchRunnable, 1, TimeUnit.MINUTES);
        }
    }

    /**
     *
     * @param runnable wird 120 Minuten nach jedem Match ausgeführt
     */
    public void addMatchEndSchedul(Runnable runnable)
    {
        matchEndSchudls.add(runnable);
    }
}
