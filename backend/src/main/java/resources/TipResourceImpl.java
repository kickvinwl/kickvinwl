package resources;

import entities.User;
import persistence.UserPersistenceService;

import javax.ws.rs.core.Response;

public class TipResourceImpl extends TipResource {
    @Override
    public Response setTip(String token, TipResource.MatchTip tip) {
        Response.ResponseBuilder response = Response.accepted();

        //Token -> User

        //


        return response.build();
    }

    @Override
    public Response getTipByToken(String token) {
        Response.ResponseBuilder response = Response.accepted();

        //Token -> User
        User user = UserPersistenceService.getInstance().getBySessionKey(token);

        //User -> MatchTip
        MatchTip matchTip = null; //TODO Match aus MatchTipPersistenceService beziehen


        return response.entity(matchTip).build();
    }
}
