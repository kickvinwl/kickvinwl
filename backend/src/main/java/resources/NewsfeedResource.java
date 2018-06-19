package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/{a:news|News}")
public abstract class NewsfeedResource {
    Response response;

    @GET
    @Path("/{a:get|Get}")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response getNews(String token);
}
