package br.com.alex.gestao_vagas.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {
    private MessageSource messageSource;
    public ExceptionHandlerController(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleMethodAgrumentoNotValidException(MethodArgumentNotValidException e){
        List<ErrorMessageDTO> dtoList = e.getBindingResult().getFieldErrors().stream().map(err->{
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
                return new ErrorMessageDTO(message, err.getField());
        }).toList();

        return new ResponseEntity<>(dtoList, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
