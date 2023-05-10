package GooRoom.projectgooroom.global.exception;

/**
 * 회원 관련 예외
 */
public class MemberException extends  BaseException{

    private BaseExceptionType exceptionType;

    public MemberException(BaseExceptionType exceptionType){
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType(){
        return exceptionType;
    }
}
