package cl.usm.hdd.Taller1.services;

import cl.usm.hdd.Taller1.entities.Cliente;
import cl.usm.hdd.Taller1.repositories.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ClienteServiceImpl implements ClienteService{
    @Autowired
    private ClientesRepository clientesRepository;

    private List<String> estadosValidos = Stream.of(
            "Habilitado",
            "Con Deuda",
            "En lista negra").collect(Collectors.toList());

    public Cliente crearCliente(Cliente cliente) {
        return clientesRepository.save(cliente);
    }

    public List<Cliente> listaClientes() {
        return clientesRepository.findAll();
    }

    public List<Cliente> listaClientesPorEstado(String estado) {
         if (estadosValidos.stream().anyMatch(e->e.equalsIgnoreCase(estado))) {
            return clientesRepository.findByEstado(estado);
        }
        throw new RuntimeException("Estado inválido");  // Lanzamos una excepción si el estado no es válido
    }
}
