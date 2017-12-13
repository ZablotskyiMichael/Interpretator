package interpretator.model.flow;

@SuppressWarnings("serial")
public class ReturnException extends RuntimeException{
    private final Object result;

    public ReturnException(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }
}
