package calculator.abstract_tree.binary;

import calculator.abstract_tree.ExpressionTree;
import calculator.abstract_tree.Operator;

/**
 * 
 */
public class Modulo extends BinaryExpression {

	/**
	 * 
	 * @param leftExpr
	 * @param rightExpr
	 */
	public Modulo(ExpressionTree leftExpr, ExpressionTree rightExpr) {
		super(leftExpr, Operator.MODULO, rightExpr);
	}

	/* (non-Javadoc)
	 * @see calculator.structur.BinaryExpression#eval()
	 */
	@Override
	public double eval() throws Exception {
		return this.leftExpr.eval() % this.rightExpr.eval();
	}
	
}
