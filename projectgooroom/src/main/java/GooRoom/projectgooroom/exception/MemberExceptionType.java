package GooRoom.projectgooroom.exception;

import org.springframework.http.HttpStatus;

public enum MemberExceptionType implements BaseExceptionType {
    ALREADY_EXIST_USER_EMAIL(409, HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    WRONG_PASSWORD(401,HttpStatus.UNAUTHORIZED, "비밀번호가 잘못되었습니다."),
    NOT_FOUND_MEMBER(404, HttpStatus.NOT_FOUND, "회원 정보가 없습니다.");


    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    MemberExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
