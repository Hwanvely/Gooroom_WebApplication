package GooRoom.projectgooroom.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 커스텀 예외 클래스 메소드
 */
public interface BaseExceptionType {
    int getErrorCode();

    HttpStatus getHttpStatus();

    String getErrorMessage();
}
