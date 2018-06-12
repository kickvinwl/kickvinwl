package resources;


import resources.datamodel.MatchTipTransform;
import resources.datamodel.Tip;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Optional;

@Path("/{a:tip|Tip}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public abstract class TipResource {
    Response response;

//    @POST
//    @Path("/{a:set|Set}")
    public abstract Response setTip(String token, ArrayList<Tip> tipList);

    @GET
    @Path("/{a:get|Get}")
    public abstract Response getTipByToken( @QueryParam("token") String token, @QueryParam("gameday") Optional<String> gameday);
}
