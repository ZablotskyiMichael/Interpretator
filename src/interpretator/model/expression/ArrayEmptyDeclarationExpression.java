package interpretator.model.expression;

import interpretator.model.Array;
import interpretator.model.Expression;

import static interpretator.utils.ArrayUtils.validateSize;

public class ArrayEmptyDeclarationExpression implements Expression {
    private final Expression arraySizeExpression;

    public ArrayEmptyDeclarationExpression(Expression arraySizeExpression) {
        this.arraySizeExpression = arraySizeExpression;
    }

    @Override
    public Object getValue() {
        Object size = arraySizeExpression.getValue();
        validateSize(size);
        return new Array((Integer) size);
    }


}
