package interpretator.component.impl;

import interpretator.component.FunctionNameVerifier;
import interpretator.component.VariableVerificator;

public class FunctionNameVerifierImpl implements FunctionNameVerifier {
    private final VariableVerificator variableVerificator;

    public FunctionNameVerifierImpl(VariableVerificator variableVerificator) {
        this.variableVerificator = variableVerificator;
    }

    @Override
    public boolean isValidFunctionName(String name) {
        try {
            variableVerificator.verify(name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
