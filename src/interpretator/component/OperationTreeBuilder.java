package interpretator.component;

import interpretator.model.Operation;
import interpretator.model.SourceLine;

public interface OperationTreeBuilder {

	Operation[] build(SourceLine[] sourceLines);
}
