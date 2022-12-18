package isep.shared.exceptions;

public class InvalidOrderException extends Exception{
    public InvalidOrderException(){
        super("Cannot order 0 products!");
    }
}