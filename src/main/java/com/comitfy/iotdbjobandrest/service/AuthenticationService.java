package com.comitfy.iotdbjobandrest.service;

import com.comitfy.iotdbjobandrest.dto.UserDTO;
import com.comitfy.iotdbjobandrest.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationService(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public String login(UserDTO userDTO) {

        if (userDTO.getHash().equals("e73a869cbcb8a3b8ee456a9cb6b352bc")) {
            return jwtTokenUtil.generateToken(userDTO.getHash());
        }

        return null; // Kullanıcı doğrulanamazsa null dönecektir.
    }
}