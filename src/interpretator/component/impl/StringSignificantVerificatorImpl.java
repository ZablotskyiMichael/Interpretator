package interpretator.component.impl;

import interpretator.component.StringSignificantVerificator;

public class StringSignificantVerificatorImpl implements StringSignificantVerificator {
	@Override
	public boolean isSignificant(String string) {
		return !string.trim().isEmpty() && !string.trim().startsWith("//");
	}
}
