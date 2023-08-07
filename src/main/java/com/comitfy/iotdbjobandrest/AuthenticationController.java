package com.comitfy.iotdbjobandrest;

import com.comitfy.iotdbjobandrest.dto.BaseResponseDTO;
import com.comitfy.iotdbjobandrest.dto.UserDTO;
import com.comitfy.iotdbjobandrest.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDTO> login(@RequestBody UserDTO user) {
        String token = authenticationService.login(user);
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        if (token != null) {
            baseResponseDTO.setMessage(token);
            baseResponseDTO.setSuccess(Boolean.TRUE);
            return ResponseEntity.ok(baseResponseDTO);
        } else {
            baseResponseDTO.setMessage("Kullanıcı doğrulama başarısız");
            baseResponseDTO.setSuccess(Boolean.FALSE);
            return ResponseEntity.badRequest().body(baseResponseDTO);
        }
    }
}
