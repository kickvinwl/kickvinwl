package resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/{a:news|News}")
public abstract class NewsfeedResource {
    Response response;

    /**
     *
     * @param token the session key of the user
     * @return a response with either the requested data or a
     * corresponding response code.
     */
    @GET
    @Path("/{a:get|Get}")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response getNews(@QueryParam("token") String token);

    /**
     *
     * @param input
     * @return
     */
    @POST
    @Path("/create")
    public abstract Response postNews(Map<String, String> input);

    @POST
    @Path("/delete/{id}")
    public abstract Response deleteNews(Map<String, String> input, @PathParam("id") int newsId);

}
