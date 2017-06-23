package calculator.abstract_tree;

/**
 * 
 */
public class Value implements ExpressionTree {

	private double value;
	
	/**
	 * 
	 * @param value
	 */
	public Value(double value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see calculator.structur.ExpressionTree#eval()
	 */
	@Override
	public double eval() {
		return this.value;
	}

	/* (non-Javadoc)
	 * @see calculator.structur.ExpressionTree#print()
	 */
	@Override
	public String print() {
		return Double.toString(this.value);
	}

}
