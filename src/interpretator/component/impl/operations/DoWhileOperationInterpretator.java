package interpretator.component.impl.operations;

import interpretator.component.OperationInterpretator;
import interpretator.model.flow.BreakException;
import interpretator.model.operation.DoWhileOperation;
import interpretator.model.Operation;

public class DoWhileOperationInterpretator extends AbstractOperationInterpretator implements OperationInterpretator {

    @Override
    public String getKeyWord() {
        return "do";
    }

    @Override
    public void interpert(Operation operation) {
        DoWhileOperation doWhileOperation = (DoWhileOperation) operation;
        String[] conditionalExpression = doWhileOperation.getConditionalExpression();
        do {
            checkForTermination();
            try {
                invokeBlock(doWhileOperation.getTrueOperations());
            } catch (BreakException e) {
                break;
            }
        } while (evaluate(conditionalExpression));
    }
}
