package api.database.lmi.controller;

import api.database.lmi.model.Componente;
import api.database.lmi.repository.ComponenteRepository;
import api.database.lmi.service.ServiceRelatorio;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController /*Arquitetura REST*/
@RequestMapping(value = "/componentes")
public class ComponenteController {


    @Autowired /* de fosse CDI seria @Inject*/
    private ComponenteRepository componenteRepository;

    @Autowired
    private ServiceRelatorio serviceRelatorio;

    /* Serviço RESTful */

    /*componente por id*/
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Componente> init(@PathVariable(value = "id") Long id) {

        Optional<Componente> componente = componenteRepository.findById(id);

        return new ResponseEntity<Componente>(componente.get(), HttpStatus.OK);
    }

    /*---------------------------------------------------------------------------------*/
    /*listar componente*/
    @GetMapping(value = "/", produces = "application/json")
    @CachePut("cachecomponentes")
    public ResponseEntity<Page<Componente>> componente () throws InterruptedException{

        PageRequest page = PageRequest.of(0,10, Sort.by("nome"));

        Page<Componente> list = componenteRepository.findAll(page);

        //List<Componente> list = (List<Componente>) componenteRepository.findAll();

        return new ResponseEntity<Page<Componente>>(list, HttpStatus.OK);
    }

    /*---------------------------------------------------------------------------------*/
    /*listar componente por paginacao*/
    @GetMapping(value = "/page/{pagina}", produces = "application/json")
    @CachePut("cachecomponentes")
    public ResponseEntity<Page<Componente>> componentePagina (@PathVariable("pagina") int pagina) throws InterruptedException{

        PageRequest page = PageRequest.of(pagina,10, Sort.by("nome"));

        Page<Componente> list = componenteRepository.findAll(page);

        //List<Componente> list = (List<Componente>) componenteRepository.findAll();

        return new ResponseEntity<Page<Componente>>(list, HttpStatus.OK);
    }

    /*---------------------------------------------------------------------------------*/
    /*END-POINT consulta componente por nome*/
    @GetMapping(value = "/componentesPorNome/{nome}", produces = "application/json")
    public ResponseEntity<Page<Componente>> componentesPorNome (@PathVariable("nome") String nome) throws InterruptedException{

        PageRequest pageRequest = null;
        Page<Componente> list = null;

        if(nome == null || (nome != null && nome.trim().isEmpty()) || nome.equalsIgnoreCase("undefined")) {
            pageRequest = PageRequest.of(0, 10, Sort.by("nome"));
            list = componenteRepository.findAll(pageRequest);
        } else {
            pageRequest = PageRequest.of(0, 10, Sort.by("nome"));
            list = componenteRepository.findComponenteByNomePage(nome, pageRequest);
        }

        return new ResponseEntity<Page<Componente>>(list, HttpStatus.OK);
    }

    /*---------------------------------------------------------------------------------*/
    /*END-POINT consulta componente por nome*/
    @GetMapping(value = "/componentesPorNome/{nome}/page/{page}", produces = "application/json")
    public ResponseEntity<Page<Componente>> componentesPorNomePage (@PathVariable("nome") String nome, @PathVariable("page") int page) throws InterruptedException{

        PageRequest pageRequest = null;
        Page<Componente> list = null;

        if(nome == null || (nome != null && nome.trim().isEmpty()) || nome.equalsIgnoreCase("undefined")) {
            pageRequest = PageRequest.of(page, 10, Sort.by("nome"));
            list = componenteRepository.findAll(pageRequest);
        } else {
            pageRequest = PageRequest.of(page, 10, Sort.by("nome"));
            list = componenteRepository.findComponenteByNomePage(nome, pageRequest);
        }

        return new ResponseEntity<Page<Componente>>(list, HttpStatus.OK);
    }
    /*---------------------------------------------------------------------------------*/
    /*inserir componente*/
    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Componente> cadastrar(@RequestBody Componente componente) {

        Componente componenteSalvo = componenteRepository.save(componente);

        return new ResponseEntity<Componente>(componenteSalvo, HttpStatus.OK);
    }

    /*---------------------------------------------------------------------------------*/
    /*atualizar componente*/
    @PutMapping(value = "/", produces = "application/json")
    public ResponseEntity<Componente> atualizar(@RequestBody Componente componente) {
        /*outras rotinas antes de atualizar*/
        Componente componenteSalvo = componenteRepository.save(componente);

        return new ResponseEntity<Componente>(componenteSalvo, HttpStatus.OK);
    }

    /*---------------------------------------------------------------------------------*/
    /*delete componente*/
    @DeleteMapping(value = "/{id}", produces = "application/text")
    public String delete(@PathVariable("id") Long id) {

        componenteRepository.deleteById(id);

        return "Componente excluido com sucesso";
    }

    /*---------------------------------------------------------------------------------*/
    @GetMapping(value="/relatoriolist", produces = "application/text")
    public ResponseEntity<String> downloadRelatorioAllList(HttpServletRequest request) throws Exception{
        byte[] pdf = serviceRelatorio.gerarRelatorio("relatorio-componentestotal",
                request.getServletContext());

        String base64Pdf = "data:application/pdf;base64," + Base64.encodeBase64String(pdf);

        return new ResponseEntity<String>(base64Pdf, HttpStatus.OK);
    }

    /*---------------------------------------------------------------------------------*/
    @GetMapping(value="/relatoriopedidos", produces = "application/text")
    public ResponseEntity<String> downloadRelatorioPedidos(HttpServletRequest request) throws Exception{
        byte[] pdf = serviceRelatorio.gerarRelatorio("relatorio-componentespedidos",
                request.getServletContext());

        String base64Pdf = "data:application/pdf;base64," + Base64.encodeBase64String(pdf);

        return new ResponseEntity<String>(base64Pdf, HttpStatus.OK);
    }

}
