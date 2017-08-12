package calculator.parser;

import calculator.tokenizer.Token;
import calculator.tokenizer.TokenID;
import calculator.tokenizer.Tokenzier;

/**
 *
 */
public class Parser {
	
	private Tokenzier scanner;
	private Token currentToken;
	private String expression;
	private String error;
	
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
		this.expression = expression;
		this.scanner = new Tokenzier(expression);
		this.currentToken = this.scanner.getToken();
		
		if ( f_expression() && f_EndOfExpression() ) {
			return true;
		} else {
	    	// Syntax error
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
    	if (this.currentToken.isFirstFollowOf(FirstFollow.E_1)) {
    		//System.out.println("E -> T e");
    		return f_term() && f_expressionF();
    	}
    	
    	// Syntax error
		this.error = String.format(
    			"Error while parsing '%s' at position %s: Expected a character of the following set '%s'. Actual character is '%s'.", 
    			this.expression, this.currentToken.POSITION, FirstFollow.E_1, 
    			this.currentToken.VALUE
    		);
    	return false;
    }
    
    /**
     * expressionF ::= "+" term expressionF | "-" term expressionF | epsilon
     * @return
     */
    private boolean f_expressionF() {
    	// expressionF ::= "+" term expressionF
    	if (this.currentToken.isFirstFollowOf(FirstFollow.E_2)) {
    		//System.out.println("e -> + T e");
    		return f_tPlus() && f_term() && f_expressionF();
    	}
    	
    	// expressionF ::= "-" term expressionF
    	if (this.currentToken.isFirstFollowOf(FirstFollow.E_3)) {
    		//System.out.println("e -> - T e");
    		return f_tMinus() && f_term() && f_expressionF();
    	}
    	
    	// expressionF ::= epsilon    	
    	if (this.currentToken.isFirstFollowOf(FirstFollow.E_4)) {
    		//System.out.println("e -> .");
    		return true;
    	}
    	
    	// Syntax error
		this.error = String.format(
    			"Error while parsing '%s' at position %s: Expected a character of the following set '%s%s%s'. Actual character is '%s'.", 
    			this.expression, this.currentToken.POSITION, 
    			FirstFollow.E_2, FirstFollow.E_3, FirstFollow.E_4, 
    			this.currentToken.VALUE
    		);
    	return false;
    }
    
    /**
     * term ::= power termF
     * @return
     */
    private boolean f_term() {
    	// term ::= power termF
    	if (this.currentToken.isFirstFollowOf(FirstFollow.T_1)) {
    		//System.out.println("T -> P t");
    		return f_power() && f_termF();
    	}
    	
    	// Syntax error
		this.error = String.format(
    			"Error while parsing '%s' at position %s: Expected a character of the following set '%s'. Actual character is '%s'.", 
    			this.expression, this.currentToken.POSITION, FirstFollow.T_1, 
    			this.currentToken.VALUE
    		);
    	return false;
    }
    
    /**
     * termF ::= "*" power termF | "/" power termF | "%" power termF | epsilon
     * @return
     */
    private boolean f_termF() {    	
    	// termF ::= "*" power termF    	
    	if (this.currentToken.isFirstFollowOf(FirstFollow.T_2)) {
    		//System.out.println("t -> * P t");
    		return f_tMultiply() && f_power() && f_termF();
    	}
    	
    	// termF ::= "/" power termF    	
    	if (this.currentToken.isFirstFollowOf(FirstFollow.T_3)) {
    		//System.out.println("t -> / P t");
    		return f_tDivide() && f_power() && f_termF();
    	}
    	
    	// termF ::= "%" power termF    	
    	if (this.currentToken.isFirstFollowOf(FirstFollow.T_4)) {
        	//System.out.println("t -> % P t");
    		return f_tModulo() && f_power() && f_termF();
    	}

    	// termF ::= epsilon
    	if (this.currentToken.isFirstFollowOf(FirstFollow.T_5)) {
        	//System.out.println("t -> .");
    		return true;
    	}
    	
    	// Syntax error
		this.error = String.format(
    			"Error while parsing '%s' at position %s: Expected a character of the following set '%s%s%s%s'. Actual character is '%s'.", 
    			this.expression, this.currentToken.POSITION, 
    			FirstFollow.T_2, FirstFollow.T_3, FirstFollow.T_4, FirstFollow.T_5,
    			this.currentToken.VALUE
    		);
    	return false;
    }
    
