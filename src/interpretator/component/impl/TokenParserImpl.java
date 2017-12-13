package interpretator.component.impl;

import interpretator.component.TokenParser;
import interpretator.exception.SyntaxInterpretatorException;

import java.util.ArrayList;

public class TokenParserImpl implements TokenParser {
	@Override
	public String[] parse(String string) {
		ArrayList<String> array = new ArrayList<>();
		StringIterator iterator = new StringIterator(string);
		StringBuilder tokenBuilder = new StringBuilder();
		while (iterator.hasNext()) {
			char ch = iterator.getCurrent();
			if (isDelimeter(ch)) {
				addTokenIfExists(array, tokenBuilder);
			} else if (ch == '"') {
				tokenBuilder.append(ch);
				iterator.next();
				addTokenIfExists(array, stringToken(iterator, tokenBuilder));
			} else if (isOperator(ch)) {
				addTokenIfExists(array, tokenBuilder);
				String operator = readFullOperator(ch, iterator);
				array.add(operator);
			} else {
				tokenBuilder.append(ch);
			}
			iterator.next();
		}
		addTokenIfExists(array, tokenBuilder);
		return array.toArray(new String[0]);
	}

	private StringBuilder stringToken(StringIterator iterator, StringBuilder tokenBuilder) {
		while (iterator.hasNext()) {
			char ch = iterator.getCurrent();
			if (ch == '"') {
				tokenBuilder.append(ch);
				break;
			} else {
				tokenBuilder.append(ch);
			}
			if (iterator.index == iterator.source.length() - 1) {
				throw new SyntaxInterpretatorException(
						"String variable must be enclosed in \"\", there is no closing \".");
			} else {
				iterator.next();
			}
		}
		return tokenBuilder;
	}

	private void addTokenIfExists(ArrayList<String> array, StringBuilder tokenBuilder) {
		if (tokenBuilder.length() > 0) {
			array.add(tokenBuilder.toString());
			tokenBuilder.delete(0, tokenBuilder.length());
		}
	}

	private String readFullOperator(char ch, StringIterator iterator) {
		if ('>' == ch) {
			return readGreaterThanSignOperator(iterator, ch);
		} else if ('<' == ch) {
			return readLessThanSignOperator(iterator, ch);
		} else if ("+-=*/%!&|^".indexOf(ch) != -1) {
			return readDoubleOperator(iterator, ch);
		} else {
			return String.valueOf(ch);
		}
	}

	private String readGreaterThanSignOperator(StringIterator iterator, char ch) {
		iterator.next();
		if (iterator.hasNext()) {
			if (iterator.getCurrent() == ch) {
				iterator.next();
				if (iterator.hasNext()) {
					if (iterator.getCurrent() == ch) {
						iterator.next();
						if (iterator.hasNext()) {
							if (iterator.getCurrent() == '=') {
								return String.valueOf(ch) + String.valueOf(ch) + String.valueOf(ch) + "=";
							}
						}
						iterator.previous();
						return String.valueOf(ch) + String.valueOf(ch) + String.valueOf(ch);
					} else if (iterator.getCurrent() == '=') {
						return String.valueOf(ch) + String.valueOf(ch) + "=";
					}
				}
				iterator.previous();
				return String.valueOf(ch) + String.valueOf(ch);
			} else if (iterator.getCurrent() == '=') {
				return String.valueOf(ch) + "=";
			} else {
				iterator.previous();
			}
		}
		return String.valueOf(ch);
	}

	private String readLessThanSignOperator(StringIterator iterator, char ch) {
		iterator.next();
		if (iterator.hasNext()) {
			if (iterator.getCurrent() == ch) {
				iterator.next();
				if (iterator.hasNext()) {
					if (iterator.getCurrent() == '=') {
						return String.valueOf(ch) + String.valueOf(ch) + "=";
					}
				}
				iterator.previous();
				return String.valueOf(ch) + String.valueOf(ch);
			} else if (iterator.getCurrent() == '=') {
				return String.valueOf(ch) + "=";
			} else {
				iterator.previous();
			}
		}
		return String.valueOf(ch);
	}

	private String readDoubleOperator(StringIterator iterator, char ch) {
		iterator.next();
		if (iterator.hasNext()) {
			if (iterator.getCurrent() == '=') {
				return String.valueOf(ch) + "=";
			} else {
				if ("+-&|".indexOf(ch) != -1) {
					if (iterator.getCurrent() == ch) {
						return String.valueOf(ch) + String.valueOf(ch);
					} else {
						iterator.previous();
					}
				} else {
					iterator.previous();
				}
			}
		}
		return String.valueOf(ch);
	}

	private boolean isOperator(char ch) {
		return "+-=*/%<>!&|^~()[]{},;:".indexOf(ch) != -1;
	}

	private boolean isDelimeter(char ch) {
		return " \t".indexOf(ch) != -1;
	}

	private static class StringIterator {
		private final String source;
		private int index;

		public StringIterator(String source) {
			this.source = source;
		}

		public boolean hasNext() {
			return index < source.length();
		}

		public char getCurrent() {
			return source.charAt(index);
		}

		public void next() {
			index++;
		}

		public void previous() {
			index--;
		}
	}
}
