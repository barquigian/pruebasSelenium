package co.edu.uniquindio.grid.users.dto;

import co.edu.uniquindio.grid.users.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserView(@NotBlank(message = "El nombre de usuario es obligatorio.") String username,
                       @NotBlank(message = "La clave es obligatoria.") String password) {
    public User toUser(){
        return User.builder().username(username).password(BcryptUtil.bcryptHash(password)).build();
    }
}
