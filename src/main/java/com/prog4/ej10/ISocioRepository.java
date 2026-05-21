package com.prog4.ej10;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISocioRepository extends CrudRepository<Socio,  Long> {
}
