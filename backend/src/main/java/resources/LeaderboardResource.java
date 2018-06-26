package resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/{a:leaderboard|Leaderboard}")
public abstract class LeaderboardResource {

    @GET
    @Path("/season")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response getSeasonLeaderboard();

    @GET
    @Path("/gameday")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response getGamedayLeaderboard();

    @GET
    @Path("/alltime/{group}")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response getAlltimeLeaderboard(@DefaultValue("") @PathParam("group") String groupName));

    @GET
    @Path("/calc")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response calculateLeaderboard();
}
