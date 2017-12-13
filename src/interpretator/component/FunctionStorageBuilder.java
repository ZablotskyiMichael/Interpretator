package interpretator.component;

import interpretator.model.SourceLine;

public interface FunctionStorageBuilder {

    FunctionStorage build(SourceLine[] sourceLines);
}
