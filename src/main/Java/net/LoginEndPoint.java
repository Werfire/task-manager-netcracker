package net;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("login")
public class LoginEndPoint {

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMethod(){
        return "This API needs login.";
    }
}
