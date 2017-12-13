package interpretator.component;

import interpretator.model.Operation;

public interface OperationInterpretator {

	String getKeyWord();

	default boolean isSupport(Operation operation) {
		return getKeyWord().equals(operation.getToken(0));
	}

	void interpert(Operation operation);
}
