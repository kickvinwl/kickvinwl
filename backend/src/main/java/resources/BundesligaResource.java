package resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/{a:bl/BL/Bl/bL}")
public abstract class BundesligaResource {

    Response response;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response getBundesligaTable(@QueryParam("token") String token);

}
