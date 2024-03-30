package co.edu.uniquindio.grid.users;

import co.edu.uniquindio.grid.users.dto.UserView;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@RunOnVirtualThread
@Path("/users")
public class UserResource {

    private final UserRepository repository;

    public UserResource() {
        repository = null;
    }

    @Inject
    public UserResource(UserRepository repository) {
        this.repository = repository;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response register(@Valid UserView userView,@Context UriInfo uriInfo) {
        var user = repository.save(userView.toUser());
        var urlNewUserResource = uriInfo.getAbsolutePathBuilder().path(user.getUsername()).build();
        return Response
                .created(urlNewUserResource)
                .entity(user)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Page<User> findAll(@QueryParam("page") Integer page, @QueryParam("per_page") Integer size) {
        if( page == null ){
            page = 0;
        }
        if( size == null ){
            size = 10;
        }
        return repository.findAll(PageRequest.of(page,size));
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "user"})
    public User find(@PathParam("username") String username) {
        return repository.findById(username).orElseThrow(()->new WebApplicationException("Recurso no enconrado", Response.Status.NOT_FOUND));
    }

    @DELETE
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "user"})
    public Response delete(@PathParam("username") String username) {
        var user = find(username);
        repository.delete( user );
        return Response.noContent().build();
    }

    @PUT
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "user"})
    public User update(@PathParam("username") String username,UserView userView) {
        var user = find(username);
        user.setPassword(userView.password());
        return repository.save(user);
    }
}
