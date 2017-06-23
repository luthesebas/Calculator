package calculator.abstract_tree.binary;

import calculator.abstract_tree.ExpressionTree;
import calculator.abstract_tree.Operator;

/**
 * 
 */
public class Power extends BinaryExpression {

	/**
	 * 
	 * @param leftExpr
	 * @param rightExpr
	 */
	public Power(ExpressionTree leftExpr, ExpressionTree rightExpr) {
		super(leftExpr, Operator.POWER, rightExpr);
	}

	/* (non-Javadoc)
	 * @see calculator.structur.BinaryExpression#eval()
	 */
	@Override
	public double eval() throws Exception {
		return Math.pow(this.leftExpr.eval(), this.rightExpr.eval());
	}
	
}
