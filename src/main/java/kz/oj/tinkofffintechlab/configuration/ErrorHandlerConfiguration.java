package kz.oj.tinkofffintechlab.configuration;

import com.google.api.gax.rpc.UnavailableException;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ErrorHandlerConfiguration extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<String> handle(StatusRuntimeException ex) {

        String message = ex.getMessage();
        if (message.equals("INVALID_ARGUMENT: Target language is invalid.")) {
            return ResponseEntity.badRequest().body("Не найден язык исходного сообщения");
        }

        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<String> handle(UnknownHostException ex) {

        String message = ex.getMessage();
        if (message.equals("translate.googleapis.com")) {
            return ResponseEntity.badRequest().body("Ошибка доступа к ресурсу перевода");
        }

        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(ExecutionException.class)
    public ResponseEntity<String> handle(ExecutionException ex) {

        Throwable cause = ex.getCause();
        if (cause instanceof UnavailableException && cause.getMessage().equals("io.grpc.StatusRuntimeException: UNAVAILABLE: Unable to resolve host translate.googleapis.com")) {
            return ResponseEntity.badRequest().body("Ошибка доступа к ресурсу перевода");
        }

        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception ex) {

        return ResponseEntity.internalServerError().body("Что-то пошло не так");
    }
}
