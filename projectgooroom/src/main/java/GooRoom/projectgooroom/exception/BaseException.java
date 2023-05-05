package GooRoom.projectgooroom.exception;

/**
 * 커스텀 예외 클래스
 */
public abstract class BaseException extends RuntimeException {
    public abstract BaseExceptionType getExceptionType();
}
