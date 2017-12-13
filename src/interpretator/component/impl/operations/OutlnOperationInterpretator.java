package interpretator.component.impl.operations;

import interpretator.component.OperationInterpretator;
import interpretator.model.Operation;

public class OutlnOperationInterpretator extends OutOperationInterpretator implements OperationInterpretator {
	@Override
	public String getKeyWord() {
		return "outln";
	}

	@Override
	public void interpert(Operation operation) {
		super.interpert(operation);
		System.out.println();
	}
}
