package resources;


import resources.datamodel.TipList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/{a:tip|Tip}")
public abstract class TipResource {
    Response response;

    @POST
    @Path("/{a:set|Set}")
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract Response setTip(TipList tips);

    @GET
    @Path("/{a:get|Get}")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response getTipByToken( @QueryParam("token") String token, @QueryParam("gameday") String gameday);
}
