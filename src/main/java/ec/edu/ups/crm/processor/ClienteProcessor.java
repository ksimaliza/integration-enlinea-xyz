package ec.edu.ups.crm.processor;

import ec.edu.ups.crm.dto.ClienteDTO;
import javax.sql.DataSource;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author ksimaliza
 */
public class ClienteProcessor implements Processor {
    
    private final Logger LOGGER = LoggerFactory.getLogger(ClienteProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        String url = "jdbc:postgresql://127.0.0.1:5400/postgres";
        DataSource dataSource = dataSource(url);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        ClienteDTO clienteDTO =  (ClienteDTO) exchange.getIn().getBody();       
        definirClientesPorEmpresa(clienteDTO, exchange);
        String sql = "INSERT INTO cliente_xyz (\"tipoCanal\", \"numCedula\", nombre, apellido) VALUES(?,?,?,?);";
        jdbcTemplate.update(sql,clienteDTO.getTipoCanal(), clienteDTO.getNumCedula(), clienteDTO.getNombre(), clienteDTO.getApellido());
        
        exchange.getIn().setBody(clienteDTO);
        LOGGER.info("Cliente agregado {}",clienteDTO);
    }

    private void definirClientesPorEmpresa(ClienteDTO clienteDTO, Exchange exchange) {
        switch (clienteDTO.getTipoCanal()) {
            case "CANALDIGITAL":
                exchange.getIn().setHeader("empresa", "123");
                break;
            case "CANALOFICINA":
                exchange.getIn().setHeader("empresa", "abc");
                break;
            default:
                break;
        }
    }
    
    private static DataSource dataSource(String connectURI) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUsername("postgres");
        ds.setPassword("secret");
        ds.setUrl(connectURI);
        return ds;
    }
    
}
