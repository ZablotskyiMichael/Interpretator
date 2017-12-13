package interpretator.component;

import interpretator.model.Expression;

public interface BinaryCalculator {

	Object calculate(Expression expression1, Expression expression2);
}
