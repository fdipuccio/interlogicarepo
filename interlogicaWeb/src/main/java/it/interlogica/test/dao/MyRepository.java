package it.interlogica.test.dao;

import it.interlogica.test.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface MyRepository extends JpaRepository<CsvRecordModel, Long> {

}
