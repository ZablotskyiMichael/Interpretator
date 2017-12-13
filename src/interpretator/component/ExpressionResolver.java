package interpretator.component;

import interpretator.model.Expression;

public interface ExpressionResolver {

	boolean isSupport(String... tokens);

	Expression resolve(String... tokens);
}
