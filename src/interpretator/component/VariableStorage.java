package interpretator.component;

public interface VariableStorage {

	void put(String varName, Object value);

	Object get(String varName);

	boolean isDefined(String varName);

	VariableStorage createChildVariableStorage();
}
