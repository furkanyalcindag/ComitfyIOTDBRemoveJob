package com.comitfy.iotdbjobandrest.dto;

import lombok.Data;

@Data
public class ResponseTokenDTO {
    private String status;

    private TokenDTO data;
}
