package util;

import entities.Match;
import persistence.MatchPersistenceService;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Schedular {

    private ArrayList<Runnable> matchEndSchudls;
    private ScheduledExecutorService scheduledExecutorService;
    private Match nextEndingMatch;

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
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                nextEndingMatch = MatchPersistenceService.getInstance().getNextMatch();
                matchEndSchudls.forEach(Runnable::run);

                if(nextEndingMatch.getMatchDateTime().after(new Date())) {
                    scheduledExecutorService.schedule(this, nextEndingMatch.getMatchDateTime().getTime() - System.currentTimeMillis() + 120 * 60 * 1000, TimeUnit.MILLISECONDS);
                }
                else
                {
                    System.out.println("keine Spiele in der Zukunft vorhanden!");
                    scheduledExecutorService.schedule(this, 1, TimeUnit.MINUTES);
                }
            }
        },nextEndingMatch.getMatchDateTime().getTime() - System.currentTimeMillis() + 120 * 60 * 1000, TimeUnit.MILLISECONDS);
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
