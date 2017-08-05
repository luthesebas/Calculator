package calculator.parser;

import calculator.scanner.ScannerRX;
import calculator.scanner.Token;
import calculator.scanner.TokenID;

/**
 *
 */
public class Parser {
	
	private static final String FF_EXPRESSION   = "(i";
	private static final String FF_EXPRESSION_1 = "+";
	private static final String FF_EXPRESSION_2 = "-";
	private static final String FF_EXPRESSION_3 = "#)";
	
	private static final String FF_TERM   = "(i";
	private static final String FF_TERM_1 = "*";
	private static final String FF_TERM_2 = "/";
	private static final String FF_TERM_3 = "%";
	private static final String FF_TERM_4 = "#)+-";
	
	private static final String FF_POWER   = "(i";
	private static final String FF_POWER_1 = "^";
	private static final String FF_POWER_2 = "#)(i+-*/%";
	
	private static final String FF_FACTOR   = "(i";
	private static final String FF_FACTOR_1 = "+";
	private static final String FF_FACTOR_2 = "-";
	private static final String FF_FACTOR_3 = "#)(i*/%^";
	
	private static final String FF_NUMERAL   = "(";
	private static final String FF_NUMERAL_1 = "i";
	
	private ScannerRX scanner;
	private Token currentToken;
	
	/**
	 * Verifies a arithmetic expression
	 */
	public Parser() {
		//
	}
	
	/**
	 * Verifies a arithmetic expression
	 * @param expression
	 */
	public boolean parse(String expression) {
		this.scanner = new ScannerRX(expression);
		this.currentToken = this.scanner.getToken();
		
		if ( f_expression() && f_EndOfExpression() ) {
			return true;
		} else {
			return false;
		}
	}
    
    /*
     * LL1 arithmetic grammar:
     */

    /**
     * expression ::= term expressionF
     * @return
     */
    private boolean f_expression() {
    	// expression ::= term expressionF    	
    	if (Token.isFirstFollow(this.currentToken, FF_EXPRESSION)) {
    		System.out.println("E -> T e");
    		return f_term() && f_expressionF();
    	}
    	
    	// Syntax error
    	String error = String.format(
    			"Parsing: %s ; Expected: '%s' ; Actual: '%s'", 
    			"E", 
    			FF_EXPRESSION, 
    			this.currentToken.name
    		);
    	System.out.println(error);
    	return false;
    }
    
    /**
     * expressionF ::= "+" term expressionF | "-" term expressionF | epsilon
     * @return
     */
    private boolean f_expressionF() {
    	// expressionF ::= "+" term expressionF
    	if (Token.isFirstFollow(this.currentToken, FF_EXPRESSION_1)) {
    		System.out.println("e -> + T e");
    		return f_tPlus() && f_term() && f_expressionF();
    	}
    	
    	// expressionF ::= "-" term expressionF
    	if (Token.isFirstFollow(this.currentToken, FF_EXPRESSION_2)) {
    		System.out.println("e -> - T e");
    		return f_tMinus() && f_term() && f_expressionF();
    	}
    	
    	// expressionF ::= epsilon    	
    	if (Token.isFirstFollow(this.currentToken, FF_EXPRESSION_3)) {
    		System.out.println("e -> .");
    		return true;
    	}
    	
    	// Syntax error
    	String error = String.format(
    			"Parsing: %s ; Expected: '%s%s%s' ; Actual: '%s'", 
    			"e", 
    			FF_EXPRESSION_1, 
    			FF_EXPRESSION_2, 
    			FF_EXPRESSION_3, 
    			this.currentToken.name
    		);
    	System.out.println(error);	
    	return false;
    }
    
    /**
     * term ::= power termF
     * @return
     */
    private boolean f_term() {
    	// term ::= power termF
    	if (Token.isFirstFollow(this.currentToken, FF_TERM)) {
    		System.out.println("T -> P t");
    		return f_power() && f_termF();
    	}
    	
    	// Syntax error
    	String error = String.format(
    			"Parsing: %s ; Expected: '%s' ; Actual: '%s'", 
    			"T", 
    			FF_TERM, 
    			this.currentToken.name
    		);
    	System.out.println(error);
    	return false;
    }
    
