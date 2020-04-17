package api.database.lmi.controller;

import api.database.lmi.model.Usuario;
import api.database.lmi.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController /*Arquitetura REST*/
@RequestMapping(value = "/usuario")
public class IndexController {

    @Autowired /* de fosse CDI seria @Inject*/
    private UsuarioRepository usuarioRepository;

    /* Serviço RESTful */

    /*usuario por id*/
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Usuario> init(@PathVariable (value = "id") Long id) {

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
    }

    /*---------------------------------------------------------------------------------*/
    /*listar usuarios*/
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<Usuario>> usuario () {

        List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();

        return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
    }

    /*---------------------------------------------------------------------------------*/
    /*inserir usuario*/
    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {

        String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
        usuario.setSenha(senhacriptografada);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
    }

    /*---------------------------------------------------------------------------------*/
    /*atualizar usuario*/
    @PutMapping(value = "/", produces = "application/json")
    public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) {

        Usuario userTemp = usuarioRepository.findUserByLogin(usuario.getLogin());

        if(!userTemp.getSenha().equals(usuario.getSenha())) {
            String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
            usuario.setSenha(senhacriptografada);
        }

        /*outras rotinas antes de atualizar*/
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
    }

    /*---------------------------------------------------------------------------------*/
    /*delete usuario*/
    @DeleteMapping(value = "/{id}", produces = "application/text")
    public String delete(@PathVariable("id") Long id) {

        usuarioRepository.deleteById(id);

        return "Usuário excluido com sucesso";
    }
}
