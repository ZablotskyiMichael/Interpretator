package interpretator.model.operation;

import interpretator.model.Operation;
import interpretator.model.SourceLine;

import java.util.Collections;
import java.util.List;

public class IfOperation extends SimpleOperation {
    private final List<Operation> trueOperations;
    private final List<Operation> falseOperations;

    public IfOperation(String[] tokens, SourceLine sourceLine,
                       List<Operation> trueOperations, List<Operation> falseOperations) {
        super(tokens, sourceLine);
        this.trueOperations = trueOperations;
        this.falseOperations = falseOperations;
    }

    public IfOperation(String[] tokens, SourceLine sourceLine,
                       List<Operation> trueOperations) {
        this(tokens, sourceLine, trueOperations, Collections.emptyList());
    }

    public IfOperation(String[] tokens, SourceLine sourceLine,
                       List<Operation> trueOperations, IfOperation elseIf) {
        this(tokens, sourceLine, trueOperations, Collections.singletonList(elseIf));
    }

    public List<Operation> getTrueOperations() {
        return trueOperations;
    }

    public List<Operation> getFalseOperations() {
        return falseOperations;
    }
}
