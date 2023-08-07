package com.comitfy.iotdbjobandrest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;

@Service
@Slf4j
public class TelegramClient {

    private String token = "1345369654:AAF9KVPI8Po-2fP2PNoldyWIr24XuRggOpI";
    private String telegramBaseUrl = "https://api.telegram.org/bot";
    private String apiUrl = telegramBaseUrl+token;

    private RestTemplate restTemplate;


    @Autowired
    public TelegramClient() {
        RestTemplate restTemplate = new RestTemplate();
        this.restTemplate  = restTemplate;
    }

    public void sendMessage(String message, String chatID) throws Exception {
        try {
            RestTemplate restTemplate = new RestTemplate();
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl+"/sendMessage")
                    .queryParam("chat_id", chatID)
                    .queryParam("text", message);
            ResponseEntity exchange = restTemplate.exchange(builder.toUriString().replaceAll("%20", " "), HttpMethod.GET, null, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error response : State code: {}, response: {} ", e.getStatusCode(), e.getResponseBodyAsString());
            throw e;
        } catch (Exception err) {
            log.error("Error: {} ", err.getMessage());
            throw new Exception("This service is not available at the moment!");
        }
    }

    public void sendPhotoFile(String chatID, String caption, File photo) throws Exception {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap body = new LinkedMultiValueMap();
          /*  ByteArrayResource fileAsResource = new ByteArrayResource(photo.getBytes()){
                @Override
                public String getFilename(){
                    return photo.getOriginalFilename();
                }
            };*/


            body.add("Content-Type", "image/png");
            body.add("document", new FileSystemResource(photo));
            HttpEntity<MultiValueMap> requestEntity = new HttpEntity(body, headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl+"/sendDocument")
                    .queryParam("chat_id", chatID)
                    .queryParam("caption", caption);
            System.out.println(requestEntity);
            String exchange = restTemplate.postForObject(
                    builder.toUriString().replaceAll("%20", " "),
                    requestEntity,
                    String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error response : State code: {}, response: {} ", e.getStatusCode(), e.getResponseBodyAsString());
            throw e;
        } catch (Exception err) {
            log.error("Error: {} ", err.getMessage());
            throw new Exception("This service is not available at the moment!");
        }
    }

    public void sendPhoto(String chatID, String caption, String photo) throws Exception {
        try {
            RestTemplate restTemplate = new RestTemplate();
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl+"/sendPhoto")
                    .queryParam("chat_id", chatID)
                    .queryParam("photo", photo)
                    .queryParam("caption", caption);
            String exchange = restTemplate.postForObject(
                    builder.toUriString().replaceAll("%20", " "),
                    null,
                    String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error response : State code: {}, response: {} ", e.getStatusCode(), e.getResponseBodyAsString());
            throw e;
        } catch (Exception err) {
            log.error("Error: {} ", err.getMessage());
            throw new Exception("This service is not available at the moment!");
        }
    }
}