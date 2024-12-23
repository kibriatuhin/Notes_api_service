package com.notes_api_service.handler;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class GenericResponse {
    private HttpStatus responseStatus;
    private  String status;
    private  String message;
    private  Object data;
    public ResponseEntity<?> createResponseEntity() {
        Map<String,Object> map = new HashMap<>();
        map.put("status", status);
        map.put("message", message);

        if (!ObjectUtils.isEmpty(data)){
            map.put("data",  data);
        }
        return new ResponseEntity<>(map, responseStatus);
    }
}
