package login.exception;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(Exception e){
        super(e);
    }
    public UnauthorizedException(String msg){
        super(msg);
    }
}
