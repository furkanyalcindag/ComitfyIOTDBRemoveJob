package com.comitfy.iotdbjobandrest.dto;

import lombok.Data;

@Data
public class TokenDTO {

    private String id;
    private String session;
    private String apiToken;
    private Long valueFor;

}
