package calculator.abstract_tree.binary;

import calculator.abstract_tree.ExpressionTree;
import calculator.abstract_tree.Operator;

/**
 * 
 */
public class Division extends BinaryExpression {

	/**
	 * 
	 * @param leftExpr
	 * @param rightExpr
	 */
	public Division(ExpressionTree leftExpr, ExpressionTree rightExpr) {
		super(leftExpr, Operator.PLUS, rightExpr);
	}
	
	/* (non-Javadoc)
	 * @see calculator.structur.BinaryExpression#eval()
	 */
	@Override
	public double eval() throws Exception {
		return this.leftExpr.eval() / this.rightExpr.eval();
	}
	
}
