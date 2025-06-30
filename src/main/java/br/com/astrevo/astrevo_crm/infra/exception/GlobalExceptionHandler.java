package br.com.astrevo.astrevo_crm.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleClienteNaoEncontrado(ClienteNaoEncontradoException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DeletarClienteComStatusInativoException.class)
    public ResponseEntity<ErrorResponseDTO> handlerDeletarClienteComStatusInativo(DeletarClienteComStatusInativoException ex){
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DocumentoExistenteException.class)
    public ResponseEntity<ErrorResponseDTO> handlerDocumentoExistente(DocumentoExistenteException ex){
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    //MÉTODOS AUXILIARES
    private record ErrorResponseDTO(
            int status_code,
            String status,
            String message,
            LocalDateTime timestamp
    ) {}
}
