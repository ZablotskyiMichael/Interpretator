package interpretator.component.impl.expressionresolver;

import interpretator.component.BinaryCalculator;
import interpretator.component.ExpressionResolver;
import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.expression.BinaryAssignmentExpression;
import interpretator.model.expression.BinaryExpression;
import interpretator.model.Expression;

import java.util.Map;

public class BinaryExpressionResolver implements ExpressionResolver {
	private ExpressionResolver singleExpressionResolver;
	private Map<String, BinaryCalculator> binaryCalculatorMap;
	private Map<String, BinaryCalculator> binaryAssignmentCalculatorMap;

	public BinaryExpressionResolver(ExpressionResolver singleExpressionResolver,
			Map<String, BinaryCalculator> binaryCalculatorMap,
			Map<String, BinaryCalculator> binaryAssignmentCalculatorMap) {
		this.singleExpressionResolver = singleExpressionResolver;
		this.binaryCalculatorMap = binaryCalculatorMap;
		this.binaryAssignmentCalculatorMap = binaryAssignmentCalculatorMap;
	}

	@Override
	public boolean isSupport(String... tokens) {
		return tokens.length == 3;
	}

	@Override
	public Expression resolve(String... tokens) {
		Expression operand1 = singleExpressionResolver.resolve(tokens[0]);
		String operator = tokens[1];
		Expression operand2 = singleExpressionResolver.resolve(tokens[2]);
		BinaryCalculator binaryCalculator = binaryCalculatorMap.get(operator);
		if (binaryCalculator != null) {
			return new BinaryExpression(operand1, binaryCalculator, operand2);
		} else {
			BinaryCalculator binaryAssignmentCalculator = binaryAssignmentCalculatorMap.get(operator);
			if (binaryAssignmentCalculator != null) {
				return new BinaryAssignmentExpression(operand1, binaryAssignmentCalculator, operand2, tokens[0]);
			} else {
				throw new SyntaxInterpretatorException("Unsupported operator: " + operator);
			}
		}
	}
}
