package com.comitfy.rabbitmqflink.consumer;

import com.comitfy.rabbitmqflink.service.IOTDBService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMQConsumer {

    @Autowired
    IOTDBService iotdbService;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

   //@RabbitListener(queues = {"${rabbitmq.queue.dat.request.name}"})
    public void consume(String message) throws IoTDBConnectionException, JsonProcessingException, StatementExecutionException {
        LOGGER.info(String.format("Received message -> %s", message));
       try {
           iotdbService.streaming(message);
       } catch (IOException e) {
           LOGGER.error(e.getMessage());

       }
       // iotdbService.insert(message);


    }
}
