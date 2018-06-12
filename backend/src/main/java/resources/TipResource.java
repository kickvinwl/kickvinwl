package resources;

import resources.datamodel.Tip;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/{a:tip|Tip}")
@Consumes(MediaType.APPLICATION_JSON)
public abstract class TipResource {
    Response response;

//    @POST
//    @Path("/{a:set|Set}")
    public abstract Response setTip(String token, ArrayList<Tip> tipList);

    @GET
    @Path("/{a:get|Get}/{gameday}/{token}")
    public abstract Response getTipByToken(@PathParam("gameday") String gameday, @PathParam("token") String token);
}
