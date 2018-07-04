package util;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Schedular {

    ArrayList<Runnable> matchEndSchudls;
    ScheduledExecutorService scheduledExecutorService;

    public Schedular() {

    }

    private void initMatchEndSchedul()
    {
        matchEndSchudls = new ArrayList<>();
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    public void addMatchEndSchedul(Runnable runnable)
    {
        matchEndSchudls.add(runnable);
    }
}
