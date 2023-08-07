package com.comitfy.iotdbjobandrest;

import com.comitfy.iotdbjobandrest.dto.RemoveDTO;
import com.comitfy.iotdbjobandrest.service.IOTDBService;
import com.comitfy.iotdbjobandrest.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class IOTDBRemoveController {


    private final JwtTokenUtil jwtTokenUtil;


    private final IOTDBService iotdbService;


    @PostMapping("/remove")
    public ResponseEntity<String> getSecureData(@RequestHeader("Authorization") String token,@RequestBody RemoveDTO removeDTO) {
        // Tokenı alınan header'dan çıkart
        String jwtToken = token.substring("Bearer ".length());

        // Tokenı doğrula

        if (jwtTokenUtil.validateToken(jwtToken)) {
            // Token geçerliyse kullanıcıyı elde et
            String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

            iotdbService.delete(removeDTO.getSessionId());


            // Kullanıcı adını kullanarak istenilen işlemi yap
            // Örnek olarak, kullanıcının özel verilerini çek veya işlem yapabilirsiniz.

            return  new ResponseEntity<>("Silme işlemi başladı", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}


