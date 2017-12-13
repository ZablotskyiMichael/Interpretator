package interpretator.component.impl.operations;

import interpretator.component.OperationInterpretator;
import interpretator.model.Expression;
import interpretator.model.Operation;
import interpretator.model.flow.ReturnException;

import static interpretator.Config.VOID;
import static interpretator.Config.expressionResolver;

public class ReturnOperationInterpretator implements OperationInterpretator {

    @Override
    public String getKeyWord() {
        return "return";
    }

    @Override
    public void interpert(Operation operation) {
        if(operation.getTokenCount() == 1) {
            throw new ReturnException(VOID);
        } else {
            Expression expression = expressionResolver.resolve(operation.getSubTokens(1));
            throw new ReturnException(expression.getValue());
        }
    }
}
