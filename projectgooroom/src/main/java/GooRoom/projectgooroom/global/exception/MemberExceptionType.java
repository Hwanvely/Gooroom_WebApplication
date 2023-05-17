package GooRoom.projectgooroom.global.exception;

import org.springframework.http.HttpStatus;

public enum MemberExceptionType implements BaseExceptionType {
    //회원가입 시
    ALREADY_EXIST_USER_EMAIL(419, HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    ALREADY_EXIST_USER_NICKNAME(429, HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),

    //로그인 실패 시
    LOGIN_FAILURE(401,HttpStatus.UNAUTHORIZED, "등록되지 않은 이메일 또는 비밀번호를 잘못 입력했습니다."),

    //회원을 찾을 수 없는 경우
    NOT_FOUND_MEMBER(404, HttpStatus.NOT_FOUND, "회원 정보가 없습니다."),

    //유효하지 않은 토큰인 경우
    REFRESH_TOKEN_NOT_EXIST(480, HttpStatus.BAD_REQUEST, "만료 또는 유효하지 않은 ACCESS 토큰입니다."),
    INVALIDATE_ACCESS_TOKEN(499, HttpStatus.UNAUTHORIZED, "만료 또는 유효하지 않은 ACCESS 토큰입니다."),
    INVALIDATE_REFRESH_TOKEN(489, HttpStatus.UNAUTHORIZED, "만료 또는 유효하지 않은 REFRESH 토큰입니다."),

    //MemberInformation 이미 존재 시
    ALREADY_EXIST_MEMBERINFORMATION(409, HttpStatus.CONFLICT, "성향정보가 이미 존재합니다.");


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
