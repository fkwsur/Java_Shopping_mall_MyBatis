package shopping.mall.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
public class GlobalException {

    @ExceptionHandler
    public ResponseEntity<Object> handleRuntimeException(Exception e) {
        Map<String, String> map = new HashMap<>();
        map.put("error", e.toString());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}