package calculator.abstract_tree;

/**
 *
 */
public interface ExpressionTree {
	
	/**
	 * 
	 * @return
	 * @throws Exception 
	 */
	public abstract double eval() throws Exception;
	
	/**
	 * 
	 * @return
	 */
	public abstract String print();
	
}
