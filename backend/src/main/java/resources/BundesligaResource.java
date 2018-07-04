package resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/{a:table}")
public abstract class BundesligaResource {

    Response response;

    @GET
    @Path("/{a:bl1|Bl1|BL1|bL1}")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response getBundesligaTable(@QueryParam("token") String token);

}
