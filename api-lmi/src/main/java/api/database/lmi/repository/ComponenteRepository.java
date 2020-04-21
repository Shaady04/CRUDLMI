package api.database.lmi.repository;

import api.database.lmi.model.Componente;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponenteRepository extends CrudRepository<Componente, Long> {

    @Query("select u from Componente u where u.nome like %?1%")
    List<Componente> findUserByNomeComponente(String nome);

}
