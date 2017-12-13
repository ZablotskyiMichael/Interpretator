package interpretator.model;

import java.util.Collections;
import java.util.List;

public class Function {
    private final String name;
    private final List<String> arguments;
    private final List<Operation> body;

    public Function(String name, List<String> arguments, List<Operation> body) {
        this.name = name;
        this.arguments = arguments;
        this.body = body;
    }

    public Function(String name, List<Operation> body) {
        this(name, Collections.emptyList(), body);
    }

    public String getName() {
        return name;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public List<Operation> getBody() {
        return body;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, String.join(",", arguments));
    }
}
