package com.comitfy.rabbitmqflink.dto;

import lombok.Data;

@Data
public class ResponseTokenDTO {
    private String status;

    private TokenDTO data;
}
