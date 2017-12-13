package interpretator.component.impl;

import interpretator.component.VariableStorage;

import java.util.HashMap;
import java.util.Map;

public class VariableStorageImpl implements VariableStorage {
	private Map<String, Object> map = new HashMap<>();

	@Override
	public void put(String varName, Object value) {
		map.put(varName, value);
	}

	@Override
	public Object get(String varName) {
		return map.get(varName);
	}

	@Override
	public boolean isDefined(String varName) {
		return map.containsKey(varName);
	}

	@Override
	public VariableStorage createChildVariableStorage() {
		return new VariableStorageImpl() {
			@Override
			public void put(String varName, Object value) {
				if (VariableStorageImpl.this.isDefined(varName)) {
					VariableStorageImpl.this.put(varName, value);
				} else {
					super.put(varName, value);
				}
			}

			@Override
			public Object get(String varName) {
				if (VariableStorageImpl.this.isDefined(varName)) {
					return VariableStorageImpl.this.get(varName);
				} else {
					return super.get(varName);
				}
			}

			@Override
			public boolean isDefined(String varName) {
				if (VariableStorageImpl.this.isDefined(varName)) {
					return true;
				} else {
					return super.isDefined(varName);
				}
			}
		};
	}
}
