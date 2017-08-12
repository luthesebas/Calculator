/**
 * 
 */
package calculator.tokenizer;

/**
 * 
 */
public class Tokenzier {
	
	private final static String SEPARATORS = "-+*/%^()";
	private final static String REGEX_TOKENIZE = "(?<=[" + SEPARATORS + "])|(?=[" + SEPARATORS + "])";
	private final static String REGEX_INTEGER = "[0-9]+";
	private final static String REGEX_FRACT_0 = REGEX_INTEGER + "[.]" + REGEX_INTEGER;
	private final static String REGEX_FRACT_1 = REGEX_INTEGER + "[.]" + REGEX_INTEGER +"?";
	private final static String REGEX_FRACT_2 = REGEX_INTEGER + "?[.]" + REGEX_INTEGER;
	private final static String REGEX_FRACT_OR_INT = "(" + REGEX_FRACT_0 + "|" + REGEX_FRACT_1 + "|" + REGEX_FRACT_2 + "|" + REGEX_INTEGER + ")";
	private final static String REGEX_DECIMAL = "[+-]?" + REGEX_FRACT_OR_INT + "([eE][+-]?" + REGEX_INTEGER + ")?";
	
	private final String[] tokens;
	private int index;
	
	/**
	 * 
	 * @param expression
	 */
	public Tokenzier(String expression) {		
		this.tokens = expression.split(REGEX_TOKENIZE);
		this.index = -1;
	}
	
	/**
	 * 
	 * @return
	 */
	public Token getToken() {
		this.index++;
		
		// End of expression reached? 
		if (this.index == this.tokens.length) {
			return new Token(TokenID.END_OF_EXPRESSION, "#", this.index);
		}
		
		// Scan current character of the expression
		String tokenValue = this.tokens[this.index];
		
		// Number
		if (tokenValue.matches(REGEX_DECIMAL)) {
			return new Number(tokenValue, this.index);
		}
		
		// Arithmetic operations
		switch(tokenValue) {
			case "+": return new Token(TokenID.PLUS, tokenValue, this.index);
			case "-": return new Token(TokenID.MINUS, tokenValue, this.index);
			case "*": return new Token(TokenID.MULTIPLY, tokenValue, this.index);
			case "/": return new Token(TokenID.DIVIDE, tokenValue, this.index);
			case "%": return new Token(TokenID.MODULO, tokenValue, this.index);
			case "^": return new Token(TokenID.POWER, tokenValue, this.index);
			case "(": return new Token(TokenID.LEFT_PARENTHESIS, tokenValue, this.index);
			case ")": return new Token(TokenID.RIGHT_PARENTHESIS, tokenValue, this.index);
			default: return new Token(TokenID.INVALID, tokenValue, this.index);
		}//switch
	}
	

}

