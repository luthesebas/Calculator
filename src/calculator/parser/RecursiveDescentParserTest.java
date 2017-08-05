/**
 * 
 */
package calculator.parser;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 */
public class RecursiveDescentParserTest {

	@Test
	public void test() {
		RecursiveDescentParser parser;
		
		parser = new RecursiveDescentParser("2+(5*5)-(1*(1+0))");
		double solution = parser.parse();		
		assertEquals(26, solution, 0.01);
		
		solution = parser.parse("-5*1+0/1-0");	
		assertEquals(-5, solution, 0.01);
		
		parser.setExpression("5*1+cos(0)");
		solution = parser.parse();
		assertEquals(6, solution, 0.01);
		
		solution = parser.parse("2^2^2^2");
		assertEquals(65536, solution, 0.01);
		
		solution = parser.parse("5.11+5-5.5.");
		assertEquals(Double.NaN, solution, 0.01);
		
	}

}
