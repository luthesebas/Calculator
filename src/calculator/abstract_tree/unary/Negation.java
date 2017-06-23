package calculator.abstract_tree.unary;

import calculator.abstract_tree.ExpressionTree;
import calculator.abstract_tree.Operator;

/**
 * 
 */
public class Negation extends UnaryExpression {

	/**
	 * @param operator
	 * @param expr
	 */
	public Negation(ExpressionTree expr) {
		super(Operator.MINUS, expr);
	}
	
	/* (non-Javadoc)
	 * @see calculator.structur.unary.UnaryExpression#eval()
	 */
	@Override
	public double eval() throws Exception {
		return - this.expr.eval();
	}

}
