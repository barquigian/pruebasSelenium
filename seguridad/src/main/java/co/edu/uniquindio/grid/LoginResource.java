package co.edu.uniquindio.grid;

import co.edu.uniquindio.grid.users.UserRepository;
import co.edu.uniquindio.grid.users.dto.UserView;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Path("/login")
public class LoginResource {

    private final UserRepository repository;

    @Inject
    public LoginResource(UserRepository repository) {
        this.repository = repository;
    }


    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public String login(UserView userView) {
        var user = repository.findById(userView.username());
        if(user.isEmpty() || ! BcryptUtil.matches(userView.password(),user.get().getPassword()) ){
            throw new WebApplicationException("Usuario o clave incorrecta", Response.Status.BAD_REQUEST);
        }
        return Jwt.issuer("https://grid.uniquindio.edu.co")
                .subject(userView.username())
                .groups("user")
                .expiresAt(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant())
                .sign();

//        return Jwt.issuer("grid.uniquindio.edu.co")
//                .subject(login.username())
//                .expiresAt(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant())
//                .sign(KeyUtils.generateSecretKey(SignatureAlgorithm.HS512));

//        return Jwt.issuer("grid.uniquindio.edu.co")
//                .subject(login.username())
//                .expiresAt(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant())
//                .innerSign(KeyUtils.generateSecretKey(SignatureAlgorithm.HS512)).encrypt(KeyUtils.generateSecretKey(KeyEncryptionAlgorithm.A256KW));
    }
}
