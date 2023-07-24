package com.comitfy.rabbitmqflink;

import com.comitfy.rabbitmqflink.service.TelegramClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TelegramController {

    @Autowired
    TelegramClient telegramClient;


    @PostMapping("/sendPhoto/file")
    public void sendPhotoFileToTelegramGroup(@RequestParam("caption") String caption, @RequestPart("photo") File photo) throws Exception {
        List <String> chatIdList = new ArrayList();
        chatIdList.add("-950571960");

        for(String chatId: chatIdList){
            telegramClient.sendPhotoFile(chatId,caption, photo);
        }
    }

}
