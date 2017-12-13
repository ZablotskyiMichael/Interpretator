package interpretator.model.expression;

import interpretator.exception.RuntimeInterpretatorException;
import interpretator.model.Expression;

import static interpretator.Config.variableStorage;

public class VariableExpression implements Expression {

	private final String variableName;

	public VariableExpression(String variableName) {
		this.variableName = variableName;
	}

	public String getVariableName() {
		return variableName;
	}

	@Override
	public Object getValue() {
		if (variableStorage.isDefined(variableName)) {
			return variableStorage.get(variableName);
		} else {
			throw new RuntimeInterpretatorException(String.format("Variable '%s' not defined", variableName));
		}
	}

	public void setValue(Object value) {
		if (variableStorage.isDefined(variableName)) {
			variableStorage.put(variableName, value);
		} else {
			throw new RuntimeInterpretatorException(String.format("Variable '%s' not defined", variableName));
		}
	}
}
