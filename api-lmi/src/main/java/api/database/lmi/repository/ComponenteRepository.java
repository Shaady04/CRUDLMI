package api.database.lmi.repository;

import api.database.lmi.model.Componente;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponenteRepository extends JpaRepository<Componente, Long> {

     @Query("select u from Componente u where u.nome || u.tipo || u.caixa || u.gaveta like %?1%")
     List<Componente> findComponenteByNome(String nome);

     @Query("select u from Componente u where u.nome || u.tipo || u.caixa || u.gaveta like %?1%")
     default Page<Componente> findComponenteByNomePage(String nome, PageRequest pageRequest) {

         Componente componente = new Componente();
         componente.setNome(nome);


         /*Configurando para pesquisar por nome e paginacão*/
         ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                 .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

         Example<Componente> example = Example.of(componente, exampleMatcher);

         Page<Componente> retorno = findAll(example, pageRequest);

         return retorno;
     }
}
