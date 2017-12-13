package interpretator.model.expression;

import interpretator.exception.RuntimeInterpretatorException;
import interpretator.model.Array;

import static interpretator.utils.TypeUtils.getType;

public class ArrayLengthExpression extends VariableExpression {
    public ArrayLengthExpression(String variableName) {
        super(variableName);
    }

    @Override
    public Object getValue() {
        Object value = super.getValue();
        if(!(value instanceof Array)) {
            throw new RuntimeInterpretatorException("Array variable expected. Current type is "+getType(value));
        }
        return ((Array)value).length();
    }
}
