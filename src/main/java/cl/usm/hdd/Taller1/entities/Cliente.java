package cl.usm.hdd.Taller1.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document("clientes")
public class Cliente {
    private String nombre;
    private String apellidos;
    private String rut;
    private int numSerie;
    private String estado;
}
