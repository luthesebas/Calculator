/**
 * 
 */
package calculator.tokenizer;

/**
 * 
 */
public class Number extends Token {

	private double value;
	
	/**
	 * @param ID
	 * @param position
	 */
	public Number(String value, int position) {
		super(TokenID.NUMBER, value, position);
		try {
			this.value = Double.parseDouble(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Token[id=%s, name=%s, pos=%s, value=%s]", this.ID.ordinal(), this.ID, this.POSITION, this.value);
	}

}
