package it.interlogica.test.service;

import it.interlogica.test.dao.*;
import it.interlogica.test.model.*;
import org.apache.commons.csv.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

@Service
public class FileService {

    private static final Logger log = LoggerFactory.getLogger(FileService.class);
    private final String regEx= "^27\\d{9}$";

    @Value( "${file.path}" )
    private String path;
    @Value( "${file.name}" )
    private String name;

    @Autowired
    FileDao fileDao;

    @Autowired
    MyRepository myRepository;
    /**
     *
     * @param parser CSVParser
     */
    public int check(CSVParser parser) {
        log.info(" Start checking the file...");

        String[] errorRecord ;
        List<String[]> recordListToFile = new ArrayList<>();
        List<CsvRecordModel> recordListToDb = new ArrayList<>();
        for (CSVRecord record : parser) {
            String phone = record.get(1);
            Pattern pattern = Pattern.compile(regEx);
            if(!pattern.matcher(phone).matches()){
                errorRecord = new String[]{record.get(0), record.get(1)};
                CsvRecordModel csvRecordModel = new CsvRecordModel().setId(Long.valueOf(record.get(0))).setPhone(record.get(1));
                recordListToFile.add(errorRecord);
                recordListToDb.add(csvRecordModel);
                log.debug(" the id phone {} is  {}",record.get(1), pattern.matcher(phone).matches());
            }

       }

        if(!recordListToFile.isEmpty()) {
            write(recordListToFile);
            log.debug(recordListToFile.size()+ " Records written on file");

            /*
             differenti  implementazioni per  la persistenza
             */
            //1
            //fileDao.save(recordListToDb);
            //2
            //fileDao.insertWithQuery(recordListToDb);
            //3
            myRepository.saveAll(recordListToDb);
            log.debug(recordListToFile.size()+ " Records   saved on db");

        }
        log.info(" End checking the file...");

        return recordListToFile.size();
    }


    private void write(List<String[]> records ){
        try {
            FileWriter pw = new FileWriter(path+name,true);
            CSVPrinter csvPrinter = new CSVPrinter(pw, CSVFormat.DEFAULT.withHeader("ID", "PHONE"));
            csvPrinter.printRecords(records);
            csvPrinter.flush();
            pw.close();

        } catch (IOException  e) {
            log.error("Cannot  write  the file",e);
        }
    }
}
