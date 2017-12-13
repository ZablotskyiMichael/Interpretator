package interpretator.model.expression;

import interpretator.Config;
import interpretator.component.VariableStorage;
import interpretator.component.impl.VariableStorageImpl;
import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.Expression;
import interpretator.model.Function;
import interpretator.model.Operation;
import interpretator.model.flow.ReturnException;

import java.util.List;

import static interpretator.Config.VOID;
import static interpretator.Config.contextInterpretator;
import static interpretator.Config.functionStorage;

public class FunctionInvokeExpression implements Expression {
    private final String functionName;
    private final List<Expression> arguments;

    public FunctionInvokeExpression(String functionName, List<Expression> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public Object getValue() {
        Function function = functionStorage.findByName(functionName);
        validateFunctionArguments(function);
        VariableStorage functionVariableStorage = new VariableStorageImpl();
        setLocalArguments(function, functionVariableStorage);
        return invokeFunction(function, functionVariableStorage);
    }

    private Object invokeFunction(Function function, VariableStorage functionVariableStorage) {
        VariableStorage currentVariableStorage = Config.variableStorage;
        Config.variableStorage = functionVariableStorage;
        try {
            for (Operation operation : function.getBody()) {
                try {
                    contextInterpretator.interpert(operation);
                } catch (ReturnException e) {
                    return e.getResult();
                }
            }
            return VOID;
        } finally {
            Config.variableStorage = currentVariableStorage;
        }
    }

    private void setLocalArguments(Function function, VariableStorage functionVariableStorage) {
        for (int i = 0; i < function.getArguments().size(); i++) {
            String varName = function.getArguments().get(i);
            Object varValue = arguments.get(i).getValue();
            functionVariableStorage.put(varName, varValue);
        }
    }

    private void validateFunctionArguments(Function function) {
        if(arguments.size() != function.getArguments().size()) {
            throw new SyntaxInterpretatorException("Invalid count of arguments");
        }
    }
}
