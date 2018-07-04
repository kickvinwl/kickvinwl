package resources;

import entities.Match;
import entities.Matchday;
import persistence.MatchPersistenceService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/manki")
@Produces(MediaType.APPLICATION_JSON)
public class Resource {


    @GET
    @Path("/nextMatch")
    public Match getName() {

        return MatchPersistenceService.getInstance().getNextMatch();
    }
}