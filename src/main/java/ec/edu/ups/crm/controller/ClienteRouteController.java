package ec.edu.ups.crm.controller;

import ec.edu.ups.crm.dto.ClienteDTO;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ksimaliza
 */
@RestController
public class ClienteRouteController {

    @Autowired
    private ProducerTemplate producerTemplate;

    @PostMapping(value = "/addCliente")
    public ResponseEntity<String> addCliente(@RequestBody ClienteDTO cliente) {
        try {
            producerTemplate.requestBody("direct:crearCliente", cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (CamelExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

}
