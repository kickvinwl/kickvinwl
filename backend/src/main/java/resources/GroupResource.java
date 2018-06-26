package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/{a:group|Group}")
public abstract class GroupResource {

    /**
     * token         sessionKey of the user that is creating the group
     * groupName     desired GroupName
     * groupPassword password to enter a group
     * @return Response of the request
     */
    @POST
    @Path("/{a:create|Create")
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract Response createGroup(Map<String, String> paramsamsam);

    /**
     *
     * @param token     sessionKey of the user thas is leaving the group
     * @param groupName group name of the group that the user is attempting to leave
     * @return Response of the request
     */
//    @POST
//    @Path("{a:leave|Leave")
//    public abstract Response leaveGroup(String token,
//                                        String groupName);

}
