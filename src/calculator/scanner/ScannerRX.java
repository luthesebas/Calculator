/**
 * 
 */
package calculator.scanner;

import java.util.Arrays;

/**
 * 
 */
public class ScannerRX {
	
	private static final String REGEX_SPLIT 		= "-+*/%^()";
	private static final String REGEX_TOKENIZE 		= "(?<=[" + REGEX_SPLIT + "])|(?=[" + REGEX_SPLIT + "])";
	private static final String REGEX_INTEGER		= "[0-9]+";
	private static final String REGEX_FRACT_0		= REGEX_INTEGER + "[.]" + REGEX_INTEGER;
	private static final String REGEX_FRACT_1		= REGEX_INTEGER + "[.]" + REGEX_INTEGER +"?";
	private static final String REGEX_FRACT_2		= REGEX_INTEGER + "?[.]" + REGEX_INTEGER;
	private static final String REGEX_FRACT_OR_INT	= "(" + REGEX_FRACT_0 + "|" + REGEX_FRACT_1 + "|" + REGEX_FRACT_2 + "|" + REGEX_INTEGER + ")";
	private static final String REGEX_DECIMAL		= "[+-]?" + REGEX_FRACT_OR_INT + "([eE][+-]?" + REGEX_INTEGER + ")?";
	
	private final String[] tokens;
	private int index;
	
	public ScannerRX(String expression) {		
		this.tokens = expression.split(REGEX_TOKENIZE);
		this.index = -1;
		
		System.out.println("TokenValues" + Arrays.toString(this.tokens));
	}
	
	public Token getToken() {
		Token token;
		this.index++;
		
		// Expression end reached? 
		if (this.index == this.tokens.length) {
			System.out.println("Token[End_of_Expression]");
			return new Token(this.index, TokenID.END_OF_EXPRESSION, "#");
		}
		
		// Scan current character of the expression
		String tok = this.tokens[this.index];		
		switch(tok) {
			case "+": token = new Token(this.index, TokenID.PLUS, tok); break;
			case "-": token = new Token(this.index, TokenID.MINUS, tok); break;
			case "*": token = new Token(this.index, TokenID.MULTIPLY, tok); break;
			case "/": token = new Token(this.index, TokenID.DIVIDE, tok); break;
			case "%": token = new Token(this.index, TokenID.MODULO, tok); break;
			case "^": token = new Token(this.index, TokenID.POWER, tok); break;
			case "(": token = new Token(this.index, TokenID.LEFT_PARENTHESIS, tok); break;
			case ")": token = new Token(this.index, TokenID.RIGHT_PARENTHESIS, tok); break;
			default: token = new Token(this.index, TokenID.INVALID, tok);
		}	
		
		/*
		 *  Is token a number n? 
		 *  n := x | x.x | x. | .x
		 *  x := [0-9]+
		 *  
		 *  "[+-]?([0-9]+[.][0-9]+|[0-9]+)([eE][+-]?[0-9]+)?"
		 */
		if (tok.matches(REGEX_DECIMAL)) {
			return new Number(this.index, tok);
		} else {
			return token;
		}
	}
	
	public static void main(String[] args) {
		ScannerRX scanner = new ScannerRX("45*5/0.7+(2.-5%3)+1");
		
		Token token = scanner.getToken();
		while (token.id != TokenID.END_OF_EXPRESSION) {
			System.out.println(token);
			token = scanner.getToken();
		}//while
	}
	
	
}
