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
		RecursiveDescentParser parser = new RecursiveDescentParser("2+(5*5)-(1*(1+0))");
		double sol = parser.parse();
		
		assertEquals(26, sol, 0.01);
		
		parser = new RecursiveDescentParser("-5*1+0/1-0");
		sol = parser.parse();
		
		assertEquals(-5, sol, 0.01);
		
		parser = new RecursiveDescentParser("5*1+cos(0)");
		sol = parser.parse();
		
		assertEquals(6, sol, 0.01);
		
		parser = new RecursiveDescentParser("2^2^2^2");
		sol = parser.parse();
		
		assertEquals(65536, sol, 0.01);
		
		parser = new RecursiveDescentParser("5.11.+5");
		sol = parser.parse();
		
		assertEquals(Double.NaN, sol, 0.01);
	}

}
