package exeception;

public class TransactionException extends Exception {
    public TransactionException() {
        super();
    }

    public TransactionException(String message) {
        super(message);
    }
}
