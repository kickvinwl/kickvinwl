package resources;


import resources.datamodel.SearchResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/{a:search|Search}")
public abstract class SearchResource {

    SearchResult searchResult;
    Response response;

    @GET
    @Path("/withParameters")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response searchForParameters(@QueryParam("token") String token, @QueryParam("searchString") String searchString);

}
