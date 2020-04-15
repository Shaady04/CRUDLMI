package api.database.lmi.repository;

import api.database.lmi.model.Componente;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponenteRepository extends CrudRepository<Componente, Long> {
}
