package calculator.abstract_tree;

import static org.junit.Assert.*;

import org.junit.Test;

import calculator.abstract_tree.binary.*;
import calculator.abstract_tree.unary.*;

/**
 * 
 */
public class ExpressionTreeTest {

	@Test
	public void test() {
		
		double value_1 = 2;
		double value_2 = 4;
		double value_3 = 3.557_799_881_1;
		double value_4 = 1E-11;
		double expected_solution = ((-value_1) + value_2) * value_3 + value_4;
		//double expected_solution = 7.115_599_762_21;
		double accuracy = 1E-12;
		
		// Expected
		String expected_expression = String.format("((- %s) + %s) * %s + %s", 
				value_1, value_2, value_3, value_4);
		
		// Set up expression tree as expected
		ExpressionTree actual_expression = null;
		
		Negation node_1 = new Negation(new Value(value_1));						// node_1 	:= (-value_1)
		Addition node_2 = new Addition(node_1, new Value(value_2));				// node_2 	:= node_1 + value_2
		Multiplication node_3 = new Multiplication(node_2, new Value(value_3)); // node_3 	:= node_2 * value_3
		actual_expression = new Addition(node_3, new Value(value_4));			// expr		:= node_3 + value_4
		
		//============================
		// RUN CALCULATION
		//============================
		
		String expression_tree = "";
		double actual_solution = 0;
		
		try {
			expression_tree = actual_expression.print();
			actual_solution = actual_expression.eval();
			
			System.out.println("Expected tree: " + expected_expression);
			System.out.println("Internal tree: " + expression_tree);

			System.out.println("Expected solution: " + expected_solution);
			System.out.println("Actual solution:   " + actual_solution);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(expected_solution, actual_solution, accuracy);
	}

}
