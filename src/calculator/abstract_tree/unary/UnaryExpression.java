package calculator.abstract_tree.unary;

import calculator.abstract_tree.ExpressionTree;
import calculator.abstract_tree.Operator;

/**
 * 
 */
public class UnaryExpression implements ExpressionTree {
	
	private Operator operator;
	protected ExpressionTree expr;
	
	/**
	 * 
	 * @param leftExpr
	 * @param operator
	 * @param rightExpr
	 */
	public UnaryExpression(Operator operator, ExpressionTree expr) {
		this.operator = operator;
		this.expr = expr;
	}

	/* (non-Javadoc)
	 * @see calculator.structur.ExpressionTree#eval()
	 */
	@Override
	public double eval() throws Exception {
		switch (this.operator) {
		//case PLUS: 	return + this.expr.eval();
		case MINUS: return - this.expr.eval();
		default: throw new Exception("Invalid operator");
		}
	}

	/* (non-Javadoc)
	 * @see calculator.structur.ExpressionTree#print()
	 */
	@Override
	public String print() {
		return String.format("(%s %s)", 
				this.operator.toString(),
				this.expr.print());
	}

	
}
