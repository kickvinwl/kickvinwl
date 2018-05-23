package resources;

import de.kvwl.commons.authentication.AuthenticationServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class Login {


    @GET
    public String getName(@QueryParam("name") String name, @QueryParam("pw") String pw) {

        return "Hallo " + AuthenticationServiceFactory.getInstance().isUserInGroup(name, pw,"GG_APP_Ermaechtigung_GOP_Kataloge_RW");
    }
}