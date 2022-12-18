package isep.shared.exceptions;

public class InvalidHubException extends Exception{
    public InvalidHubException(){
        super("Enterprise must be a hub!");
    }
}
