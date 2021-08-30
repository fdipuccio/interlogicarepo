package it.interlogica.test;

import it.interlogica.test.dao.*;
import it.interlogica.test.model.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit4.*;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbTest {

    @Autowired
    private MyRepository myRepository;

    @Test
    public void saveTest() {
        CsvRecordModel csvRecordModel = new CsvRecordModel(1L,"275555555");
        myRepository.save(csvRecordModel);

        Optional<CsvRecordModel> csvRecord = myRepository.findById(1L);
        assertEquals("275555555", csvRecord.get().getPhone());

    }
}

