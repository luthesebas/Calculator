/**
 * 
 */
package calculator.tokenizer;

/**
 * 
 */
public enum TokenID {
	
	INVALID(null),
	END_OF_EXPRESSION("#"),
	  
	NUMBER("i"),
	  
	PLUS("+"),
	MINUS("-"),
	MULTIPLY("*"),
	DIVIDE("/"),
	MODULO("%"),
	POWER("^"),
	  
	LEFT_PARENTHESIS("("),
	RIGHT_PARENTHESIS(")"),
	
	;

	public String VALUE;
	
	/**
	 * 
	 * @param value
	 */
	TokenID(String value) {
		this.VALUE = value;
	}	

}
