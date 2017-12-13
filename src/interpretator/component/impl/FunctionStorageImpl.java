package interpretator.component.impl;

import interpretator.component.FunctionStorage;
import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.Function;

import java.util.Map;

public class FunctionStorageImpl implements FunctionStorage {
    private final Map<String, Function> map;

    public FunctionStorageImpl(Map<String, Function> map) {
        this.map = map;
    }

    @Override
    public Function findByName(String name) {
        Function function = map.get(name);
        if(function != null) {
            return function;
        } else {
            throw new SyntaxInterpretatorException(
                    String.format("Function %s is not defined", name));
        }
    }

    @Override
    public Function findMain() {
        return findByName("main");
    }
}
