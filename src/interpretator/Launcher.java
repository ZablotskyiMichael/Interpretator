package interpretator;

import interpretator.component.Interpretator;

public class Launcher {
	public static void main(String[] args) {
		Interpretator interpretator = Config.interpretator;
		interpretator.interpret("test.javamm");
	}
}
