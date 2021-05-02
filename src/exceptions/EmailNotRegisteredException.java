package exceptions;

public class EmailNotRegisteredException extends Exception {
    public EmailNotRegisteredException() {
        super("Email no registrado");
    }
}
