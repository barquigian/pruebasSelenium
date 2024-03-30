package co.edu.uniquindio.grid;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

//@LoginConfig(authMethod = "MP-JWT")
//@ApplicationPath("/api")
//@ApplicationScoped
//@DeclareRoles({"user","admin"})

@Path("/hello")
public class GreetingResource {

    @GET
    @Path("/all")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @GET
    @Path("/secured")
    @RolesAllowed({ "user"})
    @Produces(MediaType.TEXT_PLAIN)
    public String helloSecured() {
        return "Hello RESTEasy";
    }
}
