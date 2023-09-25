package cl.usm.hdd.Taller1.controllers;

import cl.usm.hdd.Taller1.entities.Cliente;
import cl.usm.hdd.Taller1.services.ClienteService;
import cl.usm.hdd.Taller1.services.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class ClienteController {
    @Autowired
    private ClienteServiceImpl clienteService;
    @GetMapping("/mostrarClientes")
    public ResponseEntity<List<Cliente>> mostrarClientes() {
        try {
            return ResponseEntity.ok(clienteService.listaClientes());
        } catch (NullPointerException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/mostrarClientes/{estado}")
    public ResponseEntity<List<Cliente>> mostrarClientesPorEstado(@PathVariable String estado) {
        try {
            List<Cliente> clientes = clienteService.listaClientesPorEstado(estado);
            return ResponseEntity.ok(clientes);
        } catch (RuntimeException e) {  // Capturamos la excepción para indicar que el estado no es válido
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/ingresarCliente")
    public ResponseEntity<Cliente> ingresarCliente(@RequestBody Cliente cliente) {
        try {
            if (validacionRUT(cliente.getRut())) {
                return ResponseEntity.ok(clienteService.crearCliente(cliente));
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private Pattern pattern = Pattern.compile("(\\b[0-9|.]{1,10})\\-([K|k|0-9])");
    private boolean validacionRUT(String rut) {
        Matcher matcher = pattern.matcher(rut);
        if (matcher.find()) { // Si el rut sigue el patrón del RUN, se procede a validar por Modulo 11
            String rutBase = matcher.group(1).replace(".","");
            int numA = 0;
            for (int i=0; i < rutBase.length(); i++) {
                int multiplicador = (i%6)+2;
                int digito = Character.getNumericValue(rutBase.charAt(rutBase.length()-i-1));

                numA += multiplicador*digito;
            }
            int numB = (numA/11)*11;
            int digitoFinal = 11 - (numA - numB);
            String digitoVerificadorCalculado = Integer.toString(digitoFinal)   // 10->k , 11->0
                    .replace("10", "k")
                    .replace("11", "0");
            if (matcher.group(2).equalsIgnoreCase(digitoVerificadorCalculado)) {
                return true;
            }
        }
        return false;
    }

}