    /**
     * termF ::= "*" power termF | "/" power termF | "%" power termF | epsilon
     * @return
     */
    private boolean f_termF() {    	
    	// termF ::= "*" power termF    	
    	if (Token.isFirstFollow(this.currentToken, FF_TERM_1)) {
    		System.out.println("t -> * P t");
    		return f_tMultiply() && f_power() && f_termF();
    	}
    	
    	// termF ::= "/" power termF    	
    	if (Token.isFirstFollow(this.currentToken, FF_TERM_2)) {
    		System.out.println("t -> / P t");
    		return f_tDivide() && f_power() && f_termF();
    	}
    	
    	// termF ::= "%" power termF    	
    	if (Token.isFirstFollow(this.currentToken, FF_TERM_3)) {
        	System.out.println("t -> % P t");
    		return f_tModulo() && f_power() && f_termF();
    	}

    	// termF ::= epsilon
    	if (Token.isFirstFollow(this.currentToken, FF_TERM_4)) {
        	System.out.println("t -> .");
    		return true;
    	}
    	
    	// Syntax error
    	String error = String.format(
    			"Parsing: %s ; Expected: '%s%s%s%s' ; Actual: '%s'", 
    			"t", 
    			FF_TERM_1,
    			FF_TERM_2,
    			FF_TERM_3,
    			FF_TERM_4,
    			this.currentToken.name
    		);
    	System.out.println(error);
    	return false;
    }
    
    /**
     * power ::= factor powerF
     * @return
     */
    private boolean f_power() {
    	// power ::= factor powerF
    	if (Token.isFirstFollow(this.currentToken, FF_POWER)) {
        	System.out.println("P -> F p");
    		return f_factor() && f_powerF();
    	}
    	
    	// Syntax error
    	String error = String.format(
    			"Parsing: %s ; Expected: '%s' ; Actual: '%s'", 
    			"P", 
    			FF_POWER, 
    			this.currentToken.name
    		);
    	System.out.println(error);
    	return false;
    }
    
    /**
     * powerF ::= "^" powerF factor | epsilon
     * @return
     */
    private boolean f_powerF() {
    	// powerF ::= "^" powerF factor
    	if (Token.isFirstFollow(this.currentToken, FF_POWER_1)) {
        	System.out.println("p -> ^ p F");
    		return f_tPower() && f_powerF() && f_factor();
    	}
    	
    	// powerF ::= epsilon
    	if (Token.isFirstFollow(this.currentToken, FF_POWER_2)) {
        	System.out.println("p -> .");
    		return true;
    	}
    	
    	// Syntax error
    	String error = String.format(
    			"Parsing: %s ; Expected: '%s%s' ; Actual: '%s'", 
    			"p", 
    			FF_POWER_1,
    			FF_POWER_2,
    			this.currentToken.name
    		);
    	System.out.println(error);
    	return false;
    }
    
    /**
     * factor ::= numeral factorF
     * @return
     */
    private boolean f_factor() {
    	// factor ::= numeral factorF
    	if (Token.isFirstFollow(this.currentToken, FF_FACTOR)) {
        	System.out.println("F -> N f");
    		return f_numeral() && f_factorF();
    	}
    	
    	// Syntax error
    	String error = String.format(
    			"Parsing: %s ; Expected: '%s' ; Actual: '%s'", 
    			"F", 
    			FF_FACTOR, 
    			this.currentToken.name
    		);
    	System.out.println(error);
    	return false;
    }
    
    /**
     * factorF ::= "+" numeral | "-" numeral | epsilon
     * @return
     */
    private boolean f_factorF() {
    	// factorF ::= "+" numeral
    	if (Token.isFirstFollow(this.currentToken, FF_FACTOR_1)) {
        	System.out.println("f -> + N");
    		return f_tPlus() && f_numeral();
    	}
    	
    	// factorF ::= "-" numeral
    	if (Token.isFirstFollow(this.currentToken, FF_FACTOR_2)) {
        	System.out.println("f -> - N");
    		return f_tMinus() && f_numeral();
    	}
    	
    	// factorF ::= epsilon
    	if (Token.isFirstFollow(this.currentToken, FF_FACTOR_3)) {
        	System.out.println("f -> .");
    		return true;
    	}
    	
    	// Syntax error
    	String error = String.format(
    			"Parsing: %s ; Expected: '%s%s%s' ; Actual: '%s'", 
    			"f", 
    			FF_FACTOR_1,
    			FF_FACTOR_2,
    			FF_FACTOR_3,
    			this.currentToken.name
    		);
    	System.out.println(error);
    	return false;
    }
    
