package payk96.rpg_shop.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidEnum(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException ife) {
            Class<?> targetType = ife.getTargetType();
            if (targetType.isEnum()) {
                Object[] enumConstants = targetType.getEnumConstants();
                List<String> allowedValues = Arrays.stream(enumConstants)
                        .map(Object::toString)
                        .toList();

                Map<String, Object> body = new LinkedHashMap<>();
                body.put("error", "Invalid enum value");
                body.put("invalidValue", ife.getValue());
                body.put("expectedType", targetType.getSimpleName());
                body.put("allowedValues", allowedValues);

                return ResponseEntity.badRequest().body(body);
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Invalid request", "message", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleEnumParamMismatch(MethodArgumentTypeMismatchException ex) {
        if (ex.getRequiredType() != null && ex.getRequiredType().isEnum()) {
            Object[] allowedValues = ex.getRequiredType().getEnumConstants();
            List<String> allowed = Arrays.stream(allowedValues)
                    .map(Object::toString)
                    .toList();

            Map<String, Object> body = new LinkedHashMap<>();
            body.put("error", "Invalid enum value in request parameter");
            body.put("parameter", ex.getName());
            body.put("invalidValue", ex.getValue());
            body.put("expectedType", ex.getRequiredType().getSimpleName());
            body.put("allowedValues", allowed);

            return ResponseEntity.badRequest().body(body);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Invalid parameter", "message", ex.getMessage()));
    }

}

