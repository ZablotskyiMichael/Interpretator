package interpretator.component.impl.operations;

import static interpretator.Config.variableStorage;
import static interpretator.Config.variableVerificator;

import java.util.Scanner;

import interpretator.component.OperationInterpretator;
import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.Operation;

public class InOperationInterpretator implements OperationInterpretator {
	
	@Override
	public String getKeyWord() {
		return "in";
	}

	@Override
	public void interpert(Operation operation) {
		validateOperation(operation);
        String varName = operation.getToken(1);
        variableVerificator.verify(varName);
        variableStorage.put(varName, readVariableValue(varName));
	}

	@SuppressWarnings("resource")
	private Object readVariableValue(String varName) {
		System.out.println(String.format("Input %s:", varName));
		return new Scanner(System.in).nextInt();
	}

	private void validateOperation(Operation operation) {
		if(operation.getTokenCount() == 1) {
            throw new SyntaxInterpretatorException("Missing expression");
        } else if (operation.getTokenCount() > 2) {
        	throw new SyntaxInterpretatorException("The length of the expression is exceeded");
		}
	}
}
