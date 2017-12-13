package interpretator.model.expression;

import static interpretator.Config.variableStorage;

import interpretator.component.BinaryCalculator;
import interpretator.model.Expression;

public class BinaryAssignmentExpression implements Expression {
	private final Expression operand1;
	private final BinaryCalculator binaryCalculator;
	private final Expression operand2;
	private final String varName;

	public BinaryAssignmentExpression(Expression operand1, BinaryCalculator binaryCalculator, Expression operand2,
			String varName) {
		this.operand1 = operand1;
		this.binaryCalculator = binaryCalculator;
		this.operand2 = operand2;
		this.varName = varName;
	}

	@Override
	public Object getValue() {
		Object res =  binaryCalculator.calculate(operand1,operand2);
		if (variableStorage.isDefined(varName)) {
			variableStorage.put(varName,res);
		};
		return res;
	}
}
