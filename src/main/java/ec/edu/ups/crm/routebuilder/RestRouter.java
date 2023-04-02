package ec.edu.ups.crm.routebuilder;

import ec.edu.ups.crm.dto.ClienteDTO;
import ec.edu.ups.crm.processor.ClienteProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

@Component
public class RestRouter extends RouteBuilder {

    JacksonDataFormat jacksonDataFormat = new JacksonDataFormat(ClienteDTO.class);

    @Override
    public void configure() throws Exception {
        from("direct:crearCliente")
                .routeId("CrearCliente")
                .process(new ClienteProcessor())
                .choice()
                .when(header("empresa").isEqualTo("123"))
                    .log("log:Enviando cliente a empresa 123")
                    .marshal(jacksonDataFormat).to("rest:post:/cliente?host=localhost:3000")
                .when(header("empresa").isEqualTo("abc"))
                    .log("log:Enviando cliente a empresa abc")
                    .marshal(jacksonDataFormat).to("rest:post:/api/cliente?host=localhost:5045")
                .otherwise().log("log:No Existe ")
                .end();
    }

}
