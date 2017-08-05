/**
 * 
 */
package calculator.parser.token;

/**
 * 
 */
public class Number extends Token {

	private double value;
	
	/**
	 * @param id
	 * @param position
	 */
	public Number(int position, String value) {
		super(position, TokenID.NUMBER, value);
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
		return String.format("Token[id=%s, name=%s, pos=%s, value=%s]", this.id.ordinal(), this.id, this.position, this.value);
	}

}
