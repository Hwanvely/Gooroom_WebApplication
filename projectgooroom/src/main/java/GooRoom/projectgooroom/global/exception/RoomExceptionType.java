package GooRoom.projectgooroom.global.exception;

import org.springframework.http.HttpStatus;

public enum RoomExceptionType implements BaseExceptionType{

    NOT_FOUND_ADDRESS(404, HttpStatus.NOT_FOUND, "주소에 맞는 매물이 존재하지 않습니다.");

    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    RoomExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
