package GooRoom.projectgooroom.global.exception;

import org.springframework.http.HttpStatus;

public enum HomePostExceptionType implements BaseExceptionType{
    AUTHOR_MISMATCH(403, HttpStatus.FORBIDDEN, "작성자가 일치하지 않습니다."),
    ALREADY_PROGRESS(410, HttpStatus.BAD_REQUEST, "이미 진행중인 게시글이 존재합니다."),
    CANNOT_GET_HOME_POST(400, HttpStatus.BAD_REQUEST, "게시글을 조회할 수 없습니다."),
    CANNOT_UPDATE_HOME_POST(400, HttpStatus.BAD_REQUEST, "HomePost 수정 실패했습니다.");
    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    HomePostExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
