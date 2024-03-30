package co.edu.uniquindio.grid.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;


@Entity
@Table(name = "USUARIOS")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @Column(length = 50)
    private String username;
    @Column(length = 100)
    private String password;
}
