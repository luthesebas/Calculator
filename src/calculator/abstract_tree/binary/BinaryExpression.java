package calculator.abstract_tree.binary;

import calculator.abstract_tree.ExpressionTree;
import calculator.abstract_tree.Operator;

/**
 * 
 */
public class BinaryExpression implements ExpressionTree {

	private Operator operator;
	protected ExpressionTree leftExpr;
	protected ExpressionTree rightExpr;
	
	/**
	 * 
	 * @param leftExpr
	 * @param operator
	 * @param rightExpr
	 */
	public BinaryExpression(ExpressionTree leftExpr, Operator operator, ExpressionTree rightExpr) {
		this.operator = operator;
		this.leftExpr = leftExpr;
		this.rightExpr = rightExpr;
	}

	/* (non-Javadoc)
	 * @see calculator.structur.ExpressionTree#eval()
	 */
	@Override
	public double eval() throws Exception {
		switch (this.operator) {
		case PLUS: 		return this.leftExpr.eval() + this.rightExpr.eval();
		case MINUS: 	return this.leftExpr.eval() - this.rightExpr.eval();
		case MULTI: 	return this.leftExpr.eval() * this.rightExpr.eval();
		case DIVIDE: 	return this.leftExpr.eval() / this.rightExpr.eval();
		case MODULO: 	return this.leftExpr.eval() % this.rightExpr.eval();
		default: throw new Exception("Invalid operator");
		}
	}

	/* (non-Javadoc)
	 * @see calculator.structur.ExpressionTree#print()
	 */
	@Override
	public String print() {
		return String.format("(%s %s %s)", 
				this.leftExpr.print(), 
				this.operator.toString(), 
				this.rightExpr.print());
	}
	
	
	
}
