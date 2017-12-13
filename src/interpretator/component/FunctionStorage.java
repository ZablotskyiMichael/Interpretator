package interpretator.component;

import interpretator.model.Function;

public interface FunctionStorage {

    Function findByName(String name);

    Function findMain();
}
