package com.comitfy.iotdbjobandrest;

import com.comitfy.iotdbjobandrest.dto.BaseResponseDTO;
import com.comitfy.iotdbjobandrest.dto.RemoveDTO;
import com.comitfy.iotdbjobandrest.dto.StreamDTO;
import com.comitfy.iotdbjobandrest.service.IOTDBService;
import com.comitfy.iotdbjobandrest.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@AllArgsConstructor
public class IOTDBStreamController {


    private final JwtTokenUtil jwtTokenUtil;


    private final IOTDBService iotdbService;


    @PostMapping("/start-streaming")
    public ResponseEntity<BaseResponseDTO> getSecureData(@RequestHeader("Authorization") String token,@RequestBody StreamDTO removeDTO) throws IoTDBConnectionException, IOException, StatementExecutionException {
        // Tokenı alınan header'dan çıkart
        String jwtToken = token.substring("Bearer ".length());
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();

        // Tokenı doğrula

        if (jwtTokenUtil.validateToken(jwtToken)) {
            // Token geçerliyse kullanıcıyı elde et
            String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

            iotdbService.streaming(removeDTO.getSessionId());

            baseResponseDTO.setSuccess(Boolean.TRUE);
            baseResponseDTO.setMessage("Streaming işlemi başladı");

            // Kullanıcı adını kullanarak istenilen işlemi yap
            // Örnek olarak, kullanıcının özel verilerini çek veya işlem yapabilirsiniz.

            return  new ResponseEntity<>(baseResponseDTO, HttpStatus.UNAUTHORIZED);
        } else {
            baseResponseDTO.setSuccess(Boolean.FALSE);
            baseResponseDTO.setMessage("Kullanıcı doğrulama başarısız");
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/trial-streaming")
    public ResponseEntity<BaseResponseDTO> getSecureDataTrial(@RequestHeader("Authorization") String token,@RequestBody StreamDTO removeDTO) throws IoTDBConnectionException, IOException, StatementExecutionException, NoSuchAlgorithmException {
        // Tokenı alınan header'dan çıkart
        String jwtToken = token.substring("Bearer ".length());
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();

        // Tokenı doğrula

        if (jwtTokenUtil.validateToken(jwtToken)) {
            // Token geçerliyse kullanıcıyı elde et
            String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

            iotdbService.denemeService(removeDTO.getSessionId());

            baseResponseDTO.setSuccess(Boolean.TRUE);
            baseResponseDTO.setMessage("Streaming işlemi başladı");

            // Kullanıcı adını kullanarak istenilen işlemi yap
            // Örnek olarak, kullanıcının özel verilerini çek veya işlem yapabilirsiniz.

            return  new ResponseEntity<>(baseResponseDTO, HttpStatus.UNAUTHORIZED);
        } else {
            baseResponseDTO.setSuccess(Boolean.FALSE);
            baseResponseDTO.setMessage("Kullanıcı doğrulama başarısız");
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}


