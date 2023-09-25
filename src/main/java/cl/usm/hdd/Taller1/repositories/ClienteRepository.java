package cl.usm.hdd.Taller1.repositories;

import cl.usm.hdd.Taller1.entities.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends MongoRepository<Cliente, String> {
    List<Cliente> findByEstado(@Param("estado") String estado);
}
