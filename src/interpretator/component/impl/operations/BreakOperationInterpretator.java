package interpretator.component.impl.operations;

import interpretator.component.OperationInterpretator;
import interpretator.model.flow.BreakException;

public class BreakOperationInterpretator implements OperationInterpretator {

    @Override
    public String getKeyWord() {
        return "break";
    }

    @Override
    public void interpert(interpretator.model.Operation operation) {
        throw new BreakException();
    }
}
