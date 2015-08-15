package org.cob.dao;

import org.cob.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientJpaRepository extends JpaRepository<Client,Long>{

}
