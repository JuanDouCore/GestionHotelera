package ar.com.juanferrara.GestionHotelera.presentation.advice;

import ar.com.juanferrara.GestionHotelera.domain.exceptions.*;
import ar.com.juanferrara.GestionHotelera.domain.dto.ExceptionErrorDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseBody
    public ResponseEntity<ExceptionErrorDTO> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionErrorDTO(exception.getMessage()));
    }

    @ExceptionHandler(value = {CategoriaException.class})
    @ResponseBody
    public ResponseEntity<ExceptionErrorDTO> handleCategoriaException(CategoriaException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionErrorDTO(exception.getMessage()));
    }

    @ExceptionHandler(value = {HotelException.class})
    @ResponseBody
    public ResponseEntity<ExceptionErrorDTO> handleHotelException(HotelException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ExceptionErrorDTO(exception.getMessage()));
    }

    @ExceptionHandler(value = {ClienteException.class})
    @ResponseBody
    public ResponseEntity<ExceptionErrorDTO> handleClienteException(ClienteException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ExceptionErrorDTO(exception.getMessage()));
    }

    @ExceptionHandler(value = {HabitacionException.class})
    @ResponseBody
    public ResponseEntity<ExceptionErrorDTO> handleHabitacionException(HabitacionException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ExceptionErrorDTO(exception.getMessage()));
    }

    @ExceptionHandler(value = {ReservaException.class})
    @ResponseBody
    public ResponseEntity<ExceptionErrorDTO> handleReservaException(ReservaException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ExceptionErrorDTO(exception.getMessage()));
    }

    //Exceptions del framework
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    public ResponseEntity<ExceptionErrorDTO> handleValidationConstraintException(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionErrorDTO(exception.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentException(MethodArgumentNotValidException exception){
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionErrorDTO> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionErrorDTO(exception.getMessage()));
    }
    //HttpMessageNotReadableException

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionErrorDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionErrorDTO(exception.getMessage()));
    }
}
