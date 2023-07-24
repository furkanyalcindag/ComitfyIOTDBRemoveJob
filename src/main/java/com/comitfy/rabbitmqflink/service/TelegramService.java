package com.comitfy.rabbitmqflink.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class TelegramService {

    @Autowired
    TelegramClient telegramClient;



    @Async
    public void sendFile(File file, String caption) {



        try {

            //byte[] content = file.getInputStream().readAllBytes();

            /*MultipartFile result = new MockMultipartFile(name,
                    originalFileName, contentType, content);*/


            List<String> chatIdList = new ArrayList();
            chatIdList.add("-950571960");

            for (String chatId : chatIdList) {
                telegramClient.sendPhotoFile(chatId, caption, file);
            }
        } catch (Exception e) {
            log.error("telegram errpr {}", e.getMessage());
        }

    }
}
