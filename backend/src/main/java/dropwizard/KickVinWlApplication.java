package dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import resources.Login;
import resources.Resource;
import resources.UserResource;
import resources.UserResourceImpl;

public class KickVinWlApplication extends Application<KickVinWlConfiguration> {


    public static void main(String[] args) throws Exception {
        new KickVinWlApplication().run(args);
    }

    @Override
    public void run(KickVinWlConfiguration configuration, Environment environment) throws Exception {
        //DBInitializer.dropDatabase();
        //DBInitializer.init();

        final Resource resource = new Resource();
        environment.jersey().register(resource);

        final Login login = new Login();
        environment.jersey().register(login);

        final UserResource userResource = new UserResourceImpl();
        environment.jersey().register(userResource);
    }
}