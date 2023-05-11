package GooRoom.projectgooroom.global.exception;

public class HomePostException extends BaseException{
    private BaseExceptionType exceptionType;

    public HomePostException(BaseExceptionType exceptionType){
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType(){
        return exceptionType;
    }
}
