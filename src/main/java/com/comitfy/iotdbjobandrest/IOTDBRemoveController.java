package com.comitfy.iotdbjobandrest;

import com.comitfy.iotdbjobandrest.dto.BaseResponseDTO;
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
    public ResponseEntity<BaseResponseDTO> getSecureData(@RequestHeader("Authorization") String token,@RequestBody RemoveDTO removeDTO) {
        // Tokenı alınan header'dan çıkart
        String jwtToken = token.substring("Bearer ".length());
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();

        // Tokenı doğrula

        if (jwtTokenUtil.validateToken(jwtToken)) {
            // Token geçerliyse kullanıcıyı elde et
            String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

            iotdbService.delete(removeDTO.getSessionId());
            baseResponseDTO.setSuccess(Boolean.TRUE);
            baseResponseDTO.setMessage("Silme işlemi başladı");


            // Kullanıcı adını kullanarak istenilen işlemi yap
            // Örnek olarak, kullanıcının özel verilerini çek veya işlem yapabilirsiniz.

            return  new ResponseEntity<>(baseResponseDTO, HttpStatus.UNAUTHORIZED);
        } else {
            baseResponseDTO.setSuccess(Boolean.FALSE);
            baseResponseDTO.setMessage("Kullanıcı doğrulama başarısız");
            return new ResponseEntity<>(baseResponseDTO, HttpStatus.UNAUTHORIZED);
        }
    }
}


