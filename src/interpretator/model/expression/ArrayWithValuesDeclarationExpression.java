package interpretator.model.expression;

import interpretator.model.Array;
import interpretator.model.Expression;

import java.util.ArrayList;
import java.util.List;

public class ArrayWithValuesDeclarationExpression implements Expression {
    private final List<Expression> expressions;

    public ArrayWithValuesDeclarationExpression(List<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public Object getValue() {
        List<Object> values = new ArrayList<>();
        for(Expression expression : expressions) {
            values.add(expression.getValue());
        }
        return new Array(values.toArray());
    }
}
