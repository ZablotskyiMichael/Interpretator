package interpretator.model.expression;

import interpretator.exception.RuntimeInterpretatorException;
import interpretator.model.Array;
import interpretator.model.Expression;

import static interpretator.utils.ArrayUtils.validateIndex;
import static interpretator.utils.TypeUtils.getType;

public class ArrayElementByIndexExpression extends VariableExpression {
    private final Expression indexExpression;

    public ArrayElementByIndexExpression(String variableName,
                                         Expression indexExpression) {
        super(variableName);
        this.indexExpression = indexExpression;
    }

    @Override
    public Object getValue() {
        Object value = super.getValue();
        if(!(value instanceof Array)) {
            throw new RuntimeInterpretatorException("Array variable expected. Current type is "+getType(value));
        }
        Object index = indexExpression.getValue();
        validateIndex(index);
        return ((Array)value).get((int)index);
    }

    @Override
    public void setValue(Object value) {
        Object array = super.getValue();
        if(!(array instanceof Array)) {
            throw new RuntimeInterpretatorException("Array variable expected. Current type is "+getType(value));
        }
        Object index = indexExpression.getValue();
        validateIndex(index);
        ((Array)array).set((int)index, value);
    }
}
