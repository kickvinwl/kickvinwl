package resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/{a:leaderboard|Leaderboard}")
public abstract class LeaderboardResource {

    @GET
    @Path("/{a:get|Get}")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response getLeaderboardByGameday(@DefaultValue("-1") @QueryParam("gameday") int gameday);
}
