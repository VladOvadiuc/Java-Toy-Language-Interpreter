package Exception;

public class FileException extends RuntimeException {
    public FileException(){}
    public FileException(String msg){
        super(msg);
    }
}