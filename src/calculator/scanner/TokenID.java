/**
 * 
 */
package calculator.scanner;

/**
 * 
 */
public enum TokenID {
	INVALID,
	END_OF_EXPRESSION,
	  
	NUMBER,
	  
	PLUS,
	MINUS,
	MULTIPLY,
	DIVIDE,
	MODULO,
	POWER,
	  
	LEFT_PARENTHESIS,
	RIGHT_PARENTHESIS,
	
	; // end of enumeration

	/*
	// Value of the token -> number
	private double value;
	
	// Message for invalid input
	private String error;
	
	public void setValue(double value) {
		if (this.compareTo(TokenID.NUMBER) == 0) {
			this.value = value;
		} else {
			this.value = Double.NaN;
		}
	}

	public double getValue() {
		return this.value;
	}
	
	public void setError(String msg) {
		if (this.compareTo(TokenID.INVALID) == 0) {
			this.error = msg;
		} else {
			this.error = "";
		}
	}

	public String getError() {
		return this.error;
	}
	*/
}
