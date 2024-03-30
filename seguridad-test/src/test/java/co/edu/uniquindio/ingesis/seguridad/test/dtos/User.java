package co.edu.uniquindio.ingesis.seguridad.test.dtos;

import lombok.Builder;

@Builder
public record User (
        String username,
        String password ){

}
