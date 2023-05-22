package GooRoom.projectgooroom.global.exception;

public class RoomException extends BaseException {

    private BaseExceptionType exceptionType;

    public RoomException(BaseExceptionType exceptionType){
        this.exceptionType = exceptionType;
    }
    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
