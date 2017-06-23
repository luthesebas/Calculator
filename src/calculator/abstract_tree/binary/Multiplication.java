package calculator.abstract_tree.binary;

import calculator.abstract_tree.ExpressionTree;
import calculator.abstract_tree.Operator;

/**
 * 
 */
public class Multiplication extends BinaryExpression {

	/**
	 * 
	 * @param leftExpr
	 * @param rightExpr
	 */
	public Multiplication(ExpressionTree leftExpr, ExpressionTree rightExpr) {
		super(leftExpr, Operator.MULTI, rightExpr);
	}
	
	/* (non-Javadoc)
	 * @see calculator.structur.BinaryExpression#eval()
	 */
	@Override
	public double eval() throws Exception {
		return this.leftExpr.eval() * this.rightExpr.eval();
	}

}
