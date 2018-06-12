package resources;

import resources.datamodel.MatchTipTransform;
import resources.datamodel.Tip;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/{a:tip|Tip}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public abstract class TipResource {
    Response response;

//    @POST
//    @Path("/{a:set|Set}")
    public abstract Response setTip(String token, int gameday, String season, ArrayList<Tip> tipList);

    @GET
    @Path("/{a:get|Get}/{gameday}/{token}")
    public abstract MatchTipTransform getTipByToken(@PathParam("gameday") String gameday, @PathParam("token") String token);
}
