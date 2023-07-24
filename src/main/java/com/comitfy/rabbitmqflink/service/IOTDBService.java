package com.comitfy.rabbitmqflink.service;

import com.comitfy.rabbitmqflink.ActionType;
import com.comitfy.rabbitmqflink.configuration.IOTDBConfig;
import com.comitfy.rabbitmqflink.dto.EKGMeasurementDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.iotdb.isession.SessionDataSet;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.Session;
import org.apache.iotdb.tsfile.file.metadata.enums.CompressionType;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.file.metadata.enums.TSEncoding;
import org.apache.iotdb.tsfile.read.common.Field;
import org.apache.iotdb.tsfile.read.common.RowRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class IOTDBService {


    public static Set<String> cache = new HashSet<>();

    public static Map<String, Timer> timeSeriesCache = new ConcurrentHashMap<>();

    public static Map<String, EKGMeasurementDTO> measurementCacheByTimeSeriesPathMap = new HashMap<>();

    @Autowired
    IOTDBConfig iotdbConfig;

    @Autowired
    RestApiClientService restApiClientService;

    @Autowired
    TelegramService telegramService;




    public void deneme() throws IoTDBConnectionException, StatementExecutionException {


        Session session =
                new Session.Builder()
                        .host(iotdbConfig.getHost())
                        .port(Integer.valueOf(iotdbConfig.getPort())).
                        username(iotdbConfig.getUser()).password(iotdbConfig.getPassword()).build();


        // Session session = iotdbConfig.ioTDBConnectionManager().getSession();
        session.open();

        try {
            session.setStorageGroup(iotdbConfig.getStorageGroup());
        } catch (Exception e) {

        }


        TSEncoding encoding = TSEncoding.PLAIN;
        CompressionType compressionType = CompressionType.SNAPPY;
        String timeSeriesPath = "SDSDSDSD46" + ".own_" + "3002" + "_sid" + "343434343_3002".split("_")[0];

        session.createTimeseries(timeSeriesPath, TSDataType.INT32, encoding, compressionType);
    }


    @Async
    public void streaming(String ownSessionHash) throws IoTDBConnectionException, StatementExecutionException, IOException {

        try {
            Session session = iotdbConfig.ioTDBConnectionManager().getSession();

            String fileSeparator = System.getProperty("file.separator");


            String[] sessionArray =ownSessionHash.split("_");

            //String absoluteFilePath = fileSeparator + "C://" + fileSeparator + ownSessionHash + ".dat";
            String absoluteFilePath = fileSeparator + "var" + fileSeparator + sessionArray[0] + ".dat";

            FileOutputStream output = new FileOutputStream(absoluteFilePath, true);

            long startTS = 0;
            long endTS = 0;
            long count = 0;

            SessionDataSet sessionMinDataSet = session.executeQueryStatement("select min_time(val)  from root.ecg.*.*.sid" + sessionArray[0] + ";");
            SessionDataSet sessionMaxDataSet = session.executeQueryStatement("select max_time(val)  from root.ecg.*.*.sid" + sessionArray[0] + ";");

            SessionDataSet sessionCountDataSet = session.executeQueryStatement("select count(val)  from root.ecg.*.*.sid" + sessionArray[0] + ";");

            log.info("column name {}", sessionMinDataSet.getColumnNames().get(0));
            String sn = sessionMinDataSet.getColumnNames().get(0).split("\\.")[2];
            String own = sessionMinDataSet.getColumnNames().get(0).split("\\.")[3].split("own")[1];

            if (sessionMinDataSet.hasNext()) {
                Field min = sessionMinDataSet.next().getFields().get(0);
                startTS = min.getLongV();
            }

            if (sessionMaxDataSet.hasNext()) {
                Field max = sessionMaxDataSet.next().getFields().get(0);
                endTS = max.getLongV();
            }


            if (sessionCountDataSet.hasNext()) {
                Field max = sessionCountDataSet.next().getFields().get(0);
                count = max.getLongV();
            }

            long pageLimit = 10264;
            long pagesCount = count < 10264 ? 1 : (long) Math.ceil((double) count / 10264);

            int i = 0;
            List<EKGMeasurementDTO> ekgMeasurementDTOList = new ArrayList<>();
            int counterForEight =1;
            while (i < pagesCount) {

                long offset = i * pageLimit;


                SessionDataSet dataSet =
                        session.
                                executeQueryStatement
                                        ("select val,lead  from root.ecg.*.*.sid"
                                                + sessionArray[0] + " limit " + pageLimit + " offset " + offset + ";");



                while (dataSet.hasNext()) {

                    EKGMeasurementDTO ekgMeasurementDTO = new EKGMeasurementDTO();
                    RowRecord rowRecord = dataSet.next();
                    Field val = rowRecord.getFields().get(0);
                    String valStr = String.valueOf(val.getDoubleV());

                    Field lead = rowRecord.getFields().get(1);
                    Boolean isLead = lead.getBoolV();

                    ekgMeasurementDTO.setIsLead(isLead);
                    ekgMeasurementDTO.setVal(valStr);

                    ekgMeasurementDTOList.add(ekgMeasurementDTO);

                    if(counterForEight%8==0){
                        byte[] byteArr = EncodingService.encode(ekgMeasurementDTOList);

                        output.write(byteArr);

                        ekgMeasurementDTOList.clear();
                        System.out.println("for ownSessionHash"+ counterForEight+"of total:"+count);
                    }

                    counterForEight++;
                }


                i++;
            }

            output.close();

            File file = new File(absoluteFilePath);
            if (file.exists()) {
                telegramService.sendFile(file,ownSessionHash);
                restApiClientService.convertApiConsume(session, ownSessionHash, file);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }


}
