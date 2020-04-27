package api.database.lmi.service;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;

@Service
public class ServiceRelatorio  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public byte[] gerarRelatorio (String nomeRelatorio, ServletContext servletContext) throws Exception {

        /*obter a conexao com o banco de dados*/
        Connection connection = jdbcTemplate.getDataSource().getConnection();

        /*Carregar o caminho do arquivo Jasper*/
        String caminhoJasper = servletContext.getRealPath("relatorios")
                + File.separator + nomeRelatorio + ".jasper";

        /*Gerar o relatorio com os dados e conexao*/
        JasperPrint print = JasperFillManager.fillReport(caminhoJasper, new HashMap(), connection);

        /*Exporta para byte o PDF para fazer o download*/
        byte[] retorno = JasperExportManager.exportReportToPdf(print);

        connection.close();
        return retorno;
    }
}
