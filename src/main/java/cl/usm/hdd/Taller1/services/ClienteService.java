package cl.usm.hdd.Taller1.services;

import cl.usm.hdd.Taller1.entities.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClienteService{
    public Cliente crearCliente(Cliente cliente);
    public List<Cliente> listaClientes();
    public List<Cliente> listaClientesPorEstado(String estado);
}
