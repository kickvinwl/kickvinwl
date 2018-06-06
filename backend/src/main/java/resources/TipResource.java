package resources;

import entities.MatchTip;
import entities.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/{a:tip|Tip}")
@Consumes(MediaType.APPLICATION_JSON)
public abstract class TipResource {


    @POST
    @Path("/{a:set|Set}")
    public abstract Response setTip(@QueryParam("sessionKey") String token);

    @GET
    @Path("/{a:get|Get}/{gameday}/{token}")
    public abstract Response getTipByToken(@PathParam("gameday") String gameday, @PathParam("token") String token);
}