    /**
     * numeral ::= "(" expression ")" | number
     * @return
     */
    private boolean f_numeral() {
    	// numeral ::= "(" expression ")"
    	if (Token.isFirstFollow(this.currentToken, FF_NUMERAL)) {
        	System.out.println("N -> (E)");
    		return f_tLeftParenthesis() && f_expression() && f_tRightParenthesis();
    	}
    	
    	// numeral ::= number
    	if (Token.isFirstFollow(this.currentToken, FF_NUMERAL_1)) {
        	System.out.println("N -> i");
    		return f_number();
    	}
    	
    	// Syntax error
    	String error = String.format(
    			"Parsing: %s ; Expected: '%s%s' ; Actual: '%s'", 
    			"E", 
    			FF_NUMERAL,
    			FF_NUMERAL_1,
    			this.currentToken.name
    		);
    	System.out.println(error);
    	return false;
    }
    
    /**
     * ::=* number
     * @return
     */
    private boolean f_number() {    	
    	if (this.currentToken.id == TokenID.NUMBER) {
    		this.currentToken = this.scanner.getToken();
    		return true;
    	} else {
    		// Syntax error
    		return false;
    	}
    }
    
    
    //--------------------------------------
    // Terminal symbols
    //--------------------------------------
    
    
    /**
     * ::=* "#"
     * @return
     */
    private boolean f_EndOfExpression() {
    	if (this.currentToken.id == TokenID.END_OF_EXPRESSION) {
    		return true;
    	} else {
    		// Syntax error
    		return false;
    	}
    }
    
    /**
     * ::=* "+"
     * @return
     */
    private boolean f_tPlus() {
    	if (this.currentToken.id == TokenID.PLUS) {
    		this.currentToken = this.scanner.getToken();
    		return true;
    	} else {
    		// Syntax error
    		return false;
    	}
    }
    
    /**
     * ::=* "-"
     * @return
     */
    private boolean f_tMinus() {
    	if (this.currentToken.id == TokenID.MINUS) {
    		this.currentToken = this.scanner.getToken();
    		return true;
    	} else {
    		// Syntax error
    		return false;
    	}
    }
    
    /**
     * ::=* "*"
     * @return
     */
    private boolean f_tMultiply() {
    	if (this.currentToken.id == TokenID.MULTIPLY) {
    		this.currentToken = this.scanner.getToken();
    		return true;
    	} else {
    		// Syntax error
    		return false;
    	}
    }
    
    /**
     * ::=* "/"
     * @return
     */
    private boolean f_tDivide() {
    	if (this.currentToken.id == TokenID.DIVIDE) {
    		this.currentToken = this.scanner.getToken();
    		return true;
    	} else {
    		// Syntax error
    		return false;
    	}
    }
	
    /**
     * ::=* "%"
     * @return
     */
    private boolean f_tModulo() {
    	if (this.currentToken.id == TokenID.MODULO) {
    		this.currentToken = this.scanner.getToken();
    		return true;
    	} else {
    		// Syntax error
    		return false;
    	}
    }
    
    /**
     * ::=* "^"
     * @return
     */
    private boolean f_tPower() {
    	if (this.currentToken.id == TokenID.POWER) {
    		this.currentToken = this.scanner.getToken();
   		return true;
    	} else {
    		// Syntax error
    		return false;
    	}
    }
    
    /**
     * ::=* "("
     * @return
     */
    private boolean f_tLeftParenthesis() {
    	if (this.currentToken.id == TokenID.LEFT_PARENTHESIS) {
    		this.currentToken = this.scanner.getToken();
  		return true;
    	} else {
    		// Syntax error
    		return false;
    	}
    }
    
    /**
     * ::=* ")"
     * @return
     */
    private boolean f_tRightParenthesis() {
    	if (this.currentToken.id == TokenID.RIGHT_PARENTHESIS) {
    		this.currentToken = this.scanner.getToken();
    		return true;
    	} else {
    		// Syntax error
    		return false;
    	}
    }
    
    
}


