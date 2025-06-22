package com.example.LatteList.exception;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> manejarEntidadNoEncontrada(EntityNotFoundException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> manejarArgumentoInvalido(IllegalArgumentException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", "Usuario no encontrado");
        respuesta.put("mensaje", ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Map<String, String>> handleSecurityException(SecurityException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", "Acceso denegado");
        respuesta.put("mensaje", ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.FORBIDDEN.value());
        body.put("error", "Acceso denegado");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CafeNotFoundException.class)
    public ResponseEntity<Map<String, String>> manejarCafeNoEncontrado(CafeNotFoundException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", "Café no encontrado");
        respuesta.put("mensaje", ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResenaNotFoundException.class)
    public ResponseEntity<Map<String, String>> manejarResenaNoEncontrada(ResenaNotFoundException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", "Resena invalida");
        respuesta.put("mensaje", ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EtiquetaNotFoundException.class)
    public ResponseEntity<Map<String, String>> manejarEtiquetaInvalida(EtiquetaNotFoundException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", "Etiqueta inválida");
        respuesta.put("mensaje", ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }


    //envia el mensaje de las restricciones del request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidacionCampos(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarErrorDeEnum(HttpMessageNotReadableException ex) {
        Map<String, String> respuesta = new HashMap<>();

        String mensajeOriginal = ex.getMessage();

        if (mensajeOriginal.contains("CostoPromedio")) {
            respuesta.put("error", "El campo 'costoPromedio' debe ser uno de los siguientes: $, $$ o $$$");
        } else if (mensajeOriginal.contains("TipoDeUsuario")) {
            respuesta.put("error", "El campo 'tipoDeUsuario' debe ser uno de los siguientes: CLIENTE, DUENIO o ADMIN");
       } else if (mensajeOriginal.contains("Etiquetas")) {
            respuesta.put("error", "Una de las etiquetas ingresadas no es válida, verificá que estén bien escritas");
        } else {
            respuesta.put("error", "Uno de los campos tiene un valor inválido, revisá los enums como 'costoPromedio', 'etiquetas', etc");
        }

        return ResponseEntity.badRequest().body(respuesta);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
