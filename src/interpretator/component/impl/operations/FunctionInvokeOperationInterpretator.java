package interpretator.component.impl.operations;

import interpretator.component.OperationInterpretator;
import interpretator.model.Operation;

import static interpretator.Config.functionExpressionResolver;

public class FunctionInvokeOperationInterpretator implements OperationInterpretator {
    @Override
    public String getKeyWord() {
        return null;
    }

    @Override
    public boolean isSupport(Operation operation) {
        return functionExpressionResolver.isSupport(operation.getTokens());
    }

    @Override
    public void interpert(Operation operation) {
        functionExpressionResolver.resolve(operation.getTokens()).getValue();
    }
}
