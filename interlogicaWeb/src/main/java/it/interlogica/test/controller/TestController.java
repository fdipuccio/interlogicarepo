package it.interlogica.test.controller;

import it.interlogica.test.model.*;
import it.interlogica.test.service.*;
import org.apache.commons.csv.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.io.*;


@RestController
@RequestMapping(value = "test", produces = (MediaType.APPLICATION_JSON_VALUE))
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private FileService fileService;
    @Value( "${file.path}" )
    private String path;
    @Value( "${file.name}" )
    private String name;

    /**
     * Csv loaded  from classpath
     *
     * @param fileName the name of the file
     * @return Json response
     */
    @GetMapping("/check")
    public Response check(@RequestParam(value = "file", required = true, defaultValue = "phonenumbers") String fileName) {

        log.debug("servizio check -> filename: {}", fileName);
        Response res = new Response();

        try {
            File file = ResourceUtils.getFile("classpath:csv/" + fileName + ".csv");
            FileReader fileReader = new FileReader(file);
            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(fileReader);
            int wrongFormat = fileService.check(parser);
            populateResponse(res,wrongFormat);

        } catch (IOException e) {
            res.setMessage("error reading the file");
            res.setEsito("KO");
            log.error("error reading the file", e);
        }

        return res;
    }


    /**
     * Csv uploaded from user
     *
     * @param multipart file uploaded from user
     * @return Json response
     * @throws IOException
     */
    @PostMapping("/check")
    public Response upload(@RequestParam("file") MultipartFile multipart) {

        log.debug("servizio upload ...");

        InputStream is ;
        CSVParser parser ;
        Response res = new Response();
        try {
            is = multipart.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            parser = CSVFormat.DEFAULT.withHeader().parse(br);
            int wrongFormat = fileService.check(parser);

            populateResponse(res, wrongFormat);

        } catch (IOException e) {
            log.error("error uploading the file", e);
            res.setMessage("error uploading the file");
            res.setEsito("KO");
        }


        return res;
    }

    /**
     * The method prepare  the json response
     * @param res the response
     * @param wrongFormat the number of  wrong records
     */
    private void populateResponse(Response res, int wrongFormat) {
        res.setFileName(name);
        res.setPath(path);
        res.setMessage("File correctly loaded with " + wrongFormat + " phone numbers in the wrong format");
        res.setEsito("OK");
    }


}