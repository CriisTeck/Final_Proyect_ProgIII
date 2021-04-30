package exceptions;

public class IncorrectEmailException extends Exception {
    public IncorrectEmailException() {
        super("Correo electronico Invalido");
    }
}