    /**
     * power ::= factor powerF
     * @return
     */
    private boolean f_power() {
    	// power ::= factor powerF
    	if (this.currentToken.isFirstFollowOf(FirstFollow.P_1)) {
        	//System.out.println("P -> F p");
    		return f_factor() && f_powerF();
    	}
    	
    	// Syntax error
		this.error = String.format(
    			"Error while parsing '%s' at position %s: Expected a character of the following set '%s'. Actual character is '%s'.", 
    			this.expression, this.currentToken.POSITION, FirstFollow.P_1, 
    			this.currentToken.VALUE
    		);
    	return false;
    }
    
    /**
     * powerF ::= "^" powerF factor | epsilon
     * @return
     */
    private boolean f_powerF() {
    	// powerF ::= "^" powerF factor
    	if (this.currentToken.isFirstFollowOf(FirstFollow.P_2)) {
        	//System.out.println("p -> ^ p F");
    		return f_tPower() && f_powerF() && f_factor();
    	}
    	
    	// powerF ::= epsilon
    	if (this.currentToken.isFirstFollowOf(FirstFollow.P_3)) {
        	//System.out.println("p -> .");
    		return true;
    	}
    	
    	// Syntax error
		this.error = String.format(
    			"Error while parsing '%s' at position %s: Expected a character of the following set '%s%s'. Actual character is '%s'.", 
    			this.expression, this.currentToken.POSITION, FirstFollow.P_2, FirstFollow.P_3,
    			this.currentToken.VALUE
    		);
    	return false;
    }
    
    /**
     * factor ::= numeral factorF
     * @return
     */
    private boolean f_factor() {
    	// factor ::= numeral factorF
    	if (this.currentToken.isFirstFollowOf(FirstFollow.F_1)) {
        	//System.out.println("F -> N f");
    		return f_numeral() && f_factorF();
    	}
    	
    	// Syntax error
		this.error = String.format(
    			"Error while parsing '%s' at position %s: Expected a character of the following set '%s'. Actual character is '%s'.", 
    			this.expression, this.currentToken.POSITION, FirstFollow.F_1, 
    			this.currentToken.VALUE
    		);
    	return false;
    }
    
    /**
     * factorF ::= "+" numeral | "-" numeral | epsilon
     * @return
     */
    private boolean f_factorF() {
    	// factorF ::= "+" numeral
    	if (this.currentToken.isFirstFollowOf(FirstFollow.F_2)) {
        	//System.out.println("f -> + N");
    		return f_tPlus() && f_numeral();
    	}
    	
    	// factorF ::= "-" numeral
    	if (this.currentToken.isFirstFollowOf(FirstFollow.F_3)) {
        	//System.out.println("f -> - N");
    		return f_tMinus() && f_numeral();
    	}
    	
    	// factorF ::= epsilon
    	if (this.currentToken.isFirstFollowOf(FirstFollow.F_4)) {
        	//System.out.println("f -> .");
    		return true;
    	}
    	
    	// Syntax error
		this.error = String.format(
    			"Error while parsing '%s' at position %s: Expected a character of the following set '%s%s%s'. Actual character is '%s'.", 
    			this.expression, this.currentToken.POSITION, 
    			FirstFollow.F_2, FirstFollow.F_3, FirstFollow.F_4,
    			this.currentToken.VALUE
    		);
    	return false;
    }
    
    /**
     * numeral ::= "(" expression ")" | number
     * @return
     */
    private boolean f_numeral() {
    	// numeral ::= "(" expression ")"
    	if (this.currentToken.isFirstFollowOf(FirstFollow.N_1)) {
        	//System.out.println("N -> (E)");
    		return f_tLeftParenthesis() && f_expression() && f_tRightParenthesis();
    	}
    	
    	// numeral ::= number
    	if (this.currentToken.isFirstFollowOf(FirstFollow.N_2)) {
        	//System.out.println("N -> i");
    		return f_number();
    	}
    	
    	// Syntax error
		this.error = String.format(
    			"Error while parsing '%s' at position %s: Expected a character of the following set '%s%s'. Actual character is '%s'.", 
    			this.expression, this.currentToken.POSITION, FirstFollow.N_1, FirstFollow.N_2,
    			this.currentToken.VALUE
    		);
    	return false;
    }
    
