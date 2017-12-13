package interpretator.component.impl.calculator;

import interpretator.exception.RuntimeInterpretatorException;

import static interpretator.utils.TypeUtils.getType;

public abstract class AbstractBinaryCalculator {
	protected String getOperationName() {
		if (getClass().getSimpleName().indexOf("BinaryCalculator") != -1) {
			return getClass().getSimpleName().replace("BinaryCalculator", "").toString();
		} else if (getClass().getSimpleName().indexOf("UnaryCalculator") != -1) {
			return getClass().getSimpleName().replace("UnaryCalculator", "").toString();
		} else {
			throw new RuntimeInterpretatorException(String.format("The name '%s' of the calculator is not supported. "
					+ "The name must contain BinaryCalculator or UnaryCalculator", getClass().getSimpleName()));
		}
	}

	protected RuntimeInterpretatorException createRuntimeException(Object value1, Object value2) {
		return new RuntimeInterpretatorException(String.format("Operator '%s' not supported for types: %s and %s",
				getOperationName(), getType(value1), getType(value2)));
	}

	protected RuntimeInterpretatorException createRuntimeException(Object value) {
		return new RuntimeInterpretatorException(
				String.format("Operator '%s' not supported for types: %s", getOperationName(), getType(value)));
	}
}
