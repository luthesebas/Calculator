package calculator.abstract_tree;

/**
 * 
 */
public enum Operator {

	PLUS('+'),
	MINUS('-'),
	MULTI('*'),
	DIVIDE('/'),
	MODULO('%'),
	POWER('^'),
	
	;
	
	private char operator;
	
	Operator(char operator) {
		this.operator = operator;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return Character.toString(this.operator);
	}
	
}
