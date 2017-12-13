package interpretator.model;

public class SourceLine {
	private String str;
	private int number;

	public SourceLine(String str, int number) {
		this.str = str;
		this.number = number;
	}

	public String getStr() {
		return str;
	}

	public int getNumber() {
		return number;
	}
}
