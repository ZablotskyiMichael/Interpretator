package interpretator.component.impl.expressionresolver;

import java.util.Arrays;
import java.util.Map;

import interpretator.component.ExpressionResolver;
import interpretator.component.UnaryCalculator;
import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.Expression;
import interpretator.model.expression.UnaryExpression;
import interpretator.model.expression.UnaryIncDecPostfixExpression;
import interpretator.model.expression.UnaryIncDecPreffixExpression;

public class UnaryExpressionResolver implements ExpressionResolver {
	private ExpressionResolver singleExpressionResolver;
	private Map<String, UnaryCalculator> unaryCalculatorMap;
	private Map<String, UnaryCalculator> unaryIncDecCalculatorMap;

	public UnaryExpressionResolver(ExpressionResolver singleExpressionResolver,
			Map<String, UnaryCalculator> unaryCalculatorMap, Map<String, UnaryCalculator> unaryIncDecCalculatorMap) {
		this.singleExpressionResolver = singleExpressionResolver;
		this.unaryCalculatorMap = unaryCalculatorMap;
		this.unaryIncDecCalculatorMap = unaryIncDecCalculatorMap;

	}

	@Override
	public boolean isSupport(String... tokens) {
		return tokens.length == 2;
	}

	@Override
	public Expression resolve(String... tokens) {
		UnaryCalculator unaryCalculator = unaryCalculatorMap.get(tokens[0]);
		if (unaryCalculator != null) {
			Expression operand = singleExpressionResolver.resolve(tokens[1]);
			return new UnaryExpression(operand, unaryCalculator);
		} else {
			unaryCalculator = unaryIncDecCalculatorMap.get(tokens[0]);
			if (unaryCalculator != null) {
				Expression operand = singleExpressionResolver.resolve(tokens[1]);
				return new UnaryIncDecPreffixExpression(operand, unaryCalculator);
			} else {
				unaryCalculator = unaryIncDecCalculatorMap.get(tokens[1]);
				if (unaryCalculator != null) {
					Expression operand = singleExpressionResolver.resolve(tokens[0]);
					return new UnaryIncDecPostfixExpression(operand, unaryCalculator);
				} else {
					throw new SyntaxInterpretatorException("Unsupported unary operation: " + Arrays.toString(tokens));
				}
			}
		}
	}
}
