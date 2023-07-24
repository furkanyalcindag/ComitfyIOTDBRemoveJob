package com.comitfy.rabbitmqflink;

import com.comitfy.rabbitmqflink.service.IOTDBService;
import com.comitfy.rabbitmqflink.service.RestApiClientService;
import com.comitfy.rabbitmqflink.service.TelegramService;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

import java.io.File;


@SpringBootApplication
@EnableRetry
public class RabbitmqApplication implements CommandLineRunner {

    @Autowired
    IOTDBService iotdbService;

    @Autowired
    RestApiClientService restApiClientService;

    @Autowired
    TelegramService telegramService;

    public static void main(String[] args) throws IoTDBConnectionException {

        SpringApplication.run(RabbitmqApplication.class, args);


    }

    @Override
    public void run(String... args) throws Exception {


       /* ExecutionEnvironment env
                = ExecutionEnvironment.getExecutionEnvironment();*/




        /*EncodingService encodingService = new EncodingService();
        List<EKGMeasurementDTO> ekgMeasurementDTOList = new ArrayList<>();
        EKGMeasurementDTO ekgMeasurementDTO1 = new EKGMeasurementDTO("55", "4545",
                "dsdsdsds", "sdsdsa", 893478343l, true, true, 0, "12");
        EKGMeasurementDTO ekgMeasurementDTO2 = new EKGMeasurementDTO("55", "4545",
                "dsdsdsds", "sdsdsa", 893478343l, false, true, 0, "12");
        EKGMeasurementDTO ekgMeasurementDTO3 = new EKGMeasurementDTO("32", "4545",
                "dsdsdsds", "sdsdsa", 893478343l, false, true, 0, "12");
        EKGMeasurementDTO ekgMeasurementDTO4 = new EKGMeasurementDTO("66", "4545",
                "dsdsdsds", "sdsdsa", 893478343l, false, true, 0, "12");
        EKGMeasurementDTO ekgMeasurementDTO5 = new EKGMeasurementDTO("55", "4545",
                "dsdsdsds", "sdsdsa", 893478343l, false, true, 0, "12");
        EKGMeasurementDTO ekgMeasurementDTO6 = new EKGMeasurementDTO("2040", "4545",
                "dsdsdsds", "sdsdsa", 893478343l, false, true, 0, "12");
        EKGMeasurementDTO ekgMeasurementDTO7 = new EKGMeasurementDTO("55", "4545",
                "dsdsdsds", "sdsdsa", 893478343l, false, true, 0, "12");
        EKGMeasurementDTO ekgMeasurementDTO8 = new EKGMeasurementDTO("77", "4545",
                "dsdsdsds", "sdsdsa", 893478343l, false, true, 0, "12");

        ekgMeasurementDTOList.add(ekgMeasurementDTO1);
        ekgMeasurementDTOList.add(ekgMeasurementDTO2);
        ekgMeasurementDTOList.add(ekgMeasurementDTO3);
        ekgMeasurementDTOList.add(ekgMeasurementDTO4);
        ekgMeasurementDTOList.add(ekgMeasurementDTO5);
        ekgMeasurementDTOList.add(ekgMeasurementDTO6);
        ekgMeasurementDTOList.add(ekgMeasurementDTO7);
        ekgMeasurementDTOList.add(ekgMeasurementDTO8);

        byte[] sadsd = encodingService.encode(ekgMeasurementDTOList);
        System.out.println(sadsd);*/

       // String hash =restApiClientService.getHash(args[0]);

       // iotdbService.streaming(hash);


        /*File file = new File("C:/B46010C31A8C0ADE15AF7E14AF80855F.dat");
        telegramService.sendFile(file);*/

    }


}
