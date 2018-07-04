package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/{a:leaderboard|Leaderboard}")
public abstract class LeaderboardResource {

	@GET
	@Path("/season/{group}")
	@Produces(MediaType.APPLICATION_JSON)
	public abstract Response getSeasonLeaderboardForGroup(@PathParam("group") String groupName);

	@GET
	@Path("/gameday/{group}")
	@Produces(MediaType.APPLICATION_JSON)
	public abstract Response getGamedayLeaderboardForGroup(@PathParam("group") String groupName);

	@GET
	@Path("/alltime/{group}")
	@Produces(MediaType.APPLICATION_JSON)
	public abstract Response getAlltimeLeaderboardForGroup(@PathParam("group") String groupName);

	@GET
	@Path("/season")
	@Produces(MediaType.APPLICATION_JSON)
	public abstract Response getSeasonLeaderboard();

	@GET
	@Path("/gameday")
	@Produces(MediaType.APPLICATION_JSON)
	public abstract Response getGamedayLeaderboard();

	@GET
	@Path("/alltime")
	@Produces(MediaType.APPLICATION_JSON)
	public abstract Response getAlltimeLeaderboard();

	@GET
	@Path("/calc")
	@Produces(MediaType.APPLICATION_JSON)
	public abstract Response calculateLeaderboard();
}
