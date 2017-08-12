/**
 * 
 */
package calculator.parser;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 */
public class ParserTest {

	@Test
	public void test() {
		Parser parser = new Parser();
		
		assertTrue(parser.parse("1+2-3*4/5%2^6"));		
		assertTrue(parser.parse("2^(10)"));
		assertTrue(parser.parse("(2+5)%(5-1)"));
		assertTrue(parser.parse("4*5/(1+3)"));

		assertFalse(parser.parse("4*5/(1+3"));
		System.out.println(parser.getError());
		assertFalse(parser.parse("4*5/(1+3)+"));
		System.out.println(parser.getError());
	}

}
