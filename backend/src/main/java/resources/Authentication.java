package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/{a:pfad|Pfad}")
@Produces(MediaType.APPLICATION_JSON)
public class Authentication {


    @GET
    @Path("/name/{string}")
    public String getName(@PathParam("string") String string) {
        return "Hallo " + string;
    }
}