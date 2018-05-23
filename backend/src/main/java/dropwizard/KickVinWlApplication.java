package dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import resources.Resource;

public class KickVinWlApplication extends Application<KickVinWlConfiguration> {


    public static void main(String[] args) throws Exception {
        new KickVinWlApplication().run(args);
    }

    @Override
    public void run(KickVinWlConfiguration configuration, Environment environment) throws Exception {
        final Resource resource = new Resource();
        environment.jersey().register(resource);
    }
}
