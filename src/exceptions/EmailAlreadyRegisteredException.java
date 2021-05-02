package exceptions;

public class EmailAlreadyRegisteredException extends Exception {
    public EmailAlreadyRegisteredException() {
        super("El email ingresado ya se encuentra registrado");
    }
}
