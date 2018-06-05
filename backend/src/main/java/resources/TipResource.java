package resources;

import entities.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/{a:tip|Tip}")
@Consumes(MediaType.APPLICATION_JSON)
public abstract class TipResource {


    @POST
    @Path("a:set|Set")
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract Response setTip(String token, MatchTip tip);

    @GET
    @Path("/{a:get|Get}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response getTipByToken(@PathParam("token") String token);

    class MatchTip
    {

    }
}