    /**
     * ::=* number
     * @return
     */
    private boolean f_number() {    	
    	if (this.currentToken.ID == TokenID.NUMBER) {
    		this.currentToken = this.scanner.getToken();
    		return true;
    	} else {
    		// Syntax error
    		this.error = String.format(
    				"Error while parsing '%s' at position %s: Expected a number. Actual character is '%s'.", 
        			this.expression, this.currentToken.POSITION, this.currentToken.VALUE
        		);
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
    	if (this.currentToken.ID == TokenID.END_OF_EXPRESSION) {
    		return true;
    	} else {
    		// Syntax error
    		this.error = String.format(
    				"Error while parsing '%s' at position %s: Expected the end of expression (#). Actual character is '%s'.", 
        			this.expression, this.currentToken.POSITION, this.currentToken.VALUE
        		);
    		return false;
    	}
    }
    
    /**
     * ::=* "+"
     * @return
     */
    private boolean f_tPlus() {
    	if (this.currentToken.ID == TokenID.PLUS) {
    		this.currentToken = this.scanner.getToken();
    		return true;
    	} else {
    		// Syntax error
    		this.error = String.format(
    				"Error while parsing '%s' at position %s: Expected '+'. Actual character is '%s'.", 
        			this.expression, this.currentToken.POSITION, this.currentToken.VALUE
        		);
    		return false;
    	}
    }
    
    /**
     * ::=* "-"
     * @return
     */
    private boolean f_tMinus() {
    	if (this.currentToken.ID == TokenID.MINUS) {
    		this.currentToken = this.scanner.getToken();
    		return true;
    	} else {
    		// Syntax error
    		this.error = String.format(
    				"Error while parsing '%s' at position %s: Expected '-'. Actual character is '%s'.", 
        			this.expression, this.currentToken.POSITION, this.currentToken.VALUE
        		);
    		return false;
    	}
    }
    
    /**
     * ::=* "*"
     * @return
     */
    private boolean f_tMultiply() {
    	if (this.currentToken.ID == TokenID.MULTIPLY) {
    		this.currentToken = this.scanner.getToken();
    		return true;
    	} else {
    		// Syntax error
    		this.error = String.format(
    				"Error while parsing '%s' at position %s: Expected '*'. Actual character is '%s'.", 
        			this.expression, this.currentToken.POSITION, this.currentToken.VALUE
        		);
    		return false;
    	}
    }
    
    /**
     * ::=* "/"
     * @return
     */
    private boolean f_tDivide() {
    	if (this.currentToken.ID == TokenID.DIVIDE) {
    		this.currentToken = this.scanner.getToken();
    		return true;
    	} else {
    		// Syntax error
    		this.error = String.format(
    				"Error while parsing '%s' at position %s: Expected '/'. Actual character is '%s'.", 
        			this.expression, this.currentToken.POSITION, this.currentToken.VALUE
        		);
    		return false;
    	}
    }
	
    /**
     * ::=* "%"
     * @return
     */
    private boolean f_tModulo() {
    	if (this.currentToken.ID == TokenID.MODULO) {
    		this.currentToken = this.scanner.getToken();
    		return true;
    	} else {
    		// Syntax error
    		this.error = String.format(
    				"Error while parsing '%s' at position %s: Expected '%'. Actual character is '%s'.", 
        			this.expression, this.currentToken.POSITION, this.currentToken.VALUE
        		);
    		return false;
    	}
    }
    
    /**
     * ::=* "^"
     * @return
     */
    private boolean f_tPower() {
    	if (this.currentToken.ID == TokenID.POWER) {
    		this.currentToken = this.scanner.getToken();
   		return true;
    	} else {
    		// Syntax error
    		this.error = String.format(
    				"Error while parsing '%s' at position %s: Expected '^'. Actual character is '%s'.", 
        			this.expression, this.currentToken.POSITION, this.currentToken.VALUE
        		);
    		return false;
    	}
    }
    
    /**
     * ::=* "("
     * @return
     */
    private boolean f_tLeftParenthesis() {
    	if (this.currentToken.ID == TokenID.LEFT_PARENTHESIS) {
    		this.currentToken = this.scanner.getToken();
  		return true;
    	} else {
    		// Syntax error
    		this.error = String.format(
    				"Error while parsing '%s' at position %s: Expected '('. Actual character is '%s'.", 
        			this.expression, this.currentToken.POSITION, this.currentToken.VALUE
        		);
    		return false;
    	}
    }
    
    /**
     * ::=* ")"
     * @return
     */
    private boolean f_tRightParenthesis() {
    	if (this.currentToken.ID == TokenID.RIGHT_PARENTHESIS) {
    		this.currentToken = this.scanner.getToken();
    		return true;
    	} else {
    		// Syntax error
        	this.error = String.format(
        			"Error while parsing '%s' at position %s: Expected ')'. Actual character is '%s'.", 
        			this.expression, this.currentToken.POSITION, this.currentToken.VALUE
        		);
    		return false;
    	}
    }

	/**
	 * @return
	 */
	public String getError() {
		return this.error;
	}
    
    
}


