package interpretator.component.impl.operations;

import static interpretator.Config.contextInterpretator;
import static interpretator.Config.variableStorage;

import interpretator.component.OperationInterpretator;
import interpretator.component.VariableStorage;
import interpretator.model.Operation;
import interpretator.model.flow.BreakException;
import interpretator.model.operation.ForOperation;

public class ForOperationInterpretator extends AbstractOperationInterpretator implements OperationInterpretator {

	@Override
	public String getKeyWord() {
		return "for";
	}

	@Override
	public void interpert(Operation operation) {
		ForOperation forOperation = (ForOperation) operation;
		VariableStorage currentVariableStorage = variableStorage;
		variableStorage = variableStorage.createChildVariableStorage();
		try {
			for (contextInterpretator.interpert(forOperation.getInitialization()); 
					evaluate(forOperation.getConditionalExpression()); 
						contextInterpretator.interpert(forOperation.getUpdate())) {
				
				checkForTermination();
				try {
					invokeBlock(forOperation.getTrueOperations());
				} catch (BreakException e) {
					break;
				}
			}
		} finally {
			variableStorage = currentVariableStorage;
		}
	}
}
