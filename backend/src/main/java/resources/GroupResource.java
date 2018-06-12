package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/{a:group|Group}")
public abstract class GroupResource {

    /**
     *
     * @param token sessionKey of the user that is creating the group
     * @param groupName desired GroupName
     * @param groupPassword password to enter a group
     * @return Response of the request
     */
    @POST
    @Path("/{a:create|Create")
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract Response createGroup(@PathParam("token") String token,
                                         @PathParam("groupName") String groupName,
                                         @PathParam("groupPassword") String groupPassword );

}
