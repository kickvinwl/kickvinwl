package resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Resource to get all valid messages, or to create and delete
 * messages as an administrator.
 */
@Path("/{a:news|News}")
public abstract class NewsfeedResource {
    Response response;

    /**
     * @param token the session key of the user
     * @return Response code 200 on success, JSON data of the valid news in the body <br>
     * Response code 401 if the token expired or does not exist
     */
    @GET
    @Path("/{a:get|Get}")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response getNews(@QueryParam("token") String token);

    /**
     * @param input input data in JSON format. Contains the token of the user that
     *              is sending the request and the data for the message.
     * @return Response code 200 on success,  <br>
     * Response code 400 if there is an error in the input data, <br>
     * Response code 401 if the token expired or does not exist or user is not an
     * administrator.
     */
    @POST
    @Path("/create")
    public abstract Response createNews(Map<String, String> input);

    /**
     * @param input  input data in JSON format. Contains the token of the user that
     *               is sending the request.
     * @param newsId id of the message that is to be deleted.
     * @return Response code 200 on success, <br>
     * Response code 400 if there is an error in the input data, <br>
     * Response code 401 if the token expired or does not exist or user is not an
     * administrator <br>
     * Response code 404 if the message with the passed id does not exist.
     */
    @POST
    @Path("/delete/{id}")
    public abstract Response deleteNews(Map<String, String> input, @PathParam("id") int newsId);

}
