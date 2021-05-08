package exceptions;

public class NoMoreArticlesException extends Exception {
    public NoMoreArticlesException() {
        super("No hay mas articulos para eliminar");
    }
}
