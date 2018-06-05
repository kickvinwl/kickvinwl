package resources;

import entities.Match;
import entities.MatchTip;
import entities.User;
import persistence.MatchTipPersistenceService;
import persistence.UserPersistenceService;

import javax.ws.rs.core.Response;
import java.util.List;

public class TipResourceImpl extends TipResource {

    @Override
    public Response setTip(String token) {
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
        List<MatchTip> matchTips = MatchTipPersistenceService.getInstance().getByUserId(user.getId());


        return response.entity(matchTips.toArray()).build();
    }
}
