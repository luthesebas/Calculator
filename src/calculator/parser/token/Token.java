/**
 * 
 */
package calculator.parser.token;

/**
 * 
 */
public class Token {
		
	public final TokenID id;
	public final int position;
	public final String name;
	
	/**
	 * 
	 * @param id
	 * @param position
	 */
	public Token(final int position, final TokenID id, final String name) {
		this.id = id;
		this.position = position;
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Token[id=%s, name=%s, pos=%s]", this.id.ordinal(), this.name, this.position);
	}

	/**
	 * @param set
	 * @return
	 */
	public static boolean isFirstFollow(Token token, String set) {
		switch(token.id) {
			case PLUS: 		return set.contains("+");
			case MINUS: 	return set.contains("-");
			case MULTIPLY: 	return set.contains("*");
			case DIVIDE: 	return set.contains("/");
			case MODULO: 	return set.contains("%");
			case POWER: 	return set.contains("^");
			
			case LEFT_PARENTHESIS: 	return set.contains("(");
			case RIGHT_PARENTHESIS: return set.contains(")");
			
			case NUMBER: return set.contains("i");		
			
			case END_OF_EXPRESSION: return set.contains("#");
			
			case INVALID: return false;
			
			default: return false;
		}
	}

}
