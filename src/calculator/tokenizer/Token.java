/**
 * 
 */
package calculator.tokenizer;

/**
 * 
 */
public class Token {
		
	public final TokenID ID;
	public final int POSITION;
	public final String VALUE;
	
	/**
	 * 
	 * @param id
	 * @param position
	 */
	public Token(final TokenID id, final String value, final int position) {
		this.ID = id;
		this.VALUE = value;
		this.POSITION = position;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Token[id=%s, value=%s, pos=%s]", this.ID.ordinal(), this.VALUE, this.POSITION);
	}

	/**
	 * @param set
	 * @return
	 */
	public boolean isFirstFollowOf(String set) {
		return set.contains(this.ID.VALUE);
	}

}
