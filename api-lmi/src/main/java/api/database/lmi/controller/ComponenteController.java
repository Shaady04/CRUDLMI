package api.database.lmi.controller;

import api.database.lmi.model.Componente;
import api.database.lmi.repository.ComponenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController /*Arquitetura REST*/
@RequestMapping(value = "/componente")
public class ComponenteController {


    @Autowired /* de fosse CDI seria @Inject*/
    private ComponenteRepository componenteRepository;

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
    public ResponseEntity<List<Componente>> componente () {

        List<Componente> list = (List<Componente>) componenteRepository.findAll();

        return new ResponseEntity<List<Componente>>(list, HttpStatus.OK);
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
}
