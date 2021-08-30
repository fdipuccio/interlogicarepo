package it.interlogica.test.dao;


import it.interlogica.test.model.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.persistence.*;
import javax.transaction.*;
import java.util.*;

@Repository
public class FileDao {

    private static final Logger log = LoggerFactory.getLogger(FileDao.class);

    @Autowired
    MyRepository myRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * The method save a CsvRecordModel list with a JpaRepository  method
     * @param csvRecordModelList
     */
    @Transactional
    public void save(List<CsvRecordModel> csvRecordModelList) {
        csvRecordModelList.stream().forEach((csvRecordModel) -> myRepository.save(csvRecordModel));

    }



    /**
     * The method save a CsvRecordModel list with a native query
     * @param csvRecordModelList
     */
    @Transactional
    public void insertWithQuery(List<CsvRecordModel> csvRecordModelList) {
        for (CsvRecordModel csvRecordModel : csvRecordModelList) {
            entityManager.createNativeQuery("INSERT INTO WRONG_PHONE (id, phone) VALUES (?,?)")
                    .setParameter(1, csvRecordModel.getId())
                    .setParameter(2, csvRecordModel.getPhone())
                    .executeUpdate();
        }
    }
}
