package GooRoom.projectgooroom.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 예외 발생 Handler
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    /**
     * 커스텀 예외 발생시 처리
     * @param exception
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity handleBaseEx(BaseException exception) {
        log.error("CustomException errorMessage(): {}", exception.getExceptionType().getErrorMessage());
        log.error("CustomException errorCode(): {}", exception.getExceptionType().getErrorCode());

        return new ResponseEntity(new ExceptionDto(exception.getExceptionType().getErrorCode()), exception.getExceptionType().getHttpStatus());
    }

    /**
     * 커스텀 예외 발생시 예외 코드 반환 DTO
     */
    @Data
    @AllArgsConstructor
    static class ExceptionDto {
        private Integer errorCode;
    }
}