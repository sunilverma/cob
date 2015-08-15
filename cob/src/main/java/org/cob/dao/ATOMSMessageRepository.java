package org.cob.dao;

import org.cob.model.ATOMSMessage;
import org.cob.model.Client;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository("atomsMessageRepo")
public interface ATOMSMessageRepository extends JpaRepository<ATOMSMessage,Long>{

}
