package isep.shared.exceptions;

public class UndefinedHubsException extends Exception {
    public UndefinedHubsException(){
        super("Network must have hubs defined!");
    }
}
